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
import cn.com.chinaebi.dz.object.DuizhangErrorUpmpLst;
import cn.com.chinaebi.dz.object.base.BaseDuizhangErrorUpmpLstDAO;


public class DuizhangErrorUpmpLstDAO extends BaseDuizhangErrorUpmpLstDAO implements cn.com.chinaebi.dz.object.dao.iface.DuizhangErrorUpmpLstDAO {

	private Log log =LogFactory.getLog(getClass());
	
	public DuizhangErrorUpmpLstDAO () {}
	
	public DuizhangErrorUpmpLstDAO (Session session) {
		super(session);
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
				SQLQuery sqlQuery = session.createSQLQuery("update duizhang_error_upmp_lst set bk_chk = ? where deductSysReference = ?");
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
	public List<DuizhangErrorUpmpLst> findDateData(String reqTime) {
		Session session = null;
		List<DuizhangErrorUpmpLst> list = null;
		try {
			session = this.getSession();
//			SQLQuery query = session.createSQLQuery("select count(*) from duizhang_error_cups_lst where reqTime like '"+reqTime+"%'");
			Query query = session.createQuery("from DuizhangErrorUpmpLst where ReqTime like '"+reqTime+"%'");
			Object obj = query.list();
			if(obj != null){
				list = (List<DuizhangErrorUpmpLst>)obj;
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

}