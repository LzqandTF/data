package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.DuizhangDataDao;
import com.chinaebi.entity.DuizhangData;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "duizhangDataDao")
public class DuizhangDataDaoImpl extends MyBatisDao implements DuizhangDataDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<DuizhangData> queryOnlineDzFileData(Map<String, Object> map) {
		return getSqlSession().selectList("Duizhang_Data.queryOnlineDzFileData", map);
	}

	@Override
	public Page<DuizhangData> queryBankErrorData(Page<DuizhangData> page,Map<String, Object> map) {
		return selectPage(page, "Duizhang_Data.queryBankErrorData", "Duizhang_Data.queryBankErrorDataCount", map);
	}

	@Override
	public int queryBankDataCountOfAll(Map<String, Object> map) {
		Object result = getSqlSession().selectOne("Duizhang_Data.queryBankDataCountOfAll", map);
		return result==null?0:Integer.valueOf(result.toString());
	}
	
	@Override
	public int queryBankErrorDataCount(Map<String, Object> map) {
		Object result = getSqlSession().selectOne("Duizhang_Data.queryBankErrorDataCount", map);
		return result==null?0:Integer.valueOf(result.toString());
	}

	@Override
	public int queryBankDataCountOfDzSucess(Map<String, Object> map) {
		Object result = getSqlSession().selectOne("Duizhang_Data.queryBankDataCountOfDzSucess", map);
		return result==null?0:Integer.valueOf(result.toString());
	}

	@Override
	public List<DuizhangData> queryBankErrorDataList(Map<String, Object> map) {
		return getSqlSession().selectList("Duizhang_Data.queryBankErrorDataList", map);
	}
}
