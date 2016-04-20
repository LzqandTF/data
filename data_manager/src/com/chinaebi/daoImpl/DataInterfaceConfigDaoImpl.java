package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.DataInterfaceConfigDao;
import com.chinaebi.entity.DataInterfaceConfig;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;



@Component(value = "dataInterfaceConfigDao")
public class DataInterfaceConfigDaoImpl extends MyBatisDao implements DataInterfaceConfigDao{
	public Page<DataInterfaceConfig> queryPageDataInterfaceConfig(Page<DataInterfaceConfig> page,Map<String,Object> map){
		return selectPage(page,"DataInterfaceConfig.queryPageDataInterfaceConfig", "DataInterfaceConfig.queryPageCount",map);
	}
	
	public int insertDataInterfaceConfig(DataInterfaceConfig dataInterfaceConfig){
		return  getSqlSession().insert("DataInterfaceConfig.insertDataInterfaceConfig",dataInterfaceConfig);
	}
	
	public int deleteDataInterfaceConfig(int id){
		return getSqlSession().delete("DataInterfaceConfig.deleteDataInterfaceConfig", id);
	}
	
	public int deleteDataInterfaceConfigByObjectId(int object_id){
		return getSqlSession().delete("DataInterfaceConfig.deleteDataInterfaceConfigByObjectId", object_id);
	}
	
	public List<DataInterfaceConfig> queryDataInterfaceConfigByObjectId(int object_id){
		return getSqlSession().selectList("DataInterfaceConfig.queryDataInterfaceConfigByObjectId", object_id);
	}
	
	public List<DataInterfaceConfig> queryDataInterfaceConfigList(){
		return getSqlSession().selectList("DataInterfaceConfig.queryDataInterfaceConfigList");
	}
	
	public int queryDataInterfaceConfigNumberByValueOrName(Map<String,Object> map){
		return (Integer)getSqlSession().selectOne("DataInterfaceConfig.queryDataInterfaceConfigNumberByValueOrName",map);
	}
}
