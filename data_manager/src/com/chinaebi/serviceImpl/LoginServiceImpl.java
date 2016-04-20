package com.chinaebi.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.LoginDao;
import com.chinaebi.entity.Login;
import com.chinaebi.service.LoginService;
import com.chinaebi.utils.FlightUtil;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Service(value ="loginService")
public class LoginServiceImpl implements LoginService {
	
	private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
	
	@Autowired
	@Qualifier(value = "loginDao")
	private LoginDao loginDao;

	public Login checkLogin(String userName, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("password", FlightUtil.makeMD5(password));
		
		return loginDao.checkLogin(map);
	}

	public List<Login> queryAllLogins() {
		return loginDao.queryAllLogins();
	}

	public int addLogin(String loginName, String password,String chineseName) {
		Login login = new Login();
		login.setLoginName(loginName);
		login.setPassword(FlightUtil.makeMD5(password));
		login.setChineseName(chineseName);
		return loginDao.addLogin(login);
	}

	public int updateLogin(String id,String loginName, String password, String status,Date loginDate,String chineseName) {
		Login login = new Login();
		if(StringUtils.isNotBlank(id)){
			login.setId(Integer.parseInt(id.trim()));
			if(StringUtils.isNotBlank(loginName))
				login.setLoginName(loginName);
			if(StringUtils.isNotBlank(password))
				login.setPassword(FlightUtil.makeMD5(password));
			if(StringUtils.isNotBlank(status))
				login.setStatus(Integer.parseInt(status.trim()));
			if(StringUtils.isNotBlank(chineseName))
				login.setChineseName(chineseName);
			login.setLoginDate(loginDate);
		}
		return loginDao.updateLogin(login);
	}

	public Page<Login> queryPageLogin(String chineseName, String loginName,
			Page<Login> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(chineseName))
			map.put("chineseName", chineseName);
		map.put("userName", loginName);
		return loginDao.queryPageLogin(map, page);
	}

	public int checkAddLogin(String loginName) {
		return loginDao.checkAddLogin(loginName);
	}

	@Override
	public int updateChineseName(Map<String, Object> map) {
		int result = 0;
		try{
			result = loginDao.updateChineseName(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

}
