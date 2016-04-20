DROP TABLE IF EXISTS `inst_mer_rate_conf`;
CREATE TABLE `inst_mer_rate_conf` (
  `mer_code` varchar(15) NOT NULL,
  `card_type` varchar(3) NOT NULL,
  `fee_Poundage` varchar(64) NOT NULL,
  `lineOrinter` int(1) NOT NULL,
  `inst_id` int(11) NOT NULL,
  `inst_type` int(1) NOT NULL,
  `user_name` varchar(24) NOT NULL,
  PRIMARY KEY (`mer_code`,`card_type`,`lineOrinter`),
  KEY `inst_id` (`inst_id`,`inst_type`),
  CONSTRAINT `FK_TABLE_31_1` FOREIGN KEY (`inst_id`, `inst_type`) REFERENCES `inst_rate` (`inst_id`, `inst_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


