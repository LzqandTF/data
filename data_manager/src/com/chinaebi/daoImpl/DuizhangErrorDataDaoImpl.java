package com.chinaebi.daoImpl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.DuizhangErrorDataDao;
import com.chinaebi.entity.DuizhangErrorData;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "duizhangErrorDataDao")
public class DuizhangErrorDataDaoImpl extends MyBatisDao implements DuizhangErrorDataDao {
	@Override
	public DuizhangErrorData queryDetailById(Map<String, Object> map) {
		return (DuizhangErrorData) getSqlSession().selectOne("Duizhang_ErrorData.queryDetailById", map);
	}
	
	@Override
	public Page<DuizhangErrorData> queryPageDzErrorCupsLst(Page<DuizhangErrorData> page,Map<String,Object> map){
		return selectPage(page, "Duizhang_ErrorData.queryPageDzErrorCupsLst","Duizhang_ErrorData.count", map);
	}
	
}

