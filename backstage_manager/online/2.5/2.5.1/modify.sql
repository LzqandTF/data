alter table duizhang_upmp_lst
MODIFY COLUMN `tradeAmount` VARCHAR(12) NOT NULL;

alter table duizhang_beijingbank_lst
MODIFY COLUMN `tradeAmount` VARCHAR(12) NOT NULL;

alter table duizhang_szgh_lst
MODIFY COLUMN `tradeAmount` VARCHAR(12) NOT NULL;

alter table duizhang_szgh_lst
MODIFY COLUMN `tradeAmount` VARCHAR(12) NOT NULL;

alter table duizhang_cups_lfe_lst
MODIFY COLUMN `trade_amount` VARCHAR(20) DEFAULT NULL;

alter table duizhang_dljh_lst
MODIFY COLUMN `tradeAmount` VARCHAR(12) NOT NULL;

alter table duizhang_error_cups_lst
MODIFY COLUMN `tradeAccount` VARCHAR(12) NOT NULL;

alter table duizhang_error_upmp_lst
MODIFY COLUMN `tradeAccount` VARCHAR(12) NOT NULL;