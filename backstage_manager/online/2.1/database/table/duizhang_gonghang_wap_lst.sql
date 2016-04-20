DROP TABLE IF EXISTS `duizhang_gonghang_wap_lst`;
CREATE TABLE `duizhang_gonghang_wap_lst` (
  `id` varchar(32) NOT NULL,
  `outAccount` varchar(32) DEFAULT NULL,
  `orderId` varchar(32) DEFAULT NULL,
  `reqSysStance` varchar(32) DEFAULT NULL,
  `tradeAmount` varchar(32) DEFAULT NULL,
  `reqTime` varchar(32) DEFAULT NULL,
  `tradingType` varchar(32) DEFAULT NULL,
  `returnType` varchar(32) DEFAULT NULL,
  `countRefundAmount` varchar(32) DEFAULT NULL,
  `dz_file_name` varchar(32) DEFAULT NULL,
  `inst_name` varchar(32) DEFAULT NULL,
  `bk_chk` int(11) DEFAULT NULL,
  `deduct_stlm_date` varchar(32) DEFAULT NULL,
  `whetherTk` tinyint(1) DEFAULT '0',
  `deductSysReference` varchar(32) DEFAULT NULL,
  `termId` varchar(32) DEFAULT NULL,
  `merCode` varchar(32) DEFAULT NULL,
  `process` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `deduct_stlm_date_whetherTk_index` (`deduct_stlm_date`,`whetherTk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of duizhang_gonghang_wap_lst
-- ----------------------------
