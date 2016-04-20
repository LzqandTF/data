CREATE TABLE `merchant_fund_settle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mer_code` varchar(32) NOT NULL,
  `mer_type` int(11) NOT NULL,
  `settle_type` int(11) NOT NULL,
  `sys_batch_no` varchar(32) NOT NULL,
  `mer_batch_no` varchar(32) NOT NULL,
  `start_date` int(11) NOT NULL,
  `end_date` int(11) NOT NULL,
  `create_tab_date` int(11) NOT NULL,
  `mer_name` varchar(64) NOT NULL,
  `trade_amount` varchar(40) NOT NULL,
  `trade_count` int(11) NOT NULL,
  `refund_amount` varchar(40) NOT NULL,
  `refund_count` int(11) NOT NULL,
  `system_fee` varchar(40) NOT NULL,
  `refund_mer_fee` varchar(40) NOT NULL,
  `open_bank_name` varchar(64) NOT NULL,
  `open_acount_name` varchar(64) NOT NULL,
  `open_account_code` varchar(64) NOT NULL,
  `settle_amount` varchar(64) NOT NULL,
  `df_result` int(11) DEFAULT '0',
  `settle_way` int(11) NOT NULL,
  `settle_state` int(11) NOT NULL,
  `settle_date` int(11) NOT NULL,
  `settle_confirm_date` int(11) NOT NULL,
  `bil_manual` int(11) NOT NULL,
  `mer_fee` varchar(64) NOT NULL,
  `system_refund_fee` varchar(64) NOT NULL,
  `bil_bank` varchar(30) DEFAULT NULL,
  `error_msg` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `settle_date_index` (`settle_state`),
  KEY `settle_way_index` (`settle_way`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

