package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.TradeAmountConfDao;
import com.chinaebi.entity.TradeAmountConf;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "tradeAmountConfDao")
public class TradeAmountConfDaoImpl extends MyBatisDao implements TradeAmountConfDao {

	/**
	 * 添加交易金额配置信息
	 */
	@Override
	public int addTradeAmountConf(TradeAmountConf tradeAmountConf) {
		return getSqlSession().insert("TradeAmountConf.addTradeAmountConf",tradeAmountConf);
	}

	/**
	 * 修改交易金额配置信息
	 */
	@Override
	public int updateTradeAmountConf(TradeAmountConf tradeAmountConf) {
		return getSqlSession().update("TradeAmountConf.updateTradeAmountConf", tradeAmountConf);
	}

	/**
	 * 分页查询交易金额配置信息
	 */
	@Override
	public Page<TradeAmountConf> queryPageTradeAmountConf(
			Map<String, Object> map, Page<TradeAmountConf> page) {
		return selectPage(page, "TradeAmountConf.queryPageTradeAmountConf","TradeAmountConf.queryPageTradeAmountConfCount",map);
	}
	/**
	 * 删除交易金额配置
	 * @param instInfo
	 * @return
	 */
	@Override
	public int delTradeAmountConfById(int id) {
		return getSqlSession().delete("TradeAmountConf.deleteTradeAmountConf", id);
	}

	/**
	 * 查询交易金额配置list
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<TradeAmountConf> queryTradeAmountConf(){
		return getSqlSession().selectList("TradeAmountConf.queryTradeAmountConf");
	}

	@Override
	public String queryTradeName(Map<String, Object> map) {
		return (String) getSqlSession().selectOne("TradeAmountConf.queryTradeName", map);
	}

}
