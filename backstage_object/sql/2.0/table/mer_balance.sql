CREATE TABLE `mer_balance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mer_code` varchar(20) NOT NULL,
  `mer_category` int(11) NOT NULL,
  `mer_balance` varchar(40) NOT NULL,
  `mer_state` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `mer_code` (`mer_code`),
  CONSTRAINT `FK_mer_balance_1` FOREIGN KEY (`mer_code`) REFERENCES `mer_basic` (`mer_code`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;

