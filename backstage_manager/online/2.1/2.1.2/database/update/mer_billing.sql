-- 结算信息添加结算支行名称字段
alter table mer_billing
add column `bank_branch` varchar(60) DEFAULT NULL;