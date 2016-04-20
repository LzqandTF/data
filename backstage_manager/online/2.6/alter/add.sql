alter table channel_dz_collect
add COLUMN remark VARCHAR(32);


alter table merchant_settle_statistics
add COLUMN trade_gc_count int(11) default 0;


alter table login
add COLUMN chineseName varchar(64) DEFAULT NULL;