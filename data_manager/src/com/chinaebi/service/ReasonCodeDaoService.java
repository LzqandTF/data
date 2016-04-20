package com.chinaebi.service;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.ReasonCode;
import com.chinaebi.utils.mybaits.Page;

public interface ReasonCodeDaoService {

	/**
	 * 分页查询数据
	 * @param page
	 * @param map
	 * @return
	 */
	Page<ReasonCode> queryPageReasonCode(
			Page<ReasonCode> page, Map<String, Object> map);
	
	
	boolean deleteReasonCode(String id);
	
	boolean updateReasonCode(ReasonCode reasonCode);
	
	boolean addReasonCode(ReasonCode reasonCode);
	
	List<ReasonCode> getReasonCodeLstId(ReasonCode reasonCode);
	
	List<ReasonCode> getReasonCodeLst();
}
