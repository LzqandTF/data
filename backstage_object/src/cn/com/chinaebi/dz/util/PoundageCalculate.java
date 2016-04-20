package cn.com.chinaebi.dz.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.CardTransfer;
import cn.com.chinaebi.dz.object.InstInfoPK;
import cn.com.chinaebi.dz.object.InstMerRateConf;
import cn.com.chinaebi.dz.object.dao.FeeCalcModeDAO;
import cn.com.chinaebi.dz.object.dao.iface.CardTransferDAO;
import cn.com.chinaebi.dz.object.dao.iface.InstMerRateConfDAO;
import cn.com.chinaebi.dz.object.dao.iface.InstRateDAO;
import cn.com.chinaebi.dz.object.dao.iface.MccDiscountDAO;
import cn.com.chinaebi.dz.util.StringPingJie;

/**
 * 手续费计算
 * @author zhu.hongliang
 *
 */
public class PoundageCalculate {
	
	private static Log log = LogFactory.getLog(PoundageCalculate.class);
	
	private static final int two = 2;
	private static InstRateDAO instRateDAO;
	private static InstMerRateConfDAO instMerRateConfDAO;
	private static CardTransferDAO cardTransferDAO;
	private static MccDiscountDAO mccDiscountDAO;
	//存放渠道费率配置基本信息
	private static Map<InstInfoPK, Object[]> instRateMap = new HashMap<InstInfoPK, Object[]>();
	//存放渠道MCC渠道配置
	private static Map<InstInfoPK, Map<String, Boolean>> chanelMccRateConfMap = new HashMap<InstInfoPK, Map<String,Boolean>>();
	//存放渠道固定费率配置
	private static Map<String, List<InstMerRateConf>> instMerRateConfMap = new HashMap<String, List<InstMerRateConf>>();
	
