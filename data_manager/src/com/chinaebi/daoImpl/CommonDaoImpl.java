package com.chinaebi.daoImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.ICommonDao;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

/**
 * 通用Dao实现类
 * @author wufei
 *
 * @param <T>
 */
@Component(value = "commonDao")
public class CommonDaoImpl<T> extends MyBatisDao implements ICommonDao<T> {
	@Override
	public Page<T> queryForPage(Page<T> page, String querySQL, String queryCount, T obj) throws SQLException {
		return selectPage(page, querySQL, queryCount, obj);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> queryForList(String querySQL, T obj) throws SQLException {
		return getSqlSession().selectList(querySQL, obj);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> queryForList(String querySQL, String param) throws SQLException {
		return getSqlSession().selectList(querySQL, param);
	}
	
//	@Override
//	@SuppressWarnings("unchecked")
//	public T queryForObject(String querySQL, T obj) throws SQLException {
//		return (T) getSqlSession().selectOne(querySQL, obj);
//	}
	
//	@Override
//	@SuppressWarnings("unchecked")
//	public T queryForObject(String querySQL, String param) throws SQLException {
//		return (T) getSqlSession().selectOne(querySQL, param);
//	}
	
	@Override
	public String insert(String sql, T obj) throws SQLException {
		String respCode = "E00";
		int result = getSqlSession().insert(sql, obj);
		if (result > 0) {
			respCode = "000";
		}
		return respCode;
	}
	
	@Override
	public String update(String sql, T obj) throws SQLException {
		String respCode = "E00";
		int result = getSqlSession().insert(sql, obj);
		if (result > 0) {
			respCode = "000";
		}
		return respCode;
	}
	
	@Override
	@SuppressWarnings("unused")
	public int delete(String sql, T obj) throws SQLException {
		String respCode = "E00";
		int result = getSqlSession().delete(sql, obj);
		if (result > 0) {
			respCode = "000";
		}
		return result;
	}

	@Override
	public Page<T> queryForPage(Page<T> page, String querySQL, String queryCount, Map<String, Object> map) throws SQLException {
		return selectPage(page, querySQL, queryCount, map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T queryForObject(String querySQL, Map<String, Object> map) throws SQLException {
		return (T) getSqlSession().selectOne(querySQL, map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryForList(String querySQL, Map<String, Object> map)
			throws SQLException {
		return getSqlSession().selectList(querySQL, map);
	}

	@Override
	public String queryMoney(String sql, Map<String, Object> map) {
		return String.valueOf(getSqlSession().selectOne(sql, map));
	}

	@Override
	public int queryTradeNoDzHandlerCount(String sql, Map<String, Object> map) {
		return Integer.valueOf(getSqlSession().selectOne(sql, map).toString());
	}

	@Override
	public int updateOriginalDataJsStatus(String sql,Map<String, Object> map) {
		return getSqlSession().update(sql, map);
	}
	@Override
	public int updateOriginalDataQsStatus(String sql,Map<String, Object> map) {
		return getSqlSession().update(sql, map);
	}

	@Override
	public int queryDzSucessDataTotalCount(String sql, Map<String, Object> map) {
		return Integer.valueOf(getSqlSession().selectOne(sql, map).toString());
	}
}

