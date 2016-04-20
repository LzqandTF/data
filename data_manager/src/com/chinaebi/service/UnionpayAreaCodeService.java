package com.chinaebi.service;

import java.util.Map;

import com.chinaebi.entity.UnionpayAreaCode;

public interface UnionpayAreaCodeService {
	public UnionpayAreaCode queryAreaNameByProvince(String provice);
	
	public UnionpayAreaCode queryAreaNameByProvinceAndCity(Map<String, Object> map);
}