	/**
	 * 线下计算商户手续费
	 * @param tradeAmount ：交易金额
	 * @param merCode ：商户号
	 * @param inst_id ：渠道ID
	 * @return Double
	 */
	public static Double getMerFee(String tradeAmount,String merCode,Integer gate){
		Double float_value = 0d;
		try {
//			String dyMerCode = SettleMerchantMatchTableDAO.getInstance().getSettleMerCode(merCode);
			String merPoundage = null;
//			if(dyMerCode != null){
//				merPoundage = FeeCalcModeDAO.getInstance().getMerFee(gate, dyMerCode, gate);
//			}else{
				merPoundage = FeeCalcModeDAO.getInstance().getMerFee(gate,merCode,gate);
//			}
			tradeAmount = tradeAmount.replace("-", "");
			if(StringUtils.isNotBlank(merPoundage)){
				merPoundage = merPoundage.toUpperCase();
				/**
				 * 专门计算银行手续费的格式
				 *	X：最小值，D：最大值，R:费率,I：第三位>= I 时候进一，
				 *	如：BKFEE(0.1,100,0.008,2)
				 *	表示该手续费按照0.008的费率计算，最小为0.1元，最大为100元，当小数点后第三位是>=2时进一
				 */
				if(merPoundage.startsWith("BKFEE")){
					merPoundage = getDataVlue(merPoundage);
					String[] merPoundageArr = merPoundage.split(",");
					//X
					Double merPoundageArr0 = Double.valueOf(merPoundageArr[0]);
					//D
					Double merPoundageArr1 = Double.valueOf(merPoundageArr[1]);
					int merPoundageArr3 = Integer.valueOf(merPoundageArr[3]);
					//AMT*R
					float_value = multiply(tradeAmount, merPoundageArr[2]);
					if(float_value < merPoundageArr0){
						float_value = merPoundageArr0;
					}else if(float_value > merPoundageArr1){
						float_value = merPoundageArr1;
					}
					float_value = roundFloat_roundingMode(String.valueOf(float_value), 2, merPoundageArr3).doubleValue();
				}
				/**
				 * (固定交易费率，无上下限)其中R代表费率
				 *	如费率为0.008
				 *	则计费公式为AMT*0.008
				 */
				else if(merPoundage.startsWith("AMT")){
					String[] merPoundageArr = merPoundage.split("\\*");
					float_value = multiply(tradeAmount, merPoundageArr[1]);
					float_value = Double.valueOf(roundString(float_value, 2));
				}
				/**
				 * (固定交易费率，有下限)其中X1代表下限，R代表费率
				 *	如费率为0.005，下限为0.2
				 *	则计费公式为MAX(0.2,AMT*0.005)
				 */
				else if(merPoundage.startsWith("MAX")){
					merPoundage = getDataVlue(merPoundage);
					merPoundage = getReplaceAll(merPoundage, "AMT\\*", "");
					String[] merPoundageArr = merPoundage.split(",");
					//下限
					Double merPoundageArr0 = Double.valueOf(merPoundageArr[0]);
					//AMT*R
					float_value = multiply(tradeAmount, merPoundageArr[1]);
					if(float_value < merPoundageArr0)
						float_value = merPoundageArr0;
					float_value = Double.valueOf(roundString(float_value, 2));
				}
				/**
				 * (固定交易费率，有上限)其中X1代表上限，R代表费率
				 *	如费率为0.005，上限为50
				 *	则计费公式为MIN(50,AMT*0.005)
				 */
				else if(merPoundage.startsWith("MIN")){
					merPoundage = getDataVlue(merPoundage);
					merPoundage = getReplaceAll(merPoundage, "AMT\\*", "");
					String[] merPoundageArr = merPoundage.split(",");
					//下限
					Double merPoundageArr0 = Double.valueOf(merPoundageArr[0]);
					//AMT*R
					float_value = multiply(tradeAmount, merPoundageArr[1]);
					if(float_value > merPoundageArr0)
						float_value = merPoundageArr0;
					float_value = Double.valueOf(roundString(float_value, 2));
				}
				/**
				 * (固定交易费率，有下限和上限)其中x1,x2分别代表下限和上限，R代表费率
				 *	如费率为0.006，下限为0.8，上限为500
				 *	则计费公式为MTM(0.8,500,AMT*0.006)
				 */
				else if(merPoundage.startsWith("MTM")){
					merPoundage = getDataVlue(merPoundage);
					merPoundage = getReplaceAll(merPoundage, "AMT\\*", "");
					String[] merPoundageArr = merPoundage.split(",");
					//下限
					Double merPoundageArr0 = Double.valueOf(merPoundageArr[0]);
					//上限
					Double merPoundageArr1 = Double.valueOf(merPoundageArr[1]);
					//AMT*R
					float_value = multiply(tradeAmount, merPoundageArr[2]);
					if(float_value > merPoundageArr1){
						float_value = merPoundageArr1;
					}else if(float_value < merPoundageArr0){
						float_value = merPoundageArr0;
					}
					float_value = Double.valueOf(roundString(float_value, 2));
				}
				/**
				 * (单笔固定费用)其中X代表单笔固定手续费
				 *	如单笔固定为2.5元
				 *	则计费公式为SGL(2.5)
				 */
				else if(merPoundage.startsWith("SGL")){
					merPoundage = getDataVlue(merPoundage);
					float_value = Double.valueOf(merPoundage);
				}
				/**
				 * (单笔浮动费率)其中x1,x2…..代表分界档，R1,R2,R3…..代表分界档内的费率。
				 *	其中不包含上分界档如: <500,费率为0.08,500-5000费率为0.06,5000-50000费率为0.05 ,50000-500000费率为0.03,
				 *	>=5000000,费率为0.01 则计费公式为：FLO(500,0.08,5000,0.06,50000,0.05,500000,0.03,0.01)
				 */
				else if(merPoundage.startsWith("FLO")){
					merPoundage = getDataVlue(merPoundage);
					String[] merPoundageArr = merPoundage.split(",");
					String merPoundageArr0 = merPoundageArr[0];
					String merPoundageArr1 = merPoundageArr[1];
					String merPoundageArr2 = merPoundageArr[2];
					String merPoundageArr3 = merPoundageArr[3];
					String merPoundageArr4 = merPoundageArr[4];
					String merPoundageArr5 = merPoundageArr[5];
					String merPoundageArr6 = merPoundageArr[6];
					String merPoundageArr7 = merPoundageArr[7];
					String merPoundageArr8 = merPoundageArr[8];
					Double amount = Double.valueOf(tradeAmount)/100;
					//R1 <500
					if(amount < Double.valueOf(merPoundageArr0)){
						float_value = multiply(tradeAmount, merPoundageArr1);
					}//R2 >=500 <5000
					else if(amount >= Double.valueOf(merPoundageArr0) && amount < Float.valueOf(merPoundageArr2)){
						float_value = multiply(tradeAmount, merPoundageArr3);
					}//R3 >=5000 <50000
					else if(amount >= Double.valueOf(merPoundageArr2) && amount < Float.valueOf(merPoundageArr4)){
						float_value = multiply(tradeAmount, merPoundageArr5);
					}//R4 >=50000 <5000000
					else if(amount >= Double.valueOf(merPoundageArr4) && amount < Float.valueOf(merPoundageArr6)){
						float_value = multiply(tradeAmount, merPoundageArr7);
					}//R5 >=5000000
					else if(amount >= Double.valueOf(merPoundageArr6)){
						float_value = multiply(tradeAmount, merPoundageArr8);
					}
					float_value = Double.valueOf(roundString(float_value, 2));
				}
				/**
				 * (分段固定手续费)其中x1,x2…..代表分界档，R1,R2,R3…..代表分界档内的固定手续费。
				 *	其中不包含上分界档.如计费费率为:<20000,手续费为2,20000-50000,手续费为3,50000-100000,手续费为5,
				 *	>=100000,费率为10
				 *	则计费公式为：FIX(20000,2,50000,3,100000,5,10)
				 */
				else if(merPoundage.startsWith("FIX")){
					merPoundage = getDataVlue(merPoundage);
					String[] merPoundageArr = merPoundage.split(",");
					Double merPoundageArr0 = Double.valueOf(merPoundageArr[0]);
					Double merPoundageArr1 = Double.valueOf(merPoundageArr[1]);
					Double merPoundageArr2 = Double.valueOf(merPoundageArr[2]);
					Double merPoundageArr3 = Double.valueOf(merPoundageArr[3]);
					Double merPoundageArr4 = Double.valueOf(merPoundageArr[4]);
					Double merPoundageArr5 = Double.valueOf(merPoundageArr[5]);
					Double merPoundageArr6 = Double.valueOf(merPoundageArr[6]);
					Double amount = Double.valueOf(tradeAmount)/100;
					//R1 <20000
					if(amount < merPoundageArr0){
						float_value = merPoundageArr1;
					}//R2 >=20000 <50000
					else if(amount >= merPoundageArr0 && amount < merPoundageArr2){
						float_value = merPoundageArr3;
					}//R3 >=50000 <100000
					else if(amount >= merPoundageArr2 && amount < merPoundageArr4){
						float_value = merPoundageArr5;
					}//R4 >=1000000
					else if(amount >= merPoundageArr4){
						float_value = merPoundageArr6;
					}
				}
				/**
				 * (增量计费)最低手续费为X，每增加N的金额，手续费就增加X
				 *	如：最低手续费为2.5元，交易金额每增加20000元，手续费就增加2.5元
				 *	则计费公式为INC(2.5，20000)
				 */
				else if(merPoundage.startsWith("INC")){
					merPoundage = getDataVlue(merPoundage);
					String[] merPoundageArr = merPoundage.split(",");
					Double tradeFee = Double.valueOf(merPoundageArr[0]);
					int x = div(tradeAmount, merPoundageArr[1]);
					if(x > 1)
						float_value = tradeFee*x;
					else
						float_value = tradeFee;
				}
				/**
				 * (最低额计费)x1为最低计费额，x2为其它组合公式
				 *	只有在该额度以上才能计费，否则为零,在大于该额度以上，计费公式为X2.
				 *	如: 只有在5000以上才能计费，计费的规则为：每笔费率为0.008
				 *	则计费公式为IFBIG(5000, AMT*0.08)
				 */
				else if(merPoundage.startsWith("IFBIG")){
					merPoundage = getDataVlue(merPoundage);
					merPoundage = getReplaceAll(merPoundage, "AMT\\*", "");
					String[] merPoundageArr = merPoundage.split(",");
					Double min_value = Double.valueOf(merPoundageArr[0]);
					Double amount = Double.valueOf(tradeAmount)/100;
					if(amount > min_value)
						float_value = multiply(tradeAmount, merPoundageArr[1]);
					float_value = Double.valueOf(roundString(float_value, 2));
				}
				/**
				 * 环讯专用计费公式(固定交易费率，有下限)其中X1代表下限，R代表费率
				 *	如费率为0.005，下限为0.2
				 *	则计费公式为MAX(0.2,AMT*0.005),进一法
				 *  MAX:如果AMT*0.005 , 0.2 俩个值取最大值
				 */
				else if(merPoundage.startsWith("IPSMAX")){
					merPoundage = getDataVlue(merPoundage);
					merPoundage = getReplaceAll(merPoundage, "AMT\\*", "");
					String[] merPoundageArr = merPoundage.split(",");
					Double merPoundageArr0 = Double.valueOf(merPoundageArr[0]);
					float_value = multiply(tradeAmount, merPoundageArr[1]);
					if(float_value < merPoundageArr0)
						float_value = merPoundageArr0;
					float_value = Double.valueOf(roundString(float_value, 2));
				}else{
					log.error("No matching merchant fees formula(无商户手续费公式匹配)");
				}
				return float_value;
			}
		} catch (Exception e) {
			log.error(e);
		}
		return float_value;
	}
	
