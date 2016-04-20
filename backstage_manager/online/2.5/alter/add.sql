-- 线上原始交易表字段添加,出账用户id、入账用户id、快捷绑定卡号
alter table hlog
add COLUMN out_user_id VARCHAR(32);
alter table hlog
add COLUMN in_user_id VARCHAR(32);
alter table hlog
add COLUMN bind_mid VARCHAR(20);

alter table ryt_bjyh
add COLUMN out_user_id VARCHAR(32);
alter table ryt_bjyh
add COLUMN in_user_id VARCHAR(32);
alter table ryt_bjyh
add COLUMN bind_mid VARCHAR(20);

alter table ryt_cups_sjzf
add COLUMN out_user_id VARCHAR(32);
alter table ryt_cups_sjzf
add COLUMN in_user_id VARCHAR(32);
alter table ryt_cups_sjzf
add COLUMN bind_mid VARCHAR(20);

alter table ryt_gdyh
add COLUMN out_user_id VARCHAR(32);
alter table ryt_gdyh
add COLUMN in_user_id VARCHAR(32);
alter table ryt_gdyh
add COLUMN bind_mid VARCHAR(20);

alter table ryt_gfyh
add COLUMN out_user_id VARCHAR(32);
alter table ryt_gfyh
add COLUMN in_user_id VARCHAR(32);
alter table ryt_gfyh
add COLUMN bind_mid VARCHAR(20);

alter table ryt_gonghang
add COLUMN out_user_id VARCHAR(32);
alter table ryt_gonghang
add COLUMN in_user_id VARCHAR(32);
alter table ryt_gonghang
add COLUMN bind_mid VARCHAR(20);

alter table ryt_hebei_yh
add COLUMN out_user_id VARCHAR(32);
alter table ryt_hebei_yh
add COLUMN in_user_id VARCHAR(32);
alter table ryt_hebei_yh
add COLUMN bind_mid VARCHAR(20);

alter table ryt_hengfeng_yh
add COLUMN out_user_id VARCHAR(32);
alter table ryt_hengfeng_yh
add COLUMN in_user_id VARCHAR(32);
alter table ryt_hengfeng_yh
add COLUMN bind_mid VARCHAR(20);

alter table ryt_jh
add COLUMN out_user_id VARCHAR(32);
alter table ryt_jh
add COLUMN in_user_id VARCHAR(32);
alter table ryt_jh
add COLUMN bind_mid VARCHAR(20);

alter table ryt_jiangsu_yh
add COLUMN out_user_id VARCHAR(32);
alter table ryt_jiangsu_yh
add COLUMN in_user_id VARCHAR(32);
alter table ryt_jiangsu_yh
add COLUMN bind_mid VARCHAR(20);

alter table ryt_jsyh
add COLUMN out_user_id VARCHAR(32);
alter table ryt_jsyh
add COLUMN in_user_id VARCHAR(32);
alter table ryt_jsyh
add COLUMN bind_mid VARCHAR(20);

alter table ryt_kuaiqian
add COLUMN out_user_id VARCHAR(32);
alter table ryt_kuaiqian
add COLUMN in_user_id VARCHAR(32);
alter table ryt_kuaiqian
add COLUMN bind_mid VARCHAR(20);

alter table ryt_msyh
add COLUMN out_user_id VARCHAR(32);
alter table ryt_msyh
add COLUMN in_user_id VARCHAR(32);
alter table ryt_msyh
add COLUMN bind_mid VARCHAR(20);

alter table ryt_nh
add COLUMN out_user_id VARCHAR(32);
alter table ryt_nh
add COLUMN in_user_id VARCHAR(32);
alter table ryt_nh
add COLUMN bind_mid VARCHAR(20);

alter table ryt_pufa
add COLUMN out_user_id VARCHAR(32);
alter table ryt_pufa
add COLUMN in_user_id VARCHAR(32);
alter table ryt_pufa
add COLUMN bind_mid VARCHAR(20);

alter table ryt_qilu_yh
add COLUMN out_user_id VARCHAR(32);
alter table ryt_qilu_yh
add COLUMN in_user_id VARCHAR(32);
alter table ryt_qilu_yh
add COLUMN bind_mid VARCHAR(20);

