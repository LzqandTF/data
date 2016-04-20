DROP TABLE IF EXISTS `settle_merchant_config`;
CREATE TABLE `settle_merchant_config` (
  `settle_mer_code` varchar(40) NOT NULL,
  `settle_mer_name` varchar(128) NOT NULL,
  `operate_time` varchar(20) NOT NULL,
  `user_id` varchar(20) NOT NULL,
  PRIMARY KEY (`settle_mer_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

