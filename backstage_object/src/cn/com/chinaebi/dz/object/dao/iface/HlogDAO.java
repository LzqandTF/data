package cn.com.chinaebi.dz.object.dao.iface;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.Hlog;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.MerBasic;

public interface HlogDAO {
	public cn.com.chinaebi.dz.object.Hlog get(java.lang.Long key);

	public cn.com.chinaebi.dz.object.Hlog load(java.lang.Long key);

	public java.util.List<cn.com.chinaebi.dz.object.Hlog> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param hlog a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.Long save(cn.com.chinaebi.dz.object.Hlog hlog);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param hlog a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.Hlog hlog);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param hlog a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.Hlog hlog);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.Long id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param hlog the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.Hlog hlog);
//	/**
//	 * 在手动对账之前，根据交易时间，将渠道原始交易数据以及对账文件的是否差错处理字段的状态还原
//	 * @param summaryDate
//	 * @return
//	 */
//	public boolean reductionDataStatusType(String summaryDate,InstInfo instInfo) throws Exception;
	/**
	 * 通过交易时间，删除线上的交易数据
	 * @param tradeTime
	 * @return
	 */
	public boolean deleteOnlineData(String tradeTime,String tableName);

	/**
	 * 汇总对账平台Hlog到对应渠道原始交易表
	 * @param tradeTime 交易时间
	 * @param tableName 交易数据表名称
	 * @param deduct_sys_id	渠道ID
	 * @param bank_id 银行网关
	 * @return
	 */
	public int dataCollectHanler(String tradeTime,String tableName,int deduct_sys_id,int bank_id);
	/**
	 * 汇总线上退款交易数据
	 * @param tradeTime 交易时间
	 * @param tableName 交易数据表名称
	 * @param deduct_sys_id	渠道ID
	 * @return
	 */
	public int refundDataCollectHanler(String tradeTime,String tableName,int deduct_sys_id);
	/**
	 * 获取线上对账成功交易数据总条数
	 * @param date
	 * @param codeArr
	 * @param generateNumber
	 * @return
	 */
	public int getTotalCountOfDzSucessDataOfOnLine(String date,List<String> codeArr,int generateNumber,boolean whethercreatefilebyrange);
	/**
	 * 融易通的线上交易数据无需对账处理
	 * @param tradeTime 交易时间
	 * @param tableName 交易数据表名称
	 * @param handle_type 处理类型
	 * @param deduct_sys_id	渠道ID
	 * @param bank_id 银行网关
	 * @return boolean
	 */
	public boolean rytDispenseWithHandle(String tradeTime,String rytTab,int handle_type,int deduct_sys_id);
	
	/**
	 * 同步融易付hlog交易数据->对账系统Hlog表
	 * @param tradeTime : 交易日期
	 * @param deduct_sys_id : 扣款渠道ID
	 * @param columnName ：日期字段名称
	 * @param properties_gid : conf.properties配置的gid值
	 * @return int
	 */
	public int getRytHlog(String tradeTime,int deduct_sys_id,String columnName,String properties_gid);
	
	/**
	 * 删除对账平台hlog表中存在数据
	 * @param date ：日期
	 * @param gid ：渠道ID
	 * @return
	 */
	public int deleteHlogDataCount(String date,int gid);
	
	/**
	 * 汇总数据进入商户余额、商户资金流水、商户T+1统计
	 * @param tradeDate ： 日期
	 * @param dateColumn ：日期数据库字段名称
	 * @param gid ：渠道ID
	 * @param tableName : 渠道表名称
	 * @return boolean
	 */
	public boolean handlerRytSuccessData(String tradeDate,String dateColumn, int gid, String tableName,int bank_id);
	
	/**
	 * 统计对账成功交易进入t+1统计表
	 * @param tableName ：原始表名称
	 * @param dateColumn ：日期数据库表字段
	 * @param gid : 渠道ID
	 * @param deductStlmDate ：交易日期
	 * @param instType ：渠道类型 0:线下、1:线上
	 * @return boolean
	 */
	public boolean saveMerchantSettleStatistics(String tableName,String dateColumn,int gid,int deductStlmDate,int instType,Map<String, Object> mapMerBasic,int bank_id)throws Exception;
	
	/**
	 * 统计23点-24未对账交易进入t+1统计表
	 * @param mid : 商户号
	 * @param deductStlmDate ：清算日期
	 * @param gid ：渠道id
	 * @param instType ：渠道类型
	 * @param amount ：交易金额
	 * @param merFee ：交易手续费
	 * @return boolean
	 */
	public boolean saveSingleMerchantSettleStatistics(String mid,int deductStlmDate,int gid,int instType,double amount,double merFee,String system_fee,double zf_fee,Map<String, Object> mapMerBasic,int bank_id)throws Exception;
	
	/**
	 * 统计融易付数据库中渠道交易数据数量
	 * @param date  日期
	 * @param gid	渠道ID
	 * @param column	日期字段属性值
	 * @return
	 */
	public int getOnLineDataCountFromRYF(String date,int gid,String column);
	
	/**
	 * 统计对账系统数据库中渠道交易数据数量
	 * @param date  日期
	 * @param gid	渠道ID
	 * @param column	日期字段属性值
	 * @return
	 */
	public int getOnlineDataCountFromDzSys(String date,int gid,String column);
	
	/**
	 * 查询线上清算数据
	 * @param clas  实体类
	 * @param instInfo  渠道信息
	 * @param date  交易日期
	 * @return
	 */
	public List<?> queryQsData(Class<?> clas ,String tableName,String date,int inst_id);
	
	/**
	 * 查询线上对账单数据 
	 * @param dzDataTableName ： 对账单表名称
	 * @param deductStmlDate ：清算日期
	 * @param isTk ：是否退款
	 * @param bk_chk : 是否对账
	 * @return int
	 */
	public List<Object> queryDuizhangDataList(String dzDataTableName, String deductStmlDate ,boolean isTk,int bk_chk);
	
	/**
	 * 修改待支付-未对账数据改成无需对账
	 * @param dzDataTableName : 原始交易表名称
	 * @param sysDate ：交易日期
	 * @param update_bk_chk ：修改的对账状态 
	 * @param select_bk_chk ：查询的对账状态
	 * @return boolean
	 */
	public boolean updateDZFNoHandle(String originalTableName,String sysDate,int update_bk_chk,int select_bk_chk);
	
	/**
	 * 查询原始交易一定范围交易数据(column)
	 * @param originalTableName ：原交易表名称
	 * @param instId ：渠道ID
	 * @return Map<String, Object[]>
	 */
	public Map<String, Object[]> queryColumnMap(String originalTableName,Integer instId, int startDate,int endDate,String column);
	
	/**
	 * 查询原始交易一定范围交易数据(All)
	 * @param originalTableName ：原交易表名称
	 * @param instId ：渠道ID
	 * @return Object[]{Map<String, Object[]>,Map<String, Object[]>}
	 */
	public Object[] queryAllMap(String originalTableName,Integer instId, int startDate,int endDate);
	
	/**
	 * 更新线上对账单数据对账成功状态
	 * @param dzDataTableName : 对账单表名称
	 * @param id：主键ID
	 * @param bk_chk：对账状态 0:为对账、1:对账成功、2:对账失败
	 * @return boolean
	 */
	public boolean updateDuizhangDataBkchk(String dzDataTableName,String id,int bk_chk);
	
	/**
	 * 更新线上原始交易数据对账成功状态
	 * @param originalTableName : rytryt原始表名称
	 * @param id：主键ID
	 * @param bk_chk：对账状态 0:为对账、1:对账成功、2:对账失败
	 * @param whetherQs : 是否清算
	 * @param zf_fee : 支付手续费
	 * @param zf_file_fee : 银行手续费
	 * @param dz_deductStlmDate : 对账单清算日期
	 * @return boolean
	 */
	public boolean updateOriginalDataBkchk(String originalTableName,String id,int bk_chk,boolean whetherQs,double zf_fee,String zf_file_fee,String dz_deductStlmDate);
	
	/**
	 * 统计原交易表中各渠道对账结果
	 * @param tradeDate 交易日期
	 * @param tableName	原交易数据表名
	 * @param bk_chk	对账状态
	 * @param inst_id	渠道ID
	 * @return
	 */
	public Map<Integer,Integer> getInstDzResult(String tradeDate,String tableName,int bk_chk,int inst_id);
	
	/**
	 * 统计对账单数据表中数据总数
	 * @param deduct_stlm_date 清算日期
	 * @param tableName	对账单数据表名
	 * @return
	 */
	public int getBankTotalDataNum(String deduct_stlm_date,String tableName);
	
	/**
	 * 统计原交易数据表中数据条数
	 * @param startDate	交易起始日期
	 * @param endDate	交易截止日期
	 * @param tableName 原交易数据表名
	 * @param gid 渠道ID
	 * @return
	 */
	public int getOriTotalDataNum(int startDate,int endDate,String tableName,int gid);
	
	/**
	 * 查询原始交易对账/结算状态数据
	 * @param originalTableName ：ryt原始表名称
	 * @param sys_date ：日期
	 * @param bk_chk ：对账状态
	 * @param is_qs ：是否清算
	 * @return
	 */
	public List<Object> queryNoDzJsOriginalData(String originalTableName,int sys_date,int bk_chk,boolean is_qs);
	
	/**
	 * 查询原交易对账状态/结算状态/交易状态数据
	 * @param originalTableName ：ryt原始表名称
	 * @param sys_date ：日期
	 * @param bk_chk ：对账状态
	 * @param is_js : 是否清算
	 * @param tstat ：交易是否成功
	 * @return
	 */
	public List<Object> queryNoDzJsIsSuccessOriginalData(String originalTableName,int sys_date,int bk_chk,boolean is_qs,int tstat);

	/**
	 * 根据主键ID修改对账状态和清算状态
	 * @param originalTableName : 原始交易表名称
	 * @param id : 主键ID
	 * @param whetherQs : 清算状态
	 * @param deduct_stlm_date : 清算日期
	 * @return boolean
	 */
	public boolean updateBkChkOrWhetherQs(String originalTableName,String id,boolean whetherQs,int deduct_stlm_date);
	
	/**
	 * 保存对账单数据
	 * @param columnData 数据值
	 * @param stmt
	 * @return
	 * @throws Exception
	 */
	public boolean saveBankData(String[] columnData,PreparedStatement stmt) throws Exception;
	
	public Session getCurrentSession();
	
	/**
	 * 获取交易数据分页
	 * @param map
	 * @return
	 */
	public List<Hlog> getHlogPageData(Map<String,String[]> map) throws Exception;
	/**
	 * 获取交易数据
	 * @param map
	 * @return
	 */
	public List<Hlog> getHlogDataList(Map<String,String[]> map) throws Exception;
	
	/**
	 * 线上接口同步未完成交易数据保存
	 * @param tseq ：流水号
	 * @param version ：版本号
	 * @param mdate ：商户交易日期
	 * @param mid ：商户号
	 * @param bid ：买方id(代理商id)
	 * @param oid ：订单号
	 * @param amount ：交易金额
	 * @param type ：交易类型
	 * @param gate ：支付网关号
	 * @param author_type ：支付发起类型
	 * @param sys_date ：系统日期
	 * @param init_sys_date ：初始的系统日期
	 * @param sys_time ：系统时间
	 * @param fee_amt ：交易手续费
	 * @param bank_fee ：银行收取手续费
	 * @param tstat ：交易状态
	 * @param mobile_no ：手机号
	 * @param Phone_no ：支付的手机号
	 * @param card_no ：记录支付所用的银行卡号
	 * @param gid ：支付渠道
	 * @param pre_amt ：差价
	 * @param pre_amt1 ：优惠金额
	 * @param bk_fee_model ：银行手续费计算公式
	 * @param pay_amt : 实际交易金额
	 * @param currency ：币种
	 * @param exchange_rate ：汇率
	 * @param out_user_id ：出账用户ID
	 * @param in_user_id ：入账用户ID
	 * @param bind_mid ：快捷卡绑定商户号
	 * @param merBasic : 商户信息
	 * @return boolean
	 */
	public boolean saveSynDzfTrade(String tseq,String version,String mdate,String mid,
			String bid,String oid,String amount,String type,String gate,String author_type,String sys_date,String init_sys_date,
			String sys_time,String fee_amt,String bank_fee,String tstat,String mobile_no,String phone_no,String card_no,
			BankInst bankInst,InstInfo instInfo,String pre_amt,String pre_amt1,String bk_fee_model,String pay_amt,String currency,String exchange_rate,String out_user_id,
			String in_user_id,String bind_mid,MerBasic merBasic) throws Exception;
	
	/**
	 * 更新完成交易数据状态
	 * @param tseq ：流水号
	 * @param gid ：渠道ID
	 * @param tstat ：交易状态
	 * @param bk_flag ：收到银行交易应答标志
	 * @param bk_date ：bk_date
	 * @param bk_seq1 ：银行流水1
	 * @param bk_seq2 ：银行流水1
	 * @param bk_resp ：银行返回代码
	 * @param error_msg ：错误信息
	 * @return boolean
	 * @throws Exception
	 */
	public boolean updateSynOkTrade(String tseq,String gid,String tstat,String bk_flag,String bk_date,String bk_seq1,
			String bk_seq2,String bk_resp,String error_msg,BankInst bankInst) throws Exception;
	
	/**
	 * 根据流水查询交易表数据总数
	 * @param tseq : 流水号
	 * @param oringinalTable : 原始交易表
	 * @return int
	 */
	public int queryOringinalTableCount(Long tseq,String oringinalTable);
	
	/**
	 * 线上表数据查询
	 * @param tableName ：表名称(hlog or 渠道表)
	 * @param obj : column,value,column,value
	 * @return object[]
	 */
	public Object[] queryOnlineTableData(String tableName,Object ... obj);
}