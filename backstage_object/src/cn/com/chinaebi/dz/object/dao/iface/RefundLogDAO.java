package cn.com.chinaebi.dz.object.dao.iface;




public interface RefundLogDAO {
	public cn.com.chinaebi.dz.object.RefundLog get(java.lang.Long key);

	public cn.com.chinaebi.dz.object.RefundLog load(java.lang.Long key);

	public java.util.List<cn.com.chinaebi.dz.object.RefundLog> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param refundLog a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.Long save(cn.com.chinaebi.dz.object.RefundLog refundLog);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param refundLog a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.RefundLog refundLog);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param refundLog a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.RefundLog refundLog);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.Long id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param refundLog the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.RefundLog refundLog);
	
	/**
	 * 汇总融易通线上退款的交易数据
	 * @param id
	 * @return
	 */
	public Object getRytRefundLog(String id);
	
	/**
	 * 修改退款交易数据的数据状态
	 * @param id
	 * @return
	 */
	public int updateRytRefundLogDataStatus(String id, int stat) throws Exception;
	
	/**
	 * 根据id获取退款交易的数据
	 * @param id
	 * @return
	 */
	public Object queryDataById(String id);
	
	/**
	 * 根据主键ID 查询数据 
	 * @param id ：主键ID
	 * @return count(*)
	 */
	public Integer getRefundLog(String id);
	
	/**
	 * 根据主键修改指定column值
	 * @param id ：主键ID
	 * @param obj ：数据格式 column,value,column,value
	 * @return boolean
	 * @throws Exception
	 */
	public boolean updateRytRefundLogColumn(String id,Object ... obj) throws Exception;
	
	/**
	 * 根据原交易流水,统计退款总金额(按分计算)
	 * @param tseq ：原流水号
	 * @return Long
	 * @throws Exception
	 */
	public Long queryOriTseqTkAmt(String tseq) throws Exception;
	
	/**
	 * 保存退款交易数据
	 * @param id ：退款主键ID
	 * @param tseq ：原交易流水
	 * @param author_type ：原始交易发起类型
	 * @param mdate ：商户申请退款日期
	 * @param mid ：商户号
	 * @param oid ：退款单号
	 * @param org_mdate ：原始商户交易日期
	 * @param org_oid ：原支付订单号
	 * @param ref_amt ：退款金额
	 * @param sys_date ：原交易系统日期
	 * @param gate ：支付网关号
	 * @param card_no ：持卡人卡号
	 * @param usr_name ：持卡人名
	 * @param req_date ：退款请求日期（商户确认退款日期）
	 * @param pro_date ：退款经办日期
	 * @param ref_date ：退款审核日期 
	 * @param vstate ：审核状态
	 * @param stat ：退款状态
	 * @param reason ：退款失败原因
	 * @param etro_reason ：撤销退款原因
	 * @param refund_reason ：请求退款原因
	 * @param mer_fee ：退回给商户的手续费
	 * @param bk_fee ：银行退回的手续费
	 * @param bk_fee_real ：实收银行退回的手续费
	 * @param org_amt ：原交易金额
	 * @param org_bk_seq ：原交易的银行流水号
	 * @param gid ：支付渠道
	 * @param org_pay_amt ：原实际交易金额
	 * @param pre_amt ：差价
	 * @param pre_amt1 ：优惠金额
	 * @param mer_priv ：商户的私有数据项
	 * @param p1 ：预留字段
	 * @param sys_time ：原交易时间
	 * @return Integer
	 * @throws Exception
	 */
	public Long addRefundLog(
			String tseq,
			int author_type,
			String mdate,
			String mid,
			String oid,
			int org_mdate,
			String org_oid,
			Long ref_amt,
			int sys_date,
			int gate,
			String card_no,
			String usr_name,
			int req_date,
			int vstate,
			int stat,
			String refund_reason,
			int mer_fee,
			int bk_fee,
			int bk_fee_real,
			Long org_amt,
			String org_bk_seq,
			int gid,
			Long org_pay_amt,
			int pre_amt,
			int pre_amt1,
			String mer_priv,
			String p1,
			int sys_time,
			int refund_type) throws Exception;
}