DROP TABLE IF EXISTS `duizhang_sh_bank_wszhifu_lst`;
CREATE TABLE `duizhang_sh_bank_wszhifu_lst` (
  `id` varchar(32) NOT NULL,
  `orderId` varchar(32) DEFAULT NULL,
  `cardMark` varchar(20) DEFAULT NULL,
  `currency` varchar(23) DEFAULT NULL,
  `tradeAmount` varchar(32) DEFAULT NULL,
  `tradingType` varchar(23) DEFAULT NULL,
  `tradingState` varchar(23) DEFAULT NULL,
  `reqTime` varchar(32) DEFAULT NULL,
  `tradingTime` varchar(20) DEFAULT NULL,
  `deduct_stlm_date` varchar(32) DEFAULT NULL,
  `deduct_stlm_results` varchar(23) DEFAULT NULL,
  `note` varchar(32) DEFAULT NULL,
  `dz_file_name` varchar(32) DEFAULT NULL,
  `inst_name` varchar(32) DEFAULT NULL,
  `bk_chk` int(11) DEFAULT NULL,
  `outAccount` varchar(32) DEFAULT NULL,
  `termId` varchar(32) DEFAULT NULL,
  `whetherTk` tinyint(1) DEFAULT '0',
  `reqSysStance` varchar(32) DEFAULT NULL,
  `deductSysReference` varchar(32) DEFAULT NULL,
  `merCode` varchar(32) DEFAULT NULL,
  `process` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `deduct_stlm_date_whetherTk_index` (`deduct_stlm_date`,`whetherTk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of duizhang_sh_bank_wszhifu_lst
-- ----------------------------
