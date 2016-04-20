DROP TABLE IF EXISTS `manual_rec`;
CREATE TABLE `manual_rec` (
  `id` varchar(32) NOT NULL,
  `mer_code` varchar(32) NOT NULL,
  `mer_abbreviation` varchar(64) DEFAULT NULL,
  `rec_amount` varchar(12) NOT NULL,
  `mer_balance` varchar(40) NOT NULL,
  `addorsub` int(11) NOT NULL,
  `handler_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `settle_time` int(11) DEFAULT NULL,
  `audit_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `send_user_name` varchar(20) NOT NULL,
  `auditor_user_name` varchar(20) DEFAULT NULL,
  `data_status` int(11) DEFAULT '1',
  `request_desc` varchar(64) NOT NULL,
  `audit_desc` varchar(100) DEFAULT NULL,
  `whetherJs` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `mer_code` (`mer_code`),
  CONSTRAINT `FK_manual_rec_1` FOREIGN KEY (`mer_code`) REFERENCES `mer_basic` (`mer_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