alter table ryt_qingdao_yh
add COLUMN out_user_id VARCHAR(32);
alter table ryt_qingdao_yh
add COLUMN in_user_id VARCHAR(32);
alter table ryt_qingdao_yh
add COLUMN bind_mid VARCHAR(20);

alter table ryt_shyh
add COLUMN out_user_id VARCHAR(32);
alter table ryt_shyh
add COLUMN in_user_id VARCHAR(32);
alter table ryt_shyh
add COLUMN bind_mid VARCHAR(20);

alter table ryt_sjyh
add COLUMN out_user_id VARCHAR(32);
alter table ryt_sjyh
add COLUMN in_user_id VARCHAR(32);
alter table ryt_sjyh
add COLUMN bind_mid VARCHAR(20);

alter table ryt_upmp
add COLUMN out_user_id VARCHAR(32);
alter table ryt_upmp
add COLUMN in_user_id VARCHAR(32);
alter table ryt_upmp
add COLUMN bind_mid VARCHAR(20);

alter table ryt_xyyh
add COLUMN out_user_id VARCHAR(32);
alter table ryt_xyyh
add COLUMN in_user_id VARCHAR(32);
alter table ryt_xyyh
add COLUMN bind_mid VARCHAR(20);

alter table ryt_yl
add COLUMN out_user_id VARCHAR(32);
alter table ryt_yl
add COLUMN in_user_id VARCHAR(32);
alter table ryt_yl
add COLUMN bind_mid VARCHAR(20);

alter table ryt_yunnan_nxs
add COLUMN out_user_id VARCHAR(32);
alter table ryt_yunnan_nxs
add COLUMN in_user_id VARCHAR(32);
alter table ryt_yunnan_nxs
add COLUMN bind_mid VARCHAR(20);

alter table ryt_yzcx
add COLUMN out_user_id VARCHAR(32);
alter table ryt_yzcx
add COLUMN in_user_id VARCHAR(32);
alter table ryt_yzcx
add COLUMN bind_mid VARCHAR(20);

alter table ryt_zfb
add COLUMN out_user_id VARCHAR(32);
alter table ryt_zfb
add COLUMN in_user_id VARCHAR(32);
alter table ryt_zfb
add COLUMN bind_mid VARCHAR(20);

alter table ryt_zgyh
add COLUMN out_user_id VARCHAR(32);
alter table ryt_zgyh
add COLUMN in_user_id VARCHAR(32);
alter table ryt_zgyh
add COLUMN bind_mid VARCHAR(20);

alter table ryt_zgyh_ryt
add COLUMN out_user_id VARCHAR(32);
alter table ryt_zgyh_ryt
add COLUMN in_user_id VARCHAR(32);
alter table ryt_zgyh_ryt
add COLUMN bind_mid VARCHAR(20);

alter table ryt_zhaohang
add COLUMN out_user_id VARCHAR(32);
alter table ryt_zhaohang
add COLUMN in_user_id VARCHAR(32);
alter table ryt_zhaohang
add COLUMN bind_mid VARCHAR(20);

alter table ryt_zheshang_yh
add COLUMN out_user_id VARCHAR(32);
alter table ryt_zheshang_yh
add COLUMN in_user_id VARCHAR(32);
alter table ryt_zheshang_yh
add COLUMN bind_mid VARCHAR(20);

alter table ryt_zhxt
add COLUMN out_user_id VARCHAR(32);
alter table ryt_zhxt
add COLUMN in_user_id VARCHAR(32);
alter table ryt_zhxt
add COLUMN bind_mid VARCHAR(20);

alter table ryt_zxyh
add COLUMN out_user_id VARCHAR(32);
alter table ryt_zxyh
add COLUMN in_user_id VARCHAR(32);
alter table ryt_zxyh
add COLUMN bind_mid VARCHAR(20);

alter table channel_dz_collect
add COLUMN out_user_id VARCHAR(32);
alter table channel_dz_collect
add COLUMN in_user_id VARCHAR(32);
alter table channel_dz_collect
add COLUMN bind_mid VARCHAR(20);