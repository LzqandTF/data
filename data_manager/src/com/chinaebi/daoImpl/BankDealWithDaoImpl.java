package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.BankDealWithDao;
import com.chinaebi.entity.BankDealWith;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "bankDealWithDao")
public class BankDealWithDaoImpl extends MyBatisDao implements BankDealWithDao{
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BankDealWith> queryInstInfo()throws Exception {
		return getSqlSession().selectList("BankDealWith.queryInstInfo");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankDealWith> queryListCountInfo(Map<String, Object> map)throws Exception {
		return getSqlSession().selectList("BankDealWith.queryListCountByAll", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankDealWith> queryListCountInfoExcel(Map<String, Object> map)throws Exception {
		return getSqlSession().selectList("BankDealWith.queryListCountInfoExcel",map);
	}

	@Override
	public Page<BankDealWith> queryTradingList(Page<BankDealWith> page,Map<String, Object> map) throws Exception {
		return  selectPage(page, "BankDealWith.queryTradingList", "BankDealWith.queryTradingListCount", map);
	}

	@Override
	public String queryMerName(String mercode) throws Exception {
		return (String) getSqlSession().selectOne("BankDealWith.queryMerName", mercode);
	}

	@Override
	public String queryMernameOne(String mercode) throws Exception {
		return (String) getSqlSession().selectOne("BankDealWith.queryMernameOne", mercode);
	}

	@Override
	public BankDealWith querySumTradingList(Map<String, Object> map)throws Exception {
		return (BankDealWith) getSqlSession().selectOne("BankDealWith.querySumTradingList", map);
	}

}
