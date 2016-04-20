package com.chinaebi.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.ChannelDzCollectDao;
import com.chinaebi.entity.ChannelDzCollect;
import com.chinaebi.exception.PageException;
import com.chinaebi.exception.SelectException;
import com.chinaebi.service.ChannelDzCollectService;
import com.chinaebi.utils.Ryt_trade_type;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "channelDzCollectService")
public class ChannelDzCollectServiceImpl implements ChannelDzCollectService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Qualifier(value = "channelDzCollectDao")
	private ChannelDzCollectDao channelDzCollectDao;
	
	@Override
	public Page<ChannelDzCollect> queryPageChannelDzCollect(Page<ChannelDzCollect> page, Map<String, Object> map) {
		Page<ChannelDzCollect> pageList = null;
		try {
			pageList = channelDzCollectDao.queryPageChannelDzCollect(page, map);
			if(pageList != null && pageList.getResult() != null && pageList.getResult().size() > 0){
				for (ChannelDzCollect channelDzCollect : pageList.getResult()) {
					if(StringUtils.isNotBlank(channelDzCollect.getTrade_type())){
						channelDzCollect.setTrade_type(Ryt_trade_type.getRytTradeName(Integer.valueOf(channelDzCollect.getTrade_type())));
					}
				}
			}
			if (pageList.getResult() == null) {
				throw new PageException("channelDzCollectDao.queryPageChannelDzCollect(pageList, map) 查询数据为NULL");
			}
		} catch (Exception e) {
			logger.error("交易明细数据查询出错：" + e.getMessage());
		}
		return pageList;
	}

	@Override
	public List<ChannelDzCollect> queryChannelDzCollectDataLst(Map<String, Object> map) {
		List<ChannelDzCollect> list = null;
		try {
			list = channelDzCollectDao.queryChannelDzCollectDataLst(map);
			if (list == null) {
				throw new SelectException("channelDzCollectDao.queryChannelDzCollectDataLst(map)  查询结果为NULL");
			}
		} catch (Exception e) {
			logger.error("商户结算单明细下载出现异常：" + e.getMessage());
		}
		return list;
	}

	@Override
	public int updateChannelDzCollectDataQsStatus(Map<String,Object> map) {
		int result = 0;
		try{
			result = channelDzCollectDao.updateChannelDzCollectDataQsStatus(map);
		}catch(Exception e){
			logger.error("修改对账总表数据的清算状态抛出异常"+e.getMessage());
		}
		return result;
	}

	@Override
	public Page<ChannelDzCollect> queryPageChannelDzData(Page<ChannelDzCollect> page, Map<String, Object> map) {
		Page<ChannelDzCollect> pageList = null;
		try {
			pageList = channelDzCollectDao.queryPageChannelDzData(page, map);
			
			if (pageList.getResult() == null) {
				throw new PageException("channelDzCollectDao.queryPageChannelDzData(pageList, map) 查询数据为NULL");
			}
		} catch (Exception e) {
			logger.error("交易明细数据查询出错：" + e.getMessage());
		}
		return pageList;
	}

	@Override
	public List<ChannelDzCollect> queryChannelDzDataList(Map<String, Object> map) {
		List<ChannelDzCollect> list = null;
		try {
			list = channelDzCollectDao.queryChannelDzDataList(map);
			if (list == null) {
				throw new SelectException("channelDzCollectDao.queryChannelDzDataList(map)  查询结果为NULL");
			}
		} catch (Exception e) {
			logger.error("商户结算单明细下载出现异常：" + e.getMessage());
		}
		return list;
	}

	@Override
	public Map<Integer,String> queryChannelDzDataTradeAmount(Map<String, Object> map) {
		List<ChannelDzCollect> list = null;
		Map<Integer,String> map_result = null;
		try {
			list = channelDzCollectDao.queryChannelDzDataTradeAmount(map);
			if (list == null) {
				throw new SelectException("channelDzCollectDao.queryChannelDzDataTradeAmount(map)  查询结果为NULL");
			}else{
				map_result = new HashMap<Integer,String>();
				for (ChannelDzCollect channelDzCollect : list) {
					if(channelDzCollect.getBk_chk() == 1){
						map_result.put(channelDzCollect.getBk_chk(), channelDzCollect.getTotalAmount()+","+channelDzCollect.getMer_fee());
					}else{
						map_result.put(channelDzCollect.getBk_chk(), channelDzCollect.getTotalAmount());
					}
				}
			}
		} catch (Exception e) {
			logger.error("商户结算单明细下载出现异常：" + e.getMessage());
		}
		return map_result;
	}

	@Override
	public int addNoDzDataToChannelDzCollect(ChannelDzCollect channelDzCollect) {
		int effectNum = 0;
		try {
			effectNum = channelDzCollectDao.addNoDzDataToChannelDzCollect(channelDzCollect);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return effectNum;
	}
}
