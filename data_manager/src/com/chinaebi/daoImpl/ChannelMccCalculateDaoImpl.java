package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.ChannelMccCalculateDao;
import com.chinaebi.entity.ChannelMccCalculate;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "channelMccCalculateDao")
public class ChannelMccCalculateDaoImpl extends MyBatisDao implements ChannelMccCalculateDao{

	@Override
	public Page<ChannelMccCalculate> queryPageChannelMccCalculate(Page<ChannelMccCalculate> page,
			Map<String, Object> map) {
		return selectPage(page, "ChannelMccCalculate.queryPageChannelMccCalculate",
				"ChannelMccCalculate.queryPageCount", map);
	}

	@Override
	public ChannelMccCalculate queryChannelMccCalculateById(int id) {
		return (ChannelMccCalculate)getSqlSession().selectOne("ChannelMccCalculate.queryChannelMccCalculateById", id);
	}

	@Override
	public List<ChannelMccCalculate> queryAllChannelMccCalculate() {
		return getSqlSession().selectList("ChannelMccCalculate.queryAllChannelMccCalculate");
	}

	@Override
	public int deleteChannelMccCalculate(int id) {
		return getSqlSession().delete("ChannelMccCalculate.deleteChannelMccCalculate", id);
	}

	@Override
	public int updateChannelMccCalculate(ChannelMccCalculate ChannelMccCalculate) {
		return getSqlSession().update("ChannelMccCalculate.updateChannelMccCalculate", ChannelMccCalculate);
	}

	@Override
	public int addChannelMccCalculate(ChannelMccCalculate ChannelMccCalculate) {
		return getSqlSession().insert("ChannelMccCalculate.addChannelMccCalculate", ChannelMccCalculate);
	}

}
