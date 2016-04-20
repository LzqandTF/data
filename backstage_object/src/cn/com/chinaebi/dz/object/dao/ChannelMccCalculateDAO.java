package cn.com.chinaebi.dz.object.dao;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseChannelMccCalculateDAO;


public class ChannelMccCalculateDAO extends BaseChannelMccCalculateDAO implements cn.com.chinaebi.dz.object.dao.iface.ChannelMccCalculateDAO {

	public ChannelMccCalculateDAO () {}
	
	public ChannelMccCalculateDAO (Session session) {
		super(session);
	}


}