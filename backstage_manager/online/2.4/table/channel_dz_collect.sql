alter table channel_dz_collect
add COLUMN js_date int(11) DEFAULT 0;

alter table channel_dz_collect
drop index deduct_stlm_date_settle_code;

alter table channel_dz_collect
add index js_date_settle_code(js_date,settle_code);