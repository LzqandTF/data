-- 295 324标记为退还
ALTER table mer_billing
add COLUMN refund_fee int DEFAULT 0;