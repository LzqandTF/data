DROP TABLE IF EXISTS `inst_rate`;
CREATE TABLE `inst_rate` (
  `inst_id` int(11) NOT NULL,
  `inst_type` int(1) NOT NULL,
  `whetherReturnFee` tinyint(1) NOT NULL,
  `inst_rate_type` int(1) NOT NULL,
  `inst_rate_mcc` int(1) DEFAULT NULL,
  `bank_inst_code` varchar(128) NOT NULL,
  `user_name` varchar(24) NOT NULL,
  `inst_name` varchar(64) NOT NULL,
  PRIMARY KEY (`inst_id`,`inst_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

