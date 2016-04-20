DROP TABLE IF EXISTS `duizhang_zfb_haihang_lst`;
CREATE TABLE `duizhang_zfb_haihang_lst` (
  `id` varchar(32) NOT NULL,
  `reqSysStance` varchar(32) DEFAULT NULL,
  `businessNo` varchar(32) DEFAULT NULL,
  `orderId` varchar(32) DEFAULT NULL,
  `merName` varchar(32) DEFAULT NULL,
  `reqTime` varchar(32) DEFAULT NULL,
  `partyAccount` varchar(200) DEFAULT NULL,
  `tradeAmount` varchar(32) DEFAULT NULL,
  `payAmount` varchar(23) DEFAULT NULL,
  `balanceAmount` varchar(32) DEFAULT NULL,
  `tradingChannel` varchar(200) DEFAULT NULL,
  `yw_type` varchar(200) DEFAULT NULL,
  `note` varchar(200) DEFAULT NULL,
  `dz_file_name` varchar(50) DEFAULT NULL,
  `inst_name` varchar(32) DEFAULT NULL,
  `bk_chk` int(11) DEFAULT NULL,
  `deduct_stlm_date` varchar(32) DEFAULT NULL,
  `whetherTk` tinyint(1) DEFAULT '0',
  `deductSysReference` varchar(32) DEFAULT NULL,
  `outAccount` varchar(32) DEFAULT NULL,
  `termId` varchar(32) DEFAULT NULL,
  `merCode` varchar(32) DEFAULT NULL,
  `process` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of duizhang_zfb_haihang_lst
-- ----------------------------