	/**
	 *  线上计算商户手续费
	 * @param ryfAmount : 交易金额
	 * @param gate ：网关号
	 * @param mid ：商户号
	 * @param gid ：渠道号
	 * @return
	 */
	public static Double getTradeMerFee(Integer ryfAmount,Integer gate,String mid,Integer gid){
		Double float_value = 0d;
		try {
			if(ryfAmount == 0){
				return 0d;
			}
			String tradeAmount = String.valueOf(ryfAmount);
			String merPoundage = FeeCalcModeDAO.getInstance().getMerFee(gate, mid,gid);
			if(StringUtils.isNotBlank(merPoundage)){
				merPoundage = merPoundage.toUpperCase();
				/**
				 * 专门计算银行手续费的格式
				 *	X：最小值，D：最大值，R:费率,I：第三位>= I 时候进一，
				 *	如：BKFEE(0.1,100,0.008,2)
				 *	表示该手续费按照0.008的费率计算，最小为0.1元，最大为100元，当小数点后第三位是>=2时进一
				 */
				if(merPoundage.startsWith("BKFEE")){
					merPoundage = getDataVlue(merPoundage);
					String[] merPoundageArr = merPoundage.split(",");
					//X
					Double merPoundageArr0 = Double.valueOf(merPoundageArr[0]);
					//D
					Double merPoundageArr1 = Double.valueOf(merPoundageArr[1]);
					int merPoundageArr3 = Integer.valueOf(merPoundageArr[3]);
					//AMT*R
					float_value = multiply(tradeAmount, merPoundageArr[2]);
					if(float_value < merPoundageArr0){
						float_value = merPoundageArr0;
					}else if(float_value > merPoundageArr1){
						float_value = merPoundageArr1;
					}
					float_value = roundFloat_roundingMode(String.valueOf(float_value), 2, merPoundageArr3).doubleValue();
				}
				/**
				 * (固定交易费率，无上下限)其中R代表费率
				 *	如费率为0.008
				 *	则计费公式为AMT*0.008
				 */
				else if(merPoundage.startsWith("AMT")){
					String[] merPoundageArr = merPoundage.split("\\*");
					float_value = multiply(tradeAmount, merPoundageArr[1]);
					float_value = Double.valueOf(roundString(float_value, 2));
				}
				/**
				 * (固定交易费率，有下限)其中X1代表下限，R代表费率
				 *	如费率为0.005，下限为0.2
				 *	则计费公式为MAX(0.2,AMT*0.005)
				 */
				else if(merPoundage.startsWith("MAX")){
					merPoundage = getDataVlue(merPoundage);
					merPoundage = getReplaceAll(merPoundage, "AMT\\*", "");
					String[] merPoundageArr = merPoundage.split(",");
					//下限
					Double merPoundageArr0 = Double.valueOf(merPoundageArr[0]);
					//AMT*R
					float_value = multiply(tradeAmount, merPoundageArr[1]);
					if(float_value < merPoundageArr0)
						float_value = merPoundageArr0;
					float_value = Double.valueOf(roundString(float_value, 2));
				}
				/**
				 * (固定交易费率，有上限)其中X1代表上限，R代表费率
				 *	如费率为0.005，上限为50
				 *	则计费公式为MIN(50,AMT*0.005)
				 */
				else if(merPoundage.startsWith("MIN")){
					merPoundage = getDataVlue(merPoundage);
					merPoundage = getReplaceAll(merPoundage, "AMT\\*", "");
					String[] merPoundageArr = merPoundage.split(",");
					//下限
					Double merPoundageArr0 = Double.valueOf(merPoundageArr[0]);
					//AMT*R
					float_value = multiply(tradeAmount, merPoundageArr[1]);
					if(float_value > merPoundageArr0)
						float_value = merPoundageArr0;
					float_value = Double.valueOf(roundString(float_value, 2));
				}
				/**
				 * (固定交易费率，有下限和上限)其中x1,x2分别代表下限和上限，R代表费率
				 *	如费率为0.006，下限为0.8，上限为500
				 *	则计费公式为MTM(0.8,500,AMT*0.006)
				 */
				else if(merPoundage.startsWith("MTM")){
					merPoundage = getDataVlue(merPoundage);
					merPoundage = getReplaceAll(merPoundage, "AMT\\*", "");
					String[] merPoundageArr = merPoundage.split(",");
					//下限
					Double merPoundageArr0 = Double.valueOf(merPoundageArr[0]);
					//上限
					Double merPoundageArr1 = Double.valueOf(merPoundageArr[1]);
					//AMT*R
					float_value = multiply(tradeAmount, merPoundageArr[2]);
					if(float_value > merPoundageArr1){
						float_value = merPoundageArr1;
					}else if(float_value < merPoundageArr0){
						float_value = merPoundageArr0;
					}
					float_value = Double.valueOf(roundString(float_value, 2));
				}
				/**
				 * (单笔固定费用)其中X代表单笔固定手续费
				 *	如单笔固定为2.5元
				 *	则计费公式为SGL(2.5)
				 */
				else if(merPoundage.startsWith("SGL")){
					merPoundage = getDataVlue(merPoundage);
					float_value = Double.valueOf(merPoundage);
				}
				/**
				 * (单笔浮动费率)其中x1,x2…..代表分界档，R1,R2,R3…..代表分界档内的费率。
				 *	其中不包含上分界档如: <500,费率为0.08,500-5000费率为0.06,5000-50000费率为0.05 ,50000-500000费率为0.03,
				 *	>=5000000,费率为0.01 则计费公式为：FLO(500,0.08,5000,0.06,50000,0.05,500000,0.03,0.01)
				 */
				else if(merPoundage.startsWith("FLO")){
					merPoundage = getDataVlue(merPoundage);
					String[] merPoundageArr = merPoundage.split(",");
					String merPoundageArr0 = merPoundageArr[0];
					String merPoundageArr1 = merPoundageArr[1];
					String merPoundageArr2 = merPoundageArr[2];
					String merPoundageArr3 = merPoundageArr[3];
					String merPoundageArr4 = merPoundageArr[4];
					String merPoundageArr5 = merPoundageArr[5];
					String merPoundageArr6 = merPoundageArr[6];
					String merPoundageArr7 = merPoundageArr[7];
					String merPoundageArr8 = merPoundageArr[8];
					Double amount = Double.valueOf(tradeAmount)/100;
					//R1 <500
					if(amount < Double.valueOf(merPoundageArr0)){
						float_value = multiply(tradeAmount, merPoundageArr1);
					}//R2 >=500 <5000
					else if(amount >= Double.valueOf(merPoundageArr0) && amount < Float.valueOf(merPoundageArr2)){
						float_value = multiply(tradeAmount, merPoundageArr3);
					}//R3 >=5000 <50000
					else if(amount >= Double.valueOf(merPoundageArr2) && amount < Float.valueOf(merPoundageArr4)){
						float_value = multiply(tradeAmount, merPoundageArr5);
					}//R4 >=50000 <5000000
					else if(amount >= Double.valueOf(merPoundageArr4) && amount < Float.valueOf(merPoundageArr6)){
						float_value = multiply(tradeAmount, merPoundageArr7);
					}//R5 >=5000000
					else if(amount >= Double.valueOf(merPoundageArr6)){
						float_value = multiply(tradeAmount, merPoundageArr8);
					}
					float_value = Double.valueOf(roundString(float_value, 2));
				}
				/**
				 * (分段固定手续费)其中x1,x2…..代表分界档，R1,R2,R3…..代表分界档内的固定手续费。
				 *	其中不包含上分界档.如计费费率为:<20000,手续费为2,20000-50000,手续费为3,50000-100000,手续费为5,
				 *	>=100000,费率为10
				 *	则计费公式为：FIX(20000,2,50000,3,100000,5,10)
				 */
				else if(merPoundage.startsWith("FIX")){
					merPoundage = getDataVlue(merPoundage);
					String[] merPoundageArr = merPoundage.split(",");
					Double merPoundageArr0 = Double.valueOf(merPoundageArr[0]);
					Double merPoundageArr1 = Double.valueOf(merPoundageArr[1]);
					Double merPoundageArr2 = Double.valueOf(merPoundageArr[2]);
					Double merPoundageArr3 = Double.valueOf(merPoundageArr[3]);
					Double merPoundageArr4 = Double.valueOf(merPoundageArr[4]);
					Double merPoundageArr5 = Double.valueOf(merPoundageArr[5]);
					Double merPoundageArr6 = Double.valueOf(merPoundageArr[6]);
					Double amount = Double.valueOf(tradeAmount)/100;
					//R1 <20000
					if(amount < merPoundageArr0){
						float_value = merPoundageArr1;
					}//R2 >=20000 <50000
					else if(amount >= merPoundageArr0 && amount < merPoundageArr2){
						float_value = merPoundageArr3;
					}//R3 >=50000 <100000
					else if(amount >= merPoundageArr2 && amount < merPoundageArr4){
						float_value = merPoundageArr5;
					}//R4 >=1000000
					else if(amount >= merPoundageArr4){
						float_value = merPoundageArr6;
					}
				}
				/**
				 * (增量计费)最低手续费为X，每增加N的金额，手续费就增加X
				 *	如：最低手续费为2.5元，交易金额每增加20000元，手续费就增加2.5元
				 *	则计费公式为INC(2.5，20000)
				 */
				else if(merPoundage.startsWith("INC")){
					merPoundage = getDataVlue(merPoundage);
					String[] merPoundageArr = merPoundage.split(",");
					Double tradeFee = Double.valueOf(merPoundageArr[0]);
					int x = div(tradeAmount, merPoundageArr[1]);
					if(x > 1)
						float_value = tradeFee*x;
					else
						float_value = tradeFee;
				}
				/**
				 * (最低额计费)x1为最低计费额，x2为其它组合公式
				 *	只有在该额度以上才能计费，否则为零,在大于该额度以上，计费公式为X2.
				 *	如: 只有在5000以上才能计费，计费的规则为：每笔费率为0.008
				 *	则计费公式为IFBIG(5000, AMT*0.08)
				 */
				else if(merPoundage.startsWith("IFBIG")){
					merPoundage = getDataVlue(merPoundage);
					merPoundage = getReplaceAll(merPoundage, "AMT\\*", "");
					String[] merPoundageArr = merPoundage.split(",");
					Double min_value = Double.valueOf(merPoundageArr[0]);
					Double amount = Double.valueOf(tradeAmount)/100;
					if(amount > min_value)
						float_value = multiply(tradeAmount, merPoundageArr[1]);
					float_value = Double.valueOf(roundString(float_value, 2));
				}
				/**
				 * 环讯专用计费公式(固定交易费率，有下限)其中X1代表下限，R代表费率
				 *	如费率为0.005，下限为0.2
				 *	则计费公式为MAX(0.2,AMT*0.005),进一法
				 *  MAX:如果AMT*0.005 , 0.2 俩个值取最大值
				 */
				else if(merPoundage.startsWith("IPSMAX")){
					merPoundage = getDataVlue(merPoundage);
					merPoundage = getReplaceAll(merPoundage, "AMT\\*", "");
					String[] merPoundageArr = merPoundage.split(",");
					Double merPoundageArr0 = Double.valueOf(merPoundageArr[0]);
					float_value = multiply(tradeAmount, merPoundageArr[1]);
					if(float_value < merPoundageArr0)
						float_value = merPoundageArr0;
					float_value = Double.valueOf(roundString(float_value, 2));
				}else{
					log.error("No matching merchant fees formula(无商户手续费公式匹配)");
				}
				return float_value;
			}
		} catch (Exception e) {
			log.error(e);
		}
		return float_value;
	}
	
	
	/**
	 * 通过传入公式\金额获取商户手续费
	 * @param tradeAmount ：金额
	 * @param merPoundage ：商户手续费公式
	 * @return static
	 */
	public static Double getTradeMerFee(String tradeAmount,String merPoundage){
		Double float_value = 0d;
		try {
			tradeAmount = tradeAmount.replace("-", "");
			if(StringUtils.isNotBlank(merPoundage)){
				merPoundage = merPoundage.toUpperCase();
				/**
				 * 专门计算银行手续费的格式
				 *	X：最小值，D：最大值，R:费率,I：第三位>= I 时候进一，
				 *	如：BKFEE(0.1,100,0.008,2)
				 *	表示该手续费按照0.008的费率计算，最小为0.1元，最大为100元，当小数点后第三位是>=2时进一
				 */
				if(merPoundage.startsWith("BKFEE")){
					merPoundage = getDataVlue(merPoundage);
					String[] merPoundageArr = merPoundage.split(",");
					//X
					Double merPoundageArr0 = Double.valueOf(merPoundageArr[0]);
					//D
					Double merPoundageArr1 = Double.valueOf(merPoundageArr[1]);
					int merPoundageArr3 = Integer.valueOf(merPoundageArr[3]);
					//AMT*R
					float_value = multiply(tradeAmount, merPoundageArr[2]);
					if(float_value < merPoundageArr0){
						float_value = merPoundageArr0;
					}else if(float_value > merPoundageArr1){
						float_value = merPoundageArr1;
					}
					float_value = roundFloat_roundingMode(String.valueOf(float_value), 2, merPoundageArr3).doubleValue();
				}
				/**
				 * (固定交易费率，无上下限)其中R代表费率
				 *	如费率为0.008
				 *	则计费公式为AMT*0.008
				 */
				else if(merPoundage.startsWith("AMT")){
					String[] merPoundageArr = merPoundage.split("\\*");
					float_value = multiply(tradeAmount, merPoundageArr[1]);
					float_value = Double.valueOf(roundString(float_value, 2));
				}
				/**
				 * (固定交易费率，有下限)其中X1代表下限，R代表费率
				 *	如费率为0.005，下限为0.2
				 *	则计费公式为MAX(0.2,AMT*0.005)
				 */
				else if(merPoundage.startsWith("MAX")){
					merPoundage = getDataVlue(merPoundage);
					merPoundage = getReplaceAll(merPoundage, "AMT\\*", "");
					String[] merPoundageArr = merPoundage.split(",");
					//下限
					Double merPoundageArr0 = Double.valueOf(merPoundageArr[0]);
					//AMT*R
					float_value = multiply(tradeAmount, merPoundageArr[1]);
					if(float_value < merPoundageArr0)
						float_value = merPoundageArr0;
					float_value = Double.valueOf(roundString(float_value, 2));
				}
				/**
				 * (固定交易费率，有上限)其中X1代表上限，R代表费率
				 *	如费率为0.005，上限为50
				 *	则计费公式为MIN(50,AMT*0.005)
				 */
				else if(merPoundage.startsWith("MIN")){
					merPoundage = getDataVlue(merPoundage);
					merPoundage = getReplaceAll(merPoundage, "AMT\\*", "");
					String[] merPoundageArr = merPoundage.split(",");
					//下限
					Double merPoundageArr0 = Double.valueOf(merPoundageArr[0]);
					//AMT*R
					float_value = multiply(tradeAmount, merPoundageArr[1]);
					if(float_value > merPoundageArr0)
						float_value = merPoundageArr0;
					float_value = Double.valueOf(roundString(float_value, 2));
				}
				/**
				 * (固定交易费率，有下限和上限)其中x1,x2分别代表下限和上限，R代表费率
				 *	如费率为0.006，下限为0.8，上限为500
				 *	则计费公式为MTM(0.8,500,AMT*0.006)
				 */
				else if(merPoundage.startsWith("MTM")){
					merPoundage = getDataVlue(merPoundage);
					merPoundage = getReplaceAll(merPoundage, "AMT\\*", "");
					String[] merPoundageArr = merPoundage.split(",");
					//下限
					Double merPoundageArr0 = Double.valueOf(merPoundageArr[0]);
					//上限
					Double merPoundageArr1 = Double.valueOf(merPoundageArr[1]);
					//AMT*R
					float_value = multiply(tradeAmount, merPoundageArr[2]);
					if(float_value > merPoundageArr1){
						float_value = merPoundageArr1;
					}else if(float_value < merPoundageArr0){
						float_value = merPoundageArr0;
					}
					float_value = Double.valueOf(roundString(float_value, 2));
				}
				/**
				 * (单笔固定费用)其中X代表单笔固定手续费
				 *	如单笔固定为2.5元
				 *	则计费公式为SGL(2.5)
				 */
				else if(merPoundage.startsWith("SGL")){
					merPoundage = getDataVlue(merPoundage);
					float_value = Double.valueOf(merPoundage);
				}
				/**
				 * (单笔浮动费率)其中x1,x2…..代表分界档，R1,R2,R3…..代表分界档内的费率。
				 *	其中不包含上分界档如: <500,费率为0.08,500-5000费率为0.06,5000-50000费率为0.05 ,50000-500000费率为0.03,
				 *	>=5000000,费率为0.01 则计费公式为：FLO(500,0.08,5000,0.06,50000,0.05,500000,0.03,0.01)
				 */
				else if(merPoundage.startsWith("FLO")){
					merPoundage = getDataVlue(merPoundage);
					String[] merPoundageArr = merPoundage.split(",");
					String merPoundageArr0 = merPoundageArr[0];
					String merPoundageArr1 = merPoundageArr[1];
					String merPoundageArr2 = merPoundageArr[2];
					String merPoundageArr3 = merPoundageArr[3];
					String merPoundageArr4 = merPoundageArr[4];
					String merPoundageArr5 = merPoundageArr[5];
					String merPoundageArr6 = merPoundageArr[6];
					String merPoundageArr7 = merPoundageArr[7];
					String merPoundageArr8 = merPoundageArr[8];
					Double amount = Double.valueOf(tradeAmount)/100;
					//R1 <500
					if(amount < Double.valueOf(merPoundageArr0)){
						float_value = multiply(tradeAmount, merPoundageArr1);
					}//R2 >=500 <5000
					else if(amount >= Double.valueOf(merPoundageArr0) && amount < Float.valueOf(merPoundageArr2)){
						float_value = multiply(tradeAmount, merPoundageArr3);
					}//R3 >=5000 <50000
					else if(amount >= Double.valueOf(merPoundageArr2) && amount < Float.valueOf(merPoundageArr4)){
						float_value = multiply(tradeAmount, merPoundageArr5);
					}//R4 >=50000 <5000000
					else if(amount >= Double.valueOf(merPoundageArr4) && amount < Float.valueOf(merPoundageArr6)){
						float_value = multiply(tradeAmount, merPoundageArr7);
					}//R5 >=5000000
					else if(amount >= Double.valueOf(merPoundageArr6)){
						float_value = multiply(tradeAmount, merPoundageArr8);
					}
					float_value = Double.valueOf(roundString(float_value, 2));
				}
				/**
				 * (分段固定手续费)其中x1,x2…..代表分界档，R1,R2,R3…..代表分界档内的固定手续费。
				 *	其中不包含上分界档.如计费费率为:<20000,手续费为2,20000-50000,手续费为3,50000-100000,手续费为5,
				 *	>=100000,费率为10
				 *	则计费公式为：FIX(20000,2,50000,3,100000,5,10)
				 */
				else if(merPoundage.startsWith("FIX")){
					merPoundage = getDataVlue(merPoundage);
					String[] merPoundageArr = merPoundage.split(",");
					Double merPoundageArr0 = Double.valueOf(merPoundageArr[0]);
					Double merPoundageArr1 = Double.valueOf(merPoundageArr[1]);
					Double merPoundageArr2 = Double.valueOf(merPoundageArr[2]);
					Double merPoundageArr3 = Double.valueOf(merPoundageArr[3]);
					Double merPoundageArr4 = Double.valueOf(merPoundageArr[4]);
					Double merPoundageArr5 = Double.valueOf(merPoundageArr[5]);
					Double merPoundageArr6 = Double.valueOf(merPoundageArr[6]);
					Double amount = Double.valueOf(tradeAmount)/100;
					//R1 <20000
					if(amount < merPoundageArr0){
						float_value = merPoundageArr1;
					}//R2 >=20000 <50000
					else if(amount >= merPoundageArr0 && amount < merPoundageArr2){
						float_value = merPoundageArr3;
					}//R3 >=50000 <100000
					else if(amount >= merPoundageArr2 && amount < merPoundageArr4){
						float_value = merPoundageArr5;
					}//R4 >=1000000
					else if(amount >= merPoundageArr4){
						float_value = merPoundageArr6;
					}
				}
				/**
				 * (增量计费)最低手续费为X，每增加N的金额，手续费就增加X
				 *	如：最低手续费为2.5元，交易金额每增加20000元，手续费就增加2.5元
				 *	则计费公式为INC(2.5，20000)
				 */
				else if(merPoundage.startsWith("INC")){
					merPoundage = getDataVlue(merPoundage);
					String[] merPoundageArr = merPoundage.split(",");
					Double tradeFee = Double.valueOf(merPoundageArr[0]);
					int x = div(tradeAmount, merPoundageArr[1]);
					if(x > 1)
						float_value = tradeFee*x;
					else
						float_value = tradeFee;
				}
				/**
				 * (最低额计费)x1为最低计费额，x2为其它组合公式
				 *	只有在该额度以上才能计费，否则为零,在大于该额度以上，计费公式为X2.
				 *	如: 只有在5000以上才能计费，计费的规则为：每笔费率为0.008
				 *	则计费公式为IFBIG(5000, AMT*0.08)
				 */
				else if(merPoundage.startsWith("IFBIG")){
					merPoundage = getDataVlue(merPoundage);
					merPoundage = getReplaceAll(merPoundage, "AMT\\*", "");
					String[] merPoundageArr = merPoundage.split(",");
					Double min_value = Double.valueOf(merPoundageArr[0]);
					Double amount = Double.valueOf(tradeAmount)/100;
					if(amount > min_value)
						float_value = multiply(tradeAmount, merPoundageArr[1]);
					float_value = Double.valueOf(roundString(float_value, 2));
				}
				/**
				 * 环讯专用计费公式(固定交易费率，有下限)其中X1代表下限，R代表费率
				 *	如费率为0.005，下限为0.2
				 *	则计费公式为MAX(0.2,AMT*0.005),进一法
				 *  MAX:如果AMT*0.005 , 0.2 俩个值取最大值
				 */
				else if(merPoundage.startsWith("IPSMAX")){
					merPoundage = getDataVlue(merPoundage);
					merPoundage = getReplaceAll(merPoundage, "AMT\\*", "");
					String[] merPoundageArr = merPoundage.split(",");
					Double merPoundageArr0 = Double.valueOf(merPoundageArr[0]);
					float_value = multiply(tradeAmount, merPoundageArr[1]);
					if(float_value < merPoundageArr0)
						float_value = merPoundageArr0;
					float_value = Double.valueOf(roundString(float_value, 2));
				}else{
					log.error("No matching merchant fees formula(无商户手续费公式匹配)");
				}
				return float_value;
			}
		} catch (Exception e) {
			log.error(e);
		}
		return float_value;
	}
	
