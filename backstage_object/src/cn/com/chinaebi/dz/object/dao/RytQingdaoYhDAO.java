package cn.com.chinaebi.dz.object.dao;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseRytQingdaoYhDAO;


public class RytQingdaoYhDAO extends BaseRytQingdaoYhDAO implements cn.com.chinaebi.dz.object.dao.iface.RytQingdaoYhDAO {

	public RytQingdaoYhDAO () {}
	
	public RytQingdaoYhDAO (Session session) {
		super(session);
	}


}