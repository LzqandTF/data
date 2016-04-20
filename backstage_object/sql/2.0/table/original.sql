alter table creditcardpay_trade_lst
add column ic_card_ser_no char(3) DEFAULT NULL;
alter table creditcardpay_trade_lst
add column ic_read_and_condition varchar(4) DEFAULT NULL;
alter table creditcardpay_trade_lst
add column whetherQs tinyint(1) DEFAULT '0';
alter table creditcardpay_trade_lst
add column mer_fee double(8,2) DEFAULT '0.00';
alter table creditcardpay_trade_lst
add column whetherTk tinyint(1) DEFAULT '0';
alter table creditcardpay_trade_lst
add column zf_fee double(8,2) DEFAULT '0.00';


alter table original_beijingbank_lst
add column ic_card_ser_no char(3) DEFAULT NULL;
alter table original_beijingbank_lst
add column ic_read_and_condition varchar(4) DEFAULT NULL;
alter table original_beijingbank_lst
add column whetherQs tinyint(1) DEFAULT '0';
alter table original_beijingbank_lst
add column mer_fee double(8,2) DEFAULT '0.00';
alter table original_beijingbank_lst
add column whetherTk tinyint(1) DEFAULT '0';
alter table original_beijingbank_lst
add column zf_fee double(8,2) DEFAULT '0.00';


alter table original_cups_lst
add column ic_card_ser_no char(3) DEFAULT NULL;
alter table original_cups_lst
add column ic_read_and_condition varchar(4) DEFAULT NULL;
alter table original_cups_lst
add column whetherQs tinyint(1) DEFAULT '0';
alter table original_cups_lst
add column mer_fee double(8,2) DEFAULT '0.00';
alter table original_cups_lst
add column whetherTk tinyint(1) DEFAULT '0';
alter table original_cups_lst
add column zf_fee double(8,2) DEFAULT '0.00';


alter table original_shengjingbank_lst
add column ic_card_ser_no char(3) DEFAULT NULL;
alter table original_shengjingbank_lst
add column ic_read_and_condition varchar(4) DEFAULT NULL;
alter table original_shengjingbank_lst
add column whetherQs tinyint(1) DEFAULT '0';
alter table original_shengjingbank_lst
add column mer_fee double(8,2) DEFAULT '0.00';
alter table original_shengjingbank_lst
add column whetherTk tinyint(1) DEFAULT '0';
alter table original_shengjingbank_lst
add column zf_fee double(8,2) DEFAULT '0.00';


alter table original_zhongxingbank_lst
add column ic_card_ser_no char(3) DEFAULT NULL;
alter table original_zhongxingbank_lst
add column ic_read_and_condition varchar(4) DEFAULT NULL;
alter table original_zhongxingbank_lst
add column whetherQs tinyint(1) DEFAULT '0';
alter table original_zhongxingbank_lst
add column mer_fee double(8,2) DEFAULT '0.00';
alter table original_zhongxingbank_lst
add column whetherTk tinyint(1) DEFAULT '0';
alter table original_zhongxingbank_lst
add column zf_fee double(8,2) DEFAULT '0.00';


alter table riqie_beijingbank_lst
add column ic_card_ser_no char(3) DEFAULT NULL;
alter table riqie_beijingbank_lst
add column ic_read_and_condition varchar(4) DEFAULT NULL;
alter table riqie_beijingbank_lst
add column whetherQs tinyint(1) DEFAULT '0';
alter table riqie_beijingbank_lst
add column mer_fee double(8,2) DEFAULT '0.00';
alter table riqie_beijingbank_lst
add column whetherTk tinyint(1) DEFAULT '0';
alter table riqie_beijingbank_lst
add column zf_fee double(8,2) DEFAULT '0.00';


alter table riqie_cups_lst
add column ic_card_ser_no char(3) DEFAULT NULL;
alter table riqie_cups_lst
add column ic_read_and_condition varchar(4) DEFAULT NULL;
alter table riqie_cups_lst
add column whetherQs tinyint(1) DEFAULT '0';
alter table riqie_cups_lst
add column mer_fee double(8,2) DEFAULT '0.00';
alter table riqie_cups_lst
add column whetherTk tinyint(1) DEFAULT '0';
alter table riqie_cups_lst
add column zf_fee double(8,2) DEFAULT '0.00';


alter table riqie_zhongxingbank_lst
add column ic_card_ser_no char(3) DEFAULT NULL;
alter table riqie_zhongxingbank_lst
add column ic_read_and_condition varchar(4) DEFAULT NULL;
alter table riqie_zhongxingbank_lst
add column whetherQs tinyint(1) DEFAULT '0';
alter table riqie_zhongxingbank_lst
add column mer_fee double(8,2) DEFAULT '0.00';
alter table riqie_zhongxingbank_lst
add column whetherTk tinyint(1) DEFAULT '0';
alter table riqie_zhongxingbank_lst
add column zf_fee double(8,2) DEFAULT '0.00';



update creditcardpay_trade_lst set zf_fee = 0,mer_fee = 0;
update original_beijingbank_lst set zf_fee = 0,mer_fee = 0;
update original_cups_lst set zf_fee = 0,mer_fee = 0;
update original_shengjingbank_lst set zf_fee = 0,mer_fee = 0;
update original_zhongxingbank_lst set zf_fee = 0,mer_fee = 0;
update riqie_beijingbank_lst set zf_fee = 0,mer_fee = 0;
update riqie_cups_lst set zf_fee = 0,mer_fee = 0;
update riqie_zhongxingbank_lst set zf_fee = 0,mer_fee = 0;