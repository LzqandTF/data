package cn.com.chinaebi.dz.object.dao;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseRytGfyhDAO;


public class RytGfyhDAO extends BaseRytGfyhDAO implements cn.com.chinaebi.dz.object.dao.iface.RytGfyhDAO {

	public RytGfyhDAO () {}
	
	public RytGfyhDAO (Session session) {
		super(session);
	}


}