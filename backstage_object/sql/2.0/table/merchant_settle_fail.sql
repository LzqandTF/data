CREATE TABLE `merchant_settle_fail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mer_code` varchar(32) NOT NULL,
  `mer_type` int(11) NOT NULL,
  `mer_name` varchar(64) NOT NULL,
  `last_settle_date` int(11) NOT NULL DEFAULT '0',
  `settle_start_date` int(11) NOT NULL DEFAULT '0',
  `reason_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `mer_code` (`mer_code`),
  KEY `reason_id` (`reason_id`),
  KEY `settle_start_date_index` (`settle_start_date`),
  KEY `last_settle_date_index` (`last_settle_date`),
  CONSTRAINT `FK_merchant_settle_fail_1` FOREIGN KEY (`mer_code`) REFERENCES `mer_basic` (`mer_code`),
  CONSTRAINT `FK_merchant_settle_fail_2` FOREIGN KEY (`reason_id`) REFERENCES `settle_fail_reason` (`reason_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

