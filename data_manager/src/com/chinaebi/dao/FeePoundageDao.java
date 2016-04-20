package com.chinaebi.dao;

import java.util.List;

import com.chinaebi.entity.FeePoundage;

public interface FeePoundageDao {
	/**
	 * 查询费率公式
	 * @return
	 */
	public List<FeePoundage> queryFeePoundage();
}
