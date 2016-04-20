package cn.com.chinaebi.dz.object.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.DuizhangErrorCupsLst;
import cn.com.chinaebi.dz.object.base.BaseDuizhangErrorCupsLstDAO;


public class DuizhangErrorCupsLstDAO extends BaseDuizhangErrorCupsLstDAO implements cn.com.chinaebi.dz.object.dao.iface.DuizhangErrorCupsLstDAO {

	private Log log =LogFactory.getLog(getClass());
	
	public DuizhangErrorCupsLstDAO () {}
	
	public DuizhangErrorCupsLstDAO (Session session) {
		super(session);
	}

	@Override
	public List<DuizhangErrorCupsLst> findDateData(String reqTime) {
		Session session = null;
		List<DuizhangErrorCupsLst> list = null;
		try {
			session = this.getSession();
//			SQLQuery query = session.createSQLQuery("select count(*) from duizhang_error_cups_lst where reqTime like '"+reqTime+"%'");
			Query query = session.createQuery("from DuizhangErrorCupsLst where ReqTime like '"+reqTime+"%'");
			Object obj = query.list();
			if(obj != null){
				list = (List<DuizhangErrorCupsLst>)obj;
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return list;
	}

	@Override
	public boolean findDzFileData(String reqStance)throws Exception {
		Session session = null;
		boolean flag = false;
		
		if(StringUtils.isEmpty(reqStance)){
			log.error("流水号不能为空");
			return false;
		}
		
		try {
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select count(*) from duizhang_error_cups_lst where reqSysStance = ?");
			query.setString(0, reqStance);
			Integer count = Integer.valueOf(query.uniqueResult().toString());
			if(count > 0){
				flag = true;
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return flag;
	}
	
	@Override
	public boolean updateClean(String reqSysStance,int duizhangFlag) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update duizhang_error_cups_lst set bk_chk = ? where origDataStance = ?");
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
}