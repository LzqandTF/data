package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.DzFileColumnConf;
import com.chinaebi.utils.mybaits.Page;

public interface DzFileColumnConfDao {
	public Page<DzFileColumnConf> queryPageDzFileColumnConf(Page<DzFileColumnConf> page,Map<String,Object> map);
	
	public int insertDzFileColumnConf(DzFileColumnConf dzFileColumnConf);
	
	public int updateDzFileColumnConf(DzFileColumnConf dzFileColumnConf);
	
	public int deleteDzFileColumnConf(int object_id);
	
	public DzFileColumnConf queryDzFileColumnConfById(int object_id);
	
	public List<DzFileColumnConf> queryDzFileColumnConfList();
	
	public List<DzFileColumnConf> queryDzFileColumnConfListByName(Map<String,Object> map);
	
	public int unionCheckDzColumnName(DzFileColumnConf dzFileColumnConf);
	
	public int unionCheckDzColumnattr(DzFileColumnConf dzFileColumnConf);
}
