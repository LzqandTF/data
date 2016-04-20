package cn.com.chinaebi.dz.object.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
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

import cn.com.chinaebi.cust.risk.common.sms.SMSSendUtil;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.OriginalCupsLst;
import cn.com.chinaebi.dz.object.base.BaseOriginalCupsLstDAO;
import cn.com.chinaebi.dz.util.DYDataUtil;

import java.sql.CallableStatement;


public class OriginalCupsLstDAO extends BaseOriginalCupsLstDAO implements cn.com.chinaebi.dz.object.dao.iface.OriginalCupsLstDAO {

	private Log log = LogFactory.getLog(getClass());
	
	public OriginalCupsLstDAO () {}
	
	public OriginalCupsLstDAO (Session session) {
		super(session);
	}

	@Override
	public boolean handleMoney(String tradeTime) {
		Session session = null;
		Transaction ts = null;
		boolean flag = false;
		try {
			session = this.getSession();
			if (session != null) {
				ts = session.beginTransaction();
				Statement stmt = session.connection().createStatement();
				String selectTradeLstSQL = "select req_process,trademsg_type,trade_amount from original_cups_lst where substring(deduct_sys_time,1,10) = '"+tradeTime+"'";
				ResultSet rs = stmt.executeQuery(selectTradeLstSQL);
				if(rs.next()){
					while(rs.next()){
						if(rs.getInt(3) > 0){
							SQLQuery sqlQuery = session.createSQLQuery("select count(*) from trade_amount_conf where trade_money_status = 1 and  process = '"+rs.getString(1)+"'"+" and trademsg_type = "+rs.getInt(2));
							Integer count = Integer.valueOf(sqlQuery.uniqueResult().toString());
							if(count == 1){
								String updateSQL = "update original_cups_lst set trade_amount = trade_amount-(trade_amount*2) where req_process = '"+rs.getString(1)+"' and trademsg_type = "+rs.getInt(2);
								stmt.addBatch(updateSQL);
							}
						}
					}
					stmt.executeBatch();
					ts.commit();
					flag = true;
				}else{
					log.warn(tradeTime +" : 获取北京银行原笔交易数据为空");
				}
			} else {
				log.info("handleMoney 获取session为null");
			}
		} catch (Exception e) {
			ts.rollback();
			log.error("北京银行金额处理出错：" + e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return flag;
	}

	
	@Override
	public List<OriginalCupsLst> findOriginalCupsbankLst(String originalReqTime) {
		Session session = null;
		List<OriginalCupsLst> list = null; 
		if(originalReqTime == null){
			log.error("OriginalCupsLstDAO findOriginalCupsbankLst deductStlmDate param is not null");
			return null;
		}
		try {
			Date startTime = DYDataUtil.getformatConversionDate3(originalReqTime);
			Date endTime = DYDataUtil.getformatConversionDate4(originalReqTime);
			log.info("银联cups对账查询原始交易开始时间 ："+startTime);
			log.info("银联cups对账查询原始交易开始时间 ："+endTime);
			
			session = this.getSession();
			Query query = session.createQuery("from OriginalCupsLst where TradeTime BETWEEN ? and ? and WhetherValid = 1 order by ReqTime,TrademsgType asc");
			query.setParameter(0, startTime);
			query.setParameter(1, endTime);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}else{
				log.warn("from findRiqieCupsLst where TradeTime BETWEEN ? and ? and WhetherValid = 1 is not data");
			}
		}catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}
	
	@Override
	public boolean updateClean(String reqSysStance, boolean clean,Integer bkchk,Integer tradeMsgType,boolean deductRollBk,String tradeTime) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				Date startTime = DYDataUtil.getformatConversionDate3(tradeTime);
				Date endTime = DYDataUtil.getformatConversionDate4(tradeTime);
				session = this.getSession();
				transaction = session.beginTransaction();
				StringBuffer buffer = new StringBuffer();
				buffer.append("update original_cups_lst set whetherQs = ?,bk_chk = ? where deduct_sys_stance = ? and trademsg_type = ? and deduct_roll_bk = ? and trade_time BETWEEN ? and ?");
				SQLQuery sqlQuery = session.createSQLQuery(buffer.toString());
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, bkchk);
				sqlQuery.setString(2, reqSysStance);
				sqlQuery.setInteger(3, tradeMsgType);
				sqlQuery.setBoolean(4, deductRollBk);
				sqlQuery.setParameter(5,  startTime);
				sqlQuery.setParameter(6,  endTime);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
				}
			} catch (Exception e) {
				transaction.rollback();
				log.error(e);
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn(reqSysStance +", 流水号不存在");
		}
		return flag;
	}
	
	
	@Override
	public boolean updateClean(String reqSysStance, boolean clean,boolean deductRollBk,String tradeTime) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				Date startTime = DYDataUtil.getformatConversionDate3(tradeTime);
				Date endTime = DYDataUtil.getformatConversionDate4(tradeTime);
				session = this.getSession();
				transaction = session.beginTransaction();
				StringBuffer buffer = new StringBuffer();
				buffer.append("update original_cups_lst set whetherQs = ?,bk_chk = 1 where deduct_sys_stance = ? and deduct_roll_bk = ? and deduct_stlm_date BETWEEN ? and ?");
				SQLQuery sqlQuery = session.createSQLQuery(buffer.toString());
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setString(1, reqSysStance);
				sqlQuery.setBoolean(2, deductRollBk);
				sqlQuery.setParameter(3,  startTime);
				sqlQuery.setParameter(4,  endTime);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
				}
			} catch (Exception e) {
				transaction.rollback();
				log.error(e);
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn(reqSysStance +", 流水号不存在");
		}
		return flag;
	}
	
	@Override
	public boolean updateClean(String reqSysStance, boolean clean,Integer tradeMsgType,String tradeTime) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				Date startTime = DYDataUtil.getformatConversionDate3(tradeTime);
				Date endTime = DYDataUtil.getformatConversionDate4(tradeTime);
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update original_cups_lst set whetherJs = ?,bk_chk = 1 where deduct_sys_stance = ? and trademsg_type = ? and trade_time BETWEEN ? and ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setString(1, reqSysStance);
				sqlQuery.setInteger(2, tradeMsgType);
				sqlQuery.setParameter(3, startTime);
				sqlQuery.setParameter(4, endTime);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
				}
			} catch (Exception e) {
				transaction.rollback();
				log.error(e);
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn(reqSysStance +", 流水号不存在");
		}
		return flag;
	}
	
	@Override
	public boolean updateClean(String reqSysStance, boolean clean,Integer bkchk,boolean deductRollBk,String tradeTime) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				Date startTime = DYDataUtil.getformatConversionDate3(tradeTime);
				Date endTime = DYDataUtil.getformatConversionDate4(tradeTime);
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update original_cups_lst set whetherQs = ?,bk_chk = ? where deduct_sys_stance = ? and trade_time BETWEEN ? and ? and deduct_roll_bk = ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, bkchk);
				sqlQuery.setString(2, reqSysStance);
				sqlQuery.setParameter(3, startTime);
				sqlQuery.setParameter(4, endTime);
				sqlQuery.setBoolean(5, deductRollBk);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
				}
			} catch (Exception e) {
				transaction.rollback();
				log.error(e);
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn(reqSysStance +", 流水号不存在");
		}
		return flag;
	}
	
	@Override
	public boolean updateCleanAndError(String reqSysStance, boolean clean,Integer whetherErroeHandle,Integer tradeMsgType,boolean deductRollBk,String tradeTime) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				Date startTime = DYDataUtil.getformatConversionDate3(tradeTime);
				Date endTime = DYDataUtil.getformatConversionDate4(tradeTime);
				session = this.getSession();
				transaction = session.beginTransaction();
				StringBuffer buffer = new StringBuffer();
				if(!deductRollBk){
					buffer.append("update original_cups_lst set whetherQs = ? , whetherErroeHandle = ?,bk_chk = 2 where req_sys_stance = ? and trademsg_type = ? and deduct_roll_bk = ? and trade_time BETWEEN ? and ?");
				}else{
					buffer.append("update original_cups_lst set whetherQs = ? , whetherErroeHandle = ?,bk_chk = 2 where deduct_sys_stance = ? and trademsg_type = ? and deduct_roll_bk = ? and trade_time BETWEEN ? and ?");
				}
				SQLQuery sqlQuery = session.createSQLQuery(buffer.toString());
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, whetherErroeHandle);
				sqlQuery.setString(2, reqSysStance);
				sqlQuery.setInteger(3, tradeMsgType);
				sqlQuery.setBoolean(4, deductRollBk);
				sqlQuery.setParameter(5, startTime);
				sqlQuery.setParameter(6, endTime);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
				}
			} catch (Exception e) {
				transaction.rollback();
				log.error(e);
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn(reqSysStance +", 流水号不存在");
		}
		return flag;
	}
	
	@Override
	public boolean updateCleanAndError(String reqSysStance, boolean clean,Integer whetherErroeHandle,boolean deductRollBk,String tradeTime) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				Date startTime = DYDataUtil.getformatConversionDate3(tradeTime);
				Date endTime = DYDataUtil.getformatConversionDate4(tradeTime);
				session = this.getSession();
				transaction = session.beginTransaction();
				StringBuffer buffer = new StringBuffer();
				if(!deductRollBk){
					buffer.append("update original_cups_lst set whetherQs = ? , whetherErroeHandle = ?,bk_chk = 2 where req_sys_stance = ? and deduct_roll_bk = ? and trade_time BETWEEN ? and ?");
				}else{
					buffer.append("update original_cups_lst set whetherQs = ? , whetherErroeHandle = ?,bk_chk = 2 where deduct_sys_stance = ? and deduct_roll_bk = ? and trade_time BETWEEN ? and ?");
				}
				SQLQuery sqlQuery = session.createSQLQuery(buffer.toString());
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, whetherErroeHandle);
				sqlQuery.setString(2, reqSysStance);
				sqlQuery.setBoolean(3, deductRollBk);
				sqlQuery.setParameter(4, startTime);
				sqlQuery.setParameter(5, endTime);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
				}
			} catch (Exception e) {
				transaction.rollback();
				log.error(e);
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn(reqSysStance +", 流水号不存在");
		}
		return flag;
	}
	
	@Override
	public boolean getCupsData(String tradeTime) {
		Session session = null;
		Transaction ts = null;
		int count = 0;
		boolean flag = false;
		try {
			session = this.getSession();
			if (session != null) {
				ts = session.beginTransaction();
				CallableStatement cs = (CallableStatement) session.connection().prepareCall("call proce_original_cups_lst(?,?)");
				cs.setObject(1, tradeTime);
				cs.registerOutParameter(2, Types.INTEGER);
				cs.execute();
				ts.commit();
				count = cs.getInt(2);
				log.info("中国银联汇总数据条数：" + count);
				if (count > 0) {
					flag = true;
				}
			} else {
				log.info("getCupsData 获取session为null");
			}
			
		} catch (Exception e) {
			ts.rollback();
			log.error("银联数据汇总时出错：" + e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return flag;
	}
	/**
	 * 在进行手动汇总银联数据之前，根据交易时间，将之前由存储过程汇总完成的同一时间的数据删除
	 */
	public boolean deleteCupsDataByTradeTime(String tradeTime){
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(tradeTime)){
			try{
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery q = session.createSQLQuery("select count(*) from original_cups_lst where trade_time like ?");
				q.setString(0, tradeTime + "%");
				Integer c = Integer.valueOf(q.uniqueResult().toString());
				if(c > 0){
					SQLQuery query = session.createSQLQuery("delete from original_cups_lst where trade_time like ?");
					query.setString(0, tradeTime + "%");
					int count = query.executeUpdate();
					if(count > 0){
						flag = true;
						transaction.commit();
					}
				}else{
					log.info("未查到日期为"+tradeTime+"的银联cups数据 不需要删除");
					flag = true;
				}
				
			}catch(Exception e){
				log.error(e);
				transaction.rollback();
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}
		return flag;
	}
	/**
	 * 在进行手动汇总数据之前，根据交易时间和渠道ID，将之前由存储过程分割完成的同一时间的数据删除
	 */
	public boolean deleteSplitDataByTradeTime(String summaryDate,int instId){
		log.info("进入删除分割数据方法");
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(summaryDate)){
			try{
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery q = session.createSQLQuery("select count(*) from tmp_split_tab where deduct_sys_id = ? and trade_time like ?");
				q.setInteger(0, instId);
				q.setString(1, summaryDate + "%");
				Integer c = Integer.valueOf(q.uniqueResult().toString());
				if(c > 0){
					SQLQuery query = session.createSQLQuery("delete from tmp_split_tab where deduct_sys_id = ? and trade_time like ?");
					query.setInteger(0, instId);
					query.setString(1, summaryDate + "%");
					int count = query.executeUpdate();
					if(count > 0){
						flag = true;
						transaction.commit();
					}
				}else{
					log.info("未查到日期为"+summaryDate+"的渠道分割数据 不需要删除");
					flag = true;
				}
				
			}catch(Exception e){
				log.error(e);
				transaction.rollback();
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}
		return flag;
	}
	/**
	 * 在手动对账之前，根据交易时间，将银联原始交易数据以及银联对账文件的是否差错处理字段的状态还原
	 * @param summaryDate
	 * @return
	 */
	public boolean reductionDataStatusType(String summaryDate,InstInfo instInfo){
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(summaryDate)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				log.info("更新银联CUPS原始交易数据状态");
				SQLQuery query_ori = session.createSQLQuery("select count(*) from original_cups_lst where deduct_stlm_date like ?");
				query_ori.setString(0, summaryDate + "%");
				Integer count_ori = Integer.valueOf(query_ori.uniqueResult().toString());
				if(count_ori > 0){
					CallableStatement cs = (CallableStatement) session.connection().prepareCall("call proce_dispense_with_handle(?,?,?)");
					cs.setObject(1, summaryDate);
//					cs.setObject(2, "original_cups_lst");
					cs.setObject(2, instInfo.getBank().getOriginalDataTableName());
					cs.setObject(3, 2);
					cs.execute();
//					SQLQuery sqlQuery = session.createSQLQuery("update original_cups_lst set whetherJs = 0,bk_chk = 0,whetherErroeHandle = 0,whetherRiqie = 0 where deduct_stlm_date like ? and deduct_sys_response not in('00','N1','N2') and deduct_sys_response is not null ");
//					sqlQuery.setString(0, summaryDate + "%");
//					int count =  sqlQuery.executeUpdate();
//					if(count > 0){
//						log.info("更改日期为"+summaryDate+"的银联原始交易数据字段状态，受影响条数为"+count);
//					}else{
//						log.info("更改日期为"+summaryDate+"的银联原始交易数据字段状态，受影响条数为0");
//					}
				}else{
					log.info("日期为"+summaryDate+"的银联原始交易数据条数为0，不需要更改字段状态");
				}
				log.info("更新银联CUPS对账文件数据状态");
				SQLQuery query_dz = session.createSQLQuery("select count(*) from duizhang_cups_lst where deduct_stlm_date = ?");
				query_dz.setString(0, summaryDate.replaceAll("-", ""));
				Integer count_cups = Integer.valueOf(query_dz.uniqueResult().toString());
				if(count_cups > 0){
					SQLQuery query = session.createSQLQuery("update duizhang_cups_lst set whetherErroeHandle = 0,bk_chk = 0 where deduct_stlm_date = ?");
					query.setString(0, summaryDate.replaceAll("-", ""));
					int count_dz = query.executeUpdate();
					if(count_dz > 0){
						log.info("更改日期为"+summaryDate+"的银联对账数据 是否差错处理字段状态，受影响条数为"+count_dz);
					}else{
						log.info("更改日期为"+summaryDate+"的银联对账数据 是否差错处理字段状态，受影响条数为0");
					}
				}else{
					log.info("日期为"+summaryDate+"的银联对账数据条数为0，不需要更改是否差错处理字段状态");
				}
				//修改银联CUPS日切数据
				log.info("修改银联CUPS日切数据");
				SQLQuery query_riqie = session.createSQLQuery("select count(*) from riqie_cups_lst where deduct_stlm_date like ?");
				query_riqie.setString(0, summaryDate + "%");
				Integer count_riqie = Integer.valueOf(query_riqie.uniqueResult().toString());
				log.info("需修改的日切数据条数为："+count_riqie);
				if(count_riqie > 0){
					CallableStatement cs = (CallableStatement) session.connection().prepareCall("call proce_dispense_with_handle(?,?,?)");
					cs.setObject(1, summaryDate);
					cs.setObject(2, instInfo.getBank().getRiqieOriginalTableName());
//					cs.setObject(2, "riqie_cups_lst");
					cs.setObject(3, 2);
					cs.execute();
//					SQLQuery sqlQuery_riqie = session.createSQLQuery("update riqie_cups_lst set bk_chk = ? and whetherJs = ? and whetherErroeHandle = ? where deduct_stlm_date like ? and deduct_sys_response not in('00','N1','N2') and deduct_sys_response is not null ");
//					sqlQuery_riqie.setInteger(0, 0);
//					sqlQuery_riqie.setBoolean(1, false);
//					sqlQuery_riqie.setBoolean(2, false);
//					sqlQuery_riqie.setString(3, summaryDate + "%");
//					int count =  sqlQuery_riqie.executeUpdate();
//					if(count > 0 ){
//						log.info("修改日期为"+summaryDate+"的银联CUPS日切数据，受影响条数为"+count);
//					}else{
//						log.info("修改日期为"+summaryDate+"的银联CUPS日切数据，受影响条数为0");
//					}
				}else{
					log.info("日期为"+summaryDate+"的银联CUPS日切数据条数为0");
				}
				//删除银联CUPS差错数据
				log.info("删除银联CUPS差错数据");
				SQLQuery query_chacuo = session.createSQLQuery("select count(*) from error_data_lst where deduct_stlm_date like ? and deduct_sys_id = ?");
				query_chacuo.setString(0, summaryDate + "%");
				query_chacuo.setInteger(1,11);
				Integer count_chacuo = Integer.valueOf(query_chacuo.uniqueResult().toString());
				log.info("需删除的差错数据条数为："+count_chacuo);
				if(count_chacuo > 0){
					SQLQuery sqlQuery_chacuo = session.createSQLQuery("delete from error_data_lst where deduct_stlm_date like ? and deduct_sys_id = ?");
					sqlQuery_chacuo.setString(0, summaryDate + "%");
					sqlQuery_chacuo.setInteger(1,11);
					int count = sqlQuery_chacuo.executeUpdate();
					if(count > 0){
						log.info("删除日期为"+summaryDate+"的银联CUPS差错数据，受影响条数为"+count);
					}else{
						log.info("删除日期为"+summaryDate+"的银联CUPS差错数据，受影响条数为0");
					}
				}else{
					log.info("日期为"+summaryDate+"的银联CUPS差错数据条数为0，不需要删除");
				}
				//删除银联CUPS差错数据
				log.info("删除银联差错ylcups数据");
				SQLQuery query_ylchacuo = session.createSQLQuery("select count(*) from ylcups_error_entry where trade_time like ?");
				query_ylchacuo.setString(0, summaryDate + "%");
				Integer count_ylchacuo = Integer.valueOf(query_ylchacuo.uniqueResult().toString());
				log.info("需删除的银联差错ylcups数据条数为："+count_ylchacuo);
				if(count_ylchacuo > 0){
					SQLQuery sqlQuery_ylchacuo = session.createSQLQuery("delete from ylcups_error_entry where trade_time like ?");
					sqlQuery_ylchacuo.setString(0, summaryDate + "%");
					int count = sqlQuery_ylchacuo.executeUpdate();
					if(count > 0){
						log.info("删除日期为"+summaryDate+"的银联差错ylcups数据，受影响条数为"+count);
					}else{
						log.info("删除日期为"+summaryDate+"的银联差错ylcups数据，受影响条数为0");
					}
				}else{
					log.info("日期为"+summaryDate+"的银联差错ylcups数据条数为0，不需要删除");
				}
				flag = true;
				transaction.commit();
				log.info("事务提交，还原银联CUPS数据状态成功，返回成功标志");
			} catch (Exception e) {
				try {
					transaction.rollback();
				} catch (Exception e1) {
					log.error("事务回滚异常:"+e1);
					return flag;
				}
				log.error(e);
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn(summaryDate +", 交易时间不存在");
		}
		return flag;
	}
	
	public boolean sendMessage(String eventId,String msg_req_url){
		Session session = null;
		boolean flag = false; 
		try{
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select linkMan,remark from message_config where eventName = ?");
			query.setString(0, eventId);
			List<Object[]> list = query.list();
			List<String> list_phones = new ArrayList<String>();
			if(list!=null && list.size()>0){
				String linkMan = list.get(0)[0].toString();
				String[] linkMans = linkMan.split(",");
				for(int i=0;i<linkMans.length;i++){
					list_phones.add(linkMans[i]);
				}
				String remark = list.get(0)[1].toString();
				Map<String,Boolean> map = SMSSendUtil.sendRequest(list_phones,remark, msg_req_url);
				log.info(map.get(list_phones.get(1))==true?"发送成功":"发送失败");
			}else{
				log.warn("未找到短信联系人");
			}
		}catch(Exception e){
			log.error(e);
		}finally {
			if (session != null) {
				session.close();
			}
		}
		return flag;
	}
	
	/**
	 * 查询银联CUPS对账成功数据
	 * @param date
	 * @return
	 */
	public List<OriginalCupsLst> queryCupsDzSucessDataOfNotRollBk(String date){
		Session session = null;
		List<OriginalCupsLst> list = null; 
		if(date == null){
			log.error("OriginalCupsLstDAO queryCupsDzSucessData date param is not null");
			return null;
		}
		try {
			session = this.getSession();
			Date startTime = DYDataUtil.getformatConversionDate3(date);
			Date endTime = DYDataUtil.getformatConversionDate4(date);
			Query query = session.createQuery("from OriginalCupsLst where  DeductStlmDate BETWEEN ? and ? and WhetherQs = ? and DeductRollBk = ?");
			query.setParameter(0, startTime);
			query.setParameter(1, endTime);
			query.setBoolean(2, true);
			query.setBoolean(3, false);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}else{
				log.warn("from OriginalCupsLst where  DeductStlmDate like "+date +" and WhetherQs = true and DeductRollBk = true is not data");
			}
		}catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}
	/**
	 * 查询银联CUPS对账成功数据，冲正数据
	 * @param date
	 * @return
	 */
	public List<OriginalCupsLst> queryCupsDzSucessDataOfRollBk(String date){
		Session session = null;
		List<OriginalCupsLst> list = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(date)){
			try{
				session = this.getSession();
				Date startTime = DYDataUtil.getformatConversionDate3(date);
				Date endTime = DYDataUtil.getformatConversionDate4(date);
//				SQLQuery query = session.createSQLQuery("select DISTINCT * from original_cups_lst where req_sys_stance in (select req_sys_stance from original_cups_lst group by req_sys_stance having count(1) >= 2) and trade_time like ? and deduct_roll_bk = 1 and deduct_roll_bk_response = '00' and bk_chk = 1 group by deduct_roll_bk_response,req_sys_stance order by req_sys_stance");
				Query query = session.createQuery("from OriginalCupsLst where  DeductStlmDate BETWEEN ? and ? and DeductRollBk = true and DeductRollBkResponse in ('00','N1') and WhetherQs = true group by ReqSysStance HAVING count(*) >=1 order by ReqSysStance");
				query.setParameter(0, startTime);
				query.setParameter(1, endTime);
				list = query.list();
				if(list == null || list.size() == 0){
					log.info("日期为"+date+"的银联cups冲正成功且对账成功数据为0");
				}
			}catch(Exception e){
				log.error(e);
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn("日期参数不能为空");
		}
		return list;
	}
	/**
	 * 获得银联cups非冲正交易的对账成功数据的总金额
	 * @param date
	 * @return
	 */
	public Double getCupsTotalTradeAmountOfNotRollBk(String date){
		Session session = null;
		Double num = 0d;
		if(StringUtils.isNotBlank(date)){
			try {
				session = this.getSession();
				Date startTime = DYDataUtil.getformatConversionDate3(date);
				Date endTime = DYDataUtil.getformatConversionDate4(date);
				SQLQuery query = session.createSQLQuery("select sum(trade_amount) from original_cups_lst where deduct_stlm_date BETWEEN ? and ? and bk_chk = ? and deduct_roll_bk = ?");
				query.setParameter(0, startTime);
				query.setParameter(1, endTime);
				query.setInteger(2, 1);
				query.setBoolean(3, false);
				num = query.uniqueResult()==null?0:Double.valueOf(query.uniqueResult()+"");
				log.info("获得银联cups日期为"+date+"的非冲正交易的对账成功数据的总金额为"+num);
			}catch(Exception e){
				log.error(e);
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.info("时间参数为空");
		}
		return num;
	}
	/**
	 * 获得银联cups冲正交易的对账成功数据的总金额
	 * @param date
	 * @return
	 */
	public Double getCupsTotalTradeAmountOfRollBk(String date){
		Session session = null;
		Double num = 0d;
		if(StringUtils.isNotBlank(date)){
			try {
				session = this.getSession();
				Date startTime = DYDataUtil.getformatConversionDate3(date);
				Date endTime = DYDataUtil.getformatConversionDate4(date);
				SQLQuery query = session.createSQLQuery("select sum(t.trade_amount) from (select DISTINCT req_sys_stance ,trade_amount from original_cups_lst where  deduct_stlm_date BETWEEN ? and ? and deduct_roll_bk = 1 and deduct_roll_bk_response in ('00','N1') and bk_chk = 1 group by req_sys_stance having count(*) >= 1 order by req_sys_stance) t");
				query.setParameter(0, startTime);
				query.setParameter(1, endTime);
				num = query.uniqueResult()==null?0:Double.valueOf(query.uniqueResult()+"");
				log.info("获得银联cups日期为"+date+"的冲正交易的对账成功数据的总金额为"+num);
			}catch(Exception e){
				log.error(e);
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.info("时间参数为空");
		}
		return num;
	}
	/**
	 * 查询银联内部清算数据
	 * @param date
	 * @return
	 */
	public List<OriginalCupsLst> queryCupsInnerClearData(String date){
		Session session = null;
		List<OriginalCupsLst> list = null; 
		if(date == null){
			log.error("OriginalCupsLstDAO queryCupsInnerClearData date param is not null");
			return null;
		}
		try {
			session = this.getSession();
			Query query = session.createQuery("from OriginalCupsLst where DeductStlmDate like ? and DeductSysResponse = 00");
			query.setString(0, date+"%");
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}else{
				log.warn("from queryCupsInnerClearData where DeductStlmDate like "+date +" and DeductSysResponse = 00 is not data");
			}
		}catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}
	
	
	@Override
	public boolean updateDataRiqie(String reqSysStance,boolean whetherRiqie,String deductStmlDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try{
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery query = session.createSQLQuery("update original_cups_lst set whetherRiqie = ?,bk_chk = 0,whetherErroeHandle = 0 where deduct_sys_stance = ? and substring(deduct_stlm_date,1,10) = ?");
				query.setBoolean(0, whetherRiqie);
				query.setString(1, reqSysStance);
				query.setString(2, deductStmlDate);
				int count = query.executeUpdate();
				if(count > 0){
					flag = true;
					transaction.commit();
				}
			}catch(Exception e){
				log.error(e);
				transaction.rollback();
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn("流水号不能为空");
		}
		return flag;
	}

	public boolean reductionErrorDataStatusType(String summaryDate) throws Exception{
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(summaryDate)){
			try{
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery q = session.createSQLQuery("select count(*) from ylcups_error_entry where substring(deductStlmDate,1,10) = ?");
				q.setString(0, summaryDate);
				Integer num = Integer.valueOf(q.uniqueResult().toString());
				if(num > 0){
					SQLQuery query = session.createSQLQuery("update ylcups_error_entry set bk_chk = 0 where substring(deductStlmDate,1,10) = ?");
					query.setString(0, summaryDate);
					int count = query.executeUpdate();
					if(count > 0){
						flag = true;
						transaction.commit();
					}
				}else{
					log.info("清算日期为"+summaryDate+"的差错数据为0条");
					flag = true;
					transaction.commit();
				}
			}catch(Exception e){
				log.error(e);
				transaction.rollback();
				throw e;
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn("日期参数不能为空");
		}
		return flag;
	}

	@Override
	public boolean updateCleanAndErrorRiqie(String reqSysStance, boolean clean,
			Integer whetherErroeHandle, Integer tradeMsgType,
			boolean deductRollBk, String deductStmlDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update original_cups_lst set whetherQs = ? , whetherErroeHandle = ?,bk_chk = 2 where deduct_sys_stance = ? and trademsg_type = ? and deduct_roll_bk = ? and substring(deduct_stlm_date,1,10) = ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, whetherErroeHandle);
				sqlQuery.setString(2, reqSysStance);
				sqlQuery.setInteger(3, tradeMsgType);
				sqlQuery.setBoolean(4, deductRollBk);
				sqlQuery.setString(5, deductStmlDate);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
				}
			} catch (Exception e) {
				log.error(e);
				transaction.rollback();
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn(reqSysStance +", 流水号不存在");
		}
		return flag;
	}

	@Override
	public boolean updateCleanRiqie(String reqSysStance, boolean clean,Integer bkchk,
			Integer tradeMsgType, boolean deductRollBk, String deductStmlDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update original_cups_lst set whetherQs = ?,bk_chk = ? where deduct_sys_stance = ? and trademsg_type = ? and deduct_roll_bk = ? and substring(deduct_stlm_date,1,10) = ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, bkchk);
				sqlQuery.setString(2, reqSysStance);
				sqlQuery.setInteger(3, tradeMsgType);
				sqlQuery.setBoolean(4, deductRollBk);
				sqlQuery.setString(5,  deductStmlDate);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
				}
			} catch (Exception e) {
				log.error(e);
				transaction.rollback();
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn(reqSysStance +", 流水号不存在");
		}
		return flag;
	}
	
	@Override
	public boolean updateCleanRiqie(String reqSysStance, boolean clean,Integer bkchk, boolean deductRollBk, String deductStmlDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update original_cups_lst set whetherQs = ?,bk_chk = ? where deduct_sys_stance = ?  and deduct_roll_bk = ? and substring(deduct_stlm_date,1,10) = ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, bkchk);
				sqlQuery.setString(2, reqSysStance);
				sqlQuery.setBoolean(3, deductRollBk);
				sqlQuery.setString(4,  deductStmlDate);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
				}
			} catch (Exception e) {
				log.error(e);
				transaction.rollback();
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn(reqSysStance +", 流水号不存在");
		}
		return flag;
	}

	@Override
	public boolean updateCleanRiqie(String reqSysStance, boolean clean,
			Integer tradeMsgType, String deductStmlDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update original_cups_lst set whetherJs = ?,bk_chk = 1 where deduct_sys_stance = ? and trademsg_type = ? and substring(deduct_stlm_date,1,10) = ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setString(1, reqSysStance);
				sqlQuery.setInteger(2, tradeMsgType);
				sqlQuery.setString(3, deductStmlDate);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
				}
			} catch (Exception e) {
				log.error(e);
				transaction.rollback();
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn(reqSysStance +", 流水号不存在");
		}
		return flag;
	}

	@Override
	public boolean splitOriginalCupsData(String tradeTime) {

		log.info("进入银联原始交易数据拆分阶段...");
		Session session = null;
		Transaction ts = null;
		boolean flag = false;
		int split_count = -1;
		int no_split_count = -1;
		int count = -1;
		try {
			session = this.getSession();
			if (session != null) {
				ts = session.beginTransaction();
				CallableStatement cs = (CallableStatement) session.connection().prepareCall("call split_original_cups_lst(?,?,?,?)");
				cs.setObject(1, tradeTime);
				cs.registerOutParameter(2, Types.INTEGER);
				cs.registerOutParameter(3, Types.INTEGER);
				cs.registerOutParameter(4, Types.INTEGER);
				cs.execute();
				ts.commit();
				split_count = cs.getInt(2);
				no_split_count = cs.getInt(3);
				count = cs.getInt(4);
				log.info("分割数据条数：" + split_count);
				log.info("未分割时总条数：" + no_split_count);
				log.info("拆分临时表中的数据条数：" + count);
				flag = true;
			} else {
				log.info("splitOriginalCupsData 获取session为null");
			}
		} catch (Exception e) {
			ts.rollback();
			log.info("银联原始交易数据拆分出错：" + e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return flag;
	}
	/**
	 * 根据当前交易流水号查询原笔交易的交易时间(针对冲正交易)
	 * @param sysStance
	 * @return
	 */
	public String getOriginalTradeTimeOfRollBk(String sysStance,String date){
		Session session = null;
		String original_trade_time = "";
		try{
			session = this.getSession();
			Date startTime = DYDataUtil.getformatConversionDate3(date);
			Date endTime = DYDataUtil.getformatConversionDate4(date);
			SQLQuery query = session.createSQLQuery("select deduct_sys_time from original_cups_lst where req_sys_stance = ? and deduct_stlm_date BETWEEN ? and ?  and deduct_roll_bk = 0");
			query.setString(0, sysStance);
			query.setParameter(1, startTime);
			query.setParameter(2, endTime);
			original_trade_time = query.uniqueResult()+""; 
		}catch(Exception e){
			log.error(e);
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
	/**
	 * 根据当前交易流水号查询原笔交易的交易时间(针对撤销和退货交易)
	 * @param sysStance
	 * @return
	 */
	public String getOriginalTradeTimeOfCancel(String sysStance,int tradeMsgType,String date){
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
				SQLQuery query = session.createSQLQuery("select deduct_sys_time from original_cups_lst where deduct_sys_stance = ? and deduct_stlm_date BETWEEN ? and ? ");
				query.setString(0,sysStance);
				query.setParameter(1, startTime);
				query.setParameter(2, endTime);
				original_trade_time = query.uniqueResult()+"";
			}catch(Exception e){
				log.error(e);
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
	public OriginalCupsLst queryWhetherDzSuccess(String reqSysStance,
			String deductStmlDate, boolean deductRollBk) {
		Session session = null;
		OriginalCupsLst originalCupsLst = null;
		try {
			
			Date startTime = DYDataUtil.getformatConversionDate3(deductStmlDate);
			Date endTime = DYDataUtil.getformatConversionDate4(deductStmlDate);
			log.info("银联cups对账核对原笔对账结果开始时间 ："+startTime);
			log.info("银联cups对账核对原笔对账结果开始时间 ："+endTime);
			
			session = this.getSession();
			Query query = session.createQuery("select Id,BkChk from OriginalCupsLst where ReqSysStance = ? and DeductStlmDate BETWEEN ? and ? and DeductRollBk = ?");
			query.setParameter(0, reqSysStance);
			query.setParameter(1, startTime);
			query.setParameter(2, endTime);
			query.setParameter(3, deductRollBk);
			Object obj = query.uniqueResult();
			if(obj != null){
				Object[] objects = (Object[])obj;
				originalCupsLst = new OriginalCupsLst();
				originalCupsLst.setId(objects[0].toString());
				originalCupsLst.setBkChk(Integer.valueOf(objects[1].toString()));
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return originalCupsLst;
	}
	
	@Override
	public OriginalCupsLst queryWhetherDzSuccessAll(String reqSysStance,
			String deductStmlDate, boolean deductRollBk) {
		Session session = null;
		OriginalCupsLst originalCupsLst = null;
		try {
			
			Date startTime = DYDataUtil.getformatConversionDate3(deductStmlDate);
			Date endTime = DYDataUtil.getformatConversionDate4(deductStmlDate);
			log.info("银联cups对账核对原笔对账结果开始时间 ："+startTime);
			log.info("银联cups对账核对原笔对账结果开始时间 ："+endTime);
			
			session = this.getSession();
			Query query = session.createQuery("from OriginalCupsLst where ReqSysStance = ? and DeductStlmDate BETWEEN ? and ? and DeductRollBk = ?");
			query.setParameter(0, reqSysStance);
			query.setParameter(1, startTime);
			query.setParameter(2, endTime);
			query.setParameter(3, deductRollBk);
			Object obj = query.uniqueResult();
			if(obj != null){
				originalCupsLst = (OriginalCupsLst)obj;
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return originalCupsLst;
	}
	
	@Override
	public boolean updateNoNeedHandle(String reqSysStance, boolean clean,
			Integer bkchk, Integer tradeMsgType, String deductStmlDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				Date startTime = DYDataUtil.getformatConversionDate3(deductStmlDate);
				Date endTime = DYDataUtil.getformatConversionDate4(deductStmlDate);
				SQLQuery sqlQuery = session.createSQLQuery("update original_cups_lst set whetherQs = ?,bk_chk = ? where deduct_sys_stance = ? and trademsg_type = ? and deduct_stlm_date BETWEEN ? and ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, bkchk);
				sqlQuery.setString(2, reqSysStance);
				sqlQuery.setInteger(3, tradeMsgType);
				sqlQuery.setParameter(4, startTime);
				sqlQuery.setParameter(5, endTime);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
				}
			} catch (Exception e) {
				transaction.rollback();
				log.error(e);
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn(reqSysStance +" 流水号不存在");
		}
		return flag;
	}

	@Override
	public boolean updateOriginalError(String reqSysStance, boolean clean,
			Integer bkchk, Integer whetherError, boolean deductRollBk,
			String tradeTime) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update original_cups_lst set whetherQs = ?,bk_chk = ?,whetherErroeHandle = ? where deduct_sys_stance = ? and substring(deduct_stlm_date,1,10) = ? and deduct_roll_bk = ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, bkchk);
				sqlQuery.setInteger(2, whetherError);
				sqlQuery.setString(3, reqSysStance);
				sqlQuery.setString(4, tradeTime);
				sqlQuery.setBoolean(5, deductRollBk);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
				}
			} catch (Exception e) {
				transaction.rollback();
				log.error(e);
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn(reqSysStance +", 流水号不存在");
		}
		return flag;
	}
	@Override
	public boolean dispenseWithHandleOfCups(String tradeTime, String tableName) {
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
				cs.setObject(3, 1);
				cs.execute();
				ts.commit();
				flag = true;
			} else {
				log.info("dispenseWithHandleOfCups 获取session为null");
			}
		} catch (Exception e) {
			ts.rollback();
			log.error(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return flag;
	}

	@Override
	public boolean updateSettleInfoRiqie(String reqSysStance,
			boolean whetherTk,Double zf_fee, String zf_file_fee, Integer tradeMsgType,
			boolean deductRollBk, String originalReqTime,Integer whetherReturnFee) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				Date startTime = DYDataUtil.getformatConversionDate3(originalReqTime);
				Date endTime = DYDataUtil.getformatConversionDate4(originalReqTime);
				Integer zfFeeBj = 2;//0:无需比对、1:比对成功、2:比对失败  ->默认比对失败
//				if(tradeMsgType == 20 && whetherTk && whetherReturnFee == 0){//退货交易
//					zf_fee = 0d;
//				}
				Double zf_file_fee_double = Double.valueOf(zf_file_fee);
				if(zf_fee.equals(Double.valueOf(zf_file_fee_double))){
					zfFeeBj = 1;
				}
				StringBuffer sb = new StringBuffer();
				if(whetherTk){
					zf_fee = 0-zf_fee;
					if(zf_file_fee_double == 0){
						sb.append(zf_file_fee);
					}else{
						sb.append("-");
						sb.append(zf_file_fee);
					}
				}else{
					//消费or预授权完成or消费撤销冲正or预授权完成撤销冲正or电子现金(脱机消费)
					if(tradeMsgType == 2 || tradeMsgType == 56 || tradeMsgType == 28 || tradeMsgType == 82 || tradeMsgType == 110){
						sb.append(zf_file_fee);
					}else{
						zf_fee = 0-zf_fee;
						if(zf_file_fee_double == 0){
							sb.append(zf_file_fee);
						}else{
							sb.append("-");
							sb.append(zf_file_fee);
						}
					}
				}
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update original_cups_lst set whetherTk = ?,zf_file_fee = ?,zf_fee = ?,zf_fee_bj = ? where deduct_sys_stance = ? and trademsg_type = ? and deduct_roll_bk = ? and deduct_stlm_date BETWEEN ? and ?");
				sqlQuery.setBoolean(0, whetherTk);
				sqlQuery.setString(1, sb.toString());
				sqlQuery.setDouble(2, zf_fee);
				sqlQuery.setInteger(3, zfFeeBj);
				sqlQuery.setString(4, reqSysStance);
				sqlQuery.setInteger(5, tradeMsgType);
				sqlQuery.setBoolean(6, deductRollBk);
				sqlQuery.setParameter(7, startTime);
				sqlQuery.setParameter(8, endTime);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
				}
			} catch (Exception e) {
				transaction.rollback();
				log.error(e);
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn(reqSysStance +" 流水号不存在");
		}
		return flag;
	}


	@Override
	public boolean updateSettleInfo(String reqSysStance,
			boolean whetherTk,Double zf_fee, String zf_file_fee, Integer tradeMsgType,
			boolean deductRollBk, String originalReqTime,Integer whetherReturnFee) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				Date startTime = DYDataUtil.getformatConversionDate3(originalReqTime);
				Date endTime = DYDataUtil.getformatConversionDate4(originalReqTime);
				Integer zfFeeBj = 2;//0:无需比对、1:比对成功、2:比对失败  ->默认比对失败
//				if(tradeMsgType == 20 && whetherTk && whetherReturnFee == 0){//退货交易
//					zf_fee = 0d;
//				}
				Double zf_file_fee_double = Double.valueOf(zf_file_fee);
				if(zf_fee.equals(Double.valueOf(zf_file_fee_double))){
					zfFeeBj = 1;
				}
				StringBuffer sb = new StringBuffer();
				if(whetherTk){
					zf_fee = 0-zf_fee;
					if(zf_file_fee_double == 0){
						sb.append(zf_file_fee);
					}else{
						sb.append("-");
						sb.append(zf_file_fee);
					}
				}else{
					//消费or预授权完成or消费撤销冲正or预授权完成撤销冲正or电子现金(脱机消费)
					if(tradeMsgType == 2 || tradeMsgType == 56 || tradeMsgType == 28 || tradeMsgType == 82 || tradeMsgType == 110){
						sb.append(zf_file_fee);
					}else{
						zf_fee = 0-zf_fee;
						if(zf_file_fee_double == 0){
							sb.append(zf_file_fee);
						}else{
							sb.append("-");
							sb.append(zf_file_fee);
						}
					}
				}
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update original_cups_lst set whetherTk = ?,zf_file_fee = ?,zf_fee = ?,zf_fee_bj = ? where deduct_sys_stance = ? and trademsg_type = ? and deduct_roll_bk = ? and trade_time BETWEEN ? and ?");
				sqlQuery.setBoolean(0, whetherTk);
				sqlQuery.setString(1, sb.toString());
				sqlQuery.setDouble(2, zf_fee);
				sqlQuery.setInteger(3, zfFeeBj);
				sqlQuery.setString(4, reqSysStance);
				sqlQuery.setInteger(5, tradeMsgType);
				sqlQuery.setBoolean(6, deductRollBk);
				sqlQuery.setParameter(7, startTime);
				sqlQuery.setParameter(8, endTime);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
				}
			} catch (Exception e) {
				transaction.rollback();
				log.error(e);
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn(reqSysStance +" 流水号不存在");
		}
		return flag;
	}
}