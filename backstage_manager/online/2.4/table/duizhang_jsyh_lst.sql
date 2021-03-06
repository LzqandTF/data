CREATE TABLE `duizhang_jsyh_lst` (
  `id` varchar(100) NOT NULL,
  `orderId` varchar(50) DEFAULT NULL,
  `reqSysStance` varchar(50) DEFAULT NULL,
  `reqTime` varchar(20) DEFAULT NULL,
  `tradeAmount` varchar(50) DEFAULT NULL,
  `refundAmount` varchar(50) DEFAULT NULL,
  `tradeFee` varchar(20) DEFAULT '0.00',
  `bk_chk` int(11) DEFAULT '0',
  `deduct_stlm_date` varchar(20) DEFAULT NULL,
  `whetherTk` tinyint(1) DEFAULT NULL,
  `outAccount` varchar(50) DEFAULT NULL,
  `counter_no` varchar(20) DEFAULT NULL,
  `remark1` varchar(100) DEFAULT NULL,
  `remark2` varchar(100) DEFAULT NULL,
  `trading_status` varchar(20) DEFAULT NULL,
  `trading_type` varchar(20) DEFAULT NULL,
  `dz_file_name` varchar(100) DEFAULT NULL,
  `inst_name` varchar(20) DEFAULT NULL,
  `deductSysReference` varchar(20) DEFAULT NULL,
  `termId` varchar(20) DEFAULT NULL,
  `merCode` varchar(20) DEFAULT NULL,
  `process` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

