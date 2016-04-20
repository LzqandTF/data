package cn.com.chinaebi.dz.service;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.Hlog;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.InstInfoPK;
import cn.com.chinaebi.dz.object.MerBasic;
import cn.com.chinaebi.dz.object.MerBilling;
import cn.com.chinaebi.dz.object.dao.iface.HlogDAO;
import cn.com.chinaebi.dz.object.dao.iface.RefundLogDAO;
import cn.com.chinaebi.dz.util.DYDataUtil;
import cn.com.chinaebi.dz.reload.Backstage;
import cn.com.chinaebi.dz.util.PoundageCalculate;
import cn.com.chinaebi.dz.util.StringUtils;

/**
 * 融易付商户后台退款接口处理类
 * @author zhu.hongliang
 *
 */
public class RytMerSysTkHandleService {
	
	private static RytMerSysTkHandleService rytMerSysTkHandleService = null;
	private static Log log = LogFactory.getLog(RytMerSysTkHandleService.class);
	private RefundLogDAO refundLogDAO = cn.com.chinaebi.dz.object.dao.RefundLogDAO.getInstance();
	private HlogDAO hlogDAO = cn.com.chinaebi.dz.object.dao.HlogDAO.getInstance();
	
	public static RytMerSysTkHandleService getInstance(){
		if(rytMerSysTkHandleService == null)
			rytMerSysTkHandleService = new RytMerSysTkHandleService();
		return rytMerSysTkHandleService;
	}
	
	/**
	 * 根据主键ID查询退款交易
	 * @param id : 主键ID
	 * @return Integer
	 */
	public Integer queryDataById(String id){
		Integer stat = refundLogDAO.getRefundLog(id);
		return stat;
	}
	
	/**
	 * 修改退款交易数据状态
	 * @param id : 主键ID
	 * @param obj : column,value,column,value
	 * @return boolean
	 */
	public boolean merTkUpdateIfaceHandle(String id,Object ... obj){
		boolean flag = false;
		try {
			flag = refundLogDAO.updateRytRefundLogColumn(id,obj);
		} catch (Exception e) {
			log.error(e);
		}
		return flag;
	}
	
	/**
	 * 根据订单号/流水号查询退款交易
	 * @param selectType : 0:流水号、1:订单号
	 * @param oid ：订单号 
	 * @param tseq ：流水号
	 * @return Object[]
     * returnObj[0] = response
	 * returnObj[1] = currency
	 * returnObj[2] = mid
	 * returnObj[3] = ori_tseq
	 * returnObj[4] = oriOid
	 * returnObj[5] = oriMdate
	 * returnObj[6] = amount
	 * returnObj[7] = mer_fee
	 * returnObj[8] = tkAmt
	 * returnObj[9] = refund_amt
	 * returnObj[10] = merBasic.getMerAbbreviation()
	 */
	public Object[] tkQuery(int selectType,Long mdate,String oid,Long tseq){
		String tableName = "hlog";
		Object[] obj = new Object[4];
		Object[] returnObj = new Object[11];
		try {
			switch(selectType){
			case 0:
				obj[0] = "mdate";
				obj[1] = mdate;
				obj[2] = "tseq";
				obj[3] = tseq;
				break;
			case 1:
				obj[0] = "mdate";
				obj[1] = mdate;
				obj[2] = "oid";
				obj[3] = oid;
				break;
			}
			Object[] oriObjArr = hlogDAO.queryOnlineTableData(tableName, obj);
			String response = oriObjArr[0].toString();
			if(StringUtils.equals(response, "00")){
				//currency,mid,tseq,oid,mdate,amount,mer_fee
				String currency = oriObjArr[0].toString();
				String mid = oriObjArr[1].toString();
				String ori_tseq = oriObjArr[2].toString();
				String oriOid = oriObjArr[3].toString();
				String oriMdate = oriObjArr[4].toString();
				Long amount = Long.valueOf(oriObjArr[5].toString());
				double mer_fee = Double.valueOf(oriObjArr[6].toString());
				Long tkAmt = refundLogDAO.queryOriTseqTkAmt(ori_tseq);//总申请退款金额
				Long refund_amt = 0l;//可退款申请金额
				if(amount != tkAmt && tkAmt < amount){
					refund_amt = amount - tkAmt;
				}
				returnObj[0] = response;
				returnObj[1] = currency;
				returnObj[2] = mid;
				returnObj[3] = ori_tseq;
				returnObj[4] = oriOid;
				returnObj[5] = oriMdate;
				returnObj[6] = amount;
				returnObj[7] = mer_fee;
				returnObj[8] = tkAmt;
				returnObj[9] = refund_amt;
				MerBasic merBasic = Backstage.getInstance().getMerBasic(mid);
				returnObj[10] = merBasic == null ? "无" : merBasic.getMerAbbreviation();
			}else if(StringUtils.equals(response, "001")){//没有找到原笔
				returnObj[0] = response;
			}
		} catch (Exception e) {
			log.error(e);
		}
		return returnObj;
	}
	
