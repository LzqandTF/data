package cn.com.chinaebi.dz.object.dao;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseTmpSplitTabDAO;


public class TmpSplitTabDAO extends BaseTmpSplitTabDAO implements cn.com.chinaebi.dz.object.dao.iface.TmpSplitTabDAO {

	public TmpSplitTabDAO () {}
	
	public TmpSplitTabDAO (Session session) {
		super(session);
	}


}