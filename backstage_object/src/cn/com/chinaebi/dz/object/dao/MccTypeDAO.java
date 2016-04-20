package cn.com.chinaebi.dz.object.dao;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseMccTypeDAO;


public class MccTypeDAO extends BaseMccTypeDAO implements cn.com.chinaebi.dz.object.dao.iface.MccTypeDAO {

	public MccTypeDAO () {}
	
	public MccTypeDAO (Session session) {
		super(session);
	}


}