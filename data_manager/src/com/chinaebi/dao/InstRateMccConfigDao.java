package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.InstRateMccConfig;

public interface InstRateMccConfigDao {
	public int addInstRateMccConfig(InstRateMccConfig instRateMccConfig);
	
	public int deleteInstRateMccConfig(InstRateMccConfig instRateMccConfig);
	
	public List<InstRateMccConfig> queryMccLiWaiConfig(Map<String,Integer> map);
	
	public int checkInstRateMccConfigExistOrNot(InstRateMccConfig instRateMccConfig);
}