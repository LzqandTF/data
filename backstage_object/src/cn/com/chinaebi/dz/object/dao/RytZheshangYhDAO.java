package cn.com.chinaebi.dz.object.dao;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseRytZheshangYhDAO;


public class RytZheshangYhDAO extends BaseRytZheshangYhDAO implements cn.com.chinaebi.dz.object.dao.iface.RytZheshangYhDAO {

	public RytZheshangYhDAO () {}
	
	public RytZheshangYhDAO (Session session) {
		super(session);
	}


}