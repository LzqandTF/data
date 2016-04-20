package cn.com.chinaebi.dz.object.dao;

import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.MerBalance;
import cn.com.chinaebi.dz.object.MerBasic;
import cn.com.chinaebi.dz.object.MerBilling;
import cn.com.chinaebi.dz.object.OriginalBeijingbankLst;
import cn.com.chinaebi.dz.object.base.BaseTradeLstDAO;
import cn.com.chinaebi.dz.util.DYDataUtil;
import cn.com.chinaebi.dz.util.PoundageCalculate;
import cn.com.chinaebi.dz.util.StringPingJie;


public class TradeLstDAO extends BaseTradeLstDAO implements cn.com.chinaebi.dz.object.dao.iface.TradeLstDAO {

	private Log logger = LogFactory.getLog(getClass());
	
	private static cn.com.chinaebi.dz.object.dao.iface.MerFundStanceDAO merFundStanceDAO = cn.com.chinaebi.dz.object.dao.MerFundStanceDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.MerBalanceDAO merBalanceDAO = cn.com.chinaebi.dz.object.dao.MerBalanceDAO.getInstance();
	private cn.com.chinaebi.dz.object.dao.iface.SettleMerchantMatchTableDAO settleMerchantMatchTableDAO = SettleMerchantMatchTableDAO.getInstance();
	private cn.com.chinaebi.dz.object.dao.iface.MerBasicDAO merBasicDAO = MerBasicDAO.getInstance();
	private cn.com.chinaebi.dz.object.dao.iface.MerBillingDAO merBillingDAO = MerBillingDAO.getInstance();
	public TradeLstDAO () {}
	
	public TradeLstDAO (Session session) {
		super(session);
	}

