package com.chinaebi.service;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.DzFileColumnConf;
import com.chinaebi.utils.mybaits.Page;

public interface DzFileColumnConfService {
	public Page<DzFileColumnConf> queryPageDzFileColumnConf(Page<DzFileColumnConf> page,Map<String,Object> map);
	
	public int insertDzFileColumnConf(DzFileColumnConf dzFileColumnConf);
	
	public int updateDzFileColumnConf(DzFileColumnConf dzFileColumnConf);
	
	public int deleteDzFileColumnConf(int object_id);
	
	public DzFileColumnConf queryDzFileColumnConfById(int object_id);
	
	public List<DzFileColumnConf> queryDzFileColumnConfList();
	
	public List<DzFileColumnConf> queryDzFileColumnConfListByName(String attribute_name,String file_type);
	
	public boolean unionCheckDzColumnName(DzFileColumnConf dzFileColumnConf);
	
	public boolean unionCheckDzColumnattr(DzFileColumnConf dzFileColumnConf);
}
