package cn.com.chinaebi.dz.object.dao;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseRytJiangsuYhDAO;


public class RytJiangsuYhDAO extends BaseRytJiangsuYhDAO implements cn.com.chinaebi.dz.object.dao.iface.RytJiangsuYhDAO {

	public RytJiangsuYhDAO () {}
	
	public RytJiangsuYhDAO (Session session) {
		super(session);
	}


}