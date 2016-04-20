package cn.com.chinaebi.dz.object.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseMerTradecodeDAO;


public class MerTradecodeDAO extends BaseMerTradecodeDAO implements cn.com.chinaebi.dz.object.dao.iface.MerTradecodeDAO {

	public MerTradecodeDAO () {}
	
	public MerTradecodeDAO (Session session) {
		super(session);
	}

	private Log log = LogFactory.getLog(getClass());
	
	public Map<String,Object> getMerCodeMap(Integer object_id){
		Map<String,Object> map = new HashMap<String,Object>();
		Session  session = null;
		try{
			session = this.getSession();
			String hql = "select value,status FROM mer_tradecode where object_id = ?";
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger(0, object_id);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				List<Object[]> list = listResult;
				for (Object[] object : list) {
					map.put(object[0].toString(), object[1]);
				}
			}
		}catch(Exception e){
			log.error(e);
		} finally {
			closeSession(session);
		}
		return map;
	}
	
	public List<String> getMerCodeList(Integer object_id){
		List<String> codeArr = null;
		Session  session = null;
		try{
			session = this.getSession();
//			String hql = "select value FROM mer_tradecode where object_id = ? and status = 0";
			String hql = "select value FROM mer_tradecode where object_id = ?";
			SQLQuery query = session.createSQLQuery(hql);
			query.setInteger(0, object_id);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				List<Object> list = listResult;
				codeArr = new ArrayList<String>();
				for (int i=0;i<list.size();i++) {
					codeArr.add(list.get(i)+"");
				}
			}
		}catch(Exception e){
			log.error(e);
		} finally {
			closeSession(session);
		}
		return codeArr;
	}
	
}