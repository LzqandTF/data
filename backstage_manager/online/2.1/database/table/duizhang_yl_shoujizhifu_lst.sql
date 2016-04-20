DROP TABLE IF EXISTS `duizhang_yl_shoujizhifu_lst`;
CREATE TABLE `duizhang_yl_shoujizhifu_lst` (
  `id` varchar(32) NOT NULL,
  `deductMerTermId` varchar(20) DEFAULT NULL,
  `reqTime` varchar(20) DEFAULT NULL,
  `outAccount` varchar(50) DEFAULT NULL,
  `cardIssuers` varchar(20) DEFAULT NULL,
  `tradeAmount` varchar(20) DEFAULT NULL,
  `tradeFee` varchar(20) DEFAULT NULL,
  `settlementAmount` varchar(20) DEFAULT NULL,
  `deductSysReference` varchar(50) DEFAULT NULL,
  `systemtrackingNo` varchar(50) DEFAULT NULL,
  `tradingChannel` varchar(50) DEFAULT NULL,
  `tradingType` varchar(20) DEFAULT NULL,
  `reqSysStance` varchar(50) DEFAULT NULL,
  `dz_file_name` varchar(50) DEFAULT NULL,
  `inst_name` varchar(20) DEFAULT NULL,
  `bk_chk` int(11) DEFAULT NULL,
  `deduct_stlm_date` varchar(20) DEFAULT NULL,
  `termId` varchar(20) DEFAULT NULL,
  `merCode` varchar(20) DEFAULT NULL,
  `process` varchar(20) DEFAULT NULL,
  `whetherTk` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `deduct_stlm_date_whetherTk_index` (`deduct_stlm_date`,`whetherTk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of duizhang_yl_shoujizhifu_lst
-- ----------------------------
