package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.LoginDao;
import com.chinaebi.entity.Login;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "loginDao")
public class LoginDaoImpl extends MyBatisDao implements LoginDao {

	public Login checkLogin(Map<String, Object> map) {
		return (Login)getSqlSession().selectOne("Login.queryLogin",map);
	}

	@SuppressWarnings("unchecked")
	public List<Login> queryAllLogins() {
		return getSqlSession().selectList("Login.queryAvailableUser");
	}

	public int addLogin(Login login) {
		return getSqlSession().insert("Login.addLogin",login);
	}

	public int updateLogin(Login login) {
		return getSqlSession().insert("Login.updateLogin",login);
	}

	public Page<Login> queryPageLogin(Map<String, Object> map, Page<Login> page) {
		return selectPage(page, "Login.queryPageLogin", "Login.queryPageLoginCount",map);
	}

	public int checkAddLogin(String loginName) {
		return (Integer)getSqlSession().selectOne("Login.checkAddLogin",loginName);
	}

	@Override
	public int updateChineseName(Map<String, Object> map) {
		return getSqlSession().update("Login.updateChineseName",map);
	}

}
