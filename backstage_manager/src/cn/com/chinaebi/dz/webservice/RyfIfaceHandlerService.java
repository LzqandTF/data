package cn.com.chinaebi.dz.webservice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.MerBalance;
import cn.com.chinaebi.dz.object.util.DataStatus;
import cn.com.chinaebi.dz.service.TkHandleService;
import cn.com.chinaebi.dz.util.StringPingJie;

/**
 * 融易付接口处理操作
 * @author wufei
 *
 */
public class RyfIfaceHandlerService extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(RyfIfaceHandlerService.class);
	
	private static cn.com.chinaebi.dz.object.dao.iface.RefundLogDAO refundLogDAO = cn.com.chinaebi.dz.object.dao.RefundLogDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.MerBalanceDAO merBalanceDAO = cn.com.chinaebi.dz.object.dao.MerBalanceDAO.getInstance();
	
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	/**
	 * 处理流程
	 * hand_type = 1
	 *  1:调用RefundLogDAO.getRytRefundLog方法拉取融易付refund_log表交易数据,并且调用PoundageCalculate.getTradeMerFee(amount, gate,mid)逐笔计算商户退款手续费
	 *  2:修改交易数据状态为经办
	 *  3:该笔退款交易进入a.商户余额、b.商户资金流水、c.T+1统计表
	 *  	a.商户余额:查询mer_balance表该商户是否有数据
	 *  		有:用商户余额-退款经办交易金额
	 *          无:保存插入mer_balance表中-余额显示为"负"
	 *      b.该笔退款经办数据插入到mer_fund_stance表中,无需查询数据是否存在
	 *      c.T+1统计表：根据商户号,经办日期,渠道号查询该商户是否有数据
	 *          有:退款总金额(refund_amount)修改成该笔退款经办金额、退款总笔数(refund_count)+1
	 *          无:该笔交易插入到T+1统计表中
	 *        注意：修改data_status = 1(1-退款经办)
	 * hand_type = 2
	 * 	1:根据主键id修改refund_log表数据为审核状态
	 *  2:修改该笔交易在T+1统计表中data_status = 2 (2-退款审核)
	 *  
	 * hand_type = 3
	 *  1:根据商户号查询mer_balance(商户余额表),如果数据存在返回余额、如果不存在返回"0"
	 * 
	 * 
	 * 回执内容(,hand_status = 0 表示能识别hand_type,hand_status = 1 表示无法识别)
	 * hand_type = 1、2、4
	 * 	response.getWriter().print("iface_result = success,hand_status = 0");
	 *  response.getWriter().print("iface_result = fail,hand_status = 0");
	 * hand_type = 3
	 *  response.getWriter().print("mid = 商户号,merBalance = 余额,hand_status = 0");
	 * hand_type != 1、2、3、4
	 *  response.getWriter().print("iface_result = fail,hand_status = 1");
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		String hand_type = request.getParameter("hand_type");//1:退款交易经办、2:退款交易审核、3:商户余额查询 4：系统自动退款
		String id = request.getParameter("id");//refund_log 主键ID
		String mid = request.getParameter("mid");//商户号
		log.info("接口参数 " + "hand_type:" + hand_type + " id:" + id + " mid:" + mid);
		
		Integer type = 0;
		
		// 返回xml内容
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		sb.append("<trade_result>\n");
		try {
			type = Integer.valueOf(hand_type);
		} catch (Exception e) {
			log.error("Integer.valueOf(hand_type) 转换值为 : "+hand_type);
		}
		try {
			MerBalance merBalance = null;
			if (type == 1) {//退款交易经办
				log.info("融易付系统开始调用对账系统接口做线上退款交易经办操作...");
				//  汇总退款交易数据
				Object object = refundLogDAO.getRytRefundLog(id);
				if (object != null) {
					int effectNum = refundLogDAO.updateRytRefundLogDataStatus(id, DataStatus.ryt_tk_jb);
					if (effectNum > 0) {
						boolean flag = TkHandleService.ryfTkDataHandle(object, type);
						if (flag) {
							sb.append("  <iface_result>success</iface_result>\n");
							sb.append("  <hand_status>0</hand_status>\n");
							sb.append("</trade_result>");
							response.getWriter().print(sb.toString());
							log.info("\n回执融易付平台xml信息如下:\n"+sb.toString());
						} else {
							log.error(StringPingJie.getInstance().getStringPingJie("type = ",type,"主键ID = ",id,"商户号 = ",mid,"退款交易经办处理 - merBasicDAO.queryMerInfoByMerCode(mid)数据为空"));
							sb.append("  <iface_result>fail</iface_result>\n");
							sb.append("  <hand_status>0</hand_status>\n");
							sb.append("</trade_result>");
							response.getWriter().print(sb.toString());
							log.info("\n回执融易付平台xml信息如下:\n"+sb.toString());
						}
					}
				}else{
					log.error(StringPingJie.getInstance().getStringPingJie("type = ",type,"主键ID = ",id,"退款交易经办处理 - 拉取融易付平台refundLog表数据为空"));
					sb.append("  <iface_result>fail</iface_result>\n");
					sb.append("  <hand_status>0</hand_status>\n");
					sb.append("</trade_result>");
					response.getWriter().print(sb.toString());
					log.info("\n回执融易付平台xml信息如下:\n"+sb.toString());
				}
			} else if(type == 2){//退款交易审核
				log.info("融易付系统开始调用对账系统接口做线上退款交易审核操作...");
				Object object = refundLogDAO.queryDataById(id);
				if (object != null) {
					int effectNum = refundLogDAO.updateRytRefundLogDataStatus(id, DataStatus.ryt_tk_sh);
					if (effectNum > 0) {
						boolean flag = TkHandleService.ryfTkDataHandle(object, type);
						if (flag) {
							sb.append("  <iface_result>success</iface_result>\n");
							sb.append("  <hand_status>0</hand_status>\n");
							sb.append("</trade_result>");
							response.getWriter().print(sb.toString());
							log.info("\n回执融易付平台xml信息如下:\n"+sb.toString());
						} else {
							log.error(StringPingJie.getInstance().getStringPingJie("type = ",type,"主键ID = ",id,"退款交易审核处理 - 查询退款交易数据为空"));
							sb.append("  <iface_result>fail</iface_result>\n");
							sb.append("  <hand_status>0</hand_status>\n");
							sb.append("</trade_result>");
							response.getWriter().print(sb.toString());
							log.info("\n回执融易付平台xml信息如下:\n"+sb.toString());
						}
					}
				}else{
					log.error(StringPingJie.getInstance().getStringPingJie("type = ",type,"主键ID = ",id,"退款交易审核处理 - 查询退款交易数据为空"));
					sb.append("  <iface_result>fail</iface_result>\n");
					sb.append("  <hand_status>0</hand_status>\n");
					sb.append("</trade_result>");
					response.getWriter().print(sb.toString());
					log.info("\n回执融易付平台xml信息如下:\n"+sb.toString());
				}
			}else if(type == 3){//商户余额查询
				log.info("融易付系统开始调用对账系统接口做商户余额查询操作...");
				merBalance = merBalanceDAO.findMerBalance(mid);
				if (merBalance != null) {
					sb.append("  <mid>");
					sb.append(mid);
					sb.append("</mid>\n");
					sb.append("  <merBalance>");
					sb.append(merBalance.getMerBalance());
					sb.append("</merBalance>\n");
					sb.append("  <hand_status>0</hand_status>\n");
					sb.append("</trade_result>");
					response.getWriter().print(sb.toString());
					log.info("\n回执融易付平台xml信息如下:\n"+sb.toString());
				} else {
					sb.append("  <mid>");
					sb.append(mid);
					sb.append("</mid>\n");
					sb.append("  <merBalance>0.00</merBalance>\n");
					sb.append("  <hand_status>0</hand_status>\n");
					sb.append("</trade_result>");
					response.getWriter().print(sb.toString());
					log.info("\n回执融易付平台xml信息如下:\n"+sb.toString());
				}
			}else if (type == 4) {//自动退款
				//  汇总退款交易数据
				log.info("融易付系统开始调用对账系统接口做系统自动退款操作...");
				Object object = refundLogDAO.getRytRefundLog(id);
				if (object != null) {
					int effectNum = refundLogDAO.updateRytRefundLogDataStatus(id, DataStatus.ryt_tk_sh);
					if (effectNum > 0) {
						boolean flag = TkHandleService.ryfTkDataHandle(object, 1);
						flag = TkHandleService.ryfTkDataHandle(object, 2);
						if (flag) {
							sb.append("  <iface_result>success</iface_result>\n");
							sb.append("  <hand_status>0</hand_status>\n");
							sb.append("</trade_result>");
					    	response.getWriter().print(sb.toString());
					    	log.info("\n回执融易付平台xml信息如下:\n"+sb.toString());
						} else {
							log.error(StringPingJie.getInstance().getStringPingJie("type = ",type,"主键ID = ",id,"商户号 = ",mid,"自动退款处理 - merBasicDAO.queryMerInfoByMerCode(mid)数据为空"));
							sb.append("  <iface_result>fail</iface_result>\n");
							sb.append("  <hand_status>0</hand_status>\n");
							sb.append("</trade_result>");
					    	response.getWriter().print(sb.toString());
					    	log.info("\n回执融易付平台xml信息如下:\n"+sb.toString());
						}
					}
				}else{
					log.error(StringPingJie.getInstance().getStringPingJie("type = ",type,"主键ID = ",id,"自动退款处理 - 查询退款交易数据为空"));
					sb.append("  <iface_result>fail</iface_result>\n");
					sb.append("  <hand_status>0</hand_status>\n");
					sb.append("</trade_result>");
			    	response.getWriter().print(sb.toString());
			    	log.info("\n回执融易付平台xml信息如下:\n"+sb.toString());
				}
			}else{
				log.error(type +": 值不符合规则,必须为 1、2、3、4");
				sb.append("  <iface_result>fail</iface_result>\n");
				sb.append("  <hand_status>1</hand_status>\n");
				sb.append("</trade_result>");
		    	response.getWriter().print(sb.toString());
		    	log.info("\n回执融易付平台xml信息如下:\n"+sb.toString());
			}
		} catch (Exception e) {
			log.error("融易付退款经办、审核、余额查询服务出现异常：" + e.getMessage());
			sb.append("  <iface_result>fail</iface_result>\n");
			sb.append("  <hand_status>0</hand_status>\n");
			sb.append("</trade_result>");
	    	response.getWriter().print(sb.toString());
	    	log.info("\n回执融易付平台xml信息如下:\n"+sb.toString());
		}
	}
}
