package cn.com.chinaebi.dz.object.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.base.BaseInstInfoDAO;


public class InstInfoDAO extends BaseInstInfoDAO implements cn.com.chinaebi.dz.object.dao.iface.InstInfoDAO {

	private static Log log = LogFactory.getLog(InstInfoDAO.class);
	
	public InstInfoDAO () {}
	
	public InstInfoDAO (Session session) {
		super(session);
	}
	
	
	public InstInfo getInstInfoByIdInSQL(int instId,int inst_type) throws Exception{
		Session session = null;
		InstInfo instInfo = null; 
		Transaction transaction = null;
		List<?> listResult = null;
		try {
			session = this.getSession();
			session.flush();
			session.clear();
			transaction = session.beginTransaction();
			SQLQuery query = session.createSQLQuery("select * from inst_info where inst_id = ? and inst_type = ? ").addEntity(InstInfo.class);
			query.setInteger(0, instId);
			query.setInteger(1, inst_type);
			transaction.commit();
			listResult = query.list();
			for (Object object : listResult) {
				if(object instanceof InstInfo){
					instInfo = (InstInfo)object;
				}
			}
		}catch (Exception e) {
			log.error(e);
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		log.info(instInfo == null?("select * from inst_info where inst_id = "+instId+" is no data"):"查到渠道ID为"+instId+"的数据");
		return instInfo;
	}
	public String getInstInfoNameById(int instId,int inst_type){
		Session session = null;
		String name = null; 
		try {
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select name_ from inst_info where inst_id = ? and inst_type = ? ");
			query.setInteger(0, instId);
			query.setInteger(1, inst_type);
			name = query.uniqueResult().toString();
		}catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return name;
	}
	public String getReceiviNameById(int instId,int inst_type){
		Session session = null;
		String name = null; 
		try {
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select receivi_name from inst_info where inst_id = ? and inst_type = ? ");
			query.setInteger(0, instId);
			query.setInteger(1, inst_type);
			name = query.uniqueResult()==null?"":query.uniqueResult().toString();
		}catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return name;
	}
	
	/**
	 * 通过渠道ID查询渠道开通状态
	 * @param instId 渠道id
	 * @return
	 */
	public boolean getInstStatusByInstId(int instId,int inst_type){
		Session session = null;
		boolean status = true; 
		Transaction transaction = null;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			SQLQuery query = session.createSQLQuery("select active from inst_info where inst_id = ? and inst_type = ? ");
			query.setInteger(0, instId);
			query.setInteger(1, inst_type);
			transaction.commit();
			Object obj = query.uniqueResult();
			if(obj != null){
				status = Boolean.valueOf(obj.toString());
			}else{
				log.warn("from InstInfo where Id = "+ instId +" is not data");
			}
		}catch (Exception e) {
			log.error(e);
		} finally {
			session.flush();
			session.clear();
			closeSession(session);
		}
		return status;
	}
	/**
	 * 通过渠道ID和渠道标识查询渠道退款状态
	 * @param instId 渠道id
	 * @param inst_type 渠道标识   
	 * @return
	 */
	public InstInfo getInstStatusByInstIdAndInner(int instId,int instType){
		InstInfo instInfo=null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery(" from InstInfo where InstId = ? and InstType = ?");
			query.setInteger(0, instId);
			query.setInteger(1, instType);
			transaction.commit();
			Object obj = query.uniqueResult();
			if(obj != null){
				instInfo = (InstInfo)obj;
			}else{
				log.warn("from InstInfo where InstId = "+ instId +" is not data");
			}
		}catch (Exception e) {
			log.error(e);
		} finally {
			session.flush();
			session.clear();
			closeSession(session);
		}
		return instInfo;
	}
	
}