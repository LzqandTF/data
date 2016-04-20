package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.ReasonCodeDao;
import com.chinaebi.entity.ReasonCode;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "reasonCodeDao")
public class ReasonCodeDaoImpl extends MyBatisDao implements ReasonCodeDao {

	@Override
	public Page<ReasonCode> queryPageReasonCode(Page<ReasonCode> page,
			Map<String, Object> map) {
		return selectPage(page, "ReasonCode.queryPageReasonCode",
				"ReasonCode.queryPageCount", map);
	}

	@Override
	public int deleteReasonCode(String id) {
		return getSqlSession().delete("ReasonCode.deleteReasonCode", id);
	}

	@Override
	public int updateReasonCode(ReasonCode reasonCode) {
		return getSqlSession().update("ReasonCode.updateReasonCode", reasonCode);
	}

	@Override
	public int addReasonCode(ReasonCode reasonCode) {
		return getSqlSession().insert("ReasonCode.addReasonCode", reasonCode);
	}

	@Override
	public List<ReasonCode> getReasonCodeLstId(ReasonCode reasonCode) {
		return getSqlSession().selectList("ReasonCode.queryReasonCodeList", reasonCode);
	}

	@Override
	public List<ReasonCode> getReasonCodeLst() {
		return getSqlSession().selectList("ReasonCode.queryAll");
	}

}
