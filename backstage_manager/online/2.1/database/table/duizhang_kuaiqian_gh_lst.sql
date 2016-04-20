DROP TABLE IF EXISTS `duizhang_kuaiqian_gh_lst`;
CREATE TABLE `duizhang_kuaiqian_gh_lst` (
  `id` varchar(32) NOT NULL,
  `tradeNo` varchar(64) DEFAULT NULL,
  `merName` varchar(50) DEFAULT NULL,
  `term_paper_no` varchar(50) DEFAULT '0',
  `term_name` varchar(50) DEFAULT '0',
  `outAccount` varchar(50) DEFAULT NULL,
  `institutions_no` varchar(50) DEFAULT NULL,
  `tradeAmount` varchar(50) DEFAULT NULL,
  `tradeFee` varchar(20) DEFAULT NULL,
  `reqTime` varchar(20) DEFAULT NULL,
  `term_req_time` varchar(20) DEFAULT NULL,
  `reqSysStance` varchar(50) DEFAULT NULL,
  `card_type` varchar(20) DEFAULT NULL,
  `trading_status` varchar(20) DEFAULT NULL,
  `trading_type` varchar(20) DEFAULT NULL,
  `the_model` varchar(50) DEFAULT NULL,
  `dz_file_name` varchar(100) DEFAULT NULL,
  `inst_name` varchar(20) DEFAULT NULL,
  `bk_chk` int(11) DEFAULT NULL,
  `deduct_stlm_date` varchar(20) DEFAULT NULL,
  `deductSysReference` varchar(20) DEFAULT NULL,
  `termId` varchar(20) DEFAULT NULL,
  `merCode` varchar(20) DEFAULT NULL,
  `process` varchar(20) DEFAULT NULL,
  `whetherTk` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `deduct_stlm_date_whetherTk_index` (`deduct_stlm_date`,`whetherTk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of duizhang_kuaiqian_gh_lst
-- ----------------------------
