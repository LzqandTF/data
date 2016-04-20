package com.chinaebi.daoImpl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.UnionpayAreaCodeDao;
import com.chinaebi.entity.UnionpayAreaCode;
import com.chinaebi.utils.mybaits.MyBatisDao;

@Component(value = "unionpayAreaCodeDao")
public class UnionpayAreaCodeDaoImpl extends MyBatisDao implements UnionpayAreaCodeDao {

	@Override
	public UnionpayAreaCode queryAreaNameByProvince(String province) {
		return (UnionpayAreaCode) getSqlSession().selectOne("UnionpayAreaCode.queryAreaNameByProvince", province);
	}

	@Override
	public UnionpayAreaCode queryAreaNameByProvinceAndCity(Map<String, Object> map) {
		return (UnionpayAreaCode) getSqlSession().selectOne("UnionpayAreaCode.queryAreaNameByProvinceAndCity", map);
	}

}
