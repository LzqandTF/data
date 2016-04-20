package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.ChannelDzCollectDao;
import com.chinaebi.entity.ChannelDzCollect;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

/**
 * 对账总表操作Dao接口实现类
 * @author wufei
 *
 */

@Component(value = "channelDzCollectDao")
public class ChannelDzCollectDaoImpl extends MyBatisDao implements ChannelDzCollectDao {
	@Override
	public Page<ChannelDzCollect> queryPageChannelDzCollect(Page<ChannelDzCollect> page, Map<String, Object> map) {
		return selectPage(page, "ChannelDzCollect.queryPageChannelDzCollect", "ChannelDzCollect.queryCount", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChannelDzCollect> queryChannelDzCollectDataLst(Map<String, Object> map) {
		return getSqlSession().selectList("ChannelDzCollect.queryChannelDzCollectDataLst", map);
	}

	@Override
	public int updateChannelDzCollectDataQsStatus(Map<String,Object> map) {
		return getSqlSession().update("ChannelDzCollect.updateChannelDzCollectDataQsStatus", map);
	}

	@Override
	public Page<ChannelDzCollect> queryPageChannelDzData(Page<ChannelDzCollect> page, Map<String, Object> map) {
		return selectPage(page, "ChannelDzCollect.queryPageChannelDzData", "ChannelDzCollect.queryPageCountChannelDzData", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChannelDzCollect> queryChannelDzDataList(Map<String, Object> map) {
		return getSqlSession().selectList("ChannelDzCollect.queryChannelDzDataList", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChannelDzCollect> queryChannelDzDataTradeAmount(Map<String, Object> map) {
		return getSqlSession().selectList("ChannelDzCollect.queryChannelDzDataTradeAmount", map);
	}

	@Override
	public int addNoDzDataToChannelDzCollect(ChannelDzCollect channelDzCollect) {
		return getSqlSession().insert("ChannelDzCollect.addNoDzDataToChannelDzCollect", channelDzCollect);
	}
}
