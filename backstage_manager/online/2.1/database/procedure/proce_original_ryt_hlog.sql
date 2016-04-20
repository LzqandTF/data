DROP PROCEDURE
IF EXISTS proce_original_ryt_hlog;
CREATE PROCEDURE `proce_original_ryt_hlog`(
	in tradeTime VARCHAR(20),
	in tableName VARCHAR(60),
	in deduct_sys_id int,
	out count int
)
BEGIN
	set @insetSql = concat("insert ignore into ",tableName,"(
	tseq,
  version,
  ip,
  mdate,
  mid,
  bid,
  oid,
  amount,
  pay_amt,
  type,
  gate,
  sys_date,
  init_sys_date,
  sys_time,
  batch,
  fee_amt,
  bank_fee,
  tstat,
  bk_flag,
  org_seq,
  ref_seq,
  refund_amt,
  mer_priv,
  bk_send,
  bk_recv,
  bk_url,
  fg_url,
  bk_chk,
  bk_date,
  bk_seq1,
  bk_seq2,
  bk_resp,
  mobile_no,
  trans_period,
  card_no,
  error_code,
  author_type,
  phone_no,
  oper_id,
  gid,
  pre_amt,
  bk_fee_model,
  pre_amt1,
  error_msg,
  p1,
  p2,
  p3,
  p4,
  p5,
  p6,
  p7,
  p8,
  p9,
  p10,
  p11,
  p12,
	p13,
	p14,
	p15,
  is_liq,
  is_notice,
  data_source,
  currency,
  exchange_rate,
	againPay_date,
  againPay_status,
	mer_fee)
	select 
		t.tseq,
		t.version,
		t.ip,
		t.mdate,
		t.mid,
		t.bid,
		t.oid,
		t.amount,
		t.pay_amt,
		t.type,
		t.gate,
		t.sys_date,
		t.init_sys_date,
		t.sys_time,
		t.batch,
		t.fee_amt,
		t.bank_fee,
		t.tstat,
		t.bk_flag,
		t.org_seq,
		t.ref_seq,
		t.refund_amt,
		t.mer_priv,
		t.bk_send,
		t.bk_recv,
		t.bk_url,
		t.fg_url,
		t.bk_chk,
		t.bk_date,
		t.bk_seq1,
		t.bk_seq2,
		t.bk_resp,
		t.mobile_no,
		t.trans_period,
		t.card_no,
		t.error_code,
		t.author_type,
		t.phone_no,
		t.oper_id,
		t.gid,
		t.pre_amt,
		t.bk_fee_model,
		t.pre_amt1,
		t.error_msg,
		t.p1,
		t.p2,
		t.p3,
		t.p4,
		t.p5,
		t.p6,
		t.p7,
		t.p8,
		t.p9,
		t.p10,
		t.p11,
		t.p12,
		t.p13,
		t.p14,
		t.p15,
		t.is_liq,
		t.is_notice,
		t.data_source,
		t.currency,
		t.exchange_rate,
		t.againPay_date,
		t.againPay_status,
		t.mer_fee
		from hlog t where t.sys_date = '",tradeTime,"' and t.gid = ",deduct_sys_id,";");
	PREPARE insetSql from @insetSql;
	EXECUTE insetSql;
	COMMIT;

	set @selectSql = concat("SELECT count(*) INTO @selectCount FROM ",tableName," WHERE sys_date = '",tradeTime,"' and gid = ",deduct_sys_id,";");
	PREPARE selectSql from @selectSql;
	EXECUTE selectSql;
	select @selectCount into count;

	/*
			bk_flag ：收到银行交易应答标志
					0 : 初始状态(未收到应答)
					1 : 正常收到应答
			tstat   ：交易状态
					0 ：初始状态
					1 ：待支付
					2 ：成功
					3 ：失败
					4 ：请求银行失败
					5 ：撤销
	*/
	set @updateSql = concat("update ",tableName," set whetherValid = 1 where tstat in(1,2) and sys_date = '",tradeTime,"';");
	PREPARE updateSql from @updateSql;
	EXECUTE updateSql;
	COMMIT;
	
END