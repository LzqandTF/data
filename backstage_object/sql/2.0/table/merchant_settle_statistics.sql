CREATE TABLE `merchant_settle_statistics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `inst_id` int(11) NOT NULL,
  `mer_code` varchar(32) NOT NULL,
  `mer_type` int(11) NOT NULL,
  `deduct_stlm_date` int(11) NOT NULL,
  `trade_amount` varchar(40) NOT NULL,
  `trade_count` int(11) NOT NULL,
  `refund_amount` varchar(40) NOT NULL,
  `refund_count` int(11) NOT NULL,
  `mer_fee` varchar(40) NOT NULL,
  `system_fee` varchar(40) NOT NULL,
  `mer_refund_fee` varchar(40) NOT NULL,
  `settle_amount` varchar(40) NOT NULL,
  `system_refund_fee` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `merchant_settle_statistics_index` (`mer_code`,`deduct_stlm_date`,`mer_type`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

