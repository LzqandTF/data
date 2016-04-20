-- 是否分账 默认无需分账
alter table merchant_fund_settle
add column whtherFz tinyint(1) DEFAULT '0';

-- 账户系统同步结果 
alter table merchant_fund_settle
add column syn_result int(11) DEFAULT '0';

-- 账户系统同步时间
alter table merchant_fund_settle
add column syn_date int(11) DEFAULT '0';