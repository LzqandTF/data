-- 是否分账 默认无需分账
alter table mer_billing
add column whtherFz tinyint(1) DEFAULT '0';


