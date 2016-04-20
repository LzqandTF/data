package cn.com.chinaebi.dz.object.dao;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseRuleTemplateDAO;


public class RuleTemplateDAO extends BaseRuleTemplateDAO implements cn.com.chinaebi.dz.object.dao.iface.RuleTemplateDAO {

	public RuleTemplateDAO () {}
	
	public RuleTemplateDAO (Session session) {
		super(session);
	}


}