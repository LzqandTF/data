package cn.com.chinaebi.dz.webservice;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import cn.com.chinaebi.dz.object.dao.iface.ErrorTkLstDAO;
import cn.com.chinaebi.dz.object.util.Base64;
import cn.com.chinaebi.dz.util.DYDataUtil;
import cn.com.chinaebi.dz.object.util.HmacMD5;
import cn.com.chinaebi.dz.object.util.HttpUtil;

/**
 * 备份金接口数据同步
 * @author zhu.hongliang
 */
public class BfjDataSynService {
	
	private static final Log log = LogFactory.getLog(BfjDataSynService.class);
	private static ErrorTkLstDAO errorTkLstDAO = cn.com.chinaebi.dz.object.dao.ErrorTkLstDAO.getInstance();
	
	private static BfjDataSynService bfjDataSynService = null;
	/**
	 * 定时任务执行调用
	 * @return
	 */
	public static BfjDataSynService getInstance(){
		if(bfjDataSynService == null){
			bfjDataSynService = new BfjDataSynService();
		}
		return bfjDataSynService;
	}
	
	/**
	 * 备付金同步方法
	 * @param bfj_flag : 0-删除定时任务
	 * @param bfj_key ：执行key
	 */
	@SuppressWarnings("unchecked")
	public void bfjDataSyn(int bfj_flag, String keyPath, String requestUrl){
		if(bfj_flag == 1){
			log.info("开始执行同步给备付金差错数据定时任务");
			List<Object> list = errorTkLstDAO.findErrorHandlingStatus();
			if(list != null && list.size() > 0){
				StringBuffer sb = new StringBuffer();
				for (Object object : list) {
					sb.setLength(0);
					Object[] objArr = (Object[])object;
					String tradeId = objArr[0].toString();
					Date tradeTime = (Date)objArr[1];
					String tradeAmount = objArr[2].toString();
					String deductSysId = objArr[3].toString();
					String handlingId = objArr[4].toString();
					sb.append("<?xml version='1.0' encoding='UTF-8'?>\n");
					sb.append("<MsgText>\n");
						sb.append("\t<data>\n");
							sb.append("\t\t<id>");
							sb.append(tradeId);
							sb.append("</id>\n");
							sb.append("\t\t<gid>");
							sb.append(deductSysId);
							sb.append("</gid>\n");
							sb.append("\t\t<date>");
							sb.append(DYDataUtil.getSimpleDateFormat(DYDataUtil.DATE_FORMAT_3).format(tradeTime));
							sb.append("</date>\n");
							sb.append("\t\t<refAmount>");
							sb.append(tradeAmount);
							sb.append("</refAmount>\n");
							sb.append("\t\t<flag>");
							sb.append(handlingId);
							sb.append("</flag>\n");
						sb.append("\t</data>\n");
					sb.append("</MsgText>");
					try {
						String mac = HmacMD5.encryptMAC("HmacMD5", sb.toString().getBytes(), Base64.decode("E/0VWzwEqUD1gC0F4dn4Y2JFT8QetPI7icPCVrnH1cs="));
						String xmlParams = mac + sb.toString();
						log.info("请求报文：" + xmlParams);
						String responseStr = HttpUtil.sendMessage(keyPath, requestUrl, xmlParams);
						if (StringUtils.isNotBlank(responseStr)) {
							log.info("回执信息：" + responseStr);
							String xmlString = responseStr.substring(responseStr.indexOf("<")+1, responseStr.length());
							xmlString = "<" + xmlString;
							Document doc = DocumentHelper.parseText(xmlString);
							String code = doc.getRootElement().element("code").getText();
							
							if ("0000".equals(code)) {
								log.info("报文返回成功，开始执行修改差错退款表数据操作");
								boolean flag = errorTkLstDAO.updateErrorTkLst(tradeId);
								if (flag) {
									log.info("修改差错退款表数据成功");
								}
							} else {
								 log.info("报文返回失败，不执行修改差错退款表数据操作");
							}
						}
					} catch (Exception e) {
						log.error(e);
					}
				} 
			} else {
				log.info("没有查询到需要同步给备付金系统的差错数据");
			}
		}else{
			log.info("bfj_flag != 1 不同步数据到备付金系统");
		}
	}
}
