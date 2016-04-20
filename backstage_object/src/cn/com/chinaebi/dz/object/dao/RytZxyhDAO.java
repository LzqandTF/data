package cn.com.chinaebi.dz.object.dao;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseRytZxyhDAO;


public class RytZxyhDAO extends BaseRytZxyhDAO implements cn.com.chinaebi.dz.object.dao.iface.RytZxyhDAO {

	public RytZxyhDAO () {}
	
	public RytZxyhDAO (Session session) {
		super(session);
	}


}