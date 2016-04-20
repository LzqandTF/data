package cn.com.chinaebi.dz.object.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.ChannelDzCollect;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.InstInfoPK;
import cn.com.chinaebi.dz.object.MerBasic;
import cn.com.chinaebi.dz.object.base.BaseChannelDzCollectDAO;
import cn.com.chinaebi.dz.util.DYDataUtil;
import cn.com.chinaebi.dz.util.StringPingJie;


public class ChannelDzCollectDAO extends BaseChannelDzCollectDAO implements cn.com.chinaebi.dz.object.dao.iface.ChannelDzCollectDAO {

	private Log log = LogFactory.getLog(getClass());
	
	public ChannelDzCollectDAO () {}
	
	public ChannelDzCollectDAO (Session session) {
		super(session);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int savePosChannelDzCollect(InstInfo instInfo,String deductstlmdate,
			Map<String, String> settleMerchantMatchMap,Map<String, Object> mapMerBasic) throws Exception{
		int count = -1;
		Session session = null;
		Transaction transaction = null;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			int bank_id = instInfo.getBank().getId();
			session = this.getSession();
			stringBuffer.append("select trade_id,out_account,out_account_type,in_account,in_card_name,trade_amount,trade_fee,trade_currency,trade_result,");
			stringBuffer.append("req_sys_id,req_sys_stance,req_mer_code,req_mer_term_id,deduct_sys_id,deduct_sys_stance,deduct_mer_code,deduct_mer_term_id,");
			stringBuffer.append("deduct_sys_time,deduct_stlm_date,deduct_roll_bk,trade_type,authorization_code,deduct_sys_reference,mer_name,bk_chk,");
			stringBuffer.append("whetherValid,whetherErroeHandle,whetherRiqie,whetherQs,mer_fee,whetherTk,zf_fee,zf_file_fee,zf_fee_bj,fee_formula,original_trans_info,req_process,trademsg_type,trade_time,additional_data,additional_response_data ");
			stringBuffer.append("from ");
			stringBuffer.append(instInfo.getBank().getOriginalDataTableName());
			stringBuffer.append(" where deduct_stlm_date BETWEEN ? and ?");
			SQLQuery selectQuery = session.createSQLQuery(stringBuffer.toString());
			selectQuery.setParameter(0, DYDataUtil.getformatConversionDate3(deductstlmdate));
			selectQuery.setParameter(1, DYDataUtil.getformatConversionDate4(deductstlmdate));
			List<Object> selectList = selectQuery.list();
			Integer deductstlmdate_ = Integer.valueOf(deductstlmdate.replace("-", ""));
			if(selectList != null && selectList.size() > 0){
				transaction = session.beginTransaction();
				SQLQuery deleteQuery = session.createSQLQuery("delete from channel_dz_collect where deduct_stlm_date = ? and deduct_sys_id = ? and instType = ?");
				deleteQuery.setParameter(0, deductstlmdate_);
				deleteQuery.setParameter(1, instInfo.getId().getInstId());
				deleteQuery.setParameter(2, 0);
				int deleteCount = deleteQuery.executeUpdate();
				transaction.commit();
				log.info(StringPingJie.getInstance().getStringPingJie("删除日期 ：",deductstlmdate,"渠道：",instInfo.getId().getInstId(),"渠道类型：0,数据总条数：",deleteCount));
				int forCount = 0;
				for (Object object : selectList) {
					Object[] selectObj = (Object[]) object;
					stringBuffer.setLength(0);
					stringBuffer.append("insert into channel_dz_collect(trade_id,out_account,out_account_type,in_account,in_card_name,trade_amount,trade_fee,");
					stringBuffer.append("trade_currency,trade_currency_name,trade_result,req_sys_id,req_sys_stance,req_mer_code,req_mer_term_id,deduct_sys_id,deduct_sys_stance,");
					stringBuffer.append("deduct_mer_code,deduct_mer_term_id,deduct_sys_time,deduct_stlm_date,deduct_roll_bk,trade_type,authorization_code,");
					stringBuffer.append("deduct_sys_reference,mer_name,bk_chk,whetherValid,whetherErroeHandle,whetherRiqie,whetherQs,mer_fee,whetherTk,zf_fee,");
					stringBuffer.append("zf_file_fee,zf_fee_bj,fee_formula,original_trans_info,instType,gate,settle_code,inst_name,dy_mer_name,trade_time,oid,additional_response_data,bank_id,js_date) ");
					stringBuffer.append("value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					SQLQuery insertQuery = session.createSQLQuery(stringBuffer.toString());
					insertQuery.setParameter(0, selectObj[0]);
					insertQuery.setParameter(1, selectObj[1]);
					insertQuery.setParameter(2, selectObj[2]);
					insertQuery.setParameter(3, selectObj[3]);
					insertQuery.setParameter(4, selectObj[4]);
					insertQuery.setParameter(5, Double.valueOf(selectObj[5].toString())/100);
					insertQuery.setParameter(6, selectObj[6]);
					insertQuery.setParameter(7, "01");
					insertQuery.setParameter(8, "人民币");
					int trade_result = Integer.valueOf(selectObj[8].toString());
					//2:成功、6:超时、3:失败、7:未知
					switch(trade_result){
						case 0:
							trade_result = 2;
							break;
						case 1:
							trade_result = 6;
							break;
						case 2:
							trade_result = 3;
							break;
						default:
							trade_result = 7;
							break;
					}
					insertQuery.setParameter(9, trade_result);
					insertQuery.setParameter(10, selectObj[9]);
					insertQuery.setParameter(11, selectObj[10]);
					insertQuery.setParameter(12, selectObj[11]);
					insertQuery.setParameter(13, selectObj[12]);
					insertQuery.setParameter(14, selectObj[13]);
					insertQuery.setParameter(15, selectObj[14]);
					insertQuery.setParameter(16, selectObj[15] == null ? "" : selectObj[15].toString().trim());
					insertQuery.setParameter(17, selectObj[16]);
					Date deduct_sys_time = (Date)selectObj[17];
					String deduct_sys_timeStr = DYDataUtil.getSimpleDateFormat(DYDataUtil.DATE_FORMAT_2).format(deduct_sys_time);
					insertQuery.setParameter(18, Long.valueOf(deduct_sys_timeStr));//deduct_sys_time
					Date deduct_stml_date = (Date)selectObj[18];
					String deduct_stml_dateStr = DYDataUtil.getSimpleDateFormat(DYDataUtil.DATE_FORMAT_3).format(deduct_stml_date);
					insertQuery.setParameter(19, Integer.valueOf(deduct_stml_dateStr));//deduct_stml_date
					insertQuery.setParameter(20, selectObj[19]);
					int tradeMsgType = 0;
					if(selectObj[37] != null){
						tradeMsgType = Integer.valueOf(selectObj[37].toString());
						switch(tradeMsgType){
							case 2://消费
								if(selectObj[37] != null){
									String req_process = selectObj[37].toString();
									if(StringUtils.equals(req_process, "480000")){
										tradeMsgType = 29;
									}else
										tradeMsgType = 19;
								}else
									tradeMsgType = 19;
								break;
							case 26://消费冲正
								tradeMsgType = 20;
								break;
							case 18://消费撤销
								tradeMsgType = 21;
								break;
							case 28://消费撤销冲正
								tradeMsgType = 22;
								break;
							case 20://退货
								tradeMsgType = 23;
								break;
							case 56://预授权完成
								tradeMsgType = 24;
								break;
							case 58://预授权完成撤销
								tradeMsgType = 25;
								break;
							case 80://预授权完成冲正
								tradeMsgType = 26;
								break;
							case 82://预授权完成撤销冲正
								tradeMsgType = 27;
							case 110://脱机消费
								tradeMsgType = 28;
								break;
							default://未知
								tradeMsgType = 30;
								break;
						}
					}
					insertQuery.setParameter(21, tradeMsgType);
					insertQuery.setParameter(22, selectObj[21]);
					insertQuery.setParameter(23, selectObj[22]);
					insertQuery.setParameter(24, selectObj[23]);
					insertQuery.setParameter(25, selectObj[24]);
					insertQuery.setParameter(26, selectObj[25]);
					insertQuery.setParameter(27, selectObj[26]);
					insertQuery.setParameter(28, selectObj[27]);
					insertQuery.setParameter(29, selectObj[28]);
					insertQuery.setParameter(30, selectObj[29]);
					insertQuery.setParameter(31, selectObj[30]);
					insertQuery.setParameter(32, selectObj[31]);
					insertQuery.setParameter(33, selectObj[32]);
					insertQuery.setParameter(34, selectObj[33]);
					insertQuery.setParameter(35, selectObj[34]);
					insertQuery.setParameter(36, selectObj[35]);
					insertQuery.setParameter(37, 0);
					insertQuery.setParameter(38, instInfo.getGate());
					String settleMerCode = settleMerchantMatchMap.get(selectObj[11].toString());
					settleMerCode = StringUtils.isNotBlank(settleMerCode) ? settleMerCode : selectObj[11].toString();
					insertQuery.setParameter(39, settleMerCode);
					insertQuery.setParameter(40, instInfo.getName());
					Object obj = mapMerBasic != null ? mapMerBasic.get(settleMerCode) : null;
					MerBasic merBasic = (obj == null ? null : (MerBasic)obj);
					insertQuery.setParameter(41, merBasic != null ? merBasic.getMerName() : null);
					Date trade_time = (Date)selectObj[38];
					String trade_timeStr = DYDataUtil.getSimpleDateFormat(DYDataUtil.DATE_FORMAT_2).format(trade_time);
					insertQuery.setParameter(42, Long.valueOf(trade_timeStr));//trade_time
					insertQuery.setParameter(43, selectObj[39] == null ? null : selectObj[39].toString().split("|")[0]);//oid
					insertQuery.setParameter(44, selectObj[40]);
					insertQuery.setParameter(45, bank_id);
					insertQuery.setParameter(46, deductstlmdate_);
					insertQuery.executeUpdate();
					++forCount;
				}
				transaction = session.beginTransaction();
				transaction.commit();
				count = forCount;
			}
		} catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			log.error(e);
			throw e;
		}finally{
			closeSession(session);
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int saveRytChannelDzCollect(String channelTableName,
			Integer sysDate,Map<String, Object> mapMerBasic,Map<InstInfoPK, Object> instInfoMap) throws Exception{
		int count = -1;
		Session session = null;
		Connection conn = null;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			   session = this.getSession();
			   stringBuffer.append("select card_no,amount,currency,tstat,tseq,mid,gid,");
			   stringBuffer.append("sys_date,sys_time,deduct_stlm_date,type,bk_seq1,whetherValid,whetherErroeHandle,whetherRiqie,");
			   stringBuffer.append("whetherQs,mer_fee,zf_fee,zf_file_fee,zf_fee_bj,gate,oid,bk_chk,bank_id,out_user_id,in_user_id,bind_mid");
			   stringBuffer.append(" from ");
			   stringBuffer.append(channelTableName);
			   stringBuffer.append(" where sys_date = ? or deduct_stlm_date = ?");
			   SQLQuery selectQuery = session.createSQLQuery(stringBuffer.toString());
			   selectQuery.setParameter(0, sysDate);
			   selectQuery.setParameter(1, sysDate);
			   List<Object> selectList = selectQuery.list();
			   if(selectList != null && selectList.size() > 0){
				   conn = session.connection();
				   conn.setAutoCommit(false);
				   int forCount = 0;
				   stringBuffer.setLength(0);
				   stringBuffer.append("replace into channel_dz_collect(trade_id,out_account,trade_amount,");
				   stringBuffer.append("trade_currency,trade_currency_name,trade_result,req_sys_stance,req_mer_code,deduct_sys_id,deduct_sys_stance,");
				   stringBuffer.append("deduct_sys_time,deduct_stlm_date,trade_type,instType,whetherValid,whetherErroeHandle,whetherRiqie,whetherQs,mer_fee,zf_fee,zf_file_fee,zf_fee_bj,");
				   stringBuffer.append("gate,settle_code,inst_name,dy_mer_name,deduct_mer_code,trade_time,oid,sys_date,bk_chk,bank_id,js_date,out_user_id,in_user_id,bind_mid) ");
				   stringBuffer.append("value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				   PreparedStatement stmt = conn.prepareStatement(stringBuffer.toString()); 
				   for (Object object : selectList) {
					    Object[] selectObj = (Object[]) object;
					    InstInfoPK instInfoPK = new InstInfoPK(Integer.valueOf(selectObj[6].toString()),1);
					    InstInfo instInfo = (InstInfo)instInfoMap.get(instInfoPK);
					    stmt.setObject(1, selectObj[4].toString());
					    stmt.setObject(2, selectObj[0] != null ? selectObj[0] : "");
					    stmt.setObject(3, Double.valueOf(selectObj[1].toString())/100);
					    //01:人民币、02:英镑、03:港币、04:美元、05:瑞士法郎、06:日元、07:加拿大元、08:澳大利亚元、09:欧元
					    if(selectObj[2] != null && StringUtils.isNotBlank(selectObj[2].toString())){
					    	stmt.setObject(4, selectObj[2]);
					    	int trade_currency = Integer.valueOf(selectObj[2].toString());
					    	switch (trade_currency) {
							case 1:
								stmt.setObject(5, "人民币");
								break;
							case 2:
								stmt.setObject(5, "英镑");
								break;
							case 3:
								stmt.setObject(5, "英镑");
								break;
							case 4:
								stmt.setObject(5, "美元");
								break;
							case 5:
								stmt.setObject(5, "瑞士法郎");
								break;
							case 6:
								stmt.setObject(5, "日元");
								break;
							case 7:
								stmt.setObject(5, "加拿大元");
								break;
							case 8:
								stmt.setObject(5, "澳大利亚元");
								break;
							case 9:
								stmt.setObject(5, "澳大利亚元");
								break;
							default:
								stmt.setObject(5, "人民币");
								break;
							}
					    }else{
					    	stmt.setObject(4, "01");
					    	stmt.setObject(5, "人民币");
					    }
					    stmt.setObject(6, Integer.valueOf(selectObj[3].toString()));
					    stmt.setObject(7, selectObj[4].toString());
					    stmt.setObject(8, selectObj[5]);
					    stmt.setObject(9, selectObj[6]);
					    stmt.setObject(10, selectObj[4].toString());
					    stmt.setObject(11, DYDataUtil.getStringTimeyyyyMMddHHmmss(selectObj[7].toString(), selectObj[8].toString()));
					    stmt.setObject(12,selectObj[9]);
					    stmt.setObject(13,Integer.valueOf(selectObj[10].toString()));
					    stmt.setObject(14,1);
					    stmt.setObject(15,selectObj[12]);
					    stmt.setObject(16,selectObj[13]);
					    stmt.setObject(17,selectObj[14]);
					    stmt.setObject(18,selectObj[15]);
					    stmt.setObject(19,selectObj[16]);
					    stmt.setObject(20,selectObj[17]);
					    stmt.setObject(21,selectObj[18]);
					    stmt.setObject(22,selectObj[19]);
					    stmt.setObject(23,selectObj[20]);
					    stmt.setObject(24,selectObj[5]);//mid
					    stmt.setObject(25,instInfo == null ? "未知":instInfo.getName());
					    Object obj = mapMerBasic != null ? mapMerBasic.get(selectObj[5].toString()) : null;
						MerBasic merBasic = (obj == null ? null : (MerBasic)obj);
						stmt.setObject(26, merBasic != null ? merBasic.getMerName() : null);
						stmt.setObject(27, selectObj[5]);//deduct_mer_code
						stmt.setObject(28, DYDataUtil.getStringTimeyyyyMMddHHmmss(selectObj[7].toString(), selectObj[8].toString()));//trade_time
						stmt.setObject(29, selectObj[21].toString());//oid
						stmt.setObject(30, selectObj[7]);//sys_date
						stmt.setObject(31, selectObj[22]);//bk_chk
						stmt.setObject(32, selectObj[23]);//bank_id
						stmt.setObject(33, selectObj[9]);//js_date
						stmt.setObject(34, selectObj[24]);//out_user_id
						stmt.setObject(35, selectObj[25]);//in_user_id
						stmt.setObject(36, selectObj[26]);//bind_mid
						stmt.addBatch();
					    if(forCount %100 == 0){
					    	stmt.executeBatch();
					    }
					    ++forCount;
				   }
				   stmt.executeBatch();
				   conn.commit();
				   count = forCount;
			   }
		} catch (Exception e) {
			log.error(e);
			if(conn != null){
				try {
					if(conn != null){
						conn.rollback();
					}
				} catch (SQLException e1) {
					log.info("事务回滚异常:"+e1);
				}
			}
			throw e;
		}finally{
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				log.info("conn关闭异常:"+e);
			}
			closeSession(session);
		}
		return count;
	}

	@Override
	public int saveRytTKChannelDzCollect(ChannelDzCollect channelDzCollect) throws Exception {
		int count = -1;
		Session session = null;
		Transaction transaction = null;
		StringBuilder sb = new StringBuilder();
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			sb.append("replace into channel_dz_collect(trade_id,out_account,trade_amount,trade_result,");
			sb.append("req_sys_stance,req_mer_code,deduct_sys_id,deduct_sys_stance,deduct_mer_code,trade_time,deduct_sys_time,");
			sb.append("deduct_stlm_date,trade_type,original_trans_info,bk_chk,whetherQs,mer_fee,whetherTk,instType,gate,settle_code,inst_name,dy_mer_name,oid,sys_date,bank_id,js_date) values");
			sb.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			SQLQuery insertQuery = session.createSQLQuery(sb.toString());
			insertQuery.setParameter(0, channelDzCollect.getId());
			insertQuery.setParameter(1, channelDzCollect.getOutAccount());
			insertQuery.setParameter(2, channelDzCollect.getTradeAmount());
			insertQuery.setParameter(3, channelDzCollect.getTradeResult());
			insertQuery.setParameter(4, channelDzCollect.getReqSysStance());
			insertQuery.setParameter(5, channelDzCollect.getReqMerCode());
			insertQuery.setParameter(6, channelDzCollect.getDeductSysId());
			insertQuery.setParameter(7, channelDzCollect.getDeductSysStance());
			insertQuery.setParameter(8, channelDzCollect.getDeductMerCode());
			insertQuery.setParameter(9, channelDzCollect.getTradeTime());
			insertQuery.setParameter(10, channelDzCollect.getDeductSysTime());
			insertQuery.setParameter(11, channelDzCollect.getDeductStlmDate());
			insertQuery.setParameter(12, channelDzCollect.getTradeType());
			insertQuery.setParameter(13, channelDzCollect.getOriginalTransInfo());
			insertQuery.setParameter(14, channelDzCollect.getBkChk());
			insertQuery.setParameter(15, channelDzCollect.getWhetherQs());
			insertQuery.setParameter(16, channelDzCollect.getMerFee());
			insertQuery.setParameter(17, channelDzCollect.getWhetherTk());
			insertQuery.setParameter(18, channelDzCollect.getInstType());
			insertQuery.setParameter(19, channelDzCollect.getGate());
			insertQuery.setParameter(20, channelDzCollect.getSettleCode());
			insertQuery.setParameter(21, channelDzCollect.getInstName());
			insertQuery.setParameter(22, channelDzCollect.getDyMerName());
			insertQuery.setParameter(23, channelDzCollect.getOid());
			insertQuery.setParameter(24, channelDzCollect.getSysDate());
			insertQuery.setParameter(25, channelDzCollect.getBankId());
			insertQuery.setParameter(26, channelDzCollect.getJsDate());
			count = insertQuery.executeUpdate();
			if (count > 0) {
				transaction.commit();
				log.info("已成功将退款数据插入到对账总表中");
			}
		} catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			log.error(e);
			throw e;
		} finally {
			closeSession(session);
		}
		return count;
	}

	@Override
	public Object queryCountAndMoney(String merCode, String startDate,String endDate) {
		Session session = null;
		Object object = null;
		try{
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select count(*), SUM(trade_amount) from channel_dz_collect WHERE settle_code = ? AND js_date >= ? AND js_date < ? and whetherQs = 1");
			query.setParameter(0, merCode);
			query.setParameter(1, startDate);
			query.setParameter(2, endDate);
			object = query.uniqueResult();
		}catch(Exception e){
			log.error(e);
		}finally{
			closeSession(session);
		}
		return object;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ChannelDzCollect> queryChannelDzCollectLst(String merCode, String startDate,String endDate, int startRow, int endRow) {
		Session session = null;
		List<ChannelDzCollect> list = null;
		try{
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select * from channel_dz_collect WHERE settle_code = ? AND js_date >= ? AND js_date < ? and whetherQs = 1 LIMIT ?, ?").addEntity(ChannelDzCollect.class);
			query.setParameter(0, merCode);
			query.setParameter(1, startDate);
			query.setParameter(2, endDate);
			query.setParameter(3, startRow);
			query.setParameter(4, endRow);
			List listResult = query.list();
			
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}
		}catch(Exception e){
			log.error(e);
		}finally{
			closeSession(session);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ChannelDzCollect> queryChannelDzCollectDataLst(String merCode, String startDate, String endDate) {
		Session session = null;
		List<ChannelDzCollect> list = null;
		try{
			session = this.getSession();
			Query query = session.createQuery("from ChannelDzCollect where SettleCode = ? and JsDate >= ? and JsDate < ? and WhetherQs = 1");
			query.setString(0, merCode);
			query.setString(1, startDate);
			query.setString(2, endDate);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}
		}catch(Exception e){
			log.error(e);
		}finally{
			closeSession(session);
		}
		return list;
	}
}