DROP TABLE IF EXISTS `settle_fail_reason`;
CREATE TABLE `settle_fail_reason` (
  `reason_id` int(11) NOT NULL AUTO_INCREMENT,
  `reason` varchar(128) NOT NULL,
  PRIMARY KEY (`reason_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

INSERT INTO `settle_fail_reason` VALUES ('1', '商户有交易未对账');
INSERT INTO `settle_fail_reason` VALUES ('2', '商户有差错数据未处理');
INSERT INTO `settle_fail_reason` VALUES ('3', '商户结算金额小于0');
INSERT INTO `settle_fail_reason` VALUES ('4', '商户结算金额不满足最低结算金额');
INSERT INTO `settle_fail_reason` VALUES ('5', '商户的合同已不在有效期内');
INSERT INTO `settle_fail_reason` VALUES ('6', '商户结算状态处于关闭状态');
INSERT INTO `settle_fail_reason` VALUES ('7', '其他原因');
