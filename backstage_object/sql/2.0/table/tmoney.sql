CREATE TABLE `tmoney` (
  `id` varchar(32) NOT NULL,
  `_name` varchar(64) NOT NULL,
  `total_money` varchar(20) NOT NULL,
  `settle_way` int(11) NOT NULL,
  `deduct_stlm_date` int(11) NOT NULL DEFAULT '0',
  `mer_code` varchar(32) NOT NULL DEFAULT '',
  `bil_account` varchar(50) DEFAULT NULL,
  `bil_accountname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`deduct_stlm_date`,`mer_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