	/**
	 * 数据分割
	 */
	@Override
	public boolean splitData(String tradeTime,String tableName,int deductSysId,int bank_id) {
		Session session = null;
		boolean flag = false;
		int split_count = -1;
		int no_split_count = -1;
		int count = -1;
		SimpleDateFormat simpleDateFormat = DYDataUtil.getSimpleDateFormat(DYDataUtil.DATE_FORMAT_1);
		List<OriginalBeijingbankLst> listObj = new ArrayList<OriginalBeijingbankLst>();
		Transaction transaction = null;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			if (session != null) {
				
				Date startTime = DYDataUtil.getformatConversionDate3(tradeTime);
				Date endTime = DYDataUtil.getformatConversionDate4(tradeTime);
				StringBuffer buffer = new StringBuffer("select trade_id,terminal_id,terminal_info,terminal_type,trade_time,out_account,out_account_type,out_acc_valid_time,out_account_info,out_account_info2,out_account_desc,in_account,in_card_name,in_bank_id,trade_amount,trade_fee,trade_currency,trade_result,req_sys_id,req_sys_stance,req_mer_code,req_mer_term_id,req_response,deduct_sys_id,deduct_sys_stance,deduct_mer_code,deduct_mer_term_id,deduct_roll_bk,deduct_roll_bk_result,deduct_roll_bk_reason,deduct_roll_bk_response,deduct_roll_bk_stance,trade_type,msg_num,pass_wd_mode,req_type,req_input_type,req_time,trade_req_method,trade_desc,in_account_type,trade_other_info,deduct_stlm_date,deduct_sys_time,gain_sys_id,gain_sys_stance,gain_mer_code,gain_mer_term_id,gain_sys_response,gain_result,gain_trade_amount,gain_sys_reference,nii,authorization_code,additional_response_data,additional_data,boc,custom_define_info,original_trans_info,req_process,deduct_sys_reference,trademsg_type,deduct_rollbk_sys_reference,mer_name,acqInstIdCode,fwdInstIdCode,deduct_rollbk_sys_time,agentId,whetherzero,whetherValid,ic_card_ser_no,ic_read_and_condition,fee_formula from ");
				buffer.append(tableName);
				buffer.append(" WHERE trade_time BETWEEN ? and ? and deduct_sys_id = ? and deduct_roll_bk = 1 AND (deduct_result = 1 or deduct_result = 0 or deduct_result = 2)");
				SQLQuery sqlQuery = session.createSQLQuery(buffer.toString());
				sqlQuery.setParameter(0, startTime);
				sqlQuery.setParameter(1, endTime);
				sqlQuery.setParameter(2, deductSysId);
				List list = sqlQuery.list();
				for (Object object : list) {
					Object[] obj = (Object[])object;
					OriginalBeijingbankLst tradeLst = new OriginalBeijingbankLst();
					tradeLst.setId(String.valueOf(obj[0]));
					tradeLst.setTerminalId(String.valueOf(obj[1]));
					tradeLst.setTerminalInfo(String.valueOf(obj[2]));
					tradeLst.setTerminalType(obj[3]==null?null:Short.valueOf(String.valueOf(obj[3])));
					tradeLst.setTradeTime(obj[4] == null ? null : simpleDateFormat.parse(String.valueOf(obj[4])));
					tradeLst.setOutAccount(String.valueOf(obj[5]));
					tradeLst.setOutAccountType(String.valueOf(obj[6]));
					tradeLst.setOutAccValidTime(obj[7] == null ? null : simpleDateFormat.parse(String.valueOf(obj[7])));
					tradeLst.setOutAccountInfo(obj[8] == null ? null : String.valueOf(obj[8]));
					tradeLst.setOutAccountInfo2(obj[9] == null ? null : String.valueOf(obj[9]));
					tradeLst.setOutAccountDesc(obj[10] == null ? null : String.valueOf(obj[10]));
					tradeLst.setInAccount(obj[11] == null ? null : String.valueOf(obj[11]));
					tradeLst.setInCardName(obj[12] == null ? null : String.valueOf(obj[12]));
					tradeLst.setInBankId(obj[13] == null ? null : String.valueOf(obj[13]));
					tradeLst.setTradeAmount(obj[14]==null ? null:Long.valueOf(String.valueOf(obj[14])));
					tradeLst.setTradeFee(obj[15] == null ? null : String.valueOf(obj[15]));
					tradeLst.setTradeCurrency(obj[16] == null ? null : Short.valueOf(String.valueOf(obj[16])));
					tradeLst.setTradeResult(obj[17] == null ? null : Integer.valueOf(String.valueOf(obj[17])));
					tradeLst.setReqSysId(obj[18] == null ? null : Integer.valueOf(String.valueOf(obj[18])));
					tradeLst.setReqSysStance(String.valueOf(obj[19]));
					tradeLst.setReqMerCode(String.valueOf(obj[20]));
					tradeLst.setReqMerTermId(String.valueOf(obj[21]));
					tradeLst.setReqResponse(String.valueOf(obj[22]));
					tradeLst.setDeductSysId(obj[23] == null ? null : Integer.valueOf(String.valueOf(obj[23])));
					tradeLst.setDeductSysStance(String.valueOf(obj[31]));//String.valueOf(obj[24])
					tradeLst.setDeductMerCode(String.valueOf(obj[25]));
					tradeLst.setDeductMerTermId(String.valueOf(obj[26]));
					tradeLst.setDeductRollBk(obj[27] == null ? null : Boolean.valueOf(String.valueOf(obj[27])));
					tradeLst.setDeductRollBkResult(obj[28] == null ? null : Short.valueOf(String.valueOf(obj[28])));
					tradeLst.setDeductRollBkReason(String.valueOf(obj[29]));
					tradeLst.setDeductRollBkResponse(String.valueOf(obj[30]));
					tradeLst.setDeductRollBkStance(String.valueOf(obj[31]));
					tradeLst.setTradeType(obj[32] == null ? null : Integer.valueOf(String.valueOf(obj[32])));
					tradeLst.setMsgNum(obj[33] == null ? null : Integer.valueOf(String.valueOf(obj[33])));//?
					tradeLst.setPassWdMode(obj[34] == null ? null : String.valueOf(obj[34]));
					tradeLst.setReqType(String.valueOf(obj[35]));
					tradeLst.setReqInputType(String.valueOf(obj[36]));
					tradeLst.setReqTime(obj[37] == null ? null : simpleDateFormat.parse(String.valueOf(obj[37])));
					tradeLst.setTradeReqMethod(String.valueOf(obj[38]));
					tradeLst.setTradeDesc(String.valueOf(obj[39]));
					tradeLst.setInAccountType(obj[40] == null ? null : Integer.parseInt(String.valueOf(obj[40])));//?
					tradeLst.setTradeOtherInfo(obj[41] == null ? null : String.valueOf(obj[41]));
					tradeLst.setDeductStlmDate(obj[42] == null ? null : simpleDateFormat.parse(String.valueOf(obj[42])));
					tradeLst.setDeductSysTime(obj[43] == null ? null : simpleDateFormat.parse(String.valueOf(obj[43])));
					tradeLst.setGainSysId(obj[44] == null ? null : Integer.valueOf(String.valueOf(obj[44])));//?
					tradeLst.setGainSysStance(obj[45] == null ? null : String.valueOf(obj[45]));
					tradeLst.setGainMerCode(obj[46] == null ? null : String.valueOf(obj[46]));
					tradeLst.setGainMerTermId(obj[47] == null ? null : String.valueOf(obj[47]));
					tradeLst.setGainSysResponse(obj[48] == null ? null : String.valueOf(obj[48]));
					tradeLst.setGainResult(obj[49] == null ? null : Integer.valueOf(String.valueOf(obj[49])));//?
					tradeLst.setGainTradeAmount(obj[50] == null ? null : Long.valueOf(String.valueOf(obj[50])));//?
					tradeLst.setGainSysReference(obj[51] == null ? null : String.valueOf(obj[51]));
					tradeLst.setNii(obj[52] == null ? null : String.valueOf(obj[52]));
					tradeLst.setAuthorizationCode(obj[53] == null ? null : String.valueOf(obj[53]));
					tradeLst.setAdditionalResponseData(obj[54] == null ? null : String.valueOf(obj[54]));
					tradeLst.setAdditionalData(obj[55] == null ? null : String.valueOf(obj[55]));
					tradeLst.setBoc(obj[56] == null ? null : String.valueOf(obj[56]));
					tradeLst.setCustomDefineInfo(obj[57] == null ? null : String.valueOf(obj[57]));
					tradeLst.setOriginalTransInfo(obj[58] == null ? null : String.valueOf(obj[58]));
					tradeLst.setReqProcess(String.valueOf(obj[59]));
					tradeLst.setDeductSysReference(obj[60] == null ? null : String.valueOf(obj[60]));
					tradeLst.setTrademsgType(obj[61] == null ? null : Integer.valueOf(String.valueOf(obj[61])));
					tradeLst.setDeductRollbkSysReference(obj[62] == null ? null : String.valueOf(obj[62]));
					tradeLst.setMerName(String.valueOf(obj[63]));
					tradeLst.setAcqInstIdCode(obj[64] == null ? null : String.valueOf(obj[64]));
					tradeLst.setFwdInstIdCode(obj[65] == null ? null : String.valueOf(obj[65]));
					tradeLst.setDeductRollbkSysTime(obj[66] == null ? null : simpleDateFormat.parse(String.valueOf(obj[66])));
					tradeLst.setAgentId(String.valueOf(obj[67]));
					tradeLst.setWhetherzero(String.valueOf(obj[68]));
					tradeLst.setWhetherValid(obj[69] == null ? null : Boolean.valueOf(String.valueOf(obj[69])));
					tradeLst.setIcCardSerNo(obj[70] == null ? null : String.valueOf(obj[70]));
					tradeLst.setIcReadAndCondition(obj[71] == null ? null : String.valueOf(obj[71]));
					tradeLst.setFeeFormula(obj[72] == null ? null : String.valueOf(obj[72]));
					tradeLst.setBankId(bank_id);
					listObj.add(tradeLst);
				}
				
				int _trademsg_type = 2;
				for (OriginalBeijingbankLst beijingbankLst : listObj) {
					buffer.setLength(0);
					if(beijingbankLst.getTrademsgType() == 2 ){/*消费*/
						_trademsg_type = 26;
					}else if(beijingbankLst.getTrademsgType() == 18 ){/*消费撤销*/
						_trademsg_type = 28;
					}else if(beijingbankLst.getTrademsgType() == 56 ){/*预授权完成*/
						_trademsg_type = 80;
					}else if(beijingbankLst.getTrademsgType() ==  58 ){/*预授权完成撤销*/
						_trademsg_type = 82;
					}
					
					SQLQuery tmp_split_tab = session.createSQLQuery("select count(*) from tmp_split_tab where trade_id = ?");
					tmp_split_tab.setString(0, beijingbankLst.getId());
					Integer tmp_split_tabCount = Integer.parseInt(tmp_split_tab.uniqueResult().toString());
					if(tmp_split_tabCount == 0){
						buffer.append("insert into ");
						buffer.append(tableName);
						buffer.append("(trade_id,terminal_id,terminal_info,terminal_type,trade_time,out_account,out_account_type,out_acc_valid_time,out_account_info,out_account_info2,");
						buffer.append("out_account_desc,in_account,in_card_name,in_bank_id,trade_amount,trade_fee,trade_currency,trade_result,req_sys_id,req_sys_stance,");
						buffer.append("req_mer_code,req_mer_term_id,req_response,deduct_sys_id,deduct_sys_stance,deduct_mer_code,deduct_mer_term_id,deduct_roll_bk,deduct_roll_bk_result,deduct_roll_bk_reason,");
						buffer.append("deduct_roll_bk_response,deduct_roll_bk_stance,trade_type,msg_num,pass_wd_mode,req_type,req_input_type,req_time,trade_req_method,trade_desc,");
						buffer.append("in_account_type,trade_other_info,deduct_stlm_date,deduct_sys_time,gain_sys_id,gain_sys_stance,gain_mer_code,gain_mer_term_id,gain_sys_response,gain_result,");
						buffer.append("gain_trade_amount,gain_sys_reference,nii,authorization_code,additional_response_data,additional_data,boc,custom_define_info,original_trans_info,req_process,");
						buffer.append("deduct_sys_reference,trademsg_type,deduct_rollbk_sys_reference,mer_name,acqInstIdCode,fwdInstIdCode,deduct_rollbk_sys_time,agentId,whetherzero,whetherValid,");
						buffer.append("ic_card_ser_no,ic_read_and_condition,fee_formula,bank_id)");
						buffer.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						SQLQuery insert = session.createSQLQuery(buffer.toString());
						insert.setParameter(0, UUID.randomUUID().toString().replaceAll("-", ""));
						insert.setParameter(1, beijingbankLst.getTerminalId());
						insert.setParameter(2, beijingbankLst.getTerminalInfo());
						insert.setParameter(3, beijingbankLst.getTerminalType());
						insert.setParameter(4, beijingbankLst.getTradeTime());
						insert.setParameter(5, beijingbankLst.getOutAccount());
						insert.setParameter(6, (beijingbankLst.getOutAccountType()==null || "null".equals(beijingbankLst.getOutAccountType()))?"":beijingbankLst.getOutAccountType());
						insert.setParameter(7, beijingbankLst.getOutAccValidTime());
						insert.setParameter(8, beijingbankLst.getOutAccountInfo());
						insert.setParameter(9, beijingbankLst.getOutAccountInfo2());
						insert.setParameter(10, beijingbankLst.getOutAccountDesc());
						insert.setParameter(11, beijingbankLst.getInAccount());
						insert.setParameter(12, beijingbankLst.getInCardName());
						insert.setParameter(13, beijingbankLst.getInBankId());
						insert.setParameter(14, beijingbankLst.getTradeAmount());
						insert.setParameter(15, beijingbankLst.getTradeFee());
						insert.setParameter(16, beijingbankLst.getTradeCurrency());
						insert.setParameter(17, beijingbankLst.getTradeResult());
						insert.setParameter(18, beijingbankLst.getReqSysId());
						insert.setParameter(19, beijingbankLst.getReqSysStance());
						insert.setParameter(20, beijingbankLst.getReqMerCode());
						insert.setParameter(21, beijingbankLst.getReqMerTermId());
						insert.setParameter(22, beijingbankLst.getReqResponse());
						insert.setParameter(23, beijingbankLst.getDeductSysId());
						insert.setParameter(24, beijingbankLst.getDeductSysStance());
						insert.setParameter(25, beijingbankLst.getDeductMerCode());
						insert.setParameter(26, beijingbankLst.getDeductMerTermId());
						insert.setParameter(27, beijingbankLst.isDeductRollBk());
						insert.setParameter(28, beijingbankLst.getDeductRollBkResult());
						insert.setParameter(29, beijingbankLst.getDeductRollBkReason());
						insert.setParameter(30, beijingbankLst.getDeductRollBkResponse());
						insert.setParameter(31, beijingbankLst.getDeductRollBkStance());
						insert.setParameter(32, beijingbankLst.getTradeType());
						insert.setParameter(33, beijingbankLst.getMsgNum());
						insert.setParameter(34, beijingbankLst.getPassWdMode());
						insert.setParameter(35, beijingbankLst.getReqType());
						insert.setParameter(36, beijingbankLst.getReqInputType());
						insert.setParameter(37, beijingbankLst.getReqTime());
						insert.setParameter(38, beijingbankLst.getTradeReqMethod());
						insert.setParameter(39, beijingbankLst.getTradeDesc());
						insert.setParameter(40, beijingbankLst.getInAccountType());
						insert.setParameter(41, beijingbankLst.getTradeOtherInfo());
						insert.setParameter(42, beijingbankLst.getDeductStlmDate());
						insert.setParameter(43, beijingbankLst.getDeductSysTime());
						insert.setParameter(44, beijingbankLst.getGainSysId());
						insert.setParameter(45, beijingbankLst.getGainSysStance());
						insert.setParameter(46, beijingbankLst.getGainMerCode());
						insert.setParameter(47, beijingbankLst.getGainMerTermId());
						insert.setParameter(48, beijingbankLst.getGainSysResponse());
						insert.setParameter(49, beijingbankLst.getGainResult());
						insert.setParameter(50, beijingbankLst.getGainTradeAmount());
						insert.setParameter(51, beijingbankLst.getGainSysReference());
						insert.setParameter(52, beijingbankLst.getNii());//?
						insert.setParameter(53, beijingbankLst.getAuthorizationCode());
						insert.setParameter(54, beijingbankLst.getAdditionalResponseData());
						insert.setParameter(55, beijingbankLst.getAdditionalData());
						insert.setParameter(56, beijingbankLst.getBoc());
						insert.setParameter(57, beijingbankLst.getCustomDefineInfo());
						insert.setParameter(58, beijingbankLst.getOriginalTransInfo());
						insert.setParameter(59, beijingbankLst.getReqProcess());
						insert.setParameter(60, beijingbankLst.getDeductSysReference());
						insert.setParameter(61, _trademsg_type);
						insert.setParameter(62, beijingbankLst.getDeductRollbkSysReference());
						insert.setParameter(63, beijingbankLst.getMerName());
						insert.setParameter(64, beijingbankLst.getAcqInstIdCode());
						insert.setParameter(65, beijingbankLst.getFwdInstIdCode());
						insert.setParameter(66, beijingbankLst.getDeductRollbkSysTime());
						insert.setParameter(67, beijingbankLst.getAgentId());
						insert.setParameter(68, beijingbankLst.getWhetherzero());
						insert.setParameter(69, 1);
						insert.setParameter(70, beijingbankLst.getIcCardSerNo());
						insert.setParameter(71, beijingbankLst.getIcReadAndCondition());
						insert.setParameter(72, beijingbankLst.getFeeFormula());
						insert.setParameter(73, beijingbankLst.getBankId());
						int _flag = insert.executeUpdate();
						
						SQLQuery tmp_split_tabInset = session.createSQLQuery("insert into tmp_split_tab(trade_id,trade_time,deduct_sys_id) values(?,?,?)");
						tmp_split_tabInset.setParameter(0, beijingbankLst.getId());
						tmp_split_tabInset.setParameter(1, beijingbankLst.getTradeTime());
						tmp_split_tabInset.setParameter(2, beijingbankLst.getDeductSysId());
						int tmp_split_tabFlag = tmp_split_tabInset.executeUpdate();
						
						SQLQuery tableName_update = session.createSQLQuery("update "+tableName+" set deduct_roll_bk = 0 where trade_id = ?");
						tableName_update.setString(0,  beijingbankLst.getId());
						tableName_update.executeUpdate();
					}
				}
				transaction.commit();
				flag = true;
			} else {
				logger.info("TradeLstDAO.splitData() 获取session为null");
			}
		} catch (Exception e) {
			logger.error("数据分割异常："+e);
			if (transaction != null) {
				transaction.rollback();
				logger.error("原始数据拆分出错：" + e);
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return flag;
	}

	/**
	 * 数据金额处理
	 */
	@Override
	public boolean handleMoney(String tradeTime, String tableName,int deduct_sys_id) {
		Session session = null;
		Transaction ts = null;
		boolean flag = false;
		try {
			logger.info("金额正负处理");
			session = this.getSession();
			if (session != null) {
				ts = session.beginTransaction();
				CallableStatement cs = (CallableStatement) session.connection().prepareCall("call proce_handle_money(?,?,?)");
				cs.setObject(1, tradeTime);
				cs.setObject(2, tableName);
				cs.setObject(3, deduct_sys_id);
				cs.execute();
				ts.commit();
				flag = true;
			} else {
				logger.info("TradeLstDAO.handleMoney() 获取session为null");
			}
		} catch (Exception e) {
			ts.rollback();
			logger.error("金额处理出错：" + e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return flag;
	}
	
	public int noDzdataCollectHanler(String tradeTime,String tableName,int deduct_sys_id,int bank_id){
		Session session = null;
		int count = -1;
		try {
			session = this.getSession();
			if(session  != null){
				CallableStatement cs = (CallableStatement) session.connection().prepareCall("call proce_original_NodzDatacollect_lst(?,?,?,?,?)");
				cs.setObject(1, tradeTime);
				cs.setObject(2, tableName);
				cs.setObject(3, bank_id);
				cs.setObject(4, deduct_sys_id);
				cs.registerOutParameter(5, Types.INTEGER);
				cs.execute();
				count = cs.getInt(5) <= 0 ? 0 : cs.getInt(5);
			}else{
				logger.error("TradeLstDAO.noDzdataCollectHanler() 获取session不能为空");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}finally{
			closeSession(session);
		}
		return count;
	}
	/**
	 * 数据汇总处理
	 * @return
	 */
	public int dataCollectHanler(String tradeTime,String tableName,int deduct_sys_id,int bank_id){
		Session session = null;
		int count = -1;
		try {
			session = this.getSession();
			if(session  != null){
				CallableStatement cs = (CallableStatement) session.connection().prepareCall("call proce_original_datacollect_lst(?,?,?,?,?)");
				cs.setObject(1, tradeTime);
				cs.setObject(2, tableName);
				cs.setInt(3, deduct_sys_id);
				cs.setInt(4, bank_id);
				cs.registerOutParameter(5, Types.INTEGER);
				cs.execute();
				count = cs.getInt(5) <= 0 ? 0 : cs.getInt(5);
			}else{
				logger.error("TradeLstDAO.dataCollectHanler() 获取session不能为空");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}finally{
			closeSession(session);
		}
		return count;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.com.chinaebi.dz.object.dao.iface.TradeLstDAO#dispenseWithHandle(java.lang.String, java.lang.String, int)
	 * tradeTime--交易日期
	 * tableName--数据表明
	 * flag--处理标志：1->自动定时任务执行，2->手动还原数据状态执行
	 */
	public boolean dispenseWithHandle(String tradeTime,String tableName,int flag_){
		Session session = null;
		Transaction ts = null;
		boolean flag = false;
		try {
			session = this.getSession();
			if (session != null) {
				ts = session.beginTransaction();
				CallableStatement cs = (CallableStatement) session.connection().prepareCall("call proce_dispense_with_handle(?,?,?)");
				cs.setObject(1, tradeTime);
				cs.setObject(2, tableName);
				cs.setObject(3, flag_);
				cs.execute();
				ts.commit();
				flag = true;
			} else {
				logger.info("TradeLstDAO.dispenseWithHandle() 获取session为null");
			}
		} catch (Exception e) {
			ts.rollback();
			logger.error("数据无需对账处理出错：" + e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return flag;
	}
	
	public int getTotalCountOfDzSucessDataOfOffLine(String date,List<String> codeArr,int generateNumber,boolean whethercreatefilebyrange){
		Session session = null;
		Integer totalCount = 0;
		try{
			session = this.getSession();
			Date startTime = DYDataUtil.getformatConversionDate3(date);
			Date endTime = DYDataUtil.getformatConversionDate4(date);
			StringBuffer sql = new StringBuffer("");
			sql.append(" where deduct_stlm_date BETWEEN ? and ? and whetherQs = ? ");
			if(generateNumber == 2){ //按商户生成
				if(whethercreatefilebyrange){ //true 按接口数据范围生成数据
					sql.append("and req_mer_code in (");
					if(codeArr != null && codeArr.size() > 0){
						for(int i=0;i<codeArr.size();i++){
							sql.append("'");
							sql.append(codeArr.get(i));
							sql.append("'");
							if(i != codeArr.size()-1){
								sql.append(",");
							}
						}
					}else{
						sql.append("''");
					}
					sql.append(") ");
				}else{ //不按接口数据范围生成数据
					sql.append("and req_mer_code not in (");
					if(codeArr != null && codeArr.size() > 0){
						for(int i=0;i<codeArr.size();i++){
							sql.append("'");
							sql.append(codeArr.get(i));
							sql.append("'");
							if(i != codeArr.size()-1){
								sql.append(",");
							}
						}
					}else{
						sql.append("''");
					}
					sql.append(") ");
				}
			}else if(generateNumber == 3){ //按交易码生成
				if(whethercreatefilebyrange){ //true 按接口数据范围生成数据
					sql.append(" and IF(CHAR_LENGTH(terminal_info) = 58 or CHAR_LENGTH(terminal_info) = 51 ,substring(terminal_info,27, 3) ,substring(terminal_info,35, 3) ) in (");
					if(codeArr != null && codeArr.size() > 0){
						for(int i=0;i<codeArr.size();i++){
							sql.append("'");
							sql.append(codeArr.get(i));
							sql.append("'");
							if(i != codeArr.size()-1){
								sql.append(",");
							}
						}
					}else{
						sql.append("''");
					}
					sql.append(") ");
				}else{
					sql.append(" and IF(CHAR_LENGTH(terminal_info) = 58 or CHAR_LENGTH(terminal_info) = 51 ,substring(terminal_info,27, 3) ,substring(terminal_info,35, 3) ) not in (");
					if(codeArr != null && codeArr.size() > 0){
						for(int i=0;i<codeArr.size();i++){
							sql.append("'");
							sql.append(codeArr.get(i));
							sql.append("'");
							if(i != codeArr.size()-1){
								sql.append(",");
							}
						}
					}else{
						sql.append("''");
					}
					sql.append(") ");
				}
			}
			logger.info("整理好的sql语句为"+sql.toString());
			SQLQuery cups_query = session.createSQLQuery("select count(*) from original_cups_lst "+sql.toString());
			cups_query.setParameter(0, startTime);
			cups_query.setParameter(1, endTime);
			cups_query.setInteger(2, 1);
			totalCount = totalCount + (cups_query.uniqueResult()==null?0:((BigInteger) cups_query.uniqueResult()).intValue());
			SQLQuery bjyh_query = session.createSQLQuery("select count(*) from original_beijingbank_lst "+sql.toString());
			bjyh_query.setParameter(0, startTime);
			bjyh_query.setParameter(1, endTime);
			bjyh_query.setInteger(2, 1);
			totalCount = totalCount + (bjyh_query.uniqueResult()==null?0:((BigInteger) bjyh_query.uniqueResult()).intValue());
			SQLQuery zxyh_query = session.createSQLQuery("select count(*) from original_zhongxingbank_lst "+sql.toString());
			zxyh_query.setParameter(0, startTime);
			zxyh_query.setParameter(1, endTime);
			zxyh_query.setInteger(2, 1);
			totalCount = totalCount + (zxyh_query.uniqueResult()==null?0:((BigInteger) zxyh_query.uniqueResult()).intValue());
			SQLQuery szzh_query = session.createSQLQuery("select count(*) from original_szzh_lst "+sql.toString());
			szzh_query.setParameter(0, startTime);
			szzh_query.setParameter(1, endTime);
			szzh_query.setInteger(2, 1);
			totalCount = totalCount + (szzh_query.uniqueResult()==null?0:((BigInteger) szzh_query.uniqueResult()).intValue());
			SQLQuery dljh_query = session.createSQLQuery("select count(*) from original_dljh_lst "+sql.toString());
			dljh_query.setParameter(0, startTime);
			dljh_query.setParameter(1, endTime);
			dljh_query.setInteger(2, 1);
			totalCount = totalCount + (dljh_query.uniqueResult()==null?0:((BigInteger) dljh_query.uniqueResult()).intValue());
		}catch(Exception e){
			logger.error(e);
			return 0;
		} finally {
			closeSession(session);
		}
		return totalCount;
	}
	/**
	 * 查询内部清算数据
	 * @param className 实体类名称
	 * @param status  查询数据状态
	 * @param tradeDate  查询数据日期
	 * @return
	 */
	public List<?> queryInnerClearData(Class<?> clas,String tableName,int inst_id,String status,String tradeDate,int bankType){
		Session session = null;
		List<?> listResult = null;
		try{
			
			logger.info("查询内部清算数据,参数: 数据表名----"+tableName+"-----渠道ID------"+inst_id+"------数据状态------"+status+"------交易日期-------"+tradeDate+"------网关类型--------"+bankType);
			
			session = this.getSession();
			Date startTime = DYDataUtil.getformatConversionDate3(tradeDate);
			Date endTime = DYDataUtil.getformatConversionDate4(tradeDate);
			StringBuffer sb = new StringBuffer("");
			sb.append("SELECT * FROM ");
			sb.append(tableName);
			sb.append(" WHERE ");
			if(bankType == 0){
				sb.append("trade_time BETWEEN ? AND ? AND trade_result = ? AND ");
				if(inst_id == 12){
					sb.append("gain_sys_id = ? ");
				}else{
					sb.append("deduct_sys_id = ? ");
				}
				sb.append(" ORDER BY trade_time ASC");
			}else if(bankType == 1){
				status = "2";
				sb.append("sys_date BETWEEN ? AND ? AND tstat = ? AND gid = ? ORDER BY sys_date ASC");
			}else{
				logger.debug("银行网关类型为"+bankType+",未匹配到现有网关渠道，请核实");
				return null;
			}
			
			SQLQuery query = session.createSQLQuery(sb.toString()).addEntity(clas);
			query.setParameter(0, startTime);
			query.setParameter(1, endTime);
			query.setString(2, status);
			query.setInteger(3, inst_id);
			listResult = query.list();
		}catch(Exception e){
			logger.error(e);
		} finally {
			closeSession(session);
		}
		return listResult;
	}
	/**
	 * 修改渠道内部交易数据表中对应交易结果的内部清算字段状态
	 * @param instInfo 扣款渠道对象
	 * @param status 交易结果
	 * @param tradeDate 交易日期
	 * @return
	 */
	public boolean updateInnerClearDataInnerJsStatus(String tableName,int inst_id,String status,String tradeDate,int bankType){
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		try{
			logger.info("改变内部清算数据结算状态,参数: 数据表名----"+tableName+"-----渠道ID------"+inst_id+"------数据状态------"+status+"------交易日期-------"+tradeDate+"------网关类型--------"+bankType);
			
			Date startTime = DYDataUtil.getformatConversionDate3(tradeDate);
			Date endTime = DYDataUtil.getformatConversionDate4(tradeDate);
			
			session = this.getSession();
			transaction = session.beginTransaction();
			
			StringBuffer buffer = new StringBuffer("");
			
			buffer.append("update ");
			buffer.append(tableName);
			buffer.append(" set whtherInnerJs = 1 WHERE ");
			if(bankType == 0){
				buffer.append("trade_time BETWEEN ? AND ? AND trade_result = ? AND ");
				if(inst_id == 12){
					buffer.append("gain_sys_id = ? ");
				}else{
					buffer.append("deduct_sys_id = ? ");
				}
				buffer.append(" ORDER BY trade_time ASC");
			}else if(bankType == 1){
				status = "2";
				buffer.append("sys_date BETWEEN ? AND ? AND tstat = ? AND gid = ? ORDER BY sys_date ASC");
			}else{
				logger.debug("银行网关类型为"+bankType+",未匹配到现有网关渠道，请核实");
				return false;
			}
			
			
			SQLQuery updateSql = session.createSQLQuery(buffer.toString());
			
			updateSql.setParameter(0, startTime);
			updateSql.setParameter(1, endTime);
			updateSql.setString(2, status);
			updateSql.setInteger(3, inst_id);
			
			int result = updateSql.executeUpdate();
			
			if(result >= 0){
				flag = true;
				transaction.commit();
			}
			
		}catch(Exception e){
			logger.error(e);
		}finally{
			closeSession(session);
		}
		return flag;
	}

	@Override
	public Object[] findPosChanneltotall(String date, String mer_code,
			 boolean whetherQs,String tableName) {
		Session session = null;
		Object[] obj = new Object[11];
		try{
			Date startTime = DYDataUtil.getformatConversionDate3(date);
			Date endTime = DYDataUtil.getformatConversionDate4(date);
			session = this.getSession();
			StringBuffer sb = new StringBuffer();
			//查询支付统计
			sb.append("select IFNULL(SUM(trade_amount),0),count(*),IFNULL(sum(CONVERT(zf_file_fee,DECIMAL(20,2))),0),IFNULL(sum(CONVERT(zf_fee,DECIMAL(20,2))),0),IFNULL(sum(mer_fee),0) from ");
			sb.append(tableName);
			sb.append(" where deduct_stlm_date BETWEEN ? and ? and whetherQs = ? and whetherTk = 0");
			String[] merCodeStr = mer_code.split(",");
			if(merCodeStr.length > 1){
				sb.append(" and req_mer_code in(?)");
			}else{
				sb.append(" and req_mer_code = ?");
			}
			SQLQuery not_tk_totall = session.createSQLQuery(sb.toString());
			not_tk_totall.setParameter(0, startTime);
			not_tk_totall.setParameter(1, endTime);
			not_tk_totall.setParameter(2, whetherQs);
			not_tk_totall.setParameter(3, mer_code);
			Object no_tk_result = not_tk_totall.uniqueResult();
			if(no_tk_result != null){
				Object[] valObj = (Object[])no_tk_result;
				obj[0] = valObj[0];
				obj[1] = valObj[1];
				obj[2] = valObj[2];
				obj[3] = valObj[3];
				obj[4] = valObj[4];
			}
			sb.setLength(0);
			//查询退款统计
			sb.append("select IFNULL(SUM(trade_amount),0),count(*),IFNULL(sum(CONVERT(zf_file_fee,DECIMAL(20,2))),0),IFNULL(sum(CONVERT(zf_fee,DECIMAL(20,2))),0),IFNULL(sum(mer_fee),0) from ");
			sb.append(tableName);
			sb.append(" where deduct_stlm_date BETWEEN ? and ? and whetherQs = ? and whetherTk = 1");
			if(merCodeStr.length > 1){
				sb.append(" and req_mer_code in(?)");
			}else{
				sb.append(" and req_mer_code = ?");
			}
			SQLQuery tk_totall = session.createSQLQuery(sb.toString());
			tk_totall.setParameter(0, startTime);
			tk_totall.setParameter(1, endTime);
			tk_totall.setParameter(2, whetherQs);
			tk_totall.setParameter(3, mer_code);
			Object tk_result = tk_totall.uniqueResult();
			if(tk_result != null){
				Object[] valObj = (Object[])tk_result;
				obj[5] = valObj[0];
				obj[6] = valObj[1];
				obj[7] = valObj[2];
				obj[8] = valObj[3];
				obj[9] = valObj[4];
			}
			sb.setLength(0);
			session.clear();
			session.flush();
			//查询统计正交易总笔数
			sb.append("select count(*) from ");
			sb.append(tableName);
			sb.append(" where deduct_stlm_date BETWEEN ? and ? and whetherQs = ? and whetherTk = 0 and trade_amount > 0");
			if(merCodeStr.length > 1){
				sb.append(" and req_mer_code in(?)");
			}else{
				sb.append(" and req_mer_code = ?");
			}
			SQLQuery z_count = session.createSQLQuery(sb.toString());
			z_count.setParameter(0, startTime);
			z_count.setParameter(1, endTime);
			z_count.setParameter(2, whetherQs);
			z_count.setParameter(3, mer_code);
			Object z_count_result = z_count.uniqueResult();
			if(z_count_result != null){
				sb.setLength(0);
				session.clear();
				session.flush();
				//查询统计反交易总笔数
				sb.append("select count(*) from ");
				sb.append(tableName);
				sb.append(" where deduct_stlm_date BETWEEN ? and ? and whetherQs = ? and whetherTk = 0 and trade_amount < 0");
				if(merCodeStr.length > 1){
					sb.append(" and req_mer_code in(?)");
				}else{
					sb.append(" and req_mer_code = ?");
				}
				SQLQuery f_count = session.createSQLQuery(sb.toString());
				f_count.setParameter(0, startTime);
				f_count.setParameter(1, endTime);
				f_count.setParameter(2, whetherQs);
				f_count.setParameter(3, mer_code);
				Object f_count_result = f_count.uniqueResult();
				if(f_count_result != null){
					//obj[10] = PoundageCalculate.sub(z_count_result.toString(), f_count_result.toString()).intValue();
					obj[10] = Integer.valueOf(z_count_result.toString()) - Integer.valueOf(f_count_result.toString());
				}
			}
		}catch(Exception e){
			logger.error(e);
		}finally{
			closeSession(session);
		}
		return obj;
	}
	
	@Override
	public boolean saveMerchantSettleStatistics(Object... objects) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
//			objects 下标说明
//			objects[0] : 渠道号
//			objects[1] : 结算商户号
//			objects[2] ：结算商户类型
//			objects[3] ：清算日期
//			objects[4] : 交易金额
//			objects[5] ：交易笔数
//			objects[9] ：银行手续费
//			objects[14] ：支付手续费
//			objects[8]  ：商户手续费
//			objects[6]  ：退款金额
//			objects[7]  ：退款笔数
//			objects[12] ：退回银行手续费
//			objects[15] ：退回支付手续费
//			objects[10] ：退回商户手续费
//			objects[16] ：该商户是否存在对应结算商户中
//			objects[17] ：银行网关
//			objects[18] ：正交易总笔数-反交易总笔数 = 轧差笔数
			SQLQuery selSqlQuery = session.createSQLQuery("select trade_amount,trade_count,refund_amount,refund_count,mer_fee,system_fee,mer_refund_fee,settle_amount,system_refund_fee,zf_fee,refund_zf_fee,trade_gc_count from merchant_settle_statistics where mer_code = ? and deduct_stlm_date = ? and inst_id = ? and inst_type = ? and data_status = 0");
			selSqlQuery.setString(0, objects[1].toString());
			selSqlQuery.setInteger(1, Integer.valueOf(objects[3].toString()));
			selSqlQuery.setInteger(2, Integer.valueOf(objects[0].toString()));
			selSqlQuery.setInteger(3, Integer.valueOf(objects[13].toString()));
			Object objArr = selSqlQuery.uniqueResult();
			
			boolean settle_flag = Boolean.valueOf(objects[16].toString());
			if(objArr == null){
				String tradeAmount = String.valueOf(Double.valueOf(objects[4].toString())/100);
				String refund_amount = String.valueOf(Double.valueOf(objects[6].toString())/100);
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("insert into merchant_settle_statistics(");
				stringBuffer.append("inst_id,mer_code,mer_type,deduct_stlm_date,trade_amount,trade_count,refund_amount,refund_count,mer_fee,system_fee,mer_refund_fee,settle_amount,system_refund_fee,whetherJs,data_status,inst_type,zf_fee,refund_zf_fee,bank_id,js_date,trade_gc_count");
				stringBuffer.append(") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				SQLQuery sqlQuery = session.createSQLQuery(stringBuffer.toString());
				sqlQuery.setParameter(0, objects[0]);
				sqlQuery.setParameter(1, objects[1]);
				sqlQuery.setParameter(2, objects[2]);
				sqlQuery.setParameter(3, objects[3]);
				sqlQuery.setParameter(4, tradeAmount);
				sqlQuery.setParameter(5, objects[5]);
				sqlQuery.setParameter(6, refund_amount);
				sqlQuery.setParameter(7, objects[7]);
				sqlQuery.setParameter(8, objects[8]);
				sqlQuery.setParameter(9, objects[9]);
				sqlQuery.setParameter(10, objects[10]);
				sqlQuery.setParameter(11, objects[11]);
				sqlQuery.setParameter(12, objects[12]);
				sqlQuery.setParameter(13, 0);
				sqlQuery.setParameter(14, 0);
				sqlQuery.setParameter(15, objects[13]);
				sqlQuery.setParameter(16, objects[14]);
				sqlQuery.setParameter(17, objects[15]);
				sqlQuery.setParameter(18, objects[17]);
				sqlQuery.setParameter(19, objects[3]);
				sqlQuery.setParameter(20, objects[18]);
				int addCount = sqlQuery.executeUpdate();
				/**
				 * obj[0] : 交易总金额
				 * obj[1] : 交易笔数
				 * obj[2] : 系统手续费
				 * obj[3] : 商户手续费
				 * obj[4] : 退款总金额
				 * obj[5] : 退款笔数
				 * obj[6] : 退款系统手续费
				 * obj[7] : 退款商户手续费
				 */
				if(addCount > 0){
					transaction.commit();
					flag = true;
				}
			}else{
				String trade_amount = "0.00";
				Integer trade_count = 0;
				String system_fee = "0.00";
				String zf_fee = "0.00";
				String mer_fee = "0.00";
				String refund_amount = "0.00";
				Integer refund_count = 0;
				String system_refund_fee = "0.00";
				String refund_zf_fee = "0.00";
				String mer_refund_fee = "0.00";
				String settle_amount = "0.00";
				Integer trade_gc_count = 0;
				if(settle_flag){
					Object[] obj = (Object[])objArr;
					//支付
					String trade_amount_ = String.valueOf(Double.valueOf(objects[4].toString())/100);
					trade_amount = PoundageCalculate.add(obj[0].toString(), trade_amount_).toString();
					trade_count = Integer.valueOf(obj[1].toString())+Integer.valueOf(objects[5].toString());
					system_fee = PoundageCalculate.add(obj[5].toString(), objects[9].toString()).toString();
					zf_fee = PoundageCalculate.add(obj[9].toString(), objects[14].toString()).toString();
					mer_fee = PoundageCalculate.add(obj[4].toString(), objects[8].toString()).toString();
					//退款
					String refund_amount_ = String.valueOf(Double.valueOf(objects[6].toString())/100);
					refund_amount = PoundageCalculate.add(obj[2].toString(), refund_amount_).toString();
					refund_count = Integer.valueOf(obj[3].toString())+Integer.valueOf(objects[7].toString());
					system_refund_fee = PoundageCalculate.add(obj[8].toString(), objects[12].toString()).toString();
					refund_zf_fee = PoundageCalculate.add(obj[10].toString(), objects[15].toString()).toString();
					mer_refund_fee = PoundageCalculate.add(obj[6].toString(), objects[10].toString()).toString();
					//轧差之后总笔数
					trade_gc_count = Integer.valueOf(obj[11].toString())+Integer.valueOf(objects[18].toString());
				}else{
					//支付
					trade_amount = String.valueOf(Double.valueOf(objects[4].toString())/100);
					trade_count= Integer.valueOf(objects[5].toString());
					system_fee = objects[9].toString();
					zf_fee = objects[14].toString();
					mer_fee = objects[8].toString();
					//退款
					refund_amount = String.valueOf(Double.valueOf(objects[6].toString())/100);
					refund_count = Integer.valueOf(objects[7].toString());
					system_refund_fee = objects[12].toString();
					refund_zf_fee = objects[15].toString();
					mer_refund_fee = objects[10].toString();
					trade_gc_count = Integer.valueOf(objects[18].toString());
				}
				StringBuffer stringBuffer = new StringBuffer();
				
				stringBuffer.append("update merchant_settle_statistics set ");
				stringBuffer.append("inst_id = ?,");
				stringBuffer.append("mer_code = ?,");
				stringBuffer.append("mer_type = ?,");
				stringBuffer.append("deduct_stlm_date = ?,");
				stringBuffer.append("trade_amount = ?,");
				stringBuffer.append("trade_count = ?,");
				stringBuffer.append("refund_amount = ?,");
				stringBuffer.append("refund_count = ?,");
				stringBuffer.append("mer_fee = ?,");
				stringBuffer.append("system_fee = ?,");
				stringBuffer.append("mer_refund_fee = ?,");
				stringBuffer.append("settle_amount = ?,");
				stringBuffer.append("system_refund_fee = ?,");
				stringBuffer.append("whetherJs = ?,");
				stringBuffer.append("data_status = ?,");
				stringBuffer.append("inst_type = ?,");
				stringBuffer.append("zf_fee = ?,");
				stringBuffer.append("refund_zf_fee = ?,");
				stringBuffer.append("trade_gc_count = ?");
				stringBuffer.append(" where deduct_stlm_date = ? and mer_code = ? and inst_id = ? and inst_type = ? and data_status = 0");
				SQLQuery sqlQuery = session.createSQLQuery(stringBuffer.toString());
				sqlQuery.setParameter(0, objects[0]);
				sqlQuery.setParameter(1, objects[1]);
				sqlQuery.setParameter(2, objects[2]);
				sqlQuery.setParameter(3, objects[3]);
				sqlQuery.setParameter(4, trade_amount);
				sqlQuery.setParameter(5, trade_count);
				sqlQuery.setParameter(6, refund_amount);
				sqlQuery.setParameter(7, refund_count);
				sqlQuery.setParameter(8, mer_fee);
				sqlQuery.setParameter(9, system_fee);
				sqlQuery.setParameter(10, mer_refund_fee);
				sqlQuery.setParameter(11, settle_amount);
				sqlQuery.setParameter(12, system_refund_fee);
				sqlQuery.setParameter(13, 0);
				sqlQuery.setParameter(14, 0);
				sqlQuery.setParameter(15, objects[13]);
				sqlQuery.setParameter(16, zf_fee);
				sqlQuery.setParameter(17, refund_zf_fee);
				sqlQuery.setParameter(18, trade_gc_count);
				//where
				sqlQuery.setParameter(19, objects[3]);
				sqlQuery.setParameter(20, objects[1]);
				sqlQuery.setParameter(21, objects[0]);
				sqlQuery.setParameter(22, objects[13]);
				int updateCount = sqlQuery.executeUpdate();
				if(updateCount > 0){
					transaction.commit();
					flag = true;
				}
			}
			
		} catch (Exception e) {
			transaction.rollback();
			logger.error(e);
		}finally{
			closeSession(session);
		}
		return flag;
	}

	@Override
	public String getOriginalTradeTimeOfCancel(String sysStance,
			int tradeMsgType, String date,String tableName) {
		Session session = null;
		String original_trade_time = "";
		if(StringUtils.isNotBlank(sysStance)){
			if(tradeMsgType == 18){//撤销
				sysStance = sysStance.substring(sysStance.length()-6, sysStance.length());
			}else if(tradeMsgType == 20){//退货
				sysStance = sysStance.substring(sysStance.length()-10, sysStance.length()-4);
			}else if(tradeMsgType == 58){//预授权完成撤销
				sysStance = sysStance.substring(sysStance.length()-6, sysStance.length());
			}else if(tradeMsgType == 54){//预授权撤销
				sysStance = sysStance.substring(sysStance.length()-6, sysStance.length());
			}
			try{
				session = this.getSession();
				Date startTime = DYDataUtil.getformatConversionDate3(date);
				Date endTime = DYDataUtil.getformatConversionDate4(date);
				SQLQuery query = session.createSQLQuery("select deduct_sys_time from "+tableName+" where deduct_sys_stance = ? and deduct_stlm_date BETWEEN ? and ? ");
				query.setString(0,sysStance);
				query.setParameter(1, startTime);
				query.setParameter(2, endTime);
				original_trade_time = query.uniqueResult()+"";
			}catch(Exception e){
				logger.error(e);
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}
		if(StringUtils.isNotBlank(original_trade_time) && !("null".equals(original_trade_time))){
			original_trade_time = original_trade_time.substring(0, original_trade_time.length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
		}
		return original_trade_time;
	}

	@Override
	public Object[] findRyfChanneltotall(Integer date, String mer_code,
			boolean whetherQs, String tableName) {
		Session session = null;
		Object[] obj = new Object[8];
		try{
			
			String[] tableNameArr = tableName.split(",");
			if(tableNameArr.length != 2){
				return null;
			}
			StringBuffer sb = new StringBuffer();
			//查询支付统计
			sb.append("select IFNULL(SUM(amount),0),count(*),IFNULL(sum(bank_fee),0),IFNULL(SUM(fee_amt),0) from");
			sb.append(tableNameArr[0]);
			sb.append(" where sys_date = ? and whetherQs = ? and mid = ?");
			session = this.getSession();
			SQLQuery not_tk_totall = session.createSQLQuery(sb.toString());
			not_tk_totall.setParameter(0, date);
			not_tk_totall.setParameter(1, whetherQs);
			not_tk_totall.setParameter(2, mer_code);
			Object no_tk_result = not_tk_totall.uniqueResult();
			if(no_tk_result != null){
				Object[] valObj = (Object[])no_tk_result;
				obj[0] = valObj[0];
				obj[1] = valObj[1];
				obj[2] = valObj[2];
				obj[3] = valObj[3];
			}
			sb.setLength(0);
			//查询退款统计
			sb.append("select IFNULL(SUM(ref_amt),0),count(*),IFNULL(sum(mer_fee),0),IFNULL(SUM(bk_fee),0) from");
			sb.append(tableNameArr[1]);
			sb.append(" where ref_date = ? and whetherQs = ? and mid = ?");
			session = this.getSession();
			SQLQuery tk_totall = session.createSQLQuery(sb.toString());
			tk_totall.setParameter(0, date);
			tk_totall.setParameter(1, whetherQs);
			tk_totall.setParameter(2, mer_code);
			Object tk_result = tk_totall.uniqueResult();
			if(tk_result != null){
				Object[] valObj = (Object[])tk_result;
				obj[4] = valObj[0];
				obj[5] = valObj[1];
				obj[6] = valObj[2];
				obj[7] = valObj[3];
			}
		}catch(Exception e){
			logger.error(e);
		}finally{
			closeSession(session);
		}
		return obj;
	}

	@Override
	public List<Object> getChannelMerCode(String tableName, boolean whetherQs,
			String date, String dataColumn) {
		Session session = null;
		List<Object> list = null;
		try{
			if(StringUtils.isNotBlank(tableName)){
				session = this.getSession();
				Date startTime = DYDataUtil.getformatConversionDate3(date);
				Date endTime = DYDataUtil.getformatConversionDate4(date);
				StringBuffer buffer = new StringBuffer();
				buffer.append("select DISTINCT req_mer_code from ");
				buffer.append(tableName);
				buffer.append(" where whetherQs = ? and ");
				buffer.append(dataColumn);
				buffer.append(" BETWEEN ? and ?");
				
				SQLQuery query = session.createSQLQuery(buffer.toString());
				query.setBoolean(0,whetherQs);
				query.setParameter(1, startTime);
				query.setParameter(2, endTime);
				list = query.list();
			}else{
				logger.error("线下查询渠道交易商户日期不能为空");
			}
		}catch(Exception e){
			logger.error(e);
		}finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}
	
	@Override
	public List<Object> getRytChannelMerCode(String tableName,
			boolean whetherQs, Integer date, String hlogDateColumn,String refundLogDateColumn) {
		Session session = null;
		List<Object> list = null;
		try{
			if(StringUtils.isNotBlank(tableName)){
				String[] strArr = tableName.split(",");
				if(strArr.length != 2){
					return null;
				}
				session = this.getSession();
				StringBuffer buffer = new StringBuffer();
				buffer.append("select m.mid from (");
					buffer.append("select DISTINCT mid from ");
					buffer.append(strArr[0]);
					buffer.append(" where whetherQs = ? and ");
					buffer.append(hlogDateColumn);
					buffer.append(" = ? ");
					buffer.append("union all ");
					buffer.append("select DISTINCT mid from ");
					buffer.append(strArr[1]);
					buffer.append(" where whetherQs = ? and ");
					buffer.append(refundLogDateColumn);
					buffer.append(" = ?");
				buffer.append(") m");
				
				SQLQuery query = session.createSQLQuery(buffer.toString());
				query.setBoolean(0,whetherQs);
				query.setInteger(1, date);
				query.setBoolean(2,whetherQs);
				query.setInteger(3, date);
				list = query.list();
			}else{
				logger.error("线上查询渠道交易商户日期不能为空");
			}
		}catch(Exception e){
			logger.error(e);
		}finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}
	
	/**
	 * 根据当前交易流水号查询原笔交易的交易时间(针对冲正交易)
	 * @param sysStance
	 * @return
	 */
	public String getOriginalTradeTimeOfRollBk(String sysStance,String date,String tableName){
		Session session = null;
		String original_trade_time = "";
		try{
			session = this.getSession();
			Date startTime = DYDataUtil.getformatConversionDate3(date);
			Date endTime = DYDataUtil.getformatConversionDate4(date);
			SQLQuery query = session.createSQLQuery("select deduct_sys_time from "+tableName+" where req_sys_stance = ? and deduct_stlm_date BETWEEN ? and ?  and deduct_roll_bk = 0");
			query.setString(0, sysStance);
			query.setParameter(1, startTime);
			query.setParameter(2, endTime);
			original_trade_time = query.uniqueResult()+""; 
		}catch(Exception e){
			logger.error(e);
		}finally {
			if (session != null) {
				session.close();
			}
		}
		if(StringUtils.isNotBlank(original_trade_time) && !("null".equals(original_trade_time))){
			original_trade_time = original_trade_time.substring(0, original_trade_time.length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
		}
		return original_trade_time;
	}
	
	@Override
	public boolean channelInnerDz(String date, String tableName,
			String dateColumn) {
		Session session = null;
		Transaction ts = null;
		boolean flag = false;
		try {
			session = this.getSession();
			if (session != null) {
				ts = session.beginTransaction();
				Date startTime = DYDataUtil.getformatConversionDate3(date);
				Date endTime = DYDataUtil.getformatConversionDate4(date);
				CallableStatement cs = (CallableStatement) session.connection().prepareCall("call proce_channelBankInnerDz_handler(?,?,?,?)");
				cs.setObject(1, startTime);
				cs.setObject(2, endTime);
				cs.setObject(3, tableName);
				cs.setObject(4, dateColumn);
				cs.execute();
				ts.commit();
				flag = true;
			} else {
				logger.info("TradeLstDAO.channelInnerDz() 获取session为null");
			}
		} catch (Exception e) {
			ts.rollback();
			logger.error("内部对账错误：" + e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return flag;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean deductRollBkDeWeight(String tradeTime, String tableName,int deduct_sys_id) {
		Session session = null;
		Transaction ts = null;
		boolean flag = false;
		try {
			session = this.getSession();
			if (session != null) {
				ts = session.beginTransaction();
				Date startTime = DYDataUtil.getformatConversionDate3(tradeTime);
				Date endTime = DYDataUtil.getformatConversionDate4(tradeTime);
				StringBuffer seleBuffer = new StringBuffer();
				seleBuffer.append("select * from (");
					seleBuffer.append("select count(req_sys_stance) count,req_sys_stance from ");
					seleBuffer.append(tableName);
					seleBuffer.append(" where trade_time BETWEEN ? and ? and deduct_sys_id = ? and deduct_roll_bk = 1 and whetherValid = 1 group by req_sys_stance");
				seleBuffer.append(") a where a.count > 1");
				SQLQuery seleQuery = session.createSQLQuery(seleBuffer.toString());
				seleQuery.setParameter(0, startTime);
				seleQuery.setParameter(1, endTime);
				seleQuery.setParameter(2, deduct_sys_id);
				List selectList = seleQuery.list();
				if(selectList != null && selectList.size() > 0){
					Object[] obj = null;
					for (Object object : selectList) {
						obj = (Object[])object;
						Object count = obj[0];
						Object req_sys_stance = obj[1];
						if(count != null && req_sys_stance != null){
							SQLQuery seleDeductRollBkQuery = session.createSQLQuery("select trade_id from "+tableName+" where req_sys_stance = ? and deduct_roll_bk = 1 and trade_time BETWEEN ? and ?");
							seleDeductRollBkQuery.setParameter(0, req_sys_stance);
							seleDeductRollBkQuery.setParameter(1, startTime);
							seleDeductRollBkQuery.setParameter(2, endTime);
							List listDeductRollBk = seleDeductRollBkQuery.list();
							if(listDeductRollBk != null && listDeductRollBk.size() > 0){
								for(int i = 0;i<listDeductRollBk.size()-1;i++){
									SQLQuery updateDeductRollBkQuery = session.createSQLQuery("delete from  "+tableName+" where trade_id = ?");
									updateDeductRollBkQuery.setParameter(0, listDeductRollBk.get(i));
									updateDeductRollBkQuery.executeUpdate();
								}
							}
						}
					}
					ts.commit();
					flag = true;
				}else{
					logger.warn(tableName + "没有重复冲正交易的有效性去重");
					flag = true;
				}
			} else {
				logger.info("TradeLstDAO.deductRollBkDeWeight() 获取session为null");
			}
		} catch (Exception e) {
			ts.rollback();
			logger.error("内部对账错误：" + e);
		} finally {
			closeSession(session);
		}
		return flag;
	}
	
	@Override
	public boolean merchantSettleStatisticsUtil(String deductStlmDate, String settleMerCode, boolean whetherQs, String originalDataTableName,
			Integer instId,Integer merCategory,Integer inst_type,Object[] obj,boolean settle_flag,int bank_id){
		boolean flag = false;
		try {
			
//			 * obj[0] : 交易总金额
//			 * obj[1] : 交易笔数
//			 * obj[2] : 系统手续费(zf_file_fee)
//			 * obj[3] : 系统手续费(zf_fee)
//			 * obj[4] : 商户手续费
//			 * obj[5] : 退款总金额
//			 * obj[6] : 退款笔数
//			 * obj[7] : 退款系统手续费(zf_file_fee)
//			 * obj[8] : 退款系统手续费(zf_fee)
//			 * obj[9] : 退款商户手续费
//			 * obj[10] : 正交易总笔数-反交易总笔数 = 轧差笔数
//			Object[] obj = findPosChanneltotall(deductStlmDate, mer_code, whetherQs, originalDataTableName);
			Integer deductStlmDate_int = Integer.valueOf(deductStlmDate.replaceAll("-", ""));
			flag = saveMerchantSettleStatistics(instId,
					settleMerCode,
					merCategory,
					 deductStlmDate_int,
					 obj[0],
					 obj[1],
					 obj[5],
					 obj[6],
					 obj[4],
					 obj[2],
					 obj[9],
					 "0",
					 obj[7],
					 inst_type,
					 obj[3],
					 obj[8],
					 settle_flag,
					 bank_id,
					 obj[10]);
		} catch (Exception e) {
			logger.error("对账之后T+1统计调用工具类错误：" + e);
		}
		return flag;
	}
	/**
	 * 查询清算数据
	 * @param clas  实体类
	 * @param instInfo	渠道信息
	 * @param date	清算日期
	 * @return
	 */
	public List<?> queryQsData(Class<?> clas,String tableName,String date,int inst_id){
		Session session = null;
		List<?> listResult = null;
		try{
			session = this.getSession();
			Date startTime = DYDataUtil.getformatConversionDate3(date);
			Date endTime = DYDataUtil.getformatConversionDate4(date);
			StringBuffer sb = new StringBuffer("");
			sb.append("SELECT * FROM ");
			sb.append(tableName);
			sb.append(" WHERE deduct_stlm_date BETWEEN ? AND ? AND whetherQs = ? AND deduct_roll_bk = ? AND deduct_sys_id = ? ");
//			sb.append(" WHERE deduct_stlm_date BETWEEN ? AND ? AND whetherQs = ? AND deduct_roll_bk = ? AND deduct_sys_id = ? AND bk_chk = 1");
			sb.append(" ORDER BY trade_time ASC");
			SQLQuery query = session.createSQLQuery(sb.toString()).addEntity(clas);
			query.setParameter(0, startTime);
			query.setParameter(1, endTime);
			query.setBoolean(2, true);
			query.setBoolean(3, false);
			query.setInteger(4, inst_id);
			listResult = query.list();
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return listResult;
	}
	/**
	 * 查询银联CUPS对账成功数据，冲正数据
	 * @param date
	 * @return
	 */
	public List<?> queryQsDataOfRollBk(Class<?> clas,String tableName,String date,int inst_id){
		Session session = null;
		List<?> list = null;
		if(StringUtils.isNotBlank(date)){
			try{
				session = this.getSession();
				Date startTime = DYDataUtil.getformatConversionDate3(date);
				Date endTime = DYDataUtil.getformatConversionDate4(date);
				StringBuffer sb = new StringBuffer("");
				sb.append("SELECT * FROM ");
				sb.append(tableName);
				sb.append(" WHERE deduct_stlm_date BETWEEN ? AND ? AND deduct_sys_id = ? AND deduct_roll_bk = 1 and deduct_roll_bk_response in ('00','N1') and whetherQs = 1 group by req_sys_stance HAVING count(*) >=1 order by req_sys_stance");
//				sb.append(" WHERE deduct_stlm_date BETWEEN ? AND ? AND deduct_sys_id = ? AND deduct_roll_bk = 1 and bk_chk = 1 and deduct_roll_bk_response in ('00','N1') and whetherQs = 1 group by req_sys_stance HAVING count(*) >=1 order by req_sys_stance");
				SQLQuery query = session.createSQLQuery(sb.toString()).addEntity(clas);
				query.setParameter(0, startTime);
				query.setParameter(1, endTime);
				query.setInteger(2, inst_id);
				list = query.list();
				if(list == null || list.size() == 0){
					logger.info("日期为"+date+"的"+tableName+"中冲正成功且对账成功数据为0条");
				}
			}catch(Exception e){
				logger.error(e);
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			logger.warn("日期参数不能为空");
		}
		return list;
	}
	
	@Override
	public boolean saveMerStanceAndBalance(String tradeTime, InstInfo instInfo,int bank_id) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		if(StringUtils.isNotBlank(tradeTime) && instInfo != null){
			Integer instId = instInfo.getId().getInstId();
			Integer instType = instInfo.getId().getInstType();
			String tableName = instInfo.getBank().getOriginalDataTableName();//?
			Integer gate = instInfo.getGate();
			Date startTime = DYDataUtil.getformatConversionDate3(tradeTime);
			Date endTime = DYDataUtil.getformatConversionDate4(tradeTime);
			logger.info(StringPingJie.getInstance().getStringPingJie("开始根据交易日期：" ,tradeTime , "扣款渠道：" , instId , "查询线下成功交易数据"));
			try{
				StringBuffer sqlBuffer = new StringBuffer("");
				sqlBuffer.append("select req_mer_code,deduct_sys_stance,deduct_roll_bk, deduct_sys_response, deduct_roll_bk_response, trademsg_type, trade_amount, trade_fee, deduct_sys_time,deduct_rollbk_sys_time,req_process,trade_id,deduct_stlm_date,whetherAccessStance from ");
				sqlBuffer.append(tableName);
				sqlBuffer.append(" where ");
				sqlBuffer.append(" trade_time BETWEEN ? and ? and deduct_sys_id = ? and bank_id = ? and whetherValid = 1 and whetherAccessStance = 0");
				logger.info(StringPingJie.getInstance().getStringPingJie("查询线下交易数据的SQL语句是：" , sqlBuffer.toString()));
				session = this.getSession();
				SQLQuery query = session.createSQLQuery(sqlBuffer.toString());
				query.setParameter(0, startTime);
				query.setParameter(1, endTime);
				query.setParameter(2, instInfo.getId().getInstId());
				query.setParameter(3, bank_id);
				List resultList = query.list();
				if (resultList != null && resultList.size() > 0) {
					SimpleDateFormat simpleDateFormat = DYDataUtil.getSimpleDateFormat(DYDataUtil.DATE_FORMAT_1);
					transaction = session.beginTransaction();
					for (Object object : resultList) {
						sqlBuffer.setLength(0);
						Object[] obj = (Object[]) object;
						String reqMerCode = obj[0] == null ? "" : obj[0].toString();
						String deductSysStance = obj[1] == null ? "" : obj[1].toString();
						boolean deductRollBk = Boolean.valueOf(obj[2].toString());
						String deductSysResponse = obj[3] == null ? "" : obj[3].toString().trim();
						String deductRollBkResponse = obj[4] == null ? "" : obj[4].toString().trim();
						Integer trademsgType = Integer.valueOf(obj[5].toString());
						Long tradeAmount = obj[6] == null ? 0 : Long.valueOf(obj[6].toString());
						String tradeFee = obj[7] == null ? "00000000" : obj[7].toString().substring(1);
						Date deductSysTime = simpleDateFormat.parse(obj[8].toString());
						Date deductRollbkSysTime = simpleDateFormat.parse(obj[9].toString());
						String reqProcess = obj[10].toString();
						String tradeId = obj[11].toString();
						String deductStlmDate = obj[12].toString().substring(0, 10);
						boolean whetherAccessStance = Boolean.valueOf(obj[13].toString());
						
						String settleMerCode = settleMerchantMatchTableDAO.getSettleMerCode(reqMerCode);
						if(StringUtils.isEmpty(settleMerCode)){
							settleMerCode = reqMerCode;
						}
						double mer_fee = 0d;
						Integer derc_status = 2;// 1:消费(支付)、2:退款(冲正)
						MerBasic merBasic = merBasicDAO.queryMerBasic(settleMerCode);
						MerBilling merBilling = merBillingDAO.queryMerBilling(settleMerCode);
						if(merBasic != null){
							Integer refundFee = merBilling.getRefundFee();
							if(refundFee == 0 && trademsgType == 20){//退货交易不退还手续费
								mer_fee = 0d;
							}else{
								if(StringUtils.equals(reqProcess, "480000") && trademsgType == 2){ //转账汇款
									mer_fee = Double.valueOf(tradeFee)/100;
								}else{
									mer_fee = PoundageCalculate.getMerFee(String.valueOf(tradeAmount),settleMerCode,gate);
								}
							}
							
							//消费or预授权完成or消费撤销冲正or预授权完成撤销冲正or电子现金(脱机消费)
							if(trademsgType == 2 || trademsgType == 56 || trademsgType == 28 || trademsgType == 82 || trademsgType == 110){
								derc_status = 1;
							}
							
							//修改商户手续费
							sqlBuffer.append("update ");
							sqlBuffer.append(tableName);
							sqlBuffer.append(" set mer_fee = ? where trade_id = ?");
							SQLQuery updateMerFeeSql = session.createSQLQuery(sqlBuffer.toString());
							updateMerFeeSql.setParameter(0, derc_status == 1 ? mer_fee : 0-mer_fee);
							updateMerFeeSql.setParameter(1, tradeId);
							updateMerFeeSql.executeUpdate();
							
							//成功交易--进入资金流水、计算商户余额
							if((StringUtils.equals(deductSysResponse, "00") || StringUtils.equals(deductRollBkResponse, "00")) && whetherAccessStance == false){
								//保存商户资金流水修改更新商户余额
								boolean save_flag = saveMerFundStanceAndupdateMerBalance(settleMerCode, deductSysTime, deductRollbkSysTime, deductSysStance, mer_fee, tradeAmount, derc_status, deductRollBk, instId, instType, deductStlmDate,bank_id,merBasic,merBilling);
								if(save_flag){//资金流水表数据保存成功,才修改是否进入商户余额字段标识
									sqlBuffer.setLength(0);
									//修改whetherAccessStance字段信息表示该笔交易已经进入商户余额、资金流水
									sqlBuffer.append("update ");
									sqlBuffer.append(tableName);
									sqlBuffer.append(" set whetherAccessStance = ? where trade_id = ?");
									SQLQuery updateWhetherAccessStanceSql = session.createSQLQuery(sqlBuffer.toString());
									updateWhetherAccessStanceSql.setParameter(0, true);
									updateWhetherAccessStanceSql.setParameter(1, tradeId);
									updateWhetherAccessStanceSql.executeUpdate();
								}
							}
						}else{
							logger.error(StringPingJie.getInstance().getStringPingJie("该商户号不存在:" , reqMerCode));
						}
					}
					transaction.commit();
				}else{
					logger.info(StringPingJie.getInstance().getStringPingJie("执行上述sql没有查询到交易数据"));
				}
				
				flag = true;
			}catch(Exception e){
				logger.error(e);
				if(transaction != null)
					transaction.rollback();
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			logger.warn("日期参数不能为空");
		}
		return flag;
	}
	
	
	@Override
	public boolean saveMerFundStanceAndupdateMerBalance(String merCode,
			Date deductSysTime,Date deductRollbkSysTime, String tradeStance, double mer_fee,
			Long tradeAmount, Integer derc_status,boolean isDeductRollBk,Integer instId,
			Integer instType,String deductStlmDate,int bank_id,MerBasic merBasic,MerBilling merBilling) {
		boolean flag = false;
		try {
			MerBalance merBalance = merBalanceDAO.findMerBalance(merCode);
			double change_amount = 0d;
			String balance = "0";
			double account_amount = 0d;
			double trade_amount = 0d;
			if(merBalance != null){
				balance = merBalance.getMerBalance();//商户余额
			}
			if(derc_status == 2 && mer_fee > 0){
				mer_fee = 0-mer_fee;
			}
			trade_amount = tradeAmount.doubleValue()/100;
			if(merBilling != null){
				if(merBilling.getBilWay() == 1){ //全额
					change_amount = trade_amount;
				}else if(merBilling.getBilWay() == 2){ //净额
					change_amount = trade_amount-mer_fee;//变动金额
				}else{
					change_amount = trade_amount-mer_fee;//变动金额
				}
				
				account_amount = PoundageCalculate.add(balance, change_amount);//account_amount+change_amount;//账户余额
				Date tradeTime = isDeductRollBk ? deductRollbkSysTime : deductSysTime;
				merFundStanceDAO.saveMerFundStance(merCode, tradeTime, trade_amount, mer_fee, change_amount, account_amount, tradeStance, derc_status, merBasic.getMerState(), merBasic.getMerCategory(), merBasic.getMerAbbreviation(),instId,deductStlmDate,instType,bank_id);
				flag = true;
			}else{
				logger.warn(merCode+" 商户结算信息不存在");
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return flag;
	}

	@Override
	public Integer findOriChannelStanceData(String deductStlmDate,
			String tradeId, String tableName) {
		Session session = null;
		Integer bk_chk = 0;
		try {
			session = this.getSession();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select bk_chk from ");
			buffer.append(tableName);
			buffer.append(" where trade_id = '");
			buffer.append(tradeId);
			buffer.append("'");
			SQLQuery sqlQuery = session.createSQLQuery(buffer.toString());
			Object obj = sqlQuery.uniqueResult();
			if(obj != null){
				bk_chk = Integer.valueOf(obj.toString());
			}
		} catch(Exception e){
			logger.error(e);
		}finally {
			closeSession(session);
		}
		return bk_chk;
	}
	
	
	public boolean updateDeductRollBkStanceHandler(String tradeAmount,String reqMerCode,
			int gate,Date deductSysTime,Date deductRollbkSysTime,String deductSysStance,boolean isDeductRollBk,
			int instId, int inst_type, String deductStlmDate,int bank_id,String trade_id,String originalDataTableName, 
			String riqieOriginalTableName,String columnName,int whether_flag){
		boolean update_flag = false;
		try {
			tradeAmount = tradeAmount.replaceAll(",", "").trim();
			double mer_fee = 0d;
			Integer derc_status = 2;// 1:消费(支付)、2:退款(冲正)
			String settleMerCode = settleMerchantMatchTableDAO.getSettleMerCode(reqMerCode);
			if(StringUtils.isEmpty(settleMerCode)){
				settleMerCode = reqMerCode;
			}
			MerBasic merBasic = merBasicDAO.queryMerBasic(settleMerCode);
			MerBilling merBilling = merBillingDAO.queryMerBilling(settleMerCode);
			if(merBasic != null){
				mer_fee = PoundageCalculate.getMerFee(String.valueOf(tradeAmount),settleMerCode,gate);
				boolean flag = saveMerFundStanceAndupdateMerBalance(settleMerCode, deductSysTime, deductRollbkSysTime, deductSysStance, mer_fee, Long.valueOf(tradeAmount), derc_status, isDeductRollBk, instId, inst_type, deductStlmDate,bank_id,merBasic,merBilling);
				if(flag)
					updateChannelWhetherAccessStance(trade_id, originalDataTableName, riqieOriginalTableName, columnName, whether_flag);
			}
			update_flag = true;
		} catch (Exception e) {
			logger.error(e);
		}
		return update_flag;
	}

	@Override
	public Integer findDuizChannelStanceData(String deductStlmDate,
			String id, String columnName, String tableName) {
		Session session = null;
		Integer bk_chk = null;
		try {
			session = this.getSession();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select bk_chk from ");
			buffer.append(tableName);
			buffer.append(" where ");
			buffer.append(columnName);
			buffer.append(" = '");
			buffer.append(id);
			buffer.append("'");
			SQLQuery sqlQuery = session.createSQLQuery(buffer.toString());
			Object obj = sqlQuery.uniqueResult();
			if(obj != null){
				bk_chk = Integer.valueOf(obj.toString());
			}
		} catch(Exception e){
			logger.error(e);
		}finally {
			closeSession(session);
		}
		return bk_chk;
	}

	@Override
	public boolean updateChannelWhetherAccessStance(String id,String riqieTable,String originalTable,String columnName,Integer whether_flag) {
		Session session = null;
		boolean flag = false;
		Transaction transaction = null;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			StringBuffer buffer = new StringBuffer();
			if(StringUtils.isNotBlank(riqieTable)){
				buffer.append("update ");
				buffer.append(riqieTable);
				buffer.append(" set whetherAccessStance = ? where ");
				buffer.append(columnName);
				buffer.append("= ? ");
				SQLQuery sqlQuery = session.createSQLQuery(buffer.toString());
				sqlQuery.setParameter(0, whether_flag);
				sqlQuery.setParameter(1, id);
				sqlQuery.executeUpdate();
			}
			buffer.setLength(0);
			if(StringUtils.isNotBlank(originalTable)){
				buffer.append("update ");
				buffer.append(originalTable);
				buffer.append(" set whetherAccessStance = ? where ");
				buffer.append(columnName);
				buffer.append("= ? ");
				SQLQuery sqlQuery = session.createSQLQuery(buffer.toString());
				sqlQuery.setParameter(0, whether_flag);
				sqlQuery.setParameter(1, id);
				sqlQuery.executeUpdate();
			}
			transaction.commit();
		} catch(Exception e){
			logger.error(e);
			if(transaction != null)
				transaction.rollback();
		}finally {
			closeSession(session);
		}
		return flag;
	}

	@Override
	public boolean updateMerchantSettleStatistics(String merCode,String reqSysStance,Long tradeAmount,Double merFee,Double zfFee,String zf_file_fee,
			String deductStlmDate,Integer inst_id,Integer inst_type,Integer data_status,boolean whetherTk) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			SQLQuery selectQuery = session.createSQLQuery("select trade_amount,trade_count,refund_amount,refund_count,mer_fee,system_fee,mer_refund_fee,system_refund_fee,zf_fee,refund_zf_fee from merchant_settle_statistics where mer_code = ? and deduct_stlm_date = ? and inst_id = ? and inst_type = ? and data_status = ?");
			selectQuery.setParameter(0, merCode);
			selectQuery.setParameter(1, Integer.valueOf(deductStlmDate.replace("-", "")));
			selectQuery.setParameter(2, inst_id);
			selectQuery.setParameter(3, inst_type);
			selectQuery.setParameter(4, data_status);
			Object obj = selectQuery.uniqueResult();
			if(obj != null){
				Object[] objArr = (Object[])obj;
				String trade_amount = objArr[0].toString();
				int trade_count = Integer.valueOf(objArr[1].toString());
				String refund_amount = objArr[2].toString();
				int refund_count = Integer.valueOf(objArr[3].toString());
				String mer_fee = objArr[4].toString();
				String system_fee = objArr[5].toString();
				String mer_refund_fee = objArr[6].toString();
				String system_refund_fee = objArr[7].toString();
				String zf_fee = objArr[8].toString();
				String refund_zf_fee = objArr[9].toString();
				
				StringBuffer buffer = new StringBuffer();
				double amount = tradeAmount/100;
				if(whetherTk){
					refund_amount = PoundageCalculate.sub(refund_amount, String.valueOf(amount)).toString();
					mer_refund_fee = PoundageCalculate.sub(mer_refund_fee, String.valueOf(merFee)).toString();
					system_refund_fee = PoundageCalculate.sub(system_refund_fee, String.valueOf(zf_file_fee)).toString();
					refund_zf_fee = PoundageCalculate.sub(refund_zf_fee, String.valueOf(zfFee)).toString();
					refund_count = refund_count - 1;
				}else{
					if(amount > 0){
						trade_amount = PoundageCalculate.sub(trade_amount, String.valueOf(amount)).toString();
						mer_fee = PoundageCalculate.sub(mer_fee, String.valueOf(merFee)).toString();
						system_fee = PoundageCalculate.sub(system_fee, String.valueOf(zf_file_fee)).toString();
						zf_fee = PoundageCalculate.sub(zf_fee, String.valueOf(zfFee)).toString();
					}else{
						trade_amount = PoundageCalculate.add(trade_amount, String.valueOf(amount)).toString();
						mer_fee = PoundageCalculate.add(mer_fee, String.valueOf(merFee)).toString();
						system_fee = PoundageCalculate.add(system_fee, String.valueOf(zf_file_fee)).toString();
						zf_fee = PoundageCalculate.add(zf_fee, String.valueOf(zfFee)).toString();
					}
					trade_count = trade_count - 1;
				}
				buffer.append("update merchant_settle_statistics set ");
				buffer.append("refund_amount = ? , refund_count = ?,mer_refund_fee = ?,system_refund_fee = ?,refund_zf_fee = ?,");
				buffer.append("trade_amount = ?,trade_count = ?,mer_fee = ?,system_fee = ?,zf_fee = ? ");
				buffer.append("where mer_code = ? and deduct_stlm_date = ? and inst_id = ? and inst_type = ? and data_status = ?");
				SQLQuery sqlQuery = session.createSQLQuery(buffer.toString());
				sqlQuery.setParameter(0, refund_amount);
				sqlQuery.setParameter(1, refund_count);
				sqlQuery.setParameter(2, mer_refund_fee);
				sqlQuery.setParameter(3, system_refund_fee);
				sqlQuery.setParameter(4, refund_zf_fee);
				sqlQuery.setParameter(5, trade_amount);
				sqlQuery.setParameter(6, trade_count);
				sqlQuery.setParameter(7, mer_fee);
				sqlQuery.setParameter(8, system_fee);
				sqlQuery.setParameter(9, zf_fee);
				sqlQuery.setParameter(10, merCode);
				sqlQuery.setParameter(11, Integer.valueOf(deductStlmDate.replace("-", "")));
				sqlQuery.setParameter(12, inst_id);
				sqlQuery.setParameter(13, inst_type);
				sqlQuery.setParameter(14, data_status);
				sqlQuery.executeUpdate();
				
				
				if(StringUtils.isNotBlank(reqSysStance)){
					SQLQuery selectStance = session.createSQLQuery("select id,change_amount from mer_fund_stance where mer_code = ? and deduct_stlm_date = ? and inst_id = ? and inst_type = ? and trade_stance = ?");
					selectStance.setParameter(0, merCode);
					selectStance.setParameter(1, deductStlmDate);
					selectStance.setParameter(2, inst_id);
					selectStance.setParameter(3, inst_type);
					selectStance.setParameter(4, reqSysStance);
					Object objStanceArr = selectStance.uniqueResult();
					if(objStanceArr != null){
						Object[] stanceArr = (Object[])objStanceArr;
						String id = stanceArr[0].toString();
						Double change_amount = Double.valueOf(stanceArr[1].toString());
						SQLQuery deleteSqlQuery = session.createSQLQuery("delete from mer_fund_stance where id = ?");
						deleteSqlQuery.setParameter(0, id);
						deleteSqlQuery.executeUpdate();
						
						SQLQuery selectBalance = session.createSQLQuery("select mer_balance from mer_balance where mer_code = ?");
						selectBalance.setParameter(0, merCode);
						Object balanceObj = selectBalance.uniqueResult();
						if(balanceObj != null){
							String mer_balance = balanceObj.toString();
							mer_balance = PoundageCalculate.sub(mer_balance, change_amount).toString();
							SQLQuery updateBalance = session.createSQLQuery("update mer_balance set mer_balance = ? where mer_code = ?");
							updateBalance.setParameter(0, mer_balance);
							updateBalance.setParameter(1, merCode);
							updateBalance.executeUpdate();
						}
					}
				}
				transaction.commit();
			}
		} catch(Exception e){
			logger.error(e);
			if(transaction != null)
				transaction.rollback();
		}finally {
			closeSession(session);
		}
		return flag;
	}

	@Override
	public boolean deleteSettleMerchantSettleStatistics(String deductStlmDate,
			String settleMerCode, Integer instId, Integer inst_type) {
		boolean flag = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			SQLQuery selSqlQuery = session.createSQLQuery("delete from merchant_settle_statistics where mer_code = ? and deduct_stlm_date = ? and inst_id = ? and inst_type = ? and data_status = 0");
			selSqlQuery.setParameter(0, settleMerCode);
			selSqlQuery.setParameter(1, Integer.valueOf(deductStlmDate.replace("-", "")));
			selSqlQuery.setParameter(2, instId);
			selSqlQuery.setParameter(3, inst_type);
			Integer deleteCount = selSqlQuery.executeUpdate();
			if(deleteCount > 0){
				transaction.commit();
				flag = true;
			}
		} catch (Exception e) {
			logger.error(e);
			if(transaction != null)
				transaction.rollback();
		}finally{
			closeSession(session);
		}
		return flag;
	}
	
	
	public static void main(String[] args) {
		
		
//		System.out.println(Double.valueOf("3") - Double.valueOf("4"));
		System.out.println(Integer.valueOf("3") - Integer.valueOf("4"));
	}
}