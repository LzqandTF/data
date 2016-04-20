package cn.com.chinaebi.dz.object.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import cn.com.chinaebi.dz.object.CustomObject;
import cn.com.chinaebi.dz.object.UnionpayAreaCode;
import cn.com.chinaebi.dz.object.base.BaseUnionpayAreaCodeDAO;


public class UnionpayAreaCodeDAO extends BaseUnionpayAreaCodeDAO implements cn.com.chinaebi.dz.object.dao.iface.UnionpayAreaCodeDAO {

	private Log log =LogFactory.getLog(getClass());
	
	public UnionpayAreaCodeDAO () {}
	
	public UnionpayAreaCodeDAO (Session session) {
		super(session);
	}
	
	/**
	 * 查询sql
	 * @param sql
	 * @return
	 */
	public UnionpayAreaCode getAreaCode(String prov){
		Session session = null;
		UnionpayAreaCode areaCode=null;
		try{
			session = this.getSession();
			Query  query = session.createQuery("from UnionpayAreaCode  where Id= ? ");
			query.setInteger(0, Integer.parseInt(prov));
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				areaCode = (UnionpayAreaCode) listResult.get(0);
			}else{
				log.warn(" not data");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return areaCode;
	}
}