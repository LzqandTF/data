package cn.com.chinaebi.dz.object.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.CallableStatement;

import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.OriginalBeijingbankLst;
import cn.com.chinaebi.dz.object.base.BaseOriginalBeijingbankLstDAO;
import cn.com.chinaebi.dz.util.DYDataUtil;


public class OriginalBeijingbankLstDAO extends BaseOriginalBeijingbankLstDAO implements cn.com.chinaebi.dz.object.dao.iface.OriginalBeijingbankLstDAO {
	
	private Log log = LogFactory.getLog(getClass());
	
	public OriginalBeijingbankLstDAO () {}
	
	public OriginalBeijingbankLstDAO (Session session) {
		super(session);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OriginalBeijingbankLst> findOriginalBeijingbankLst(
			String originalReqTime) {
		Session session = null;
		List<OriginalBeijingbankLst> list = null; 
		if(originalReqTime == null){
			log.warn("OriginalBeijingbankLstDAO findRiqieBeijingbankLst originalReqTime param is not null");
			return null;
		}
		try {
			
			Date startTime = DYDataUtil.getformatConversionDate3(originalReqTime);
			Date endTime = DYDataUtil.getformatConversionDate4(originalReqTime);
			log.info("北京银行对账查询原始交易开始时间 ："+startTime);
			log.info("北京银行对账查询原始交易开始时间 ："+endTime);
			
			session = this.getSession();
			Query query = session.createQuery("from OriginalBeijingbankLst where TradeTime BETWEEN ? and ? and WhetherValid = 1 order by ReqTime,TrademsgType asc");
			query.setParameter(0, startTime);
			query.setParameter(1, endTime);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}else{
				log.warn("from OriginalBeijingbankLst where BETWEEN ? and ? and whetherValid = 1 is not data");
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
				session = this.getSession();
				transaction = session.beginTransaction();
				Date startTime = DYDataUtil.getformatConversionDate3(tradeTime);
				Date endTime = DYDataUtil.getformatConversionDate4(tradeTime);
				SQLQuery sqlQuery = session.createSQLQuery("update original_beijingbank_lst set whetherQs = ?,bk_chk = ? where req_sys_stance = ? and trademsg_type = ? and deduct_roll_bk = ? and trade_time BETWEEN ? and ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, bkchk);
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
			log.error(reqSysStance +" 流水号不存在");
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
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update original_beijingbank_lst set whetherQs = ?,bk_chk = 1 where req_sys_stance = ? and deduct_roll_bk = ? and substrng(deduct_stlm_date,1,10) = ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setString(1, reqSysStance);
				sqlQuery.setBoolean(2, deductRollBk);
				sqlQuery.setString(3, tradeTime);
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
			log.warn(reqSysStance +" ,流水号不存在");
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
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update original_beijingbank_lst set whetherQs = ?,bk_chk = 1 where req_sys_stance = ? and trademsg_type = ? and substring(trade_time,1,10) = ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setString(1, reqSysStance);
				sqlQuery.setInteger(2, tradeMsgType);
				sqlQuery.setString(3, tradeTime);
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
	public boolean updateCleanAndError(String reqSysStance, boolean clean,Integer whetherErroeHandle,Integer tradeMsgType,String tradeTime) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update original_beijingbank_lst set whetherJs = ? , whetherErroeHandle = ?,bk_chk = 2 where req_sys_stance = ? and trademsg_type = ? and substring(trade_time,1,10) = ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, whetherErroeHandle);
				sqlQuery.setString(2, reqSysStance);
				sqlQuery.setInteger(3, tradeMsgType);
				sqlQuery.setString(4, tradeTime);
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
			log.error(reqSysStance +" 流水号不存在");
		}
		return flag;
	}

	@Override
	public boolean getBeijingBankData(String tradeTime) {
		Session session = null;
		Transaction ts = null;
		int count = 0;
		boolean flag = false;
		try {
			session = this.getSession();
			if(session  != null){
				ts = session.beginTransaction();
				CallableStatement cs = (CallableStatement) session.connection().prepareCall("call proce_original_beijingbank_lst(?,?)");
				cs.setObject(1, tradeTime);
				cs.registerOutParameter(2, Types.INTEGER);
				cs.execute();
				ts.commit();
				count = cs.getInt(2);
				log.info("北京银行汇总数据条数：" + count);
				if (count > 0) {
					flag = true;
				}
			}else 
				log.info("getBeijingBankData 获取session 为null");
		} catch (Exception e) {
			ts.rollback();
			log.error("北京银行数据汇总出错：" + e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return flag;
	}
	/**
	 * 在进行手动汇总北京银行数据之前，根据交易时间，将之前由存储过程汇总完成的同一时间的数据删除
	 */
	public boolean deleteBeijingbankDataByTradeTime(String tradeTime){
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(tradeTime)){
			try{
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery q = session.createSQLQuery("select count(*) from original_beijingbank_lst where trade_time like ?");
				q.setString(0, tradeTime + "%");
				Integer c = Integer.valueOf(q.uniqueResult().toString());
				if(c > 0){
					SQLQuery query = session.createSQLQuery("delete from original_beijingbank_lst where trade_time like ?");
					query.setString(0, tradeTime + "%");
					int count = query.executeUpdate();
					if(count > 0){
						flag = true;
						transaction.commit();
					}
				}else{
					log.info("未查到北京银行日期为"+tradeTime+"的数据，不需要删除");
					flag = true;
				}
			}catch(Exception e){
				transaction.rollback();
				log.error(e);
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}
		return flag;
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
				String selectTradeLstSQL = "select req_process,trademsg_type,trade_amount from original_beijingbank_lst where substring(deduct_sys_time,1,10) = '"+tradeTime+"'";
				ResultSet rs = stmt.executeQuery(selectTradeLstSQL);
				if(rs.next()){
					while(rs.next()){
						if(rs.getInt(3) > 0){
							SQLQuery sqlQuery = session.createSQLQuery("select count(*) from trade_amount_conf where trade_money_status = 1 and  process = '"+rs.getString(1)+"'"+" and trademsg_type = "+rs.getInt(2));
							Integer count = Integer.valueOf(sqlQuery.uniqueResult().toString());
							if(count == 1){
								String updateSQL = "update original_beijingbank_lst set trade_amount = trade_amount-(trade_amount*2) where req_process = '"+rs.getString(1)+"' and trademsg_type = "+rs.getInt(2);
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
	
	
	/**
	 * 在手动对账之前，根据交易时间，将北京银行原始交易数据 以及北京银行对账数据是否差错处理字段的状态还原
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
				//更新北京银行原始交易数据的状态
				log.info("更新北京银行原始交易数据的状态");
				SQLQuery query_ori = session.createSQLQuery("select count(*) from original_beijingbank_lst where deduct_stlm_date like ?");
				query_ori.setString(0, summaryDate + "%");
				Integer count_ori = Integer.valueOf(query_ori.uniqueResult().toString());
				if(count_ori > 0){
					CallableStatement cs = (CallableStatement) session.connection().prepareCall("call proce_dispense_with_handle(?,?,?)");
					cs.setObject(1, summaryDate);
					cs.setObject(2, instInfo.getBank().getOriginalDataTableName());
					cs.setObject(3, 2);
					cs.execute();
				}else{
					log.info("日期为"+summaryDate+"的北京银行原始交易数据条数为0，不需要更改字段状态");
				}
				//更改北京银行对账文件数据的状态
				log.info("更改北京银行对账文件数据的状态");
				SQLQuery query_dz = session.createSQLQuery("select count(*) from duizhang_beijingbank_lst where deduct_stlm_date = ?");
				query_dz.setString(0, summaryDate.replaceAll("-", ""));
				Integer count_beijing = Integer.valueOf(query_dz.uniqueResult().toString());
				if(count_beijing > 0){
					SQLQuery sqlQuery_dz = session.createSQLQuery("update duizhang_beijingbank_lst set whetherErroeHandle = 0,bk_chk = 0 where deduct_stlm_date = ?");
					sqlQuery_dz.setString(0, summaryDate.replaceAll("-", ""));
					int count_dz = sqlQuery_dz.executeUpdate();
					if(count_dz > 0){
						log.info("更改日期为"+summaryDate+"的北京银行对账数据 是否差错处理字段状态，受影响条数为"+count_dz);
					}else{
						log.info("更改日期为"+summaryDate+"的北京银行对账数据 是否差错处理字段状态，受影响条数为0");
					}
				}else{
					log.info("日期为"+summaryDate+"的北京银行对账数据条数为0，不需要更改是否差错处理字段状态");
				}
				//修改北京银行日切数据
				log.info("修改北京银行日切数据");
				SQLQuery query_riqie = session.createSQLQuery("select count(*) from riqie_beijingbank_lst where deduct_stlm_date like ?");
				query_riqie.setString(0, summaryDate + "%");
				Integer count_riqie = Integer.valueOf(query_riqie.uniqueResult().toString());
				log.info("需修改的日切数据条数为："+count_riqie);
				if(count_riqie > 0){
					CallableStatement cs = (CallableStatement) session.connection().prepareCall("call proce_dispense_with_handle(?,?,?)");
					cs.setObject(1, summaryDate);
					cs.setObject(2, instInfo.getBank().getRiqieOriginalTableName());
					cs.setObject(3, 2);
					cs.execute();
				}else{
					log.info("日期为"+summaryDate+"的北京银行日切数据条数为0");
				}
				//删除北京银行差错数据
				log.info("删除北京银行差错数据");
				SQLQuery query_chacuo = session.createSQLQuery("select count(*) from error_data_lst where deduct_stlm_date like ? and deduct_sys_id = ?");
				query_chacuo.setString(0, summaryDate + "%");
				query_chacuo.setInteger(1, 70001);
				Integer count_chacuo = Integer.valueOf(query_chacuo.uniqueResult().toString());
				log.info("需删除的差错数据条数为："+count_chacuo);
				if(count_chacuo > 0){
					SQLQuery sqlQuery_chacuo = session.createSQLQuery("delete from error_data_lst where deduct_stlm_date like ? and deduct_sys_id = ?");
					sqlQuery_chacuo.setString(0, summaryDate + "%");
					sqlQuery_chacuo.setInteger(1, 70001);
					int count = sqlQuery_chacuo.executeUpdate();
					if(count > 0){
						log.info("删除日期为"+summaryDate+"的北京银行差错数据，受影响条数为"+count);
					}else{
						log.info("删除日期为"+summaryDate+"的北京银行差错数据，受影响条数为0");
					}
				}else{
					log.info("日期为"+summaryDate+"的北京银行差错数据条数为0，不需要删除");
				}
				flag = true;
				transaction.commit();
				log.info("事务提交，还原北京银行原始交易数据状态以及北京银行对账数据是否差错处理状态成功，返回成功标志");
			} catch (Exception e) {
				try {
					transaction.rollback();
				} catch (Exception e1) {
					log.error("事务回滚抛出异常:"+e1);
					log.error("还原北京银行数据状态失败");
					return flag;
				}
				log.error(e);
				log.error("还原北京银行数据状态失败");
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
	
	/**
	 * 查询上海银联POSP对账成功数据
	 * @param date
	 * @return
	 */
	public List<OriginalBeijingbankLst> queryBeijingbankDzSucessDataOfNotRollBk(String date){
		Session session = null;
		List<OriginalBeijingbankLst> list = null; 
		if(date == null){
			log.error("OriginalBeijingbankLstDAO queryBeijingbankDzSucessData date param is not null");
			return null;
		}
		try {
			session = this.getSession();
			Date startTime = DYDataUtil.getformatConversionDate3(date);
			Date endTime = DYDataUtil.getformatConversionDate4(date);
			Query query = session.createQuery("from OriginalBeijingbankLst where  DeductStlmDate BETWEEN ? and ? and WhetherQs = ? and DeductRollBk = ?");
			query.setParameter(0, startTime);
			query.setParameter(1, endTime);
			query.setBoolean(2, true);
			query.setBoolean(3, false);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}else{
				log.warn("from OriginalBeijingbankLst where  DeductStlmDate like "+date +" and WhetherQs = true and DeductRollBk = true is not data");
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
	 * 查询上海银联POSP对账成功数据，冲正数据
	 * @param date
	 * @return
	 */
	public List<OriginalBeijingbankLst> queryBeijingbankDzSucessDataOfRollBk(String date){
		Session session = null;
		List<OriginalBeijingbankLst> list = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(date)){
			try{
				session = this.getSession();
				Date startTime = DYDataUtil.getformatConversionDate3(date);
				Date endTime = DYDataUtil.getformatConversionDate4(date);
				Query query = session.createQuery("from OriginalBeijingbankLst where DeductStlmDate BETWEEN ? and ? and DeductRollBk = true and DeductRollBkResponse in ('00','N1') and WhetherQs = true group by ReqSysStance HAVING count(*) >=1 order by ReqSysStance");
				query.setParameter(0, startTime);
				query.setParameter(1, endTime);
				list = query.list();
				if(list == null || list.size() == 0){
					log.info("日期为"+date+"的北京银行冲正成功且对账成功数据为0");
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
	 * 获得上海银联POSP非冲正交易的对账成功数据的总金额
	 * @param date
	 * @return
	 */
	public Double getBeijingbankTotalTradeAmountOfNotRollBk(String date){
		Session session = null;
		Double num = 0d;
		if(StringUtils.isNotBlank(date)){
			try {
				session = this.getSession();
				Date startTime = DYDataUtil.getformatConversionDate3(date);
				Date endTime = DYDataUtil.getformatConversionDate4(date);
				SQLQuery query = session.createSQLQuery("select sum(trade_amount) from original_beijingbank_lst where deduct_stlm_date BETWEEN ? and ? and bk_chk = ? and deduct_roll_bk = ?");
				query.setParameter(0, startTime);
				query.setParameter(1, endTime);
				query.setInteger(2, 1);
				query.setBoolean(3, false);
				num = query.uniqueResult()==null?0:Double.valueOf(query.uniqueResult()+"");
				log.info("获得上海银联POSP日期为"+date+"的非冲正交易的对账成功数据的总金额为"+num);
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
	 * 获得上海银联POSP冲正交易的对账成功数据的总金额
	 * @param date
	 * @return
	 */
	public Double getBeijingbankTotalTradeAmountOfRollBk(String date){
		Session session = null;
		Double num = 0d;
		if(StringUtils.isNotBlank(date)){
			try {
				session = this.getSession();
				Date startTime = DYDataUtil.getformatConversionDate3(date);
				Date endTime = DYDataUtil.getformatConversionDate4(date);
				SQLQuery query = session.createSQLQuery("select sum(t.trade_amount) from (select DISTINCT req_sys_stance ,trade_amount from original_beijingbank_lst where  deduct_stlm_date BETWEEN ? and ? and deduct_roll_bk = 1 and deduct_roll_bk_response in ('00','N1') and bk_chk = 1 group by req_sys_stance having count(*) >= 1 order by req_sys_stance) t");
				query.setParameter(0, startTime);
				query.setParameter(1, endTime);
				num = query.uniqueResult()==null?0:Double.valueOf(query.uniqueResult()+"");
				log.info("获得上海银联POSP日期为"+date+"的冲正交易的对账成功数据的总金额为"+num);
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

	@Override
	public boolean updateCleanAndError(String reqSysStance, boolean clean,Integer whetherErroeHandle,Integer tradeMsgType,boolean deductRollBk,String deductStmlDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update original_beijingbank_lst set whetherQs = ? , whetherErroeHandle = ?,bk_chk = 2 where req_sys_stance = ? and trademsg_type = ? and deduct_roll_bk = ? and substring(deduct_stlm_date,1,10) = ?");
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
				transaction.rollback();
				log.error(e);
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.error(reqSysStance +", 流水号不存在");
		}
		return flag;
	}
	/**
	 * 查询上海银联POSP内部清算数据
	 * @param date
	 * @return
	 */
	public List<OriginalBeijingbankLst> queryBeijingbankClearInnerData(String date){
		Session session = null;
		List<OriginalBeijingbankLst> list = null; 
		if(date == null){
			log.error("OriginalBeijingbankLstDAO queryBeijingbankClearInnerData date param is not null");
			return null;
		}
		try {
			session = this.getSession();
			Query query = session.createQuery("from OriginalBeijingbankLst where DeductStlmDate like ? and DeductSysResponse = 00");
			query.setString(0, date+"%");
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}else{
				log.warn("from queryBeijingbankClearInnerData where DeductStlmDate like "+date +" and DeductSysResponse = 00 is not data");
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
	public boolean updateDataRiqie(String reqSysStance,boolean whetherRiqie,String deductStlmDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try{
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery query = session.createSQLQuery("update original_beijingbank_lst set whetherRiqie = ?,bk_chk = 0,whetherErroeHandle = 0 where req_sys_stance = ? and substring(deduct_stlm_date,1,10) = ?");
				query.setBoolean(0, whetherRiqie);
				query.setString(1, reqSysStance);
				query.setString(2, deductStlmDate);
				int count = query.executeUpdate();
				if(count > 0){
					flag = true;
					transaction.commit();
				}
			}catch(Exception e){
				transaction.rollback();
				log.error(e);
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.info("流水号不能为空");
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
				Date startTime = DYDataUtil.getformatConversionDate3(deductStmlDate);
				Date endTime = DYDataUtil.getformatConversionDate4(deductStmlDate);
				SQLQuery sqlQuery = session.createSQLQuery("update original_beijingbank_lst set whetherQs = ?,bk_chk = ? where req_sys_stance = ? and trademsg_type = ? and deduct_roll_bk = ? and deduct_stlm_date BETWEEN ? and ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, bkchk);
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
			log.error(reqSysStance +" 流水号不存在");
		}
		return flag;
	}
	
	@Override
	public boolean updateCleanRiqie(String reqSysStance, boolean clean,
			 boolean deductRollBk, String deductStmlDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update original_beijingbank_lst set whetherQs = ?,bk_chk = 1 where req_sys_stance = ? and deduct_roll_bk = ? and substring(deduct_stlm_date,1,10) = ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setString(1, reqSysStance);
				sqlQuery.setBoolean(2, deductRollBk);
				sqlQuery.setString(3, deductStmlDate);
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
			log.error(reqSysStance +" 流水号不存在");
		}
		return flag;
	}

	@Override
	public boolean splitOriginalBeijingData(String tradeTime) {
		log.info("进入北京银行原始交易数据拆分阶段...");
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
				CallableStatement cs = (CallableStatement) session.connection().prepareCall("call split_original_beijingbank_lst(?,?,?,?)");
				cs.setObject(1, tradeTime);
				cs.registerOutParameter(2, Types.INTEGER);
				cs.registerOutParameter(3, Types.INTEGER);
				cs.registerOutParameter(4, Types.INTEGER);
				cs.execute();
				ts.commit();
				split_count = cs.getInt(2);
				no_split_count = cs.getInt(3);
				count = cs.getInt(4);
//				split_count = cs.getInt("split_count");
//				no_split_count = cs.getInt("no_split_count");
//				count = cs.getInt("count");
				log.info("分割数据条数：" + split_count);
				log.info("未分割时总条数：" + no_split_count);
				log.info("拆分临时表中的数据条数：" + count);
				flag = true;
			} else {
				log.info("splitOriginalBeijingData 获取session为null");
			}
		} catch (Exception e) {
			ts.rollback();
			log.info("北京银行原始交易数据拆分出错：" + e.getMessage());
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
			SQLQuery query = session.createSQLQuery("select deduct_sys_time from original_beijingbank_lst where req_sys_stance = ? and deduct_stlm_date BETWEEN ? and ? and deduct_roll_bk = 0 and trademsg_type = 2");
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
			}
			try{
				session = this.getSession();
				Date startTime = DYDataUtil.getformatConversionDate3(date);
				Date endTime = DYDataUtil.getformatConversionDate4(date);
				SQLQuery query = session.createSQLQuery("select deduct_sys_time from original_beijingbank_lst where req_sys_stance = ? and deduct_stlm_date BETWEEN ? and ? ");
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
	public OriginalBeijingbankLst queryWhetherDzSuccess(String reqSysStance,
			String deductStlmDate, boolean deductRollBk) {
		Session session = null;
		OriginalBeijingbankLst originalBeijingbankLst = null;
		try {
			
			Date startTime = DYDataUtil.getformatConversionDate3(deductStlmDate);
			Date endTime = DYDataUtil.getformatConversionDate4(deductStlmDate);
			log.info("北京银行核对原笔对账结果开始时间 ："+startTime);
			log.info("北京银行核对原笔对账结果开始时间 ："+endTime);
			
			session = this.getSession();
			Query query = session.createQuery("select Id,BkChk from OriginalBeijingbankLst where ReqSysStance = ? and DeductStlmDate BETWEEN ? and ? and DeductRollBk = ?");
			query.setParameter(0, reqSysStance);
			query.setParameter(1, startTime);
			query.setParameter(2, endTime);
			query.setParameter(3, deductRollBk);
			Object obj = query.uniqueResult();
			if(obj != null){
				Object[] objects = (Object[])obj;
				originalBeijingbankLst = new OriginalBeijingbankLst();
				originalBeijingbankLst.setId(objects[0].toString());
				originalBeijingbankLst.setBkChk(Integer.valueOf(objects[1].toString()));
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return originalBeijingbankLst;
	}

	
	@Override
	public OriginalBeijingbankLst queryWhetherDzSuccessAll(String reqSysStance,
			String deductStlmDate, boolean deductRollBk) {
		Session session = null;
		OriginalBeijingbankLst originalBeijingbankLst = null;
		try {
			
			Date startTime = DYDataUtil.getformatConversionDate3(deductStlmDate);
			Date endTime = DYDataUtil.getformatConversionDate4(deductStlmDate);
			log.info("北京银行核对原笔对账结果开始时间 ："+startTime);
			log.info("北京银行核对原笔对账结果开始时间 ："+endTime);
			
			session = this.getSession();
			Query query = session.createQuery("from OriginalBeijingbankLst where ReqSysStance = ? and DeductStlmDate BETWEEN ? and ? and DeductRollBk = ?");
			query.setParameter(0, reqSysStance);
			query.setParameter(1, startTime);
			query.setParameter(2, endTime);
			query.setParameter(3, deductRollBk);
			Object obj = query.uniqueResult();
			if(obj != null){
				originalBeijingbankLst = (OriginalBeijingbankLst)obj;
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return originalBeijingbankLst;
	}
	
	@Override
	public boolean updateCleanRiqie(String reqSysStance, boolean clean,
			Integer bkchk, boolean deductRollBk, String deductStmlDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update original_beijingbank_lst set whetherQs = ?,bk_chk = ? where req_sys_stance = ? and deduct_roll_bk = ? and substring(deduct_stlm_date,1,10) = ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, bkchk);
				sqlQuery.setString(2, reqSysStance);
				sqlQuery.setBoolean(3, deductRollBk);
				sqlQuery.setString(4, deductStmlDate);
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
			log.error(reqSysStance +" 流水号不存在");
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
				SQLQuery sqlQuery = session.createSQLQuery("update original_beijingbank_lst set whetherQs = ?,bk_chk = ?,whetherErroeHandle = ? where deduct_sys_stance = ? and substring(deduct_stlm_date,1,10) = ? and deduct_roll_bk = ?");
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
				Date startTime = DYDataUtil.getformatConversionDate3(deductStmlDate);
				Date endTime = DYDataUtil.getformatConversionDate4(deductStmlDate);
				SQLQuery sqlQuery = session.createSQLQuery("update original_beijingbank_lst set whetherQs = ? , whetherErroeHandle = ?,bk_chk = 2 where req_sys_stance = ? and trademsg_type = ? and deduct_roll_bk = ? and deduct_stlm_date BETWEEN ? and ?");
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
			log.error(reqSysStance +", 流水号不存在");
		}
		return flag;
	}

	@Override
	public boolean updateClean(String reqSysStance, boolean clean,
			Integer bkchk, boolean deductRollBk, String tradeTime) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update original_beijingbank_lst set whetherQs = ?,bk_chk = ? where req_sys_stance = ? and deduct_roll_bk = ? and substring(trade_time,1,10) = ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, bkchk);
				sqlQuery.setString(2, reqSysStance);
				sqlQuery.setBoolean(3, deductRollBk);
				sqlQuery.setString(4, tradeTime);
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
			log.error(reqSysStance +" 流水号不存在");
		}
		return flag;
	}

	@Override
	public boolean dispenseWithHandleOfBeijingBank(String tradeTime, String tableName) {
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
				log.info("dispenseWithHandleOfBeijingBank 获取session为null");
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
				SQLQuery sqlQuery = session.createSQLQuery("update original_beijingbank_lst set whetherTk = ?,zf_file_fee = ?,zf_fee = ?,zf_fee_bj = ? where deduct_sys_stance = ? and trademsg_type = ? and deduct_roll_bk = ? and deduct_stlm_date BETWEEN ? and ?");
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
				SQLQuery sqlQuery = session.createSQLQuery("update original_beijingbank_lst set whetherTk = ?,zf_file_fee = ?,zf_fee = ?,zf_fee_bj = ? where deduct_sys_stance = ? and trademsg_type = ? and deduct_roll_bk = ? and trade_time BETWEEN ? and ?");
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
				SQLQuery sqlQuery = session.createSQLQuery("update original_beijingbank_lst set whetherQs = ?,bk_chk = ? where deduct_sys_stance = ? and trademsg_type = ? and deduct_stlm_date BETWEEN ? and ?");
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
}