alter table mer_fund_stance
add index trade_time_index(trade_time);


alter table merchant_fund_settle
add index settle_date_index(settle_date);
alter table merchant_fund_settle
add index create_tab_date_index(create_tab_date);
alter table merchant_fund_settle
add index settle_way_index(settle_way);


alter table merchant_settle_fail
add index settle_start_date_index(settle_start_date);
alter table merchant_settle_fail
add index last_settle_date_index(last_settle_date);


alter table merchant_settle_statistics
add index merchant_settle_statistics_index(mer_code,deduct_stlm_date,mer_type);



ALTER TABLE `original_dljh_lst` ADD INDEX trade_time ( `trade_time` );
ALTER TABLE `original_dljh_lst` ADD INDEX deduct_stlm_date ( `deduct_stlm_date` );
ALTER TABLE `original_dljh_lst` ADD INDEX req_sys_stance ( `req_sys_stance` );
ALTER TABLE `original_dljh_lst` ADD INDEX deduct_sys_stance ( `deduct_sys_stance` );
ALTER TABLE `original_dljh_lst` ADD INDEX whetherValid ( `whetherValid` );
ALTER TABLE `original_dljh_lst` ADD INDEX bk_chk ( `bk_chk` );
ALTER TABLE `original_dljh_lst` ADD INDEX deduct_roll_bk ( `deduct_roll_bk` );
ALTER TABLE `original_dljh_lst` ADD INDEX req_mer_code ( `req_mer_code` );


ALTER TABLE `riqie_dljh_lst` ADD INDEX deduct_sys_time ( `deduct_sys_time` );
ALTER TABLE `riqie_dljh_lst` ADD INDEX whetherValid ( `whetherValid` );


ALTER TABLE `original_szzh_lst` ADD INDEX trade_time ( `trade_time` ) ;
ALTER TABLE `original_szzh_lst` ADD INDEX deduct_stlm_date ( `deduct_stlm_date` );
ALTER TABLE `original_szzh_lst` ADD INDEX req_sys_stance ( `req_sys_stance` );
ALTER TABLE `original_szzh_lst` ADD INDEX deduct_sys_stance ( `deduct_sys_stance` );
ALTER TABLE `original_szzh_lst` ADD INDEX whetherValid ( `whetherValid` ) ;
ALTER TABLE `original_szzh_lst` ADD INDEX bk_chk ( `bk_chk` );
ALTER TABLE `original_szzh_lst` ADD INDEX deduct_roll_bk ( `deduct_roll_bk` );
ALTER TABLE `original_szzh_lst` ADD INDEX req_mer_code ( `req_mer_code` );

ALTER TABLE `riqie_szzh_lst` ADD INDEX deduct_sys_time ( `deduct_sys_time` );
ALTER TABLE `riqie_szzh_lst` ADD INDEX whetherValid ( `whetherValid` );

ALTER TABLE `duizhang_szzh_lst` ADD INDEX deductSysReference (`deductSysReference`);
ALTER TABLE `duizhang_szzh_lst` ADD INDEX dz_index (deduct_stlm_date,deductSysReference);

ALTER TABLE `duizhang_dljh_lst` ADD INDEX dz_index (deduct_stlm_date,reqSysStance );
ALTER TABLE `duizhang_dljh_lst` ADD INDEX reqSysStance ( `reqSysStance` );

ALTER TABLE `duizhang_upmp_lst` ADD INDEX dz_index (deduct_stlm_date,deductSysReference );
ALTER TABLE `duizhang_upmp_lst` ADD INDEX deductSysReference ( `deductSysReference` );



