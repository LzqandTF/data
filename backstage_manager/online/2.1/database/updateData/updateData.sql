update inst_reconciliation_columns set inst_type = 0;

update inst_reconciliation_columns set inst_type = 1 where inst_id = 55001;

update function_right set url = 'sysConfig/reconciliation_rules.jsp' where func_name = '对账规则配置' and level = 2 and parent_id = 8;

update settle_fail_reason set reason = '商户无可结算订单' where reason_id = 7;

INSERT INTO settle_fail_reason VALUES ('8', '商户结算金额大于商户余额');

INSERT INTO settle_fail_reason VALUES ('9', '保存商户结算信息失败');

update dz_file_column_conf set attribute_column = 'zfFileFee' where dz_column_id = 26;