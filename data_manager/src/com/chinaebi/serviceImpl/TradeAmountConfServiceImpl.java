package com.chinaebi.serviceImpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.chinaebi.exception.DeleteException;
import com.chinaebi.exception.InsertException;
import com.chinaebi.exception.PageException;
import com.chinaebi.exception.SelectException;
import com.chinaebi.exception.UpdateException;

import com.chinaebi.dao.TradeAmountConfDao;
import com.chinaebi.entity.TradeAmountConf;
import com.chinaebi.service.TradeAmountConfService;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "tradeAmountConfService")
public class TradeAmountConfServiceImpl implements TradeAmountConfService{

	private static final Logger log = LoggerFactory.getLogger(TradeAmountConfServiceImpl.class);
	
	@Autowired
	@Qualifier(value="tradeAmountConfDao")
	private TradeAmountConfDao tradeAmountConfDao;
	
	/**
	 * 分页查询交易金额配置信息
	 * @param page
	 * @param map
	 * @return
	 */
	@Override
	public Page<TradeAmountConf> queryPageTradeAmountConf(
			Page<TradeAmountConf> page, Map<String, Object> map) {
		Page<TradeAmountConf> pageList = null;
		try{
			pageList = tradeAmountConfDao.queryPageTradeAmountConf(map, page);
			if(pageList.getResult() == null){
				throw new PageException("tradeAmountConfDao.queryPageTradeAmountConf(map, page)  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return pageList;
	}
	/**
	 * 
	 * 修改交易金额配置信息
	 * @param tradeAmountConf
	 * @return
	 */
	public int updateTradeAmountConf(TradeAmountConf tradeAmountConf){
		int effectNum = 0;
		try{
			effectNum = tradeAmountConfDao.updateTradeAmountConf(tradeAmountConf);
			if(!(effectNum > 0)){
				throw new UpdateException("tradeAmountConfDao.updateTradeAmountConf(tradeAmountConf)  交易金额配置信息修改失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}
	/**
	 * 删除交易金额配置信息
	 * @param valueOf
	 * @return
	 */
	public boolean deleteInstInfo(int id){
		try{
			int effectNum =  tradeAmountConfDao.delTradeAmountConfById(id);
			if(effectNum > 0){
				return true;
			}else{
				throw new DeleteException("tradeAmountConfDao.delTradeAmountConfById(id)  删除交易金额配置信息失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return false;
	}
	/**
	 * 添加交易金额配置信息
	 * @param tradeAmountConf
	 * @return
	 */
	public int addTradeAmountConf(TradeAmountConf tradeAmountConf){
		int effectNum = 0;
		try{
			effectNum = tradeAmountConfDao.addTradeAmountConf(tradeAmountConf);
			if(!(effectNum > 0)){
				throw new InsertException("tradeAmountConfDao.addTradeAmountConf(tradeAmountConf)  添加交易金额配置信息失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}
	/**
	 * 查询交易金额配置list
	 * @return
	 */
	public List<TradeAmountConf> queryTradeAmountConf(){
		List<TradeAmountConf> list = null;
		try{
			list = tradeAmountConfDao.queryTradeAmountConf();
			if(list==null || list.size()==0){
				throw new SelectException("tradeAmountConfDao.queryTradeAmountConf()   查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}
	public String queryTradeName(Map<String, Object> map){
		return tradeAmountConfDao.queryTradeName(map);
	}
}
