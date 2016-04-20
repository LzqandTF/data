DROP TABLE IF EXISTS `duizhang_zhaohang_lst`;
CREATE TABLE `duizhang_zhaohang_lst` (
  `id` varchar(32) NOT NULL,
  `reqSysStance` varchar(20) DEFAULT NULL,
  `reqTime` varchar(20) DEFAULT NULL,
  `orderId` varchar(20) DEFAULT NULL,
  `tradingType` varchar(20) DEFAULT NULL,
  `tradeAmount` varchar(20) DEFAULT NULL,
  `tradeFee` varchar(20) DEFAULT NULL,
  `bookedDate` varchar(20) DEFAULT NULL,
  `dz_file_name` varchar(20) DEFAULT NULL,
  `inst_name` varchar(20) DEFAULT NULL,
  `bk_chk` int(11) DEFAULT NULL,
  `deduct_stlm_date` varchar(20) DEFAULT NULL,
  `termId` varchar(20) DEFAULT NULL,
  `merCode` varchar(20) DEFAULT NULL,
  `process` varchar(20) DEFAULT NULL,
  `deductSysReference` varchar(20) DEFAULT NULL,
  `outAccount` varchar(20) DEFAULT NULL,
  `whetherTk` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `deduct_stlm_date_whetherTk_index` (`deduct_stlm_date`,`whetherTk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

