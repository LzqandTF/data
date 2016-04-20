package cn.com.chinaebi.dz.object.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.YlcupsErrorEntry;
import cn.com.chinaebi.dz.object.base.BaseYlcupsErrorEntryDAO;


public class YlcupsErrorEntryDAO extends BaseYlcupsErrorEntryDAO implements cn.com.chinaebi.dz.object.dao.iface.YlcupsErrorEntryDAO {

	private Log log = LogFactory.getLog(getClass());
	public YlcupsErrorEntryDAO () {}
	
	public YlcupsErrorEntryDAO (Session session) {
		super(session);
	}

	@Override
	public List<YlcupsErrorEntry> findYlcupsErrorEntryLst(
			String errorDataReqTime) {
		Session session = null;
		List<YlcupsErrorEntry> list = null; 
		if(StringUtils.isEmpty(errorDataReqTime)){
			log.error("YlcupsErrorEntryDAO findErrorDataCupsbankLst errorDataReqTime param is not null");
			return null;
		}
		try {
			session = this.getSession();
			Query query = session.createQuery("from YlcupsErrorEntry where entering_time like '"+errorDataReqTime+"%' and trade_status = 2 order by entering_time asc");
			query.setString(0, errorDataReqTime);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}else{
				log.warn("from YlcupsErrorEntry where entering_time like '"+errorDataReqTime+"%' and trade_status = 2 order by entering_time asc is not data");
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			log.error(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}

	@Override
	public boolean updateClean(String reqSysStance, int duizhangFlag) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update ylcups_error_entry set bk_chk = ? where reqSysStance = ?");
				sqlQuery.setInteger(0, duizhangFlag);
				sqlQuery.setString(1, reqSysStance);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
				}
			} catch (Exception e) {
				transaction.rollback();
				log.error(e);
				log.error(e.getMessage());
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
	public boolean rytUpdateClean(String deductSysReference,int duizhangFlag) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(deductSysReference)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update ylcups_error_entry set bk_chk = ? where deduct_sys_reference = ?");
				sqlQuery.setInteger(0, duizhangFlag);
				sqlQuery.setString(1, deductSysReference);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
				}
			} catch (Exception e) {
				transaction.rollback();
				log.error(e);
				log.error(e.getMessage());
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.error(deductSysReference +" 参考号不能为空");
		}
		return flag;
	}

	@Override
	public boolean findYlcupsErrorEntry(String reqSysStance) {
		Session session = null;
		boolean flag = false;
		try {
			if(StringUtils.isNotBlank(reqSysStance)){
				session = this.getSession();
				Query query = session.createSQLQuery("select count(*) from ylcups_error_entry where reqSysStance = ?");
				query.setString(0, reqSysStance);
				Integer count = Integer.valueOf(query.uniqueResult().toString());
				if(count > 0){
					flag = true;
				}
			}else{
				log.error("查询银联cups差错原始交易数据时流水号不能为空");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			closeSession(session);
		}
		return flag;
	}
	
	@Override
	public boolean findRytYlcupsErrorEntry(String deductSysReference) {
		Session session = null;
		boolean flag = false;
		try {
			if(StringUtils.isNotBlank(deductSysReference)){
				session = this.getSession();
				Query query = session.createSQLQuery("select count(*) from ylcups_error_entry where deduct_sys_reference = ?");
				query.setString(0, deductSysReference);
				Integer count = Integer.valueOf(query.uniqueResult().toString());
				if(count > 0){
					flag = true;
				}
			}else{
				log.error("查询银联cups差错原始交易数据时参考号不能为空");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			closeSession(session);
		}
		return flag;
	}


}