DROP TABLE IF EXISTS `settle_merchant_match_table`;
CREATE TABLE `settle_merchant_match_table` (
  `id` varchar(32) NOT NULL,
  `dy_mer_code` varchar(40) NOT NULL,
  `settle_mer_code` varchar(40) NOT NULL,
  `user_id` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `settle_mer_code` (`settle_mer_code`),
  CONSTRAINT `FK_settle_merchant_match_table_1` FOREIGN KEY (`settle_mer_code`) REFERENCES `settle_merchant_config` (`settle_mer_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

