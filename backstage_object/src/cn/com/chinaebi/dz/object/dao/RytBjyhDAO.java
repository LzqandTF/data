package cn.com.chinaebi.dz.object.dao;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseRytBjyhDAO;


public class RytBjyhDAO extends BaseRytBjyhDAO implements cn.com.chinaebi.dz.object.dao.iface.RytBjyhDAO {

	public RytBjyhDAO () {}
	
	public RytBjyhDAO (Session session) {
		super(session);
	}


}