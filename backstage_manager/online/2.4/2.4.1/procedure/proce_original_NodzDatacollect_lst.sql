DROP PROCEDURE
IF EXISTS proce_original_NodzDatacollect_lst;

/**
	汇总所有不需要外部对账渠道数据
	目前:华势、盛京银行
	华势：
		CALL proce_original_NodzDatacollect_lst("2014-08-11","creditcardpay_trade_lst",6,6,@count);
    select @count;
  盛京银行：
    CALL proce_original_NodzDatacollect_lst("2014-11-05","original_shengjingbank_lst",8,12,@count);
    select @count;
*/
CREATE PROCEDURE proce_original_NodzDatacollect_lst(
	in tradeTime VARCHAR(20),
	in tableName VARCHAR(50),
	in bankGate int,
  in deduct_sys_id int,
  out count int
)
BEGIN
  DECLARE deduct_sys_id_str VARCHAR(30) DEFAULT "deduct_sys_id";
  if deduct_sys_id = 12 THEN
		set deduct_sys_id_str = "gain_sys_id";
	end if;

	set @insetSql = concat("INSERT ignore INTO ",tableName,"(
	trade_id,
	terminal_id,
	terminal_info,
	terminal_type,
	trade_time,
	out_account,
	out_account_type,
	out_acc_valid_time,
	out_account_info,
	out_account_info2,
	out_account_desc,
	in_account,
	in_card_name,
	in_bank_id,
	trade_amount,
	trade_fee,
	trade_currency,
	trade_result,
	req_sys_id,
	req_sys_stance,
	req_mer_code,
	req_mer_term_id,
	req_response,
	deduct_sys_id,
	deduct_sys_stance,
	deduct_mer_code,
	deduct_mer_term_id,
	deduct_result,
	deduct_sys_response,
	deduct_roll_bk,
	deduct_roll_bk_result,
	deduct_roll_bk_reason,
	deduct_roll_bk_response,
	deduct_roll_bk_stance,
	trade_type,
	msg_num,
	pass_wd_mode,
	req_type,
	req_input_type,
	req_time,
	trade_req_method,
	trade_desc,
	in_account_type,
	trade_other_info,
	deduct_stlm_date,
	deduct_sys_time,
	gain_sys_id,
	gain_sys_stance,
	gain_mer_code,
	gain_mer_term_id,
	gain_sys_response,
	gain_result,
	gain_trade_amount,
	gain_sys_reference,
	nii,
	authorization_code,
	additional_response_data,
	additional_data,
	boc,
	custom_define_info,
	original_trans_info,
	req_process,
	deduct_sys_reference,
	trademsg_type,
	deduct_rollbk_sys_reference,
	mer_name,
	acqInstIdCode,
	fwdInstIdCode,
	deduct_rollbk_sys_time,
	agentId,
	whetherzero,
	ic_card_ser_no,
	ic_read_and_condition,
	fee_formula,bank_id) SELECT
		t.trade_id,
	t.terminal_id,
	t.terminal_info,
	t.terminal_type,
	t.trade_time,
	t.out_account,
	t.out_account_type,
	t.out_acc_valid_time,
	t.out_account_info,
	t.out_account_info2,
	t.out_account_desc,
	t.in_account,
	t.in_card_name,
	t.in_bank_id,
	t.trade_amount,
	t.trade_fee,
	t.trade_currency,
	t.trade_result,
	t.req_sys_id,
	t.req_sys_stance,
	t.req_mer_code,
	t.req_mer_term_id,
	t.req_response,
	t.deduct_sys_id,
	t.deduct_sys_stance,
	t.deduct_mer_code,
	t.deduct_mer_term_id,
	t.deduct_result,
	t.deduct_sys_response,
	t.deduct_roll_bk,
	t.deduct_roll_bk_result,
	t.deduct_roll_bk_reason,
	t.deduct_roll_bk_response,
	t.deduct_roll_bk_stance,
	t.trade_type,
	t.msg_num,
	t.pass_wd_mode,
	t.req_type,
	t.req_input_type,
	t.req_time,
	t.trade_req_method,
	t.trade_desc,
	t.in_account_type,
	t.trade_other_info,
	t.deduct_stlm_date,
	t.deduct_sys_time,
	t.gain_sys_id,
	t.gain_sys_stance,
	t.gain_mer_code,
	t.gain_mer_term_id,
	t.gain_sys_response,
	t.gain_result,
	t.gain_trade_amount,
	t.gain_sys_reference,
	t.nii,
	t.authorization_code,
	t.additional_response_data,
	t.additional_data,
	t.boc,
	t.custom_define_info,
	t.original_trans_info,
	t.req_process,
	t.deduct_sys_reference,
	t.trademsg_type,
	t.deduct_rollbk_sys_reference,
	t.mer_name,
	t.acqInstIdCode,
	t.fwdInstIdCode,
	t.deduct_rollbk_sys_time,
	t.agentId,
	t.whetherzero,
	t.ic_card_ser_no,
  t.ic_read_and_condition,
	t.fee_formula,
	",bankGate,"
	FROM
		trade_lst t
	WHERE
		t.trade_time BETWEEN DATE_FORMAT('",tradeTime," 00:00:00','%Y-%m-%d %T') and DATE_FORMAT('",tradeTime," 23:59:59','%Y-%m-%d %T') and ",deduct_sys_id_str," = ",deduct_sys_id,";");

	PREPARE insetSql from @insetSql;
	EXECUTE insetSql;
	COMMIT;

  set @selectSql = concat("SELECT count(*) INTO @selectCount FROM ",tableName," WHERE trade_time BETWEEN DATE_FORMAT('",tradeTime," 00:00:00','%Y-%m-%d %T') and DATE_FORMAT('",tradeTime," 23:59:59','%Y-%m-%d %T') and ",deduct_sys_id_str," = ",deduct_sys_id,";");
	PREPARE selectSql from @selectSql;
	EXECUTE selectSql;
	select @selectCount into count;
end
