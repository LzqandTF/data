package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.SettleFailReasonDao;
import com.chinaebi.entity.SettleFailReason;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "settleFailReasonDao")
public class SettleFailReasonDaoImpl extends MyBatisDao implements SettleFailReasonDao{

	@Override
	public int addSettleFailReason(SettleFailReason settleFailReason) {
		return getSqlSession().insert("SettleFailReason.addSettleFailReason",settleFailReason);
	}

	@Override
	public int updateSettleFailReason(SettleFailReason settleFailReason) {
		return getSqlSession().update("SettleFailReason.updateSettleFailReason", settleFailReason);
	}

	@Override
	public int deleteSettleFailReason(int reason_id) {
		return getSqlSession().delete("SettleFailReason.deleteSettleFailReason", reason_id);
	}

	@Override
	public Page<SettleFailReason> queryPageSettleFailReason(
			Page<SettleFailReason> page, Map<String, Object> map) {
		return selectPage(page, "SettleFailReason.queryPageSettleFailReason", "SettleFailReason.queryPageSettleFailReasonCount",map);
	}

	@Override
	public SettleFailReason querySettleFailReasonById(int reason_id) {
		return (SettleFailReason)getSqlSession().selectOne("SettleFailReason.querySettleFailReasonById", reason_id);
	}
	
	/**
	 * 获取结算发起失败原因集合
	 * @return
	 */
	public List<SettleFailReason> getSettleFailReasonList(){
		return getSqlSession().selectList("SettleFailReason.getSettleFailReasonList");
	}

}
