package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.ManualRecDao;
import com.chinaebi.entity.ManualRec;
import com.chinaebi.entity.Merchants;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "manualRecDao")
public class ManualRecDaoImpl extends MyBatisDao implements ManualRecDao {

	@Override
	public Merchants queryMerInfoByMerCode(String merCode) {
		return (Merchants) getSqlSession().selectOne("Merchants.queryMerInfoByMerCode", merCode);
	}
	
	@Override
	public int addManualRec(Map<String, Object> map) {
		return getSqlSession().insert("Manual_Rec.addManualRec", map);
	}
	
	@Override
	public Page<ManualRec> queryPageDataLst(Page<ManualRec> page,
			Map<String, Object> map) {
		return selectPage(page, "Manual_Rec.queryPageDataLst", "Manual_Rec.queryCount", map);
	}

	@Override
	public int updateDataById(Map<String, Object> map) {
		return getSqlSession().update("Manual_Rec.updateDataById", map);
	}

	@Override
	public int updateDataToFail(Map<String, Object> map) {
		return getSqlSession().update("Manual_Rec.updateDataToFail", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ManualRec> queryDataLst(Map<String, Object> map) {
		return getSqlSession().selectList("Manual_Rec.queryDataLst", map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ManualRec> queryMerManualRec(String mer_code) {
		return getSqlSession().selectList("Manual_Rec.queryMerManualRec", mer_code);
	}

	@Override
	public int updateMerManualRecJsStatus(Map<String, Object> map) {
		return getSqlSession().update("Manual_Rec.updateMerManualRecJsStatus",map);
	}

	@Override
	public Page<ManualRec> queryPageAllData(Page<ManualRec> page,
			Map<String, Object> map) {
		return selectPage(page, "Manual_Rec.queryPageAllData", "Manual_Rec.queryCount", map);
	}
}
