/**
 * 交易金额配置类
 * @author Li.Jikui
 * @date 2014-2-25
 */
package com.chinaebi.dao;
import java.util.List;
import java.util.Map;

import com.chinaebi.entity.TradeAmountConf;
import com.chinaebi.utils.mybaits.Page;

public interface TradeAmountConfDao {
	
	
	
	/**
	 * 添加交易金额配置
	 * @param instInfo
	 * @return
	 */
	public int addTradeAmountConf(TradeAmountConf tradeAmountConf);
	
	/**
	 * 修改交易金额配置
	 * @param instInfo
	 * @return
	 */
	public int updateTradeAmountConf(TradeAmountConf tradeAmountConf);
	
	/**
	 * 分页查询
	 * @param map
	 * @param page
	 * @return
	 */
	public Page<TradeAmountConf> queryPageTradeAmountConf(Map<String, Object> map,Page<TradeAmountConf> page);
		
	
	/**
	 * 删除交易金额配置
	 * @param instInfo
	 * @return
	 */
	public int delTradeAmountConfById(int id);
	
	/**
	 * 查询交易金额配置list
	 * @return
	 */
	public List<TradeAmountConf> queryTradeAmountConf();
	
	public String queryTradeName(Map<String, Object> map);
}
