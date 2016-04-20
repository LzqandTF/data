package com.chinaebi.service;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.BankInst;
import com.chinaebi.utils.mybaits.Page;

public interface BankInstService {
	
	/**
	 * 银行机构维护分页查询
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<BankInst> queryPageBankInst(Page<BankInst> page, Map<String, Object> map);
	
	/**
	 * 根据机构名称查询机构信息
	 * @param bankName
	 * @return
	 */
	public BankInst queryBankInstByBankName(String bankName);
	
	/**
	 * 添加银行机构
	 * @param bankInst
	 * @return
	 */
	public int addBankInst(Map<String, Object> map);
	
	/**
	 * 根据银行机构ID修改数据信息
	 * @param map
	 * @return
	 */
	public int updateBankInstByBankId(Map<String, Object> map);
	
	/**
	 * 根据银行机构ID删除数据
	 * @param bankId
	 * @return
	 */
	public int deleteBankInstByBankId(String bankId);
	
	/**
	 * 获取所有银行机构信息
	 * @return
	 */
	public List<BankInst> queryAllBankInst();
	
	/**
	 * 调用接口更新后台内存
	 * @param bankId
	 * @return
	 */
	public boolean updateRamBankInstInfo(int bankId);
	
}
