package cn.com.chinaebi.dz.object.dao;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseRytGdyhDAO;


public class RytGdyhDAO extends BaseRytGdyhDAO implements cn.com.chinaebi.dz.object.dao.iface.RytGdyhDAO {

	public RytGdyhDAO () {}
	
	public RytGdyhDAO (Session session) {
		super(session);
	}


}