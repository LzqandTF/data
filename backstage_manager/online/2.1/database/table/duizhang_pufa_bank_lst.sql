DROP TABLE IF EXISTS `duizhang_pufa_bank_lst`;
CREATE TABLE `duizhang_pufa_bank_lst` (
  `id` varchar(32) NOT NULL,
  `orderAbbreviations` varchar(32) DEFAULT NULL,
  `deduct_stlm_date` varchar(23) DEFAULT NULL,
  `reqTime` varchar(23) DEFAULT NULL,
  `orderId` varchar(23) DEFAULT NULL,
  `reqSysStance` varchar(23) DEFAULT NULL,
  `merCode` varchar(23) DEFAULT NULL,
  `deductSysReference` varchar(32) DEFAULT NULL,
  `tradeAmount` varchar(32) DEFAULT NULL,
  `tradeFee` varchar(23) DEFAULT NULL,
  `settlementAmount` varchar(32) DEFAULT NULL,
  `deductSysResponse` varchar(32) DEFAULT NULL,
  `keep1` varchar(32) DEFAULT NULL,
  `keep2` varchar(32) DEFAULT NULL,
  `dz_file_name` varchar(50) DEFAULT NULL,
  `inst_name` varchar(50) DEFAULT NULL,
  `bk_chk` int(11) DEFAULT NULL,
  `whetherTk` tinyint(1) NOT NULL DEFAULT '0',
  `outAccount` varchar(32) DEFAULT NULL,
  `termId` varchar(32) DEFAULT NULL,
  `process` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `deduct_stlm_date_whetherTk_index` (`deduct_stlm_date`,`whetherTk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of duizhang_pufa_bank_lst
-- ----------------------------
