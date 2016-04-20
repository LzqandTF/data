package cn.com.chinaebi.dz.object.dao;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BasePoliceTypeDAO;


public class PoliceTypeDAO extends BasePoliceTypeDAO implements cn.com.chinaebi.dz.object.dao.iface.PoliceTypeDAO {

	public PoliceTypeDAO () {}
	
	public PoliceTypeDAO (Session session) {
		super(session);
	}


}