package cn.com.chinaebi.dz.object.dao;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseRytZhxtDAO;


public class RytZhxtDAO extends BaseRytZhxtDAO implements cn.com.chinaebi.dz.object.dao.iface.RytZhxtDAO {

	public RytZhxtDAO () {}
	
	public RytZhxtDAO (Session session) {
		super(session);
	}


}