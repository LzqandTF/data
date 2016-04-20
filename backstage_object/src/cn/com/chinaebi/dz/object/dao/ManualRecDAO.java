package cn.com.chinaebi.dz.object.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import cn.com.chinaebi.dz.object.ManualRec;
import cn.com.chinaebi.dz.object.base.BaseManualRecDAO;

public class ManualRecDAO extends BaseManualRecDAO implements cn.com.chinaebi.dz.object.dao.iface.ManualRecDAO {
	private Log log = LogFactory.getLog(getClass());
	
	public ManualRecDAO () {}
	
	public ManualRecDAO (Session session) {
		super(session);
	}

	@Override
	public Object queryCountAndMoney(String merCode, String startDate,String endDate) {
		Session session = null;
		Object object = null;
		try{
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select count(*), SUM(rec_amount) from manual_rec WHERE mer_code = ? AND REPLACE(substring(audit_time,1,10),'-','') BETWEEN ? AND ?");
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
	public List<ManualRec> queryManualRecLst(String merCode, String startDate,String endDate, int startRow, int endRow) {
		Session session = null;
		List<ManualRec> list = null;
		try{
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select * from manual_rec WHERE mer_code = ? AND REPLACE(substring(audit_time,1,10),'-','') BETWEEN ? AND ? limit ?, ?").addEntity(ManualRec.class);
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
	public List<ManualRec> queryManualRecDataLst(String merCode,String startDate, String endDate) {
		Session session = null;
		List<ManualRec> list = null;
		try{
			session = this.getSession();
			Query query = session.createQuery("from ManualRec where MerCode = ? and REPLACE(substring(AuditTime,1,10),'-','') between ? and ?");
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