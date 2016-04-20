alter table original_beijingbank_lst
add COLUMN zf_fee_bj int(1) default 0;

alter table riqie_beijingbank_lst
add COLUMN zf_fee_bj int(1) default 0;

alter table original_cups_lst
add COLUMN zf_fee_bj int(1) default 0;

alter table riqie_cups_lst
add COLUMN zf_fee_bj int(1) default 0;

alter table original_dljh_lst
add COLUMN zf_fee_bj int(1) default 0;

alter table riqie_dljh_lst
add COLUMN zf_fee_bj int(1) default 0;

alter table original_szzh_lst
add COLUMN zf_fee_bj int(1) default 0;

alter table riqie_szzh_lst
add COLUMN zf_fee_bj int(1) default 0;

alter table creditcardpay_trade_lst
add COLUMN zf_file_fee varchar(12) DEFAULT '0';

alter table creditcardpay_trade_lst
add COLUMN zf_fee_bj int(1) default 0;

alter table original_shengjingbank_lst
add COLUMN zf_file_fee varchar(12) DEFAULT '0';

alter table original_shengjingbank_lst
add COLUMN zf_fee_bj int(1) default 0;

alter table original_zhongxingbank_lst
add COLUMN zf_file_fee varchar(12) DEFAULT '0';

alter table original_zhongxingbank_lst
add COLUMN zf_fee_bj int(1) default 0;

alter table riqie_zhongxingbank_lst
add COLUMN zf_file_fee varchar(12) DEFAULT '0';

alter table riqie_zhongxingbank_lst
add COLUMN zf_fee_bj int(1) default 0;

alter table error_data_lst
add COLUMN zf_fee_bj int(1) default 0;




ALTER table original_beijingbank_lst
add COLUMN fee_formula varchar(25) DEFAULT NULL;
ALTER table riqie_beijingbank_lst
add COLUMN fee_formula varchar(25) DEFAULT NULL;

ALTER table original_cups_lst
add COLUMN fee_formula varchar(25) DEFAULT NULL;
ALTER table riqie_cups_lst
add COLUMN fee_formula varchar(25) DEFAULT NULL;

ALTER table original_dljh_lst
add COLUMN fee_formula varchar(25) DEFAULT NULL;
ALTER table riqie_dljh_lst
add COLUMN fee_formula varchar(25) DEFAULT NULL;

ALTER table original_szzh_lst
add COLUMN fee_formula varchar(25) DEFAULT NULL;
ALTER table riqie_szzh_lst
add COLUMN fee_formula varchar(25) DEFAULT NULL;

ALTER table original_zhongxingbank_lst
add COLUMN fee_formula varchar(25) DEFAULT NULL;
ALTER table riqie_zhongxingbank_lst
add COLUMN fee_formula varchar(25) DEFAULT NULL;

ALTER table original_shengjingbank_lst
add COLUMN fee_formula varchar(25) DEFAULT NULL;
ALTER table creditcardpay_trade_lst
add COLUMN fee_formula varchar(25) DEFAULT NULL;
ALTER table error_data_lst
add COLUMN fee_formula varchar(25) DEFAULT NULL;
















