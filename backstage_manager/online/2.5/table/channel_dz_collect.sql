-- 出账用户ID
alter table channel_dz_collect
add column out_user_id varchar(32);

-- 入账用户ID
alter table channel_dz_collect
add column in_user_id varchar(32);

-- 快捷卡绑定商户号
alter table channel_dz_collect
add column bind_mid varchar(20);