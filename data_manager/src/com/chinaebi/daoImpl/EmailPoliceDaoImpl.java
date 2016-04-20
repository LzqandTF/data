package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.EmailPoliceDao;
import com.chinaebi.entity.EmailPolice;
import com.chinaebi.entity.PoliceType;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "emailPoliceDao")
public class EmailPoliceDaoImpl extends MyBatisDao implements EmailPoliceDao {

	@Override
	public Page<EmailPolice> queryPageEmailPolice(Page<EmailPolice> page, Map<String, Object> map) {
		return selectPage(page, "Email_Police.queryPageEmailPolice", "Email_Police.queryCount", map);
	}

	@Override
	public int addEmailPolice(EmailPolice emailPolice) {
		return getSqlSession().insert("Email_Police.addEmailPolice", emailPolice);
	}

	@Override
	public int updateEmailPolice(EmailPolice emailPolice) {
		return getSqlSession().update("Email_Police.updateEmailPolice", emailPolice);
	}

	@Override
	public int deleteEmailPolice(int emailId) {
		return getSqlSession().delete("Email_Police.deleteEmailPolice", emailId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmailPolice> queryAllByPoliceId(int policeId) {
		return getSqlSession().selectList("Email_Police.queryAllByPoliceId", policeId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PoliceType> queryAll() {
		return getSqlSession().selectList("PoliceType.queryPoliceTypeList");
	}

	@Override
	public int queryDataByEmailOrPhone(Map<String, Object> map) {
		return (Integer) getSqlSession().selectOne("Email_Police.queryDataByEmailOrPhone", map);
	}
}
