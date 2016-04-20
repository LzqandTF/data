package com.chinaebi.service;

import java.util.List;

import com.chinaebi.entity.FeePoundage;

public interface FeePoundageService {
	/**
	 * 查询费率公式
	 * @return
	 */
	public List<FeePoundage> queryFeePoundage();
}
