package com.chinaebi.dao;

import java.util.Map;

import com.chinaebi.entity.UnionpayAreaCode;

public interface UnionpayAreaCodeDao {
	public UnionpayAreaCode queryAreaNameByProvince(String province);
	
	public UnionpayAreaCode queryAreaNameByProvinceAndCity(Map<String, Object> map);
}
