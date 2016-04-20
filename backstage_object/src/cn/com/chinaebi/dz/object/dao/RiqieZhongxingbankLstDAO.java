package cn.com.chinaebi.dz.object.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.RiqieZhongxingbankLst;
import cn.com.chinaebi.dz.object.base.BaseRiqieZhongxingbankLstDAO;
import cn.com.chinaebi.dz.util.DYDataUtil;


public class RiqieZhongxingbankLstDAO extends BaseRiqieZhongxingbankLstDAO implements cn.com.chinaebi.dz.object.dao.iface.RiqieZhongxingbankLstDAO {

	private Log log = LogFactory.getLog(getClass());
	
	public RiqieZhongxingbankLstDAO () {}
	
	public RiqieZhongxingbankLstDAO (Session session) {
		super(session);
	}


	@Override
	public List<RiqieZhongxingbankLst> findRiqieZhongxingbankLst(String deductStlmDate) {
		Session session = null;
		List<RiqieZhongxingbankLst> list = null; 
		if(deductStlmDate == null){
			log.error("RiqieZhongxingbankLstDAO findRiqieZhongxingbankLst deductStlmDate param is not null");
			return null;
		}
		try {
			
			Date startTime = DYDataUtil.getformatConversionDate3(deductStlmDate);
			Date endTime = DYDataUtil.getformatConversionDate4(deductStlmDate);
			log.info("中信银行对账查询日切原始交易开始时间 ："+startTime);
			log.info("中信银行对账查询日切原始交易开始时间 ："+endTime);
			
			session = this.getSession();
			Query query = session.createQuery("from RiqieZhongxingbankLst where DeductStlmDate BETWEEN ? and ? and whetherValid = 1");
//			query.setString(0, deductStlmDate+"%");
			query.setParameter(0, startTime);
			query.setParameter(1, endTime);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}else{
				log.warn("from findRiqieZhongxingbankLst where BETWEEN ? and ? and whetherValid = 1 is not data");
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
	public boolean updateClean(String reqSysStance, boolean clean,Integer bkchk,Integer tradeMsgType,boolean deductRollBk,String deductStmlDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update riqie_zhongxingbank_lst set whetherQs = ?,bk_chk = ? where req_sys_stance = ? and trademsg_type = ? and deduct_roll_bk = ? and substring(deduct_stlm_date,1,10) = ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, bkchk);
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
			log.warn(reqSysStance +" 流水号不存在");
		}
		return flag;
	}
	
	@Override
	public boolean updateClean(String reqSysStance, boolean clean,boolean deductRollBk,String deductStmlDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update riqie_zhongxingbank_lst set whetherQs = ?,bk_chk = 1 where req_sys_stance = ? and deduct_roll_bk = ? and substring(deduct_stlm_date,1,10) = ?");
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
			log.warn(reqSysStance +" 流水号不存在");
		}
		return flag;
	}
	
	@Override
	public boolean updateCleanAndError(String reqSysStance, boolean clean,Integer whetherErroeHandle,Integer tradeMsgType,boolean deductRollBk,String deductStlmDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update riqie_zhongxingbank_lst set whetherQs = ? , whetherErroeHandle = ?,bk_chk = 2 where req_sys_stance = ? and trademsg_type = ? and deduct_roll_bk = ? and substring(deduct_stlm_date,1,10) = ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, whetherErroeHandle);
				sqlQuery.setString(2, reqSysStance);
				sqlQuery.setInteger(3, tradeMsgType);
				sqlQuery.setBoolean(4, deductRollBk);
				sqlQuery.setString(5, deductStlmDate);
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
	public boolean updateCleanAndError(String reqSysStance, boolean clean,Integer whetherErroeHandle,Integer tradeMsgType,String deductStlmDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update riqie_zhongxingbank_lst set whetherJs = ? , whetherErroeHandle = ?,bk_chk = 2 where req_sys_stance = ? and trademsg_type = ? and substring(deduct_stlm_date,1,10) = ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, whetherErroeHandle);
				sqlQuery.setString(2, reqSysStance);
				sqlQuery.setInteger(3, tradeMsgType);
				sqlQuery.setString(4, deductStlmDate);
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
	public boolean updateClean(String reqSysStance, boolean clean,
			Integer tradeMsgType,String deductStmlDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update riqie_zhongxingbank_lst set whetherQs = ?,bk_chk = 1 where req_sys_stance = ? and trademsg_type = ? and substring(deduct_stlm_date,1,10) = ?");
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
	public boolean saveRiqieZhongxing(RiqieZhongxingbankLst riqieZhongxingbankLst,Integer flagStatus) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			SQLQuery sqlQuery = session.createSQLQuery("select count(*) from riqie_zhongxingbank_lst where trade_id = ?");
			sqlQuery.setString(0, riqieZhongxingbankLst.getId());
			Integer count = Integer.valueOf(sqlQuery.uniqueResult().toString());
			if(count > 0 ){
				update(riqieZhongxingbankLst);
				transaction.commit();
				flag = true;
			}else{
				save(riqieZhongxingbankLst);
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
		return flag;
	}
	
}