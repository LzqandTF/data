package cn.com.chinaebi.dz.object.dao;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseRytHengfengYhDAO;


public class RytHengfengYhDAO extends BaseRytHengfengYhDAO implements cn.com.chinaebi.dz.object.dao.iface.RytHengfengYhDAO {

	public RytHengfengYhDAO () {}
	
	public RytHengfengYhDAO (Session session) {
		super(session);
	}


}