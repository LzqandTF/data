alter table duizhang_yl_wap_lst
add index deduct_stlm_date_whethertk_index(deduct_stlm_date,whetherTk);
alter table duizhang_yl_wap_lst
add index reqSysStance_index(reqSysStance);

alter table duizhang_gonghang_wap_lst
add index deduct_stlm_date_whethertk_index(deduct_stlm_date,whetherTk);
alter table duizhang_gonghang_wap_lst
add index reqSysStance_index(reqSysStance);

alter table duizhang_jiaohang_vas_lst
add index deduct_stlm_date_whethertk_index(deduct_stlm_date,whetherTk);
alter table duizhang_jiaohang_vas_lst
add index reqSysStance_index(reqSysStance);

alter table duizhang_kuaiqian_gh_lst
add index deduct_stlm_date_whethertk_index(deduct_stlm_date,whetherTk);
alter table duizhang_kuaiqian_gh_lst
add index reqSysStance_index(reqSysStance);

alter table duizhang_kuaiqian_hh_lst
add index deduct_stlm_date_whethertk_index(deduct_stlm_date,whetherTk);
alter table duizhang_kuaiqian_hh_lst
add index reqSysStance_index(reqSysStance);

alter table duizhang_kuaiqian_lst
add index deduct_stlm_date_whethertk_index(deduct_stlm_date,whetherTk);
alter table duizhang_kuaiqian_lst
add index reqSysStance_index(reqSysStance);

alter table duizhang_pufa_bank_lst
add index deduct_stlm_date_whethertk_index(deduct_stlm_date,whetherTk);
alter table duizhang_pufa_bank_lst
add index reqSysStance_index(reqSysStance);


alter table duizhang_sh_bank_wszhifu_lst
add index deduct_stlm_date_whethertk_index(deduct_stlm_date,whetherTk);
alter table duizhang_sh_bank_wszhifu_lst
add index reqSysStance_index(reqSysStance);

alter table duizhang_yl_shoujizhifu_lst
add index deduct_stlm_date_whethertk_index(deduct_stlm_date,whetherTk);
alter table duizhang_yl_shoujizhifu_lst
add index reqSysStance_index(reqSysStance);

alter table duizhang_zfb_haihang_lst
add index deduct_stlm_date_whethertk_index(deduct_stlm_date,whetherTk);
alter table duizhang_zfb_haihang_lst
add index reqSysStance_index(reqSysStance);

alter table duizhang_zhaohang_lst
add index deduct_stlm_date_whethertk_index(deduct_stlm_date,whetherTk);
alter table duizhang_zhaohang_lst
add index reqSysStance_index(reqSysStance);

alter table duizhang_zhonghang_wap_lst
add index deduct_stlm_date_whethertk_index(deduct_stlm_date,whetherTk);
alter table duizhang_zhonghang_wap_lst
add index reqSysStance_index(reqSysStance);

alter table merchant_settle_statistics
add index merchant_settle_statistics_index(mer_code,deduct_stlm_date,mer_type);
alter table merchant_settle_statistics
add index inst_id_inst_type_index (inst_id,inst_type);
alter table merchant_settle_statistics
add index deduct_stlm_date_index (deduct_stlm_date);

ALTER table mer_fund_stance
add index deduct_stlm_date_index(deduct_stlm_date);

alter table fee_calc_mode
add index fee_index(gate,mid,gid,state);