	/**
	 * 商户申请退款接口处理
	 * @param tseq ：原交易流水号
	 * @param mid ：商户号
	 * @param org_oid ：原交易订单号
	 * @param ref_amt ：退款金额
	 * @param mdate ：商户申请退款日期
	 * @param refund_reason ：商户申请退款原因
	 * @return String[]
	 */
	public synchronized String[] merReqIfaceHanle(String tseq,String mid,String org_oid,String ref_amt,String mdate,String refund_reason){
		String[] result = new String[4];
		result[0] = "001";
		result[2] = "";
		try {
			Hlog hlog = hlogDAO.get(Long.valueOf(tseq));
			if(hlog != null && StringUtils.equals(hlog.getMid(), mid)){
				if(hlog.getTstat().intValue() == 2){
					int xc_day = cn.com.chinaebi.dz.util.DYDataUtil.daysBetween(hlog.getSysDate(), mdate, DYDataUtil.DATE_FORMAT_3);
					if(xc_day > 365){
						result[1] = "退款日期超限,退款申请失败";
					}else{
						Long oriAmt = refundLogDAO.queryOriTseqTkAmt(tseq);
						Long refAmt = Long.valueOf(ref_amt);
						if(hlog.getAmount() < (oriAmt+refAmt)){
							result[1] = "退款金额大于原交易金额";
						}else{
							//退款手续费计算
							int mer_fee_int = 0;
							String oid = DYDataUtil.getSimpleDateFormat(DYDataUtil.DATE_FORMAT_2).format(new Date());
							InstInfo instInfo = Backstage.getInstance().getInstInfo(new InstInfoPK(hlog.getGid(),1));
							int refund_type = 0;
							//渠道联机退款标识判断
							if(instInfo.isWhetherApplyOnlineTk())
								refund_type = 1;
							
							MerBilling merBilling = Backstage.getInstance().getMerBilling(mid);
							if(merBilling != null){
								int refundFee = merBilling.getRefundFee();
								if(refundFee == 1){ //退还手续费
									Double mer_fee = PoundageCalculate.getTradeMerFee(refAmt.intValue(), hlog.getGate(), mid, hlog.getGid());
									mer_fee_int = PoundageCalculate.multiply_minute(mer_fee.toString(), "100").intValue(); //按分计算保存
								}
							}else{
								Double mer_fee = PoundageCalculate.getTradeMerFee(refAmt.intValue(), hlog.getGate(), mid, hlog.getGid());
								mer_fee_int = PoundageCalculate.multiply_minute(mer_fee.toString(), "100").intValue(); //按分计算保存
							}
							//数据库表操作(insert)
							Long id = refundLogDAO.addRefundLog(tseq, hlog.getAuthorType(), mdate, mid, oid, hlog.getMdate(), hlog.getOid(), 
									Long.valueOf(ref_amt), hlog.getSysDate(), hlog.getGate(), hlog.getCardNo(), null, 
									0, 0, 5, refund_reason,mer_fee_int, hlog.getBankFee(),
									hlog.getBankFee(), hlog.getAmount(), hlog.getBkSeq1(), hlog.getGid(), hlog.getPayAmt(), 
									hlog.getPreAmt(), hlog.getPreAmt1(), hlog.getMerPriv(), null, hlog.getSysTime(),refund_type);
							if(id != null){
								result[0] = "000";
								result[1] = "处理成功";
								result[2] = id+"";
								result[3] = mer_fee_int+"";
							}else{
								result[1] = "系统异常";
							}
						}
					}
				}else{
					result[1] = "失败交易不能申请退款";
				}
			}else{
				result[1] = "没有找到原笔交易数据";
			}
		} catch (Exception e) {
			log.error(e);
			result[1] = "系统异常";
		}
		log.info("result[0] = "+result[0]+",result[1] = "+result[1]+",result[2] = "+result[2]);
		return result;
	}
	
	
	/**
	 * 退款-查询原交易信息
	 * @return Object
	 */
	public Object queryOriTradeInfo(){
		Object[] obj = null;
		
		return obj;
	}
	
	
	public static void main(String[] args) throws Exception{
//		Double mer_f = PoundageCalculate.getTradeMerFee("2600", "AMT*0.006");
//		System.out.println("mer_f = "+mer_f);
//		Double mer_fee = (mer_f*100);
//		System.out.println(mer_fee.intValue());
		
		System.out.println(cn.com.chinaebi.dz.util.DYDataUtil.daysBetween(20150101, "20160101", DYDataUtil.DATE_FORMAT_3));
		
//		double mer_fees = 0.1d*100;
//		Double mer_fees = 0.10d;
//		int ss = PoundageCalculate.multiply_minute(mer_fees.toString(), "100").intValue();
//		System.out.println(ss);
	}
	
}
