package com.chinaebi.daoImpl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.PpfwDataDao;
import com.chinaebi.entity.PpfwData;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value="ppfwDataDao")
public class PpfwDataDaoImpl extends MyBatisDao implements PpfwDataDao{

	@Override
	public Page<PpfwData> queryPpfwData(Page<PpfwData> page,
			Map<String, Object> map) {
		return selectPage(page, "PpfwData.queryPagePpfwData", "PpfwData.queryCount", map);
	}
	
	@Override
	public double getPpfwFeeTotalCount(Map<String,Object> map){
		Object result = getSqlSession().selectOne("PpfwData.getPpfwFeeTotalCount",map);
		if(result!=null && StringUtils.isNotBlank(result.toString())){
			return Double.valueOf(result.toString());
		}
		return 0.00d;
	}

	@Override
	public double queryPagePpfwDataTotalCount(Map<String, Object> map) {
		Object result = getSqlSession().selectOne("PpfwData.queryPagePpfwDataTotalCount",map);
		if(result!=null && StringUtils.isNotBlank(result.toString())){
			return Double.valueOf(result.toString());
		}
		return 0.00d;
	}

}
