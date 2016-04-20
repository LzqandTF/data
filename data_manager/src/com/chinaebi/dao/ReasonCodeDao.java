package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.ReasonCode;
import com.chinaebi.utils.mybaits.Page;

public interface ReasonCodeDao {

	Page<ReasonCode> queryPageReasonCode(Page<ReasonCode> page, Map<String, Object> map);
	
	int deleteReasonCode(String id);
	
	int updateReasonCode(ReasonCode reasonCode);
	
	int addReasonCode(ReasonCode reasonCode);
	
	List<ReasonCode> getReasonCodeLstId(ReasonCode reasonCode);
	
	List<ReasonCode> getReasonCodeLst();
}
