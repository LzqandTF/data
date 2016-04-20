
alter table duizhang_yl_wap_lst RENAME duizhang_yl_lst;
alter table duizhang_zfb_haihang_lst RENAME duizhang_zfb_lst;
alter table duizhang_sh_bank_wszhifu_lst RENAME duizhang_shyh_lst;
alter table duizhang_jiaohang_vas_lst RENAME duizhang_jiaohang_lst;
alter table duizhang_gonghang_wap_lst RENAME duizhang_gonghang_lst;

alter table duizhang_cups_lst add column whetherTk tinyint(1) DEFAULT '0';
alter table duizhang_beijingbank_lst add column whetherTk tinyint(1) DEFAULT '0';
alter table duizhang_dljh_lst add column whetherTk tinyint(1) DEFAULT '0';
alter table duizhang_szgh_lst add column whetherTk tinyint(1) DEFAULT '0';
alter table duizhang_szzh_lst add column whetherTk tinyint(1) DEFAULT '0';
alter table duizhang_zhongxingbank_lst add column whetherTk tinyint(1) DEFAULT '0';

alter table duizhang_gonghang_lst add column tradeFee varchar(32) DEFAULT '0.00';
alter table duizhang_shyh_lst add column tradeFee varchar(32) default '0.00';
alter table duizhang_zfb_lst add column tradeFee varchar(32) default '0.00';
alter table duizhang_nonghang_lst add column tradeFee varchar(32) default '0.00';