package cn.com.chinaebi.dz.object.dao;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseRytMsyhDAO;


public class RytMsyhDAO extends BaseRytMsyhDAO implements cn.com.chinaebi.dz.object.dao.iface.RytMsyhDAO {

	public RytMsyhDAO () {}
	
	public RytMsyhDAO (Session session) {
		super(session);
	}


}