	/**
	 * 渠道费率计算
	 * @param obj : 渠道费率配置基本信息
	 * @param tradeAmount ：交易金额(按分)
	 * @param card_no ：卡号
	 * @param inst_id ：渠道号
	 * @param inst_type ：渠道类型   0:线下、1:线上
	 * @param gate ：网关号
	 * @param mer_code ：扣款商户号
	 * @return double
	 */
	public static double getTradeMerFee(Object[] obj,String tradeAmount,String card_no,Integer inst_id,Integer inst_type,
			String mer_code,List<InstMerRateConf> instMerRateConfs){
		Double zf_fee = 0d;
		try {
			mccDiscountDAO = cn.com.chinaebi.dz.object.dao.MccDiscountDAO.getInstance();
			cardTransferDAO = cn.com.chinaebi.dz.object.dao.CardTransferDAO.getInstance();
			//1:按MCC费率、3:按固定费率
			//渠道费率 1:标准MCC配置(成本)、2:标准MCC配置(全部)
//			Object[] obj = instRateDAO.getInstRateType(inst_id, inst_type);
			if(obj != null){
				Integer instRateType = Integer.valueOf(obj[0].toString());
				Integer instRateMcc = Integer.valueOf(obj[1].toString());
				String bankInstCode = obj[2].toString();
//				Integer whetherReturnFee = Integer.valueOf(obj[3].toString());
				switch(instRateType){
				 	case 1://1:按MCC费率
				 		String mccCode = PoundageCalculate.mccCodeSubstring(mer_code.trim());
						if(StringUtils.isNotBlank(mccCode)){
							if(instRateMcc == 1){ //1:标准MCC配置(成本)
								Object[] objArr = mccDiscountDAO.getMccDiscount(mccCode);
								if(objArr != null){
									String issuerPoundage = objArr[0].toString();
									String unionpayPoundage = objArr[1].toString();
									double issuer = getTradeMerFee(tradeAmount, issuerPoundage);
									double unionpay = getTradeMerFee(tradeAmount, unionpayPoundage);
									zf_fee = add(issuer, unionpay).doubleValue();
								}else{
									log.error(StringPingJie.getInstance().getStringPingJie("按MCC费率 mcc = ",mccCode," 没有获取到标准MCC配置(成本)手续费公式"));
								}
							}else if(instRateMcc == 2){//2:标准MCC配置(全部)
								String merPoundage = mccDiscountDAO.getMccDiscountTotal(mccCode);
								if(StringUtils.isNotBlank(merPoundage)){
									zf_fee = getTradeMerFee(tradeAmount, merPoundage);
								}else{
									log.error(StringPingJie.getInstance().getStringPingJie("按MCC费率 mcc = ",mccCode," 没有获取到标准MCC配置(全部)手续费公式"));
								}
							}else{
								log.error("按MCC费率->渠道费率无法识别");
							}
						}
						break;
				 	case 3: //3:按固定费率
						if(instMerRateConfs != null && instMerRateConfs.size() > 0){
							for (InstMerRateConf instMerRateConf : instMerRateConfs) {
								Integer lineOrinter = instMerRateConf.getId().getLineOrinter();
								String feePoundage = null;
								String cardType = instMerRateConf.getId().getCardType();
								//1:本行、2:跨行、3:跨行和本行
								switch(lineOrinter){
									case 1:
										if(StringUtils.isNotBlank(cardType)){
											if(StringUtils.equalsIgnoreCase(cardType, "All")){
												feePoundage = instMerRateConf.getFeePoundage();
												zf_fee = getTradeMerFee(tradeAmount, feePoundage);
											}else{
												CardTransfer cardTransfer = cardTransferDAO.getCardTransferByCardNo(card_no);
												if(cardTransfer != null){
													String cardInsCode = cardTransfer.getCardInsCode().substring(0, 4);
													int indexOf = bankInstCode.indexOf(cardInsCode);
													if(indexOf > -1){
														if(StringUtils.equalsIgnoreCase(cardType, "C") && StringUtils.equalsIgnoreCase(cardTransfer.getCardType(),"C")){
															feePoundage = instMerRateConf.getFeePoundage();
														}else if(StringUtils.equalsIgnoreCase(cardType, "D") && StringUtils.equalsIgnoreCase(cardTransfer.getCardType(),"D")){
															feePoundage = instMerRateConf.getFeePoundage();
														}else{
															log.error(StringPingJie.getInstance().getStringPingJie("按固定费率(本行) 卡种 = ",cardType," 配置错误 只能配置(C、D、All)"));
														}
														zf_fee = getTradeMerFee(tradeAmount, feePoundage);
													}else{
														log.error(StringPingJie.getInstance().getStringPingJie("按固定费率(本行) ",cardInsCode," 不存在 ",bankInstCode," 当中,无法匹配"));
													}
												}else{
													log.error(StringPingJie.getInstance().getStringPingJie("按固定费率(本行) instRateType =  ",instRateType," card_no = ",card_no," 卡号没有找到该卡BIN信息"));
												}
											}
										}else{
											log.error(StringPingJie.getInstance().getStringPingJie("按固定费率(本行) inst_mer_rate_conf cardType 不能为空"));
										}
										break;
									case 2:
										if(StringUtils.isNotBlank(cardType)){
											if(StringUtils.equalsIgnoreCase(cardType, "All")){
												feePoundage = instMerRateConf.getFeePoundage();
												zf_fee = getTradeMerFee(tradeAmount, feePoundage);
											}else{
												CardTransfer cardTransfer = cardTransferDAO.getCardTransferByCardNo(card_no);
												if(cardTransfer != null){
													String cardInsCode = cardTransfer.getCardInsCode().substring(0, 4);
													int indexOf = bankInstCode.indexOf(cardInsCode);
													if(indexOf > -1){
														if(StringUtils.equalsIgnoreCase(cardType, "C") && StringUtils.equalsIgnoreCase(cardTransfer.getCardType(),"C")){
															feePoundage = instMerRateConf.getFeePoundage();
														}else if(StringUtils.equalsIgnoreCase(cardType, "D") && StringUtils.equalsIgnoreCase(cardTransfer.getCardType(),"D")){
															feePoundage = instMerRateConf.getFeePoundage();
														}else{
															log.error(StringPingJie.getInstance().getStringPingJie("按固定费率(跨行) 卡种 = ",cardType," 配置错误 只能配置(C、D、All)"));
														}
														zf_fee = getTradeMerFee(tradeAmount, feePoundage);
													}else{
														log.error(StringPingJie.getInstance().getStringPingJie("按固定费率(跨行) ",cardInsCode," 不存在 ",bankInstCode," 当中,无法匹配"));
													}
												}else{
													log.error(StringPingJie.getInstance().getStringPingJie("按固定费率(跨行) instRateType =  ",instRateType," card_no = ",card_no," 卡号没有找到该卡BIN信息"));
												}
											}
										}else{
											log.error(StringPingJie.getInstance().getStringPingJie("按固定费率(跨行) inst_mer_rate_conf cardType 不能为空"));
										}
										break;
									case 3:
										if(StringUtils.isNotBlank(cardType)){
											if(StringUtils.equalsIgnoreCase(cardType, "All")){
												feePoundage = instMerRateConf.getFeePoundage();
												zf_fee = getTradeMerFee(tradeAmount, feePoundage);
											}else{
												CardTransfer cardTransfer = cardTransferDAO.getCardTransferByCardNo(card_no);
												if(cardTransfer != null){
													if(StringUtils.equalsIgnoreCase(cardType, "C") && StringUtils.equalsIgnoreCase(cardTransfer.getCardType(),"C")){
														feePoundage = instMerRateConf.getFeePoundage();
													}else if(StringUtils.equalsIgnoreCase(cardType, "D") && StringUtils.equalsIgnoreCase(cardTransfer.getCardType(),"D")){
														feePoundage = instMerRateConf.getFeePoundage();
													}else{
														log.error(StringPingJie.getInstance().getStringPingJie("按固定费率(跨行和本行) 卡种 = ",cardType," 配置错误 只能配置(C、D、All)"));
													}
													zf_fee = getTradeMerFee(tradeAmount, feePoundage);
												}else{
													log.error(StringPingJie.getInstance().getStringPingJie("按固定费率(跨行和本行) ",card_no," 卡号没有找到该卡BIN信息"));
												}
											}
										}else{
											log.error(StringPingJie.getInstance().getStringPingJie("按固定费率(跨行和本行) inst_mer_rate_conf cardType 不能为空"));
										}
										break;
									default:
										log.warn("按固定费率->本行或者跨行无法识别");
										break;
								}
								if(StringUtils.isNotBlank(feePoundage)) //表示已经获取到渠道费率公式,不需要在无效循环
									break;
							}
						}else{
							log.error(StringPingJie.getInstance().getStringPingJie("按固定费率 参数 ","渠道ID ：",inst_id,",渠道类型：",inst_type,",商户号：",mer_code,"查询getInstMerRateConf方法,数据为空"));
						}
						break;
				 	default:
				 		break;
						 
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
		return zf_fee;
	}
	
	/**
	 * 线上渠道费率计算工具类(getTradeMerFee(渠道费率计算) -> 针对此方法封装优化)
	 * 线上只有收款交易需要对账,所以没有退还手续费(添加例外)等处理
	 * @param tradeAmount ：交易金额
	 * @param card_no ：卡号
	 * @param inst_id ：渠道ID
	 * @param inst_type ：渠道类型
	 * @param mer_code ：商户号
	 * @param zf_file_fee ：支付手续费
	 * @return Double
	 */
	public static double getOnlineTradeMerFeeUtil(String tradeAmount,String card_no,Integer inst_id,Integer inst_type,
			String mer_code,String zf_file_fee){
		
		instRateDAO = cn.com.chinaebi.dz.object.dao.InstRateDAO.getInstance();
		instMerRateConfDAO = cn.com.chinaebi.dz.object.dao.InstMerRateConfDAO.getInstance();
		
		//获取渠道基本信息配置(其中包括获取银行对账单费率)
		InstInfoPK instInfoPK = new InstInfoPK(inst_id, inst_type);
		Object[] instRateObj = instRateMap.get(instInfoPK);
		if(instRateObj == null){
			instRateObj = instRateDAO.getInstRateType(inst_id, inst_type);
			instRateMap.put(instInfoPK, instRateObj);
		}
		//渠道费率开始计算
		double zf_fee = 0;
		if(instRateObj != null){
			Integer instRateType = Integer.valueOf(instRateObj[0].toString());
			if(instRateType == 2){//获取对账文件银行手续费
				zf_fee = Double.valueOf(zf_file_fee);
			}else{
				//获取渠道固定费率配置
				StringBuffer buffer = new StringBuffer();
				buffer.append(inst_id);
				buffer.append(inst_type);
				buffer.append(mer_code);
				String string = buffer.toString();
				List<InstMerRateConf> instMerRateConfs = instMerRateConfMap.get(string);
				if(instMerRateConfs == null){
					instMerRateConfs = instMerRateConfDAO.getInstMerRateConf(inst_id, inst_type,mer_code);
					instMerRateConfMap.put(string, instMerRateConfs);
				}
				zf_fee = getTradeMerFee(instRateObj, tradeAmount, card_no, inst_id, inst_type, mer_code, instMerRateConfs);
			}
		}
		return zf_fee;
	}
	
	/**
	 * 线下/线上退款交易渠道费率计算工具类(简单处理)
	 * @param obj
	 * @param tradeAmount
	 * @param card_no
	 * @param inst_id
	 * @param inst_type
	 * @param mer_code
	 * @return
	 */
	public static Double getOfflineTradeMerFeeUtil(Object[] obj,String tradeAmount,String card_no,Integer inst_id,Integer inst_type,
			String mer_code){
		instMerRateConfDAO = cn.com.chinaebi.dz.object.dao.InstMerRateConfDAO.getInstance();
		List<InstMerRateConf> instMerRateConfs = instMerRateConfDAO.getInstMerRateConf(inst_id, inst_type,mer_code);
		Double zf_fee = getTradeMerFee(obj, tradeAmount, card_no, inst_id, inst_type, mer_code, instMerRateConfs);
		return zf_fee;
	}
	
	
	
	public static String mccCodeSubstring(String merCode){
		String mccCode = null;
		if(StringUtils.isNotBlank(merCode)){
			if(merCode.length() == 15){
				mccCode = merCode.substring(7,11);
			}else{
				log.error("商户号长度必须为15个长度 "+merCode);
			}
		}else{
			log.error("商户号不能为空");
		}
		return mccCode;
	}
	
	/**
	 * 俩个数相除取得倍数、不要余数
	 * @return int
	 */
	 public static int div(String num1, String num2) {
		 BigDecimal bd1 = new BigDecimal(num1);
		 BigDecimal bd2 = new BigDecimal(num2);
		 return bd1.divide(bd2).intValue()/100;
	 }
	 
	/**
	 * 字符串忽略大小写替换
	 * 特殊字符
	 * getReplaceAll(merPoundage, "amt\\*", "")
	 * @param merPoundage ：字符串
	 * @param replaceValue ：被替换的值
	 * @param character ：替换成的数值
	 * @return
	 */
	private static String getReplaceAll(String merPoundage,String replaceValue,String character){
		if(StringUtils.isNotBlank(merPoundage)){
			merPoundage = merPoundage.replaceAll("(?i)"+replaceValue, character);
		}
		return merPoundage;
	}
	
	/**
	 * 获取()里面的数值
	 * @param merPoundage
	 * @return String
	 */
	private static String getDataVlue(String merPoundage){
		int start = merPoundage.indexOf("(")+1;
		int end = merPoundage.indexOf(")");
		return merPoundage.substring(start, end);
	}
	
	/**
	 * 保留decimalPlaces位小数点
	 * @param value ：值
	 * @param decimalPlaces ：小数点位数
	 * @return String
	 */
	public static String roundString(Double value, int decimalPlaces) 
	{    
		NumberFormat nf = NumberFormat.getNumberInstance();    
		nf.setRoundingMode(RoundingMode.HALF_UP); 
		// 四舍五入    
		nf.setMaximumFractionDigits(decimalPlaces);    
		nf.setMinimumFractionDigits(decimalPlaces);    
		return nf.format(value).replaceAll(",", "");
	}
	
	/**
	   * 提供精确的乘法运算
	   * @param v1
	   * @param v2
	   * @return 两个参数的数学积，以字符串格式返回
	   */
	public static double multiply(String v1, String v2){
	    BigDecimal b1 = new BigDecimal(v1.trim());
	    BigDecimal b2 = new BigDecimal(v2.trim());
	    return (b1.multiply(b2).doubleValue())/100;
	}
	
	/**
	 * 提供精确的乘法-返回按分计算
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static BigDecimal multiply_minute(String v1,String v2){
	    BigDecimal b1 = new BigDecimal(v1.trim());
	    BigDecimal b2 = new BigDecimal(v2.trim());
	    return b1.multiply(b2);
	}
	
	/**
	 * 指定小数点位数、指定四舍五入值,不保留小数点最后一位0
	 * @param value : 数值 String
	 * @param scale : 设置小数点位数  
	 * @param roundingMode : 表示四舍五入，可以选择其他舍值方式，例如去尾，等等
	 * @return float
	 */
	public static BigDecimal roundFloat_roundingMode(String value,int scale,int roundingMode){
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(scale,roundingMode);  
		return bd;  
	}
	/**
	 * 指定小数点位数、指定四舍五入值,不保留小数点最后一位0
	 * @param value : 数值 String
	 * @param scale : 设置小数点位数  
	 * @param roundingMode : 表示四舍五入，可以选择其他舍值方式，例如去尾，等等
	 * @return String
	 */
	public static BigDecimal roundFloat_roundingMode(float value,int scale,int roundingMode){
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(scale,roundingMode);  
		return bd;  
	}
	
	/**
	 * 保留指定位数小数点-不四舍五入
	 * @param number ：字符串金额
	 * @param precision ：位数
	 * @return String
	 */
	public static String keepPrecision(String number, int precision) {  
        BigDecimal bg = new BigDecimal(number);  
        return bg.setScale(precision, BigDecimal.ROUND_DOWN).toPlainString();  
    }  
	
	
	/**
	 * 进行加法运算
	 * @param d1 : Object
	 * @param d2 : Object
	 * @return BigDecimal
	 */
	public static BigDecimal add(Object d1, Object d2){
         BigDecimal b1 = new BigDecimal(d1.toString());
         BigDecimal b2 = new BigDecimal(d2.toString());
         return b1.add(b2).setScale(PoundageCalculate.two, BigDecimal.ROUND_DOWN);
    }
	
	public static double add(String balance,double change_amount){
		double ye = Double.valueOf(balance);
		return ye+change_amount;
	}
	
	/**
	 * 进行减法运算
	 * @param d1 : Object
	 * @param d2 : Object
	 * @return BigDecimal
	 */
	public static BigDecimal sub(Object d1, Object d2){        
	     BigDecimal b1 = new BigDecimal(d1.toString());
	     BigDecimal b2 = new BigDecimal(d2.toString());
	     return b1.subtract(b2).setScale(PoundageCalculate.two, BigDecimal.ROUND_DOWN);
	}
	
	public static String addHlog(String balance,double change_amount){
		double ye = Double.valueOf(balance);
		return String.format("%.2f",ye+change_amount);
	}
	
	public static void main(String[] args)throws Exception {
		Double a = 1.4d;
		Double b = 0.22d;
		System.out.println(add(a, b));
		System.out.println("01020000".substring(0, 4));
		System.out.println("0102,0103,0104".indexOf("0104"));
//		double bigDecimal = PoundageCalculate.add("0", -0.01d);
//		System.out.println(bigDecimal);
		//System.out.println(new Float(bigDecimal));
//		System.out.println(roundFloat(100f, 2, 2).floatValue());
//		String merPoundage = "BKFEE(0.1,100,0.008,2)";
//		String merPoundage = "AMT*0.008";
//		String merPoundage = "MAX(0.2,AMT*0.005)";
		//String merPoundage = "MIN(74,AMT*0.0125)";
//		String merPoundage = "SGL(0)";
//		String merPoundage = "FLO(500,0.08,5000,0.06,50000,0.05,500000,0.03,0.01)";
//		String merPoundage = "FIX(20000,2,50000,3,100000,5,10)";
//		String merPoundage = "INC(2.5,20000)";
//		String merPoundage = "IFBIG(5000, AMT*0.08)";
//		String merPoundage = "MAX(0.2,AMT*0.005)";
//		String tradeAmount = "-001";
//		System.out.println(getMerFee(tradeAmount, merPoundage));
	}
}
