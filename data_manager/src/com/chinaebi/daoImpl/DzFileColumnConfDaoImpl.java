package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.DzFileColumnConfDao;
import com.chinaebi.entity.DzFileColumnConf;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "dzFileColumnConfDao")
public class DzFileColumnConfDaoImpl extends MyBatisDao implements DzFileColumnConfDao{

	@Override
	public Page<DzFileColumnConf> queryPageDzFileColumnConf(Page<DzFileColumnConf> page,Map<String, Object> map) {
		return selectPage(page,"DzFileColumnConf.queryPageDzFileColumnConf", "DzFileColumnConf.queryPageCount",map);
	}

	@Override
	public int insertDzFileColumnConf(DzFileColumnConf dzFileColumnConf) {
		return  getSqlSession().insert("DzFileColumnConf.insertDzFileColumnConf",dzFileColumnConf);
	}

	@Override
	public int updateDzFileColumnConf(DzFileColumnConf dzFileColumnConf) {
		return getSqlSession().update("DzFileColumnConf.updateDzFileColumnConf", dzFileColumnConf);
	}

	@Override
	public int deleteDzFileColumnConf(int object_id) {
		return getSqlSession().delete("DzFileColumnConf.deleteDzFileColumnConf", object_id);
	}

	@Override
	public DzFileColumnConf queryDzFileColumnConfById(int object_id) {
		return (DzFileColumnConf)getSqlSession().selectOne("DzFileColumnConf.queryDzFileColumnConfById", object_id);
	}

	@Override
	public List<DzFileColumnConf> queryDzFileColumnConfList() {
		return getSqlSession().selectList("DzFileColumnConf.queryDzFileColumnConfList");
	}
	
	@Override
	public List<DzFileColumnConf> queryDzFileColumnConfListByName(Map<String,Object> map){
		return getSqlSession().selectList("DzFileColumnConf.queryDzFileColumnConfListByName",map);
	}

	@Override
	public int unionCheckDzColumnName(DzFileColumnConf dzFileColumnConf) {
		return (Integer)getSqlSession().selectOne("DzFileColumnConf.queryUnionCheckDzColumnName", dzFileColumnConf);
	}

	@Override
	public int unionCheckDzColumnattr(DzFileColumnConf dzFileColumnConf) {
		Object object = getSqlSession().selectOne("DzFileColumnConf.queryUnionCheckDzColumnattr", dzFileColumnConf);
		return (Integer)object;
	}

}
