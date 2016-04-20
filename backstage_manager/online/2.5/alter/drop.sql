drop table inst_reconciliation_columns;
drop table dz_column_conf;

-- 线上原始交易数据表不需要字段删除
alter table hlog
drop COLUMN ip;
alter table hlog
drop COLUMN bk_url;
alter table hlog
drop COLUMN fg_url;

alter table ryt_bjyh
drop COLUMN ip;
alter table ryt_bjyh
drop COLUMN bk_url;
alter table ryt_bjyh
drop COLUMN fg_url;

alter table ryt_cups_sjzf
drop COLUMN ip;
alter table ryt_cups_sjzf
drop COLUMN bk_url;
alter table ryt_cups_sjzf
drop COLUMN fg_url;

alter table ryt_gdyh
drop COLUMN ip;
alter table ryt_gdyh
drop COLUMN bk_url;
alter table ryt_gdyh
drop COLUMN fg_url;

alter table ryt_gfyh
drop COLUMN ip;
alter table ryt_gfyh
drop COLUMN bk_url;
alter table ryt_gfyh
drop COLUMN fg_url;

alter table ryt_gonghang
drop COLUMN ip;
alter table ryt_gonghang
drop COLUMN bk_url;
alter table ryt_gonghang
drop COLUMN fg_url;

alter table ryt_hebei_yh
drop COLUMN ip;
alter table ryt_hebei_yh
drop COLUMN bk_url;
alter table ryt_hebei_yh
drop COLUMN fg_url;

alter table ryt_hengfeng_yh
drop COLUMN ip;
alter table ryt_hengfeng_yh
drop COLUMN bk_url;
alter table ryt_hengfeng_yh
drop COLUMN fg_url;

alter table ryt_jh
drop COLUMN ip;
alter table ryt_jh
drop COLUMN bk_url;
alter table ryt_jh
drop COLUMN fg_url;

alter table ryt_jiangsu_yh
drop COLUMN ip;
alter table ryt_jiangsu_yh
drop COLUMN bk_url;
alter table ryt_jiangsu_yh
drop COLUMN fg_url;

alter table ryt_jsyh
drop COLUMN ip;
alter table ryt_jsyh
drop COLUMN bk_url;
alter table ryt_jsyh
drop COLUMN fg_url;

alter table ryt_kuaiqian
drop COLUMN ip;
alter table ryt_kuaiqian
drop COLUMN bk_url;
alter table ryt_kuaiqian
drop COLUMN fg_url;

alter table ryt_msyh
drop COLUMN ip;
alter table ryt_msyh
drop COLUMN bk_url;
alter table ryt_msyh
drop COLUMN fg_url;

alter table ryt_nh
drop COLUMN ip;
alter table ryt_nh
drop COLUMN bk_url;
alter table ryt_nh
drop COLUMN fg_url;

alter table ryt_pufa
drop COLUMN ip;
alter table ryt_pufa
drop COLUMN bk_url;
alter table ryt_pufa
drop COLUMN fg_url;

alter table ryt_qilu_yh
drop COLUMN ip;
alter table ryt_qilu_yh
drop COLUMN bk_url;
alter table ryt_qilu_yh
drop COLUMN fg_url;

alter table ryt_qingdao_yh
drop COLUMN ip;
alter table ryt_qingdao_yh
drop COLUMN bk_url;
alter table ryt_qingdao_yh
drop COLUMN fg_url;

alter table ryt_shyh
drop COLUMN ip;
alter table ryt_shyh
drop COLUMN bk_url;
alter table ryt_shyh
drop COLUMN fg_url;

alter table ryt_sjyh
drop COLUMN ip;
alter table ryt_sjyh
drop COLUMN bk_url;
alter table ryt_sjyh
drop COLUMN fg_url;

alter table ryt_upmp
drop COLUMN ip;
alter table ryt_upmp
drop COLUMN bk_url;
alter table ryt_upmp
drop COLUMN fg_url;

alter table ryt_xyyh
drop COLUMN ip;
alter table ryt_xyyh
drop COLUMN bk_url;
alter table ryt_xyyh
drop COLUMN fg_url;

alter table ryt_yl
drop COLUMN ip;
alter table ryt_yl
drop COLUMN bk_url;
alter table ryt_yl
drop COLUMN fg_url;

alter table ryt_yunnan_nxs
drop COLUMN ip;
alter table ryt_yunnan_nxs
drop COLUMN bk_url;
alter table ryt_yunnan_nxs
drop COLUMN fg_url;

alter table ryt_yzcx
drop COLUMN ip;
alter table ryt_yzcx
drop COLUMN bk_url;
alter table ryt_yzcx
drop COLUMN fg_url;

alter table ryt_zfb
drop COLUMN ip;
alter table ryt_zfb
drop COLUMN bk_url;
alter table ryt_zfb
drop COLUMN fg_url;

alter table ryt_zgyh
drop COLUMN ip;
alter table ryt_zgyh
drop COLUMN bk_url;
alter table ryt_zgyh
drop COLUMN fg_url;

alter table ryt_zgyh_ryt
drop COLUMN ip;
alter table ryt_zgyh_ryt
drop COLUMN bk_url;
alter table ryt_zgyh_ryt
drop COLUMN fg_url;

alter table ryt_zhaohang
drop COLUMN ip;
alter table ryt_zhaohang
drop COLUMN bk_url;
alter table ryt_zhaohang
drop COLUMN fg_url;

alter table ryt_zheshang_yh
drop COLUMN ip;
alter table ryt_zheshang_yh
drop COLUMN bk_url;
alter table ryt_zheshang_yh
drop COLUMN fg_url;

alter table ryt_zhxt
drop COLUMN ip;
alter table ryt_zhxt
drop COLUMN bk_url;
alter table ryt_zhxt
drop COLUMN fg_url;

alter table ryt_zxyh
drop COLUMN ip;
alter table ryt_zxyh
drop COLUMN bk_url;
alter table ryt_zxyh
drop COLUMN fg_url;

alter table merchant_fund_settle
drop column df_result;