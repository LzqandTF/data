CREATE TABLE `settle_fail_reason` (
  `reason_id` int(11) NOT NULL AUTO_INCREMENT,
  `reason` varchar(128) NOT NULL,
  PRIMARY KEY (`reason_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;