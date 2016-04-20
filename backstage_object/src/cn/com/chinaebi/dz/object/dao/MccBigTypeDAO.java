package cn.com.chinaebi.dz.object.dao;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseMccBigTypeDAO;


public class MccBigTypeDAO extends BaseMccBigTypeDAO implements cn.com.chinaebi.dz.object.dao.iface.MccBigTypeDAO {

	public MccBigTypeDAO () {}
	
	public MccBigTypeDAO (Session session) {
		super(session);
	}


}