CREATE TABLE `mer_billing` (
  `id` varchar(32) NOT NULL,
  `bil_object` int(11) DEFAULT NULL,
  `bil_bank` varchar(30) DEFAULT NULL,
  `bil_bankname` varchar(100) DEFAULT NULL,
  `bil_accountname` varchar(50) DEFAULT NULL,
  `bil_bankaccount` varchar(50) DEFAULT NULL,
  `bil_way` int(11) DEFAULT NULL,
  `bil_smallamt` varchar(20) DEFAULT NULL,
  `bil_cycle` int(20) DEFAULT NULL,
  `bil_manual` int(11) DEFAULT NULL,
  `bil_account` varchar(50) DEFAULT NULL,
  `bil_type` int(11) DEFAULT NULL,
  `mer_poundage` varchar(50) DEFAULT NULL,
  `mer_code` varchar(32) DEFAULT NULL,
  `bil_status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `mer_code` (`mer_code`),
  CONSTRAINT `FK_mer_billing_1` FOREIGN KEY (`mer_code`) REFERENCES `mer_basic` (`mer_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

