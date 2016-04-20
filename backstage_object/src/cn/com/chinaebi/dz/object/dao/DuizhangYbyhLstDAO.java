package cn.com.chinaebi.dz.object.dao;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseDuizhangYbyhLstDAO;


public class DuizhangYbyhLstDAO extends BaseDuizhangYbyhLstDAO implements cn.com.chinaebi.dz.object.dao.iface.DuizhangYbyhLstDAO {

	public DuizhangYbyhLstDAO () {}
	
	public DuizhangYbyhLstDAO (Session session) {
		super(session);
	}


}