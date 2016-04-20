package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.chinaebi.dao.MerchantAccountingDao;
import com.chinaebi.entity.MerFundStance;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;


@Component(value = "merchantAccountingDao")
public class MerchantAccountingDaoImpl extends MyBatisDao implements MerchantAccountingDao {

	@Override
	public Page<MerFundStance> queryPageMerchantAccounting(Page<MerFundStance> page,Map<String, Object> map)throws Exception {
		
		return selectPage(page,"MerFundStance.queryPageAccounting","MerFundStance.queryPageCountAccounting", map);
	}

	@Override
	public List<MerFundStance> queryMerchantAccountingList(Map<String, Object> map) throws Exception {
		
		return getSqlSession().selectList("MerFundStance.queryMerAccountList", map);
	}


}
