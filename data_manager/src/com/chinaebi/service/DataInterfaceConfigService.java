package com.chinaebi.service;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.DataInterfaceConfig;
import com.chinaebi.utils.mybaits.Page;

public interface DataInterfaceConfigService {
	public Page<DataInterfaceConfig> queryPageDataInterfaceConfig(Page<DataInterfaceConfig> page,Map<String,Object> map);
	
	public int insertDataInterfaceConfig(DataInterfaceConfig dataInterfaceConfig);
	
	public int deleteDataInterfaceConfig(int id);
	
	public int deleteDataInterfaceConfigByObjectId(int object_id);
	
	public List<DataInterfaceConfig> queryDataInterfaceConfigByObjectId(int object_id);
	
	public List<DataInterfaceConfig> queryDataInterfaceConfigList();
	
	public boolean queryDataInterfaceConfigNumberByValueOrName(int object_id,String value);
}
