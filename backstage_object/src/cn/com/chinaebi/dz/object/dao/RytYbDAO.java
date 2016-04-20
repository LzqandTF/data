package cn.com.chinaebi.dz.object.dao;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseRytYbDAO;


public class RytYbDAO extends BaseRytYbDAO implements cn.com.chinaebi.dz.object.dao.iface.RytYbDAO {

	public RytYbDAO () {}
	
	public RytYbDAO (Session session) {
		super(session);
	}


}