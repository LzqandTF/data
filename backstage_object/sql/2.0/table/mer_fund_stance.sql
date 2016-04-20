CREATE TABLE `mer_fund_stance` (
  `id` varchar(32) NOT NULL,
  `mer_code` varchar(20) NOT NULL,
  `trade_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `trade_amount` double(20,2) NOT NULL DEFAULT '0.00',
  `mer_fee` double(20,2) NOT NULL DEFAULT '0.00',
  `change_amount` double(20,2) NOT NULL DEFAULT '0.00',
  `account_amount` double(20,2) NOT NULL DEFAULT '0.00',
  `trade_stance` varchar(32) NOT NULL,
  `derc_status` int(11) NOT NULL,
  `mer_state` int(11) NOT NULL,
  `mer_category` int(11) NOT NULL,
  `mer_name` varchar(64) NOT NULL,
  `inst_id` int(11) NOT NULL,
  `deduct_stlm_date` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `mer_code` (`mer_code`),
  KEY `trade_time_index` (`trade_time`),
  CONSTRAINT `FK_mer_fund_stance_1` FOREIGN KEY (`mer_code`) REFERENCES `mer_basic` (`mer_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

