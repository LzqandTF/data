package cn.com.chinaebi.dz.object.dao.iface;

import java.util.Date;
import java.util.List;

import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.MerBasic;
import cn.com.chinaebi.dz.object.MerBilling;


public interface TradeLstDAO {
	public cn.com.chinaebi.dz.object.TradeLst get(java.lang.String key);

	public cn.com.chinaebi.dz.object.TradeLst load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.TradeLst> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tradeLst a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.TradeLst tradeLst);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tradeLst a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.TradeLst tradeLst);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tradeLst a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.TradeLst tradeLst);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tradeLst the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.TradeLst tradeLst);
	
	
	/**
	 * 数据分割(处理冲正和消费交易为一条记录)
	 * @param tradeTime ：交易时间
	 * @param tableName ：渠道表名称
	 * @param deductSysId ：渠道ID
	 * @return boolean
	 */
	public boolean splitData(String tradeTime,String tableName,int deductSysId,int bank_id);
	
	/**
	 * 扣款渠道多笔冲正交易有效性去重处理
	 * @param tradeTime ：交易日期
	 * @param tableName ：线下渠道表名
	 * @return
	 */
	public boolean deductRollBkDeWeight(String tradeTime,String tableName,int deduct_sys_id);
	
	/**
	 * 金额正负处理(proce_handle_money)
	 * @param tradeTime ：交易日期
	 * @param tableName ：渠道表名称
	 * @param deduct_sys_id ：扣款渠道ID
	 * @return boolean
	 */
	public boolean handleMoney(String tradeTime, String tableName,int deduct_sys_id);
	
	/**
	 * 线下渠道数据汇总(proce_original_datacollect_lst)
	 * @param tradeTime : 交易时间
	 * @param tableName ：渠道表名称
	 * @param deduct_sys_id ：渠道ID
	 * @param bank_id ：银行网关ID
	 * @return int
	 */
	public int dataCollectHanler(String tradeTime,String tableName,int deduct_sys_id,int bank_id);
	
	/**
	 * 汇总线下不需要外部对账渠道数据
	 * @param tradeTime ：交易时间
	 * @param deduct_sys_id : 渠道ID
	 * @param bank_id : 银行网关号
	 * @return
	 */
	public int noDzdataCollectHanler(String tradeTime,String tableName,int deduct_sys_id,int bank_id);
	
	/**
	 * 数据有效性处理
	 * @param tradeTime ：交易日期
	 * @param tableName ：渠道表名
	 * @param flag_ ：1->自动定时任务执行，2->手动还原数据状态执行
	 * @param deduct_sys_id : 渠道ID
	 * @return boolean
	 */
	public boolean dispenseWithHandle(String tradeTime,String tableName,int flag_);
	
	/**
	 * 统计所有渠道指定商户号或者交易码的对账成功数据个数
	 * @param date
	 * @param codeArr
	 * @return
	 */
	public int getTotalCountOfDzSucessDataOfOffLine(String date,List<String> codeArr,int generateNumber,boolean whethercreatefilebyrange);
	
	/**
	 * 查询内部清算数据
	 * @param className 实体类名称
	 * @param status  查询数据状态
	 * @param tradeDate  查询数据日期
	 * @return
	 */
	public List<?> queryInnerClearData(Class<?> clas,String tableName,int inst_id,String status,String tradeDate,int bankType);
	
	
	/**
	 * 修改渠道内部交易数据表中对应交易结果的内部清算字段状态
	 * @param instInfo 扣款渠道对象
	 * @param status 交易结果
	 * @param tradeDate 交易日期
	 * @return
	 */
	public boolean updateInnerClearDataInnerJsStatus(String tableName,int inst_id,String status,String tradeDate,int bankType);
	
	/**
	 * 查询线下商户统计(交易总金额、交易笔数、系统手续费(zf_file_fee)、系统手续费(zf_fee)、商户手续费、退款总金额、退款笔数、退款系统手续费(zf_file_fee)、退款系统手续费(zf_fee)、退款商户手续费)
	 * @param date ：日期
	 * @param mer_code ：商户号
	 * @param whetherTk ：是否退款
	 * @param whetherQs ：是否清算
	 * @return Object[]
	 */
	public Object[] findPosChanneltotall(String date,String mer_code,boolean whetherQs,String tableName);
	
	
	/**
	 * 查询线上商户统计(交易总金额、交易笔数、系统手续费、商户手续费、退款总金额、退款笔数、退款系统手续费、退款商户手续费)
	 * @param date ：日期
	 * @param mer_code ：商户号
	 * @param whetherTk ：是否退款
	 * @param whetherQs ：是否清算
	 * @return Object[]
	 */
	public Object[] findRyfChanneltotall(Integer date,String mer_code,boolean whetherQs,String tableName);
	
	/**
	 * 保存银联CUPST+1日统计
	 * @param objects[0] ：渠道号inst_id
	 * @param objects[1] ：商户号mer_code
	 * @param objects[2] ：商户类别mer_type
	 * @param objects[3] ：清算日期deduct_stlm_date
	 * @param objects[4] ：交易金额trade_amount
	 * @param objects[5] ：交易笔数trade_count
	 * @param objects[6] ：退款金额refund_amount
	 * @param objects[7] ：退款笔数refund_count
	 * @param objects[8] ：交易商户手续费mer_fee
	 * @param objects[9] ：系统手续费system_fee
	 * @param objects[10]：退回商户手续费
	 * @param objects[11]：应结算金额settle_amount
	 * @param objects[12]：退回系统手续费system_refund_fee
	 * @return boolean
	 */
	public boolean saveMerchantSettleStatistics(Object ...objects);
	
	/**
	 * 根据当前交易流水号查询原笔交易的交易时间(针对撤销和退货交易)
	 * @param sysStance
	 * @return
	 */
	public String getOriginalTradeTimeOfCancel(String sysStance,int tradeMsgType,String date,String tableName);
	
	/**
	 * 线下获取渠道交易商户号
	 * @param tableName ：渠道原始表名称
	 * @param whetherQs ：是否清算
	 * @param date ：日期
	 * @param dataColumn ：数据库日期查询字段
	 * @return List<Object>
	 */
	public List<Object> getChannelMerCode(String tableName,boolean whetherQs,String date,String dataColumn);
	
	/**
	 * 线上获取渠道交易商户号
	 * @param tableName ：渠道原始表名称
	 * @param whetherQs ：是否清算
	 * @param date ：日期
	 * @param hlogDateColumn ：收款数据库日期查询字段
	 * @param refundLogDateColumn ：退款数据库日期查询字段
	 * @return
	 */
	public List<Object> getRytChannelMerCode(String tableName,boolean whetherQs, Integer date, String hlogDateColumn,String refundLogDateColumn);
	
	/**
	 * 根据当前交易流水号查询原笔交易的交易时间(针对冲正交易)
	 * @param sysStance 请求流水号
	 * @param date 清算日期
	 * @param tableName  数据表名
	 * @return
	 */
	public String getOriginalTradeTimeOfRollBk(String sysStance,String date,String tableName);
	
	/**
	 * 线下渠道内部对账
	 * @param date ：日期
	 * @param tableName ：渠道原始表名称
	 * @param dateColumn ：日期字段
	 * @return boolean
	 */
	public boolean channelInnerDz(String date,String tableName,String dateColumn);
	
	/**
	 * 线下对账保存T+1统计数据
	 * @param deductStlmDate : 清算日期 yyyy-MM-dd
	 * @param settleMerCode : 结算商户号
	 * @param whetherQs ：是否清算
	 * @param originalDataTableName ：原始交易表
	 * @param instId ：渠道ID
	 * @param merCategory ：商户类别
	 * @param inst_type : 渠道类型
	 * @param obj ：统计数据
	 * @param settle_flag : 该商户是否存在对应结算商户中
	 * @return
	 */
	public boolean merchantSettleStatisticsUtil(String deductStlmDate,String settleMerCode, boolean whetherQs, String originalDataTableName,
			Integer instId,Integer merCategory,Integer inst_type,Object[] obj,boolean settle_flag,int bank_id);
	
	/**
	 * 根据日期、渠道、渠道类型、数据状态删除结算商户对应电银商户t+1统计数据
	 * @param deductStlmDate : 清算日期
	 * @param settleMerCode ：对应结算商户号
	 * @param instId ：渠道好
	 * @param inst_type ：渠道类型
	 * @return boolean
	 */
	public boolean deleteSettleMerchantSettleStatistics(String deductStlmDate,String settleMerCode,Integer instId,Integer inst_type);
	
	/**
	 * 查询清算数据
	 * @param clas  实体类
	 * @param tableName	表名
	 * @param date	清算日期
	 * @return
	 */
	public List<?> queryQsData(Class<?> clas,String tableName,String date,int inst_id);
	
	/**
	 * 查询清算数据，冲正数据
	 * @param clas  实体类
	 * @param instInfo	表名
	 * @param date	清算日期
	 * @return
	 */
	public List<?> queryQsDataOfRollBk(Class<?> clas,String tableName,String date,int inst_id);
	
	/**
	 * 线下渠道数据汇总保存商户余额和商户资金流水
	 * @param tradeTime ：交易时间 
	 * @param instInfo ：渠道信息
	 * @return
	 */
	public boolean saveMerStanceAndBalance(String tradeTime,InstInfo instInfo,int bank_id);
	
	/**
	 * 线下渠道对账保存商户资金流水修改更新商户余额
	 * @param merCode : 商户号
	 * @param tradeTime ：交易日期
	 * @param tradeStance ：交易流水
	 * @param mer_fee ：商户手续费
	 * @param tradeAmount ：交易金额
	 * @param derc_status ：数据状态 1:消费(支付)、2:退款(冲正)、3:差错调整、4:结算到电银账户
	 * @param isDeductRollBk ：是否冲正
	 * @param instId : 渠道ID
	 * @param instType ：渠道类型
	 * @param deductStlmDate ：清算日期
	 * @param bank_id ：银行网关
	 * @return boolean
	 */
	public boolean saveMerFundStanceAndupdateMerBalance(String merCode,
			Date deductSysTime,Date deductRollbkSysTime, String tradeStance, double mer_fee,
			Long tradeAmount, Integer derc_status,boolean isDeductRollBk,Integer instId,Integer instType,String deductStlmDate,int bank_id,MerBasic merBasic,MerBilling merBilling);
	
	/**
	 * 根据主键ID查询原始渠道交易表对账情况
	 * @param deductStlmDate ：清算日期
	 * @param tradeId ：主键ID
	 * @param tableName ：渠道表名称
	 * @return boolean
	 */
	public Integer findOriChannelStanceData(String deductStlmDate,String tradeId,String tableName);
	
	/**
	 * 根据主键ID查询对账渠道交易表对账情况
	 * @param deductStlmDate ：清算日期
	 * @param id ：唯一标识
	 * @param columnName ：字段名称
	 * @param tableName ：对账单表名称
	 * @return
	 */
	public Integer findDuizChannelStanceData(String deductStlmDate,String id,String columnName,String tableName);
	
	/**
	 * 处理冲正(失败/超时交易)对账成功进入资金流水/商户余额
	 * @param tradeAmount ：交易金额
	 * @param reqMerCode ：请求商户号
	 * @param gate ：网关号(融易付)
	 * @param deductSysTime ：扣款时间
	 * @param deductRollbkSysTime ：冲正时间
	 * @param deductSysStance ：流水号
	 * @param isDeductRollBk ：是否冲正
	 * @param instId ：渠道ID
	 * @param inst_type ：渠道类型 
	 * @param deductStlmDate ：清算日期
	 * @param bank_id ：网关号(对账系统)
	 * @param trade_id ：交易主键
	 * @param originalDataTableName ：原交易表名称
	 * @param riqieOriginalTableName ：日切交易表名称
	 * @param columnName ：字段类型
	 * @param whether_flag ：是否进入资金流水
	 * @return
	 */
	public boolean updateDeductRollBkStanceHandler(String tradeAmount,String reqMerCode,
			int gate,Date deductSysTime,Date deductRollbkSysTime,String deductSysStance,boolean isDeductRollBk,
			int instId, int inst_type, String deductStlmDate,int bank_id,String trade_id,String originalDataTableName, 
			String riqieOriginalTableName,String columnName,int whether_flag);
	
	/**
	 * 修改日切和原始交易表whetherAccessStance字段值(是否进入资金流水)
	 * @param id ：唯一标识
	 * @param riqieTable ：日切原始交易表
	 * @param originalTable ：原始交易表
	 * @param whether_flag ：是否进入资金流水
	 * @return boolean
	 */
	public boolean updateChannelWhetherAccessStance(String trade_id,String riqieTable,String originalTable,String columnName,Integer whether_flag);
	
	/**
	 * 重对账-修改T+1日统计表
	 * @param merCode : 商户号
	 * @param reqSysStance : 流水号
	 * @param tradeAmount ：交易金额
	 * @param mer_fee ：商户手续费
	 * @param zf_fee ：支付手续费
	 * @param zf_file_fee ：对账单手续费
	 * @param deductStlmDate ：清算日期
	 * @param inst_id ：渠道id
	 * @param inst_type ：渠道类型
	 * @param data_status ：T+1统计表数据状态 0-正常；1-退款经办；2-退款审核；3-差错处理
	 * @param whetherTk : 是否为退款
	 * @return boolean
	 */
	public boolean updateMerchantSettleStatistics(String merCode,String reqSysStance,Long tradeAmount,Double mer_fee,Double zf_fee,String zf_file_fee,
			String deductStlmDate,Integer inst_id,Integer inst_type,Integer data_status,boolean whetherTk);
}