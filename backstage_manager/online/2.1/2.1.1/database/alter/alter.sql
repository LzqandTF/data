alter table merchant_settle_statistics
add column zf_fee varchar(40) DEFAULT '0';

alter table merchant_settle_statistics
add column refund_zf_fee varchar(40) DEFAULT '0';


