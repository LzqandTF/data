package com.chinaebi.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.EmailPoliceDao;
import com.chinaebi.entity.EmailPolice;
import com.chinaebi.entity.PoliceType;
import com.chinaebi.exception.DeleteException;
import com.chinaebi.exception.InsertException;
import com.chinaebi.exception.PageException;
import com.chinaebi.exception.UpdateException;
import com.chinaebi.service.EmailPoliceService;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "emailPoliceService")
public class EmailPoliceServiceImpl implements EmailPoliceService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailPoliceServiceImpl.class);
	
	@Autowired
	@Qualifier(value = "emailPoliceDao")
	private EmailPoliceDao emailPoliceDao;

	@Override
	public Page<EmailPolice> queryPageEmailPolice(Page<EmailPolice> page, Map<String, Object> map) {
		Page<EmailPolice> pageList = null;
		try {
			pageList = emailPoliceDao.queryPageEmailPolice(page, map);
			if (pageList.getResult() == null) {
				throw new PageException("emailPoliceDao.queryPageEmailPolice(page, map)  查询结果为NULL");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return pageList;
	}

	@Override
	public int addEmailPolice(EmailPolice emailPolice) {
		int effectNum = 0;
		try {
			effectNum = emailPoliceDao.addEmailPolice(emailPolice);
			if ( !(effectNum > 0 ) ) {
				throw new InsertException("emailPoliceDao.addEmailPolice(emailPolice)  添加报警信息失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public int updateEmailPolice(EmailPolice emailPolice) {
		int effectNum = 0;
		try {
			effectNum = emailPoliceDao.updateEmailPolice(emailPolice);
			if ( !( effectNum > 0 ) ) {
				throw new UpdateException("emailPoliceDao.updateEmailPolice(emailPolice)  报警信息修改失败");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return effectNum;
	}

	@Override
	public int deleteEmailPolice(int emailId) {
		int effectNum = 0;
		try {
			effectNum = emailPoliceDao.deleteEmailPolice(emailId);
			if ( !( effectNum > 0 ) ) {
				throw new DeleteException("emailPoliceDao.deleteEmailPolice(emailId)  删除报警信息失败");
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return effectNum;
	}
	
	@Override
	public List<PoliceType> queryAll() {
		return emailPoliceDao.queryAll();
	}

	@Override
	public int queryEmailOrPhone(Map<String, Object> map) {
		int effectNum = 0;
		try {
			effectNum = emailPoliceDao.queryDataByEmailOrPhone(map);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public List<EmailPolice> queryAllByPoliceId(int policeId) {
		List<EmailPolice> list = null;
		try{
			list = emailPoliceDao.queryAllByPoliceId(policeId);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}
	/**
	 * 通过事件ID查询对应事件的邮件内容以及收件人信息
	 * @param eventId
	 * @return
	 */
	public Map<String, Object> queryEmailPoliceByEventId(int eventId){
		List<EmailPolice> list = null;
		Map<String,Object> map = null;
		StringBuffer email = new StringBuffer("");
		String content = "";
		String event = "";
		try{
			list = emailPoliceDao.queryAllByPoliceId(eventId);
			if(list != null && list.size() > 0){
				map = new HashMap<String,Object>();
				for (EmailPolice emailPolice : list) {
					email.append(emailPolice.getEmail() + ",");
					content = emailPolice.getEmail_content();
					event = emailPolice.getEmail_theme();
				}
				map.put("emailTheme", event);
				map.put("emailContent", content);
				map.put("email", email == null?"":email.toString().substring(0, email.toString().length()-1));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return map;
	}
}
