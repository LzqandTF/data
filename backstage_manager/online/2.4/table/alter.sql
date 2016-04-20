-- 线下
alter table original_beijingbank_lst
add COLUMN bank_id int(11) NOT NULL;

alter table riqie_beijingbank_lst
add COLUMN bank_id int(11) NOT NULL;

alter table original_cups_lst
add COLUMN bank_id int(11) NOT NULL;

alter table riqie_cups_lst
add COLUMN bank_id int(11) NOT NULL;

alter table original_dljh_lst
add COLUMN bank_id int(11) NOT NULL;

alter table riqie_dljh_lst
add COLUMN bank_id int(11) NOT NULL;

alter table original_shengjingbank_lst
add COLUMN bank_id int(11) NOT NULL;

alter table creditcardpay_trade_lst
add COLUMN bank_id int(11) NOT NULL;

alter table original_szgh_lst
add COLUMN bank_id int(11) NOT NULL;

alter table riqie_szgh_lst
add COLUMN bank_id int(11) NOT NULL;

alter table original_szzh_lst
add COLUMN bank_id int(11) NOT NULL;

alter table riqie_szzh_lst
add COLUMN bank_id int(11) NOT NULL;

alter table original_zhongxingbank_lst
add COLUMN bank_id int(11) NOT NULL;

alter table riqie_zhongxingbank_lst
add COLUMN bank_id int(11) NOT NULL;

alter table error_data_lst
add COLUMN bank_id int(11) NOT NULL;

-- 线上
ALTER table ryt_cups_sjzf
add COLUMN bank_id int(11) NOT NULL;

ALTER table ryt_gonghang
add COLUMN bank_id int(11) NOT NULL;

ALTER table ryt_jh
add COLUMN bank_id int(11) NOT NULL;

ALTER table ryt_kuaiqian
add COLUMN bank_id int(11) NOT NULL;

ALTER table ryt_pufa
add COLUMN bank_id int(11) NOT NULL;

ALTER table ryt_shyh
add COLUMN bank_id int(11) NOT NULL;

ALTER table ryt_upmp
add COLUMN bank_id int(11) NOT NULL;

ALTER table ryt_yl
add COLUMN bank_id int(11) NOT NULL;

ALTER table ryt_zfb
add COLUMN bank_id int(11) NOT NULL;

ALTER table ryt_zhaohang
add COLUMN bank_id int(11) NOT NULL;

ALTER table ryt_zgyh
add COLUMN bank_id int(11) NOT NULL;


-- 结算相关
ALTER table mer_fund_stance
add COLUMN bank_id int(11) NOT NULL;

ALTER table merchant_settle_statistics
add COLUMN bank_id int(11) NOT NULL;

-- 线上对账数据表添加订单号字段
ALTER table duizhang_kuaiqian_lst
add COLUMN orderId varchar(50) DEFAULT NULL;

ALTER table duizhang_upmp_lst
add COLUMN orderId varchar(50) DEFAULT NULL;

ALTER table duizhang_yl_shoujizhifu_lst
add COLUMN orderId varchar(50) DEFAULT NULL;

--线上对账数据表主键长度增加为100
alter table duizhang_upmp_lst
modify column `id` varchar(100) NOT NULL;

alter table duizhang_gonghang_lst
modify column `id` varchar(100) NOT NULL;

alter table duizhang_jiaohang_lst
modify column `id` varchar(100) NOT NULL;

alter table duizhang_kuaiqian_lst
modify column `id` varchar(100) NOT NULL;

alter table duizhang_pufa_bank_lst
modify column `id` varchar(100) NOT NULL;

alter table duizhang_shyh_lst
modify column `id` varchar(100) NOT NULL;

alter table duizhang_zfb_lst
modify column `id` varchar(100) NOT NULL;

alter table duizhang_zhaohang_lst
modify column `id` varchar(100) NOT NULL;

alter table duizhang_zgyh_lst
modify column `id` varchar(100) NOT NULL;

alter table duizhang_yl_lst
modify column `id` varchar(100) NOT NULL;

