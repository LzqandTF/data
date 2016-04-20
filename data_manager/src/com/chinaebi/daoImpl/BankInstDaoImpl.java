package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.BankInstDao;
import com.chinaebi.entity.BankInst;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "bankInstDao")
public class BankInstDaoImpl extends MyBatisDao implements BankInstDao {

	@Override
	public Page<BankInst> queryPageBankInst(Page<BankInst> page,Map<String, Object> map) {
		return selectPage(page, "BankInst.queryPageBankInst", "BankInst.queryCount", map);
	}
	
	@Override
	public BankInst queryBankInstByBankName(String bankName) {
		return (BankInst) getSqlSession().selectOne("BankInst.queryBankInstByBankName", bankName);
	}
	
	@Override
	public int addBankInst(Map<String, Object> map) {
		return getSqlSession().insert("BankInst.addBankInst", map);
	}

	@Override
	public int updateBankInstByBankId(Map<String, Object> map) {
		return getSqlSession().update("BankInst.updateBankInstByBankId", map);
	}

	@Override
	public int deleteBankInstByBankId(String bankId) {
		return getSqlSession().delete("BankInst.deleteBankInstByBankId", bankId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankInst> queryAllBankInst() {
		return getSqlSession().selectList("BankInst.queryAllBankInst");
	}

}
