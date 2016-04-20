alter table duizhang_gonghang_lst
drop index reqSysStance_index;
alter table duizhang_gonghang_lst
drop index deduct_stlm_date_whethertk_index;

alter table duizhang_jiaohang_lst
drop index reqSysStance_index;
alter table duizhang_jiaohang_lst
drop index deduct_stlm_date_whethertk_index;

alter table duizhang_kuaiqian_lst
drop index reqSysStance_index;
alter table duizhang_kuaiqian_lst
drop index deduct_stlm_date_whethertk_index;

alter table duizhang_pufa_bank_lst
drop index reqSysStance_index;
alter table duizhang_pufa_bank_lst
drop index deduct_stlm_date_whethertk_index;

alter table duizhang_shyh_lst
drop index reqSysStance_index;
alter table duizhang_shyh_lst
drop index deduct_stlm_date_whethertk_index;

alter table duizhang_upmp_lst
drop index deductSysReference_index;
alter table duizhang_upmp_lst
drop index deduct_stlm_date_whethertk_index;

alter table duizhang_yl_lst
drop index reqSysStance_index;
alter table duizhang_yl_lst
drop index deduct_stlm_date_whethertk_index;

alter table duizhang_yl_shoujizhifu_lst
drop index reqSysStance_index;
alter table duizhang_yl_shoujizhifu_lst
drop index deduct_stlm_date_whethertk_index;

alter table duizhang_zfb_lst
drop index reqSysStance_index;
alter table duizhang_zfb_lst
drop index deduct_stlm_date_whethertk_index;

alter table duizhang_zgyh_ryt_lst
drop index reqSysStance_index;
alter table duizhang_zgyh_ryt_lst
drop index deduct_stlm_date_whethertk_index;

alter table duizhang_zhaohang_lst
drop index reqSysStance_index;
alter table duizhang_zhaohang_lst
drop index deduct_stlm_date_whethertk_index;





alter table duizhang_gonghang_lst
add index deduct_stlm_date_bk_chk_index (deduct_stlm_date,bk_chk);
alter table duizhang_gonghang_lst
add index reqSysStance_index(reqSysStance);
alter table duizhang_gonghang_lst
add index orderId_index(orderId);

alter table duizhang_jh_lst
add index deduct_stlm_date_bk_chk_index (deduct_stlm_date,bk_chk);
alter table duizhang_jh_lst
add index reqSysStance_index(reqSysStance);
alter table duizhang_jh_lst
add index orderId_index(orderId);

alter table duizhang_jiaohang_lst
add index deduct_stlm_date_bk_chk_index (deduct_stlm_date,bk_chk);
alter table duizhang_jiaohang_lst
add index reqSysStance_index(reqSysStance);
alter table duizhang_jiaohang_lst
add index orderId_index(orderId);

alter table duizhang_jsyh_lst
add index deduct_stlm_date_bk_chk_index (deduct_stlm_date,bk_chk);
alter table duizhang_jsyh_lst
add index reqSysStance_index(reqSysStance);
alter table duizhang_jsyh_lst
add index orderId_index(orderId);

alter table duizhang_kuaiqian_lst
add index deduct_stlm_date_bk_chk_index (deduct_stlm_date,bk_chk);
alter table duizhang_kuaiqian_lst
add index reqSysStance_index(reqSysStance);
alter table duizhang_kuaiqian_lst
add index orderId_index(orderId);

alter table duizhang_nonghang_lst
add index deduct_stlm_date_bk_chk_index (deduct_stlm_date,bk_chk);
alter table duizhang_nonghang_lst
add index reqSysStance_index(reqSysStance);
alter table duizhang_nonghang_lst
add index orderId_index(orderId);

alter table duizhang_pufa_bank_lst
add index deduct_stlm_date_bk_chk_index (deduct_stlm_date,bk_chk);
alter table duizhang_pufa_bank_lst
add index reqSysStance_index(reqSysStance);
alter table duizhang_pufa_bank_lst
add index orderId_index(orderId);

alter table duizhang_shyh_lst
add index deduct_stlm_date_bk_chk_index (deduct_stlm_date,bk_chk);
alter table duizhang_shyh_lst
add index reqSysStance_index(reqSysStance);
alter table duizhang_shyh_lst
add index orderId_index(orderId);

