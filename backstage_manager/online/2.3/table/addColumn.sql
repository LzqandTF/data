alter table creditcardpay_trade_lst
add COLUMN whetherAccessStance tinyint(1) DEFAULT 0;

alter table original_shengjingbank_lst
add COLUMN whetherAccessStance tinyint(1) DEFAULT 0;

alter table original_cups_lst
add COLUMN whetherAccessStance tinyint(1) DEFAULT 0;

alter table original_beijingbank_lst
add COLUMN whetherAccessStance tinyint(1) DEFAULT 0;

alter table original_dljh_lst
add COLUMN whetherAccessStance tinyint(1) DEFAULT 0;

alter table original_szgh_lst
add COLUMN whetherAccessStance tinyint(1) DEFAULT 0;

alter table original_szzh_lst
add COLUMN whetherAccessStance tinyint(1) DEFAULT 0;

alter table original_zhongxingbank_lst
add COLUMN whetherAccessStance tinyint(1) DEFAULT 0;

alter table error_data_lst
add COLUMN whetherAccessStance tinyint(1) DEFAULT 0;

alter table riqie_cups_lst
add COLUMN whetherAccessStance tinyint(1) DEFAULT 0;

alter table riqie_beijingbank_lst
add COLUMN whetherAccessStance tinyint(1) DEFAULT 0;

alter table riqie_dljh_lst
add COLUMN whetherAccessStance tinyint(1) DEFAULT 0;

alter table riqie_szgh_lst
add COLUMN whetherAccessStance tinyint(1) DEFAULT 0;

alter table riqie_szzh_lst
add COLUMN whetherAccessStance tinyint(1) DEFAULT 0;

alter table riqie_zhongxingbank_lst
add COLUMN whetherAccessStance tinyint(1) DEFAULT 0;



-------------------------------------------------------------------------------------

ALTER table original_szgh_lst
add COLUMN `whtherInnerDz` tinyint(1) NOT NULL DEFAULT '0';

ALTER table riqie_szgh_lst
add COLUMN `whtherInnerDz` tinyint(1) NOT NULL DEFAULT '0';

ALTER table original_szzh_lst
add COLUMN `whtherInnerDz` tinyint(1) NOT NULL DEFAULT '0';

ALTER table riqie_szzh_lst
add COLUMN `whtherInnerDz` tinyint(1) NOT NULL DEFAULT '0';

ALTER table original_cups_lst
add COLUMN `whtherInnerDz` tinyint(1) NOT NULL DEFAULT '0';

ALTER table riqie_cups_lst
add COLUMN `whtherInnerDz` tinyint(1) NOT NULL DEFAULT '0';

ALTER table original_dljh_lst
add COLUMN `whtherInnerDz` tinyint(1) NOT NULL DEFAULT '0';

ALTER table riqie_dljh_lst
add COLUMN `whtherInnerDz` tinyint(1) NOT NULL DEFAULT '0';

ALTER table original_beijingbank_lst
add COLUMN `whtherInnerDz` tinyint(1) NOT NULL DEFAULT '0';

ALTER table riqie_beijingbank_lst
add COLUMN `whtherInnerDz` tinyint(1) NOT NULL DEFAULT '0';

ALTER table creditcardpay_trade_lst
add COLUMN `whtherInnerDz` tinyint(1) NOT NULL DEFAULT '0';

ALTER table original_shengjingbank_lst
add COLUMN `whtherInnerDz` tinyint(1) NOT NULL DEFAULT '0';

ALTER table error_data_lst
add COLUMN `whtherInnerDz` tinyint(1) NOT NULL DEFAULT '0';


------------------------------------------------------------------------------------------

alter table mer_fund_stance
add column stance_time VARCHAR(30) not null;

alter table error_data_lst
add COLUMN double_check_status int(1) DEFAULT 0;

alter table error_data_lst
add COLUMN cdz_remark varchar(64) DEFAULT null;

-------------------------------------------------------------------------------------------

alter table ryt_cups_sjzf
add COLUMN deduct_stlm_date int(11) DEFAULT 0;

alter table ryt_gonghang_wap
add COLUMN deduct_stlm_date int(11) DEFAULT 0;

alter table ryt_jhvas
add COLUMN deduct_stlm_date int(11) DEFAULT 0;

alter table ryt_kuaiqian
add COLUMN deduct_stlm_date int(11) DEFAULT 0;

alter table ryt_kuaiqian_gh
add COLUMN deduct_stlm_date int(11) DEFAULT 0;

alter table ryt_kuaiqian_hh
add COLUMN deduct_stlm_date int(11) DEFAULT 0;

alter table ryt_pufa_wap
add COLUMN deduct_stlm_date int(11) DEFAULT 0;

alter table ryt_sh_bank_wszhifu
add COLUMN deduct_stlm_date int(11) DEFAULT 0;

alter table ryt_upmp
add COLUMN deduct_stlm_date int(11) DEFAULT 0;

alter table ryt_yl_wap
add COLUMN deduct_stlm_date int(11) DEFAULT 0;

alter table ryt_zfb_haihang
add COLUMN deduct_stlm_date int(11) DEFAULT 0;

alter table ryt_zhaohang_wap
add COLUMN deduct_stlm_date int(11) DEFAULT 0;

alter table ryt_zonghang_wap
add COLUMN deduct_stlm_date int(11) DEFAULT 0;

-------------------------------------------------------------

alter TABLE ryt_cups_sjzf
add COLUMN whetherAccessStance tinyint(1) DEFAULT '0';

alter TABLE ryt_gonghang_wap
add COLUMN whetherAccessStance tinyint(1) DEFAULT '0';

alter TABLE ryt_jhvas
add COLUMN whetherAccessStance tinyint(1) DEFAULT '0';

alter TABLE ryt_kuaiqian
add COLUMN whetherAccessStance tinyint(1) DEFAULT '0';

alter TABLE ryt_kuaiqian_gh
add COLUMN whetherAccessStance tinyint(1) DEFAULT '0';

alter TABLE ryt_kuaiqian_hh
add COLUMN whetherAccessStance tinyint(1) DEFAULT '0';

alter TABLE ryt_pufa_wap
add COLUMN whetherAccessStance tinyint(1) DEFAULT '0';

alter TABLE ryt_sh_bank_wszhifu
add COLUMN whetherAccessStance tinyint(1) DEFAULT '0';

alter TABLE ryt_upmp
add COLUMN whetherAccessStance tinyint(1) DEFAULT '0';

alter TABLE ryt_yl_wap
add COLUMN whetherAccessStance tinyint(1) DEFAULT '0';

alter TABLE ryt_zfb_haihang
add COLUMN whetherAccessStance tinyint(1) DEFAULT '0';

alter TABLE ryt_zhaohang_wap
add COLUMN whetherAccessStance tinyint(1) DEFAULT '0';

alter TABLE ryt_zonghang_wap
add COLUMN whetherAccessStance tinyint(1) DEFAULT '0';
