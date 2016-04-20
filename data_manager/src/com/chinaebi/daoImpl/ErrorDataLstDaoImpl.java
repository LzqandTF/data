package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.ErrorDataLstDao;
import com.chinaebi.entity.ErrorDataLst;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "errorDataLstDao")
public class ErrorDataLstDaoImpl extends MyBatisDao implements ErrorDataLstDao {
	public Page<ErrorDataLst> queryPageErrorDataLst(Page<ErrorDataLst> page,
			Map<String, Object> map) {
		return selectPage(page, "ErrorData_Lst.queryPageErrorDataLst","ErrorData_Lst.selectPageErrorDataCount", map);
	}

	@Override
	public ErrorDataLst queryErrorDataLstDetails(String trade_id) {
		return (ErrorDataLst) getSqlSession().selectOne("ErrorData_Lst.queryErrorDataLstDetails", trade_id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ErrorDataLst> selectList(Map<String, Object> map) {
		try {
			return getSqlSession().selectList("ErrorData_Lst.queryPageErrorDataLst", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int updateErrorHandleMethod(ErrorDataLst errorDataLst) {
		return getSqlSession().update("ErrorData_Lst.updateErrorHandleMethod", errorDataLst);
	}

	@Override
	public int updateHandlingStatus(ErrorDataLst errorDataLst) {
		return getSqlSession().update("ErrorData_Lst.updateApprovalPass", errorDataLst);
	}

	@Override
	public int updateApproval(ErrorDataLst errorDataLst) {
		return getSqlSession().update("ErrorData_Lst.updateForReject", errorDataLst);
	}

	@Override
	public int updateHandlerRemark(ErrorDataLst errorDataLst) {
		return getSqlSession().update("ErrorData_Lst.updateHandlerRemark", errorDataLst);
	}

	@Override
	public Page<ErrorDataLst> queryPageApprovalData(Page<ErrorDataLst> page, Map<String, Object> map) {
		return selectPage(page, "ErrorData_Lst.queryPageNeedApprovalData", "ErrorData_Lst.selectPageNeedApprovalDataCount", map);
	}

	@Override
	public Page<ErrorDataLst> queryOptionErrorLst(Page<ErrorDataLst> page, Map<String, Object> map) {
		return selectPage(page, "ErrorData_Lst.queryPageOptionErrorLst", "ErrorData_Lst.selectPageOptionErrorCount", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ErrorDataLst> queryAllForDownExcel(Map<String, Object> map) {
		return getSqlSession().selectList("ErrorData_Lst.queryAllForDownExcel", map);
	}

	@Override
	public ErrorDataLst queryDetailByTradeId(String tradeId) {
		return (ErrorDataLst) getSqlSession().selectOne("ErrorData_Lst.queryDetailByTradeId", tradeId);
	}

	@Override
	public int queryErrorNoHandlerCount(Map<String, Object> map) {
		return Integer.valueOf(getSqlSession().selectOne("ErrorData_Lst.queryErrorNoHandlerCount",map).toString());
	}

	@Override
	public Page<ErrorDataLst> queryPageDzTotalErrorData(
			Page<ErrorDataLst> page, Map<String, Object> map) {
		return selectPage(page, "ErrorData_Lst.queryPageDzTotalErrorData", "ErrorData_Lst.queryPageCountDzTotalErrorData", map);
	}

	@Override
	public int queryCountDzTotalErrorData(Map<String, Object> map) {
		return Integer.valueOf(getSqlSession().selectOne("ErrorData_Lst.queryPageCountDzTotalErrorData",map).toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ErrorDataLst> queryDzTotalErrorDataList(Map<String, Object> map) {
		return getSqlSession().selectList("ErrorData_Lst.queryDzTotalErrorDataList", map);
	}

	@Override
	public int addDataToErrorDataLst(ErrorDataLst errorDataLst) {
		return getSqlSession().insert("ErrorData_Lst.addDataToErrorDataLst", errorDataLst);
	}

	@Override
	public int addUnderLineDataToErrorDataLst(ErrorDataLst errorDataLst) {
		return getSqlSession().insert("ErrorData_Lst.addUnderLineDataToErrorDataLst", errorDataLst);
	}
}

