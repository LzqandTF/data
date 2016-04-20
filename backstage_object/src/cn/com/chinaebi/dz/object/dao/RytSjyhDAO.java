package cn.com.chinaebi.dz.object.dao;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseRytSjyhDAO;


public class RytSjyhDAO extends BaseRytSjyhDAO implements cn.com.chinaebi.dz.object.dao.iface.RytSjyhDAO {

	public RytSjyhDAO () {}
	
	public RytSjyhDAO (Session session) {
		super(session);
	}


}