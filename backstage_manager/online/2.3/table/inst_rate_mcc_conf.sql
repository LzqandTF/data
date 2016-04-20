CREATE TABLE `inst_rate_mcc_conf` (
  `inst_id` int(11) NOT NULL,
  `inst_type` int(1) NOT NULL,
  `whether_return_fee` tinyint(1) NOT NULL,
  `mcc_b_type` int(11) NOT NULL,
  `mcc_s_type` int(11) NOT NULL,
  PRIMARY KEY (`inst_id`,`inst_type`,`mcc_b_type`,`mcc_s_type`),
  CONSTRAINT `FK_TABLE_31_2` FOREIGN KEY (`inst_id`, `inst_type`) REFERENCES `inst_rate` (`inst_id`, `inst_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
