DROP TABLE IF EXISTS `duizhang_zhonghang_wap_lst`;
CREATE TABLE `duizhang_zhonghang_wap_lst` (
  `id` varchar(32) NOT NULL,
  `merCode` varchar(32) DEFAULT NULL,
  `orderId` varchar(23) DEFAULT NULL,
  `deductSysReference` varchar(32) DEFAULT NULL,
  `termId` varchar(32) DEFAULT NULL,
  `reqTime` varchar(32) DEFAULT NULL,
  `orderType` varchar(23) DEFAULT NULL,
  `tradeAmount` varchar(32) DEFAULT NULL,
  `cardType` varchar(23) DEFAULT NULL,
  `deductSysResponse` varchar(23) DEFAULT NULL,
  `keep` varchar(32) DEFAULT NULL,
  `dz_file_name` varchar(50) DEFAULT NULL,
  `inst_name` varchar(50) DEFAULT NULL,
  `bk_chk` int(11) DEFAULT NULL,
  `deduct_stlm_date` varchar(20) DEFAULT NULL,
  `whetherTk` tinyint(1) DEFAULT '0',
  `reqSysStance` varchar(32) DEFAULT NULL,
  `outAccount` varchar(32) DEFAULT NULL,
  `process` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `deduct_stlm_date_whetherTk_index` (`deduct_stlm_date`,`whetherTk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of duizhang_zhonghang_wap_lst
-- ----------------------------
