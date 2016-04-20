package com.chinaebi.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.ChannelMccCalculateDao;
import com.chinaebi.entity.ChannelMccCalculate;
import com.chinaebi.service.ChannelMccCalculateService;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "channelMccCalculateService")
public class ChannelMccCalculateServiceImpl implements ChannelMccCalculateService{

	@Autowired
	@Qualifier(value = "channelMccCalculateDao")
	private ChannelMccCalculateDao channelMccCalculateDao ;
	
	@Override
	public Page<ChannelMccCalculate> queryPageChannelMccCalculate(Page<ChannelMccCalculate> page,
			Map<String, Object> map) {
		return channelMccCalculateDao.queryPageChannelMccCalculate(page, map);
	}

	@Override
	public ChannelMccCalculate queryChannelMccCalculateById(int id) {
		return channelMccCalculateDao.queryChannelMccCalculateById(id);
	}

	@Override
	public List<ChannelMccCalculate> queryAllChannelMccCalculate() {
		return channelMccCalculateDao.queryAllChannelMccCalculate();
	}

	@Override
	public int deleteChannelMccCalculate(int id) {
		return channelMccCalculateDao.deleteChannelMccCalculate(id);
	}

	@Override
	public int updateChannelMccCalculate(ChannelMccCalculate channelMccCalculate) {
		return channelMccCalculateDao.updateChannelMccCalculate(channelMccCalculate);
	}

	@Override
	public int addChannelMccCalculate(ChannelMccCalculate channelMccCalculate) {
		return channelMccCalculateDao.addChannelMccCalculate(channelMccCalculate);
	}
	
}
