package cn.com.chinaebi.dz.object.dao;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseRuleHandlerDAO;


public class RuleHandlerDAO extends BaseRuleHandlerDAO implements cn.com.chinaebi.dz.object.dao.iface.RuleHandlerDAO {

	public RuleHandlerDAO () {}
	
	public RuleHandlerDAO (Session session) {
		super(session);
	}


}