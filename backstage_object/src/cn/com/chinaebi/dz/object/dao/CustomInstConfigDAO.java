package cn.com.chinaebi.dz.object.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import cn.com.chinaebi.dz.object.CustomInstConfig;
import cn.com.chinaebi.dz.object.base.BaseCustomInstConfigDAO;


public class CustomInstConfigDAO extends BaseCustomInstConfigDAO implements cn.com.chinaebi.dz.object.dao.iface.CustomInstConfigDAO {

	public CustomInstConfigDAO () {}
	
	public CustomInstConfigDAO (Session session) {
		super(session);
	}
	
	private static final Log log = LogFactory.getLog(CustomInstConfigDAO.class);
	
	/**
	 * 通过系统接口ID查询相关渠道配置
	 * @param object_id
	 * @return
	 */
	public List<CustomInstConfig> getCustomInstConfig(int object_id){
		List<CustomInstConfig> list = null;
		Session session = null;
		try{
			session = this.getSession();
			Query query = session.createQuery("from CustomInstConfig where ObjectId = ?");
			query.setInteger(0, object_id);
			list = query.list();
		}catch(Exception e){
			log.debug("根据系统接口ID"+object_id+"查询相关渠道配置方法，抛出异常");
			log.error(e);
		}finally{
			closeSession(session);
		}
		return list;
	}

}