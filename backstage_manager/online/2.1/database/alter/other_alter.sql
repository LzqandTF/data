alter table inst_reconciliation_columns
add column inst_type int(1) NOT NULL;

alter table inst_reconciliation_columns
drop primary key; 

alter table inst_reconciliation_columns
add primary key(inst_id,inst_type); 

alter table error_data_lst
add COLUMN inst_type int(1) NOT NULL;

alter table merchant_settle_statistics 
add column data_status int(11) DEFAULT '0';

alter table merchant_settle_statistics 
add column inst_type int(1) NOT NULL;

alter table timing_task_conf 
add column inst_type int(1) NOT NULL;

alter table mer_fund_stance 
add column inst_type int(1) NOT NULL;

alter table execute_node 
add column inst_type int(1) NOT NULL;

alter table ylcups_error_entry 
add column inst_type int(1) NOT NULL;

alter table error_audit_records 
add column inst_type int(1) DEFAULT '0';

alter table hlog
add column mer_fee double(8,2) DEFAULT '0.00';

alter table duizhang_upmp_lst
add column whetherTk tinyint(1) DEFAULT '0';

alter table inst_info
add COLUMN start_row int(11) DEFAULT '0';

alter table inst_info
add COLUMN gate int(11) DEFAULT '0';

alter table refund_log
drop COLUMN bk_chk;

alter table refund_log
drop COLUMN whetherJs;

alter table refund_log
drop COLUMN whetherErroeHandle;

alter table refund_log
drop COLUMN whetherRiqie;

alter table refund_log
drop COLUMN whetherQs;

alter table refund_log
drop COLUMN tk_mer_fee;

alter table mer_billing 
MODIFY COLUMN bil_bankaccount varchar(100) DEFAULT NULL;

alter table merchant_fund_settle
add column rec_amount_add varchar(12) DEFAULT '0';

alter table merchant_fund_settle
add column rec_amount_add_count int(11) DEFAULT NULL;

alter table merchant_fund_settle
add column rec_amount_sub varchar(12) DEFAULT '0';

alter table merchant_fund_settle
add column rec_amount_sub_count int(11) DEFAULT NULL;

alter table mer_billing 
add column last_liq_date int(11) DEFAULT '0';

alter table inst_mer_rate_conf
add column gid int(11) NOT NULL;

alter table inst_mer_rate_conf 
add column g_type int(1) NOT NULL;

alter table merchant_settle_statistics 
add column whetherJs tinyint(11) DEFAULT '0';
