package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.InstInfoDao;
import com.chinaebi.entity.InstInfo;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "instInfoDao")
public class InstInfoDaoImpl extends MyBatisDao implements InstInfoDao {

	@Override
	public int addInstInfo(InstInfo instInfo) {
		return getSqlSession().insert("InstInfo.addInstInfo",instInfo);
	}

	/**
	 * 修改机构信息配置
	 */
	@Override
	public int updateInstInfo(InstInfo instInfo) {
		return getSqlSession().update("InstInfo.updateInstInfo", instInfo);
	}
	/**
	 * 删除机构信息配置
	 */
	@Override
	public int deleteInstInfo(int instId){
		return getSqlSession().delete("InstInfo.deleteInstInfo", instId);
	}

	@Override
	public Page<InstInfo> queryPageInstInfo(Page<InstInfo> page,Map<String, Object> map) {
		return selectPage(page, "InstInfo.queryPageInstInfo", "InstInfo.queryPageInstInfoCount",map);
	}

	@Override
	public List<InstInfo> queryAll() {
		return getSqlSession().selectList("InstInfo.queryAll");
	}
	/**
	 * 根据参数的值，禁用或者激活渠道
	 * @param valueOf
	 * @return
	 */
	public int updateInstInfoStatus(Map<String,Object> map){
		return getSqlSession().update("InstInfo.updateInstInfoStatus",map);
	}
	/**
	 * 根据渠道id查询渠道个数
	 * @param instId
	 * @return
	 */
	public int queryInstInfoById(int instId){
		return (Integer) getSqlSession().selectOne("InstInfo.queryInstInfoById",instId);
	}
	/**
	 * 通过渠道ID查询渠道信息
	 * @param instId
	 * @return
	 */
	public InstInfo queryInstInfoByInstId(Map<String,Integer> map){
		return (InstInfo) getSqlSession().selectOne("InstInfo.queryInstInfoByInstId", map);
	}
	
	@Override
	public int queryInstInfoCountByBankId(String bank_id) {
		return (Integer) getSqlSession().selectOne("InstInfo.queryInstInfoCountByBankId", bank_id);
	}
	
	@Override
	public int deleteInstInfoByBankId(String bank_id) {
		return getSqlSession().delete("InstInfo.deleteInstInfoByBankId", bank_id);
	}
}
