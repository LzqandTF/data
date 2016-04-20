package cn.com.chinaebi.dz.object.dao;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseDzFileColumnConfDAO;


public class DzFileColumnConfDAO extends BaseDzFileColumnConfDAO implements cn.com.chinaebi.dz.object.dao.iface.DzFileColumnConfDAO {

	public DzFileColumnConfDAO () {}
	
	public DzFileColumnConfDAO (Session session) {
		super(session);
	}


}