alter table duizhang_szzh_lst
MODIFY column batch_no VARCHAR(6) DEFAULT NULL;

alter table duizhang_szzh_lst
MODIFY column outAccount VARCHAR(19) DEFAULT NULL;

alter table duizhang_szzh_lst
MODIFY column tradeAmount VARCHAR(12) DEFAULT NULL;

alter table duizhang_szzh_lst
MODIFY column settleAmount VARCHAR(12) DEFAULT NULL;

alter table duizhang_szzh_lst
MODIFY column trade_code VARCHAR(4) DEFAULT NULL;

alter table duizhang_szzh_lst
MODIFY column stage VARCHAR(4) DEFAULT NULL;

alter table duizhang_szzh_lst
MODIFY column card_category VARCHAR(4) DEFAULT NULL;

alter table duizhang_szzh_lst
MODIFY column process VARCHAR(6) DEFAULT NULL;