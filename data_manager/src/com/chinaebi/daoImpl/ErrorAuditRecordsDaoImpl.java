package com.chinaebi.daoImpl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.ErrorAuditRecordsDao;
import com.chinaebi.entity.ErrorAuditRecords;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "errorAuditRecordsDao")
public class ErrorAuditRecordsDaoImpl extends MyBatisDao implements ErrorAuditRecordsDao{
	
	@Override
	public Page<ErrorAuditRecords> queryPageErrorAuditRecords(
			Page<ErrorAuditRecords> page, Map<String, Object> map) {
		return selectPage(page, "ErrorAuditRecords.queryPageErrorAuditRecords", "ErrorAuditRecords.queryPageErrorAuditRecordsCount", map);
	}

	@Override
	public ErrorAuditRecords queryDetail(Map<String, Object> map) {
		return (ErrorAuditRecords) getSqlSession().selectOne("ErrorAuditRecords.queryDetail", map);
	}

	@Override
	public int addErrorAuditRecords(ErrorAuditRecords errorAuditRecords) {
		return getSqlSession().insert("ErrorAuditRecords.addErrorAuditRecords", errorAuditRecords);
	}
	
}