alter table duizhang_yl_shoujizhifu_lst
modify column `id` varchar(100) NOT NULL;


-- 对账单orderId添加
alter table duizhang_kuaiqian_lst
add COLUMN orderId VARCHAR(32) DEFAULT null;
alter table duizhang_yl_shoujizhifu_lst
add COLUMN orderId VARCHAR(32) DEFAULT null;
alter table duizhang_zgyh_lst
add COLUMN orderId VARCHAR(32) DEFAULT null;


-- 线上原始交易表添加deduct_stlm_date字段
alter table ryt_cups_sjzf
add COLUMN deduct_stlm_date int(11) DEFAULT '0';

alter table ryt_gonghang
drop COLUMN deduct_stlm_date int(11) DEFAULT '0';

alter table ryt_jh
drop COLUMN deduct_stlm_date int(11) DEFAULT '0';

alter table ryt_jsyh
drop COLUMN deduct_stlm_date int(11) DEFAULT '0'; 

alter table ryt_kuaiqian
drop COLUMN deduct_stlm_date int(11) DEFAULT '0';

alter table ryt_nh
drop COLUMN deduct_stlm_date int(11) DEFAULT '0'; 

alter table ryt_pufa
drop COLUMN deduct_stlm_date int(11) DEFAULT '0'; 

alter table ryt_shyh
drop COLUMN deduct_stlm_date int(11) DEFAULT '0';

alter table ryt_upmp
drop COLUMN deduct_stlm_date int(11) DEFAULT '0'; 

alter table ryt_xyyh
drop COLUMN deduct_stlm_date int(11) DEFAULT '0'; 

alter table ryt_yl
drop COLUMN deduct_stlm_date int(11) DEFAULT '0'; 

alter table ryt_yzcx
drop COLUMN deduct_stlm_date int(11) DEFAULT '0'; 

alter table ryt_zfb
drop COLUMN deduct_stlm_date int(11) DEFAULT '0';

alter table ryt_zgyh
drop COLUMN deduct_stlm_date int(11) DEFAULT '0';

alter table ryt_zgyh_ryt
drop COLUMN deduct_stlm_date int(11) DEFAULT '0';

alter table ryt_zhaohang
drop COLUMN deduct_stlm_date int(11) DEFAULT '0';

-- 线上原始交易表添加whtherInnerJs字段
alter table ryt_cups_sjzf
add COLUMN whtherInnerJs tinyint(1) DEFAULT '0';

alter table ryt_gonghang
add COLUMN whtherInnerJs tinyint(1) DEFAULT '0';

alter table ryt_jh
add COLUMN whtherInnerJs tinyint(1) DEFAULT '0';

alter table ryt_jsyh
add COLUMN whtherInnerJs tinyint(1) DEFAULT '0'; 

alter table ryt_kuaiqian
add COLUMN whtherInnerJs tinyint(1) DEFAULT '0';

alter table ryt_nh
add COLUMN whtherInnerJs tinyint(1) DEFAULT '0'; 

alter table ryt_pufa
add COLUMN whtherInnerJs tinyint(1) DEFAULT '0'; 

alter table ryt_shyh
add COLUMN whtherInnerJs tinyint(1) DEFAULT '0';

alter table ryt_upmp
add COLUMN whtherInnerJs tinyint(1) DEFAULT '0'; 

alter table ryt_xyyh
add COLUMN whtherInnerJs tinyint(1) DEFAULT '0'; 

alter table ryt_yl
add COLUMN whtherInnerJs tinyint(1) DEFAULT '0'; 

alter table ryt_yzcx
add COLUMN whtherInnerJs tinyint(1) DEFAULT '0'; 

alter table ryt_zfb
add COLUMN whtherInnerJs tinyint(1) DEFAULT '0';

alter table ryt_zgyh
add COLUMN whtherInnerJs tinyint(1) DEFAULT '0';

alter table ryt_zgyh_ryt
add COLUMN whtherInnerJs tinyint(1) DEFAULT '0';

alter table ryt_zhaohang
add COLUMN whtherInnerJs tinyint(1) DEFAULT '0';
