drop table error_tk_lst;
CREATE TABLE `error_tk_lst` (
  `trade_id` varchar(32) NOT NULL,
  `trade_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `trade_amount` bigint(20) DEFAULT NULL,
  `deduct_sys_id` int(11) DEFAULT NULL,
  `inst_type` int(11) NOT NULL,
  `handling_id` int(11) not null,
  `bank_id` int(11) NOT NULL,
  `syn_flag` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`trade_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;