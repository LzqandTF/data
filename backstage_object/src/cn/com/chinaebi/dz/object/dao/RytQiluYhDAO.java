package cn.com.chinaebi.dz.object.dao;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseRytQiluYhDAO;


public class RytQiluYhDAO extends BaseRytQiluYhDAO implements cn.com.chinaebi.dz.object.dao.iface.RytQiluYhDAO {

	public RytQiluYhDAO () {}
	
	public RytQiluYhDAO (Session session) {
		super(session);
	}


}