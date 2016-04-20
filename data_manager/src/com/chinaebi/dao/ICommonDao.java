package com.chinaebi.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.chinaebi.utils.mybaits.Page;

/**
 * 通用Dao接口
 * @author wufei
 *
 * @param <T>
 */
public interface ICommonDao<T> {
	public abstract Page<T> queryForPage(Page<T> page, String querySQL, String queryCount, T obj) throws SQLException;
	
	public abstract Page<T> queryForPage(Page<T> page, String querySQL, String queryCount, Map<String, Object> map) throws SQLException;

	public abstract List<T> queryForList(String querySQL, T obj) throws SQLException;
	
	public abstract List<T> queryForList(String querySQL, Map<String, Object> map) throws SQLException;

	public abstract List<T> queryForList(String querySQL, String param) throws SQLException;

//	public abstract T queryForObject(String querySQL, T obj) throws SQLException;
	
	public abstract T queryForObject(String querySQL, Map<String, Object> map) throws SQLException;
	
//	public abstract T queryForObject(String querySQL, String param) throws SQLException;

	public abstract String insert(String sql, T obj) throws SQLException;

	public abstract String update(String sql, T obj) throws SQLException;

	public abstract int delete(String sql, T obj) throws SQLException;
	
	public abstract String queryMoney(String sql, Map<String, Object> map);
	
	/**
	 * 统计商户未对账数据条数
	 * @param map 查询参数
	 * @return
	 */
	public abstract int queryTradeNoDzHandlerCount(String sql,Map<String,Object> map);
	
	/**
	 * 修改原始交易数据的结算状态
	 * @param map
	 * @return
	 */
	public abstract int updateOriginalDataJsStatus(String sql,Map<String,Object> map);
	
	/**
	 * 修改原始交易数据的清算状态
	 * @param sql
	 * @param map
	 * @return
	 */
	public abstract int updateOriginalDataQsStatus(String sql,Map<String,Object> map); 
	
	/**
	 * 统计原交易对账成功数据总数
	 * @param sql
	 * @param map
	 * @return
	 */
	public abstract int queryDzSucessDataTotalCount(String sql,Map<String,Object> map);
}