alter table duizhang_upmp_lst
add index deduct_stlm_date_bk_chk_index (deduct_stlm_date,bk_chk);
alter table duizhang_upmp_lst
add index reqSysStance_index(reqSysStance);

alter table duizhang_xingye_lst
add index deduct_stlm_date_bk_chk_index (deduct_stlm_date,bk_chk);
alter table duizhang_xingye_lst
add index reqSysStance_index(reqSysStance);
alter table duizhang_xingye_lst
add index orderId_index(orderId);

alter table duizhang_yl_lst
add index deduct_stlm_date_bk_chk_index (deduct_stlm_date,bk_chk);
alter table duizhang_yl_lst
add index reqSysStance_index(reqSysStance);
alter table duizhang_yl_lst
add index orderId_index(orderId);

alter table duizhang_yl_shoujizhifu_lst
add index deduct_stlm_date_bk_chk_index (deduct_stlm_date,bk_chk);
alter table duizhang_yl_shoujizhifu_lst
add index reqSysStance_index(reqSysStance);
alter table duizhang_yl_shoujizhifu_lst
add index orderId_index(orderId);


alter table duizhang_yzcx_lst
add index deduct_stlm_date_bk_chk_index (deduct_stlm_date,bk_chk);
alter table duizhang_yzcx_lst
add index reqSysStance_index(reqSysStance);
alter table duizhang_yzcx_lst
add index orderId_index(orderId);

alter table duizhang_zfb_lst
add index deduct_stlm_date_bk_chk_index (deduct_stlm_date,bk_chk);
alter table duizhang_zfb_lst
add index reqSysStance_index(reqSysStance);
alter table duizhang_zfb_lst
add index orderId_index(orderId);

alter table duizhang_zgyh_lst
add index deduct_stlm_date_bk_chk_index (deduct_stlm_date,bk_chk);
alter table duizhang_zgyh_lst
add index reqSysStance_index(reqSysStance);
alter table duizhang_zgyh_lst
add index orderId_index(orderId);

alter table duizhang_zgyh_ryt_lst
add index deduct_stlm_date_bk_chk_index (deduct_stlm_date,bk_chk);
alter table duizhang_zgyh_ryt_lst
add index reqSysStance_index(reqSysStance);
alter table duizhang_zgyh_ryt_lst
add index orderId_index(orderId);

alter table duizhang_zhaohang_lst
add index deduct_stlm_date_bk_chk_index (deduct_stlm_date,bk_chk);
alter table duizhang_zhaohang_lst
add index reqSysStance_index(reqSysStance);
alter table duizhang_zhaohang_lst
add index orderId_index(orderId);



-----------------------------------------------
alter table ryt_cups_sjzf
add index deduct_stlm_date_index(deduct_stlm_date);

alter table ryt_gonghang
add index deduct_stlm_date_index(deduct_stlm_date);

alter table ryt_jh
add index deduct_stlm_date_index(deduct_stlm_date);

alter table ryt_jsyh
add index deduct_stlm_date_index(deduct_stlm_date);

alter table ryt_kuaiqian
add index deduct_stlm_date_index(deduct_stlm_date);

alter table ryt_nh
add index deduct_stlm_date_index(deduct_stlm_date);

alter table ryt_pufa
add index deduct_stlm_date_index(deduct_stlm_date);

alter table ryt_shyh
add index deduct_stlm_date_index(deduct_stlm_date);

alter table ryt_upmp
add index deduct_stlm_date_index(deduct_stlm_date);

alter table ryt_xyyh
add index deduct_stlm_date_index(deduct_stlm_date);

alter table ryt_yl
add index deduct_stlm_date_index(deduct_stlm_date);

alter table ryt_yzcx
add index deduct_stlm_date_index(deduct_stlm_date);

alter table ryt_zfb
add index deduct_stlm_date_index(deduct_stlm_date);

alter table ryt_zgyh
add index deduct_stlm_date_index(deduct_stlm_date);

alter table ryt_zgyh_ryt
add index deduct_stlm_date_index(deduct_stlm_date);

alter table ryt_zhaohang
add index deduct_stlm_date_index(deduct_stlm_date);