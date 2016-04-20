package com.chinaebi.service;

import java.util.Map;

import com.chinaebi.entity.ErrorAuditRecords;
import com.chinaebi.utils.mybaits.Page;

/**
 * 差错审计接口
 * @author wufei
 *
 */
public interface ErrorAuditRecordsService {
	/**
	 * 分页查询差错审计数据
	 * @param page 分页参数
	 * @param map 查询参数
	 * @return
	 */
	public Page<ErrorAuditRecords> queryPageErrorAuditRecords(Page<ErrorAuditRecords> page, Map<String, Object> map);
	
	/**
	 * 查询详细信息
	 * @param map 联合主键作为参数
	 * @return
	 */
	public ErrorAuditRecords queryDetail(Map<String, Object> map);
	
	/**
	 * 添加操作后的差错数据
	 * @param errorAuditRecords
	 * @return
	 */
	int addErrorAuditRecords(ErrorAuditRecords errorAuditRecords);
}
