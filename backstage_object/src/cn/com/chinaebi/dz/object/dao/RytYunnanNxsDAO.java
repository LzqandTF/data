package cn.com.chinaebi.dz.object.dao;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseRytYunnanNxsDAO;


public class RytYunnanNxsDAO extends BaseRytYunnanNxsDAO implements cn.com.chinaebi.dz.object.dao.iface.RytYunnanNxsDAO {

	public RytYunnanNxsDAO () {}
	
	public RytYunnanNxsDAO (Session session) {
		super(session);
	}


}