package cn.com.chinaebi.dz.object.dao;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseRytHebeiYhDAO;


public class RytHebeiYhDAO extends BaseRytHebeiYhDAO implements cn.com.chinaebi.dz.object.dao.iface.RytHebeiYhDAO {

	public RytHebeiYhDAO () {}
	
	public RytHebeiYhDAO (Session session) {
		super(session);
	}


}