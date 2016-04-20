package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.com.chinaebi.dz.object.ErrorDataLst;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.OriginalCupsLst;

public interface ErrorDataLstDAO {
	public cn.com.chinaebi.dz.object.ErrorDataLst get(java.lang.String key);

	public cn.com.chinaebi.dz.object.ErrorDataLst load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.ErrorDataLst> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param errorDataLst a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.ErrorDataLst errorDataLst);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param errorDataLst a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.ErrorDataLst errorDataLst);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param errorDataLst a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.ErrorDataLst errorDataLst);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param errorDataLst the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.ErrorDataLst errorDataLst);
	
	public List<ErrorDataLst> findErrorDataCupsbankLst(String errorDataReqTime);
	
	public boolean updateClean(String reqSysStance, boolean clean,int duizhangFlag);

	public List<ErrorDataLst> queryErrorDzSucessData(String date,List<Integer> ids,List<String> codeArr,int generateNumber);
	
	public String getTradeCodeName(String tradeCode);
	
	public boolean saveErrorData(ErrorDataLst errorDataLst);
	
	public boolean deleteErrorData(String id);
	
	/**
	 * 根据内部差错处理方式id查询处理方式中文名称
	 * @param handling_id
	 * @return
	 */
	public String getInnerErrorHandlingName(int handling_id);
	/**
	 * 根据商户号或者交易码查询出差错对账成功的数据个数
	 * @param date
	 * @param ids
	 * @param codeArr
	 * @return
	 */
	public int getTotalErrorDzDataNum(String date,List<Integer> ids,List<String> codeArr,int generateNumber);
	
	/**
	 * 保存ryt线上对账文件差错
	 * @param deduct_sys_id ：渠道ID,对账单差错无渠道ID 默认 0
	 * @param mer_name ：商户名称 目前空
	 * @param inst_type ：渠道类型
	 * @param bank_id ：网关号
	 * @param duizhangNotDzList ：对账单未对账数据集合
	 * @param yyyyMMddHHmmss ：日期格式化(yyyyMMddHHmmss)
	 * @param yyyyMMdd : 日期格式化(yyyyMMdd)
	 * @return boolean
	 */
	public boolean saveRytDuizhangFileError(Integer deduct_sys_id,String mer_name,int inst_type,int bank_id,List<Object> duizhangNotDzList,SimpleDateFormat yyyyMMddHHmmss,SimpleDateFormat yyyyMMdd);
	
	/**
	 * 保存ryt线上原始交易差错
	 * @param duizhangId : 对账单主键ID
	 * @param sys_date ：交易日期yyyyMMdd
	 * @param sys_time ：交易时间HHmmss
	 * @param deduct_stlm_date : 清算日期
	 * @param tstat ：交易状态
	 * @param tseq ：流水号
	 * @param oid ：订单号
	 * @param out_account ：卡号
	 * @param trade_amount ：交易金额
	 * @param mer_fee ：商户手续费
	 * @param zf_fee ：支付手续费
	 * @param zf_file_fee ：对账文件手续费
	 * @param deduct_sys_id ：渠道ID
	 * @param inst_type ：渠道类型
	 * @param whetherErroeHandle ：差错状态
	 * @param whetherReturnFee ：是否为退款
	 * @param mid ：商户号
	 * @param bank_id ：银行机构ID
	 * @return boolean
	 */
	public boolean saveRytOriginalTradeError(String duizhangId,Integer bk_chk,Integer sys_date,Integer sys_time,Date deduct_stlm_date,Integer tstat,String tseq,String oid,String out_account,Long trade_amount,double mer_fee,double zf_fee,String zf_file_fee,Integer deduct_sys_id,int inst_type,int whetherErroeHandle,String mid,int bank_id);
	
	/**
	 * 保存ryt线上23点之后未对账已清算原始交易数据
	 * @param sys_date ：交易日期yyyyMMdd
	 * @param sys_time ：交易时间HHmmss
	 * @param deduct_stlm_date : 清算日期
	 * @param tstat ：交易状态
	 * @param tseq ：流水号
	 * @param oid ：订单号
	 * @param out_account ：卡号
	 * @param trade_amount ：交易金额
	 * @param mer_fee ：商户手续费
	 * @param zf_fee ：支付手续费
	 * @param zf_file_fee ：对账文件手续费
	 * @param deduct_sys_id ：渠道ID
	 * @param inst_type ：渠道类型
	 * @param whetherErroeHandle ：差错状态
	 * @param whetherReturnFee ：是否为退款
	 * @param mid ：商户号
	 * @param bank_id ：银行机构ID
	 * @return boolean
	 */
	public boolean saveRytOriginalTradeQsNoDzError(Integer sys_date,Integer sys_time,Date deduct_stlm_date,Integer tstat,String tseq,String oid,String out_account,Long trade_amount,double mer_fee,double zf_fee,String zf_file_fee,Integer deduct_sys_id,int inst_type,int whetherErroeHandle,String mid,int bank_id);
	
	/**
	 * 根据清算日期查询差错数据(可选：原始交易差错和对账文件差错)
	 * @param deductStmlDate ：清算日期
	 * @param error_resource ：1：原始交易差错、2：对账文件差错
	 * @param instId ：渠道ID
	 * @param instType ：渠道类型
	 * @return List<ErrorDataLst>
	 */
	public List<ErrorDataLst> findOriErrorChannelData(String deductStmlDate,Integer error_resource,Integer instId,Integer instType);
	
	/**
	 * 修改差错处理状态和处理备注
	 * @param trade_id ：主键ID
	 * @param handlerStatus ：处理状态 0：未处理、1：待审核、2：已审核、3：已驳回、4：无需处理
	 * @param doubleCheckStatus : 重对账状态
	 * @param handlerRemark ：处理备注
	 * @return
	 */
	public boolean updateErrorHandlerStatus(String trade_id,Integer handlerStatus,Integer doubleCheckStatus,String handlerRemark);
}