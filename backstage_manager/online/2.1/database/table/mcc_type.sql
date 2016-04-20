DROP TABLE IF EXISTS `mcc_type`;
CREATE TABLE `mcc_type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(128) NOT NULL,
  `big_type_id` int(11) NOT NULL,
  PRIMARY KEY (`type_id`),
  KEY `big_type_id` (`big_type_id`),
  CONSTRAINT `FK_mcc_type_1` FOREIGN KEY (`big_type_id`) REFERENCES `mcc_big_type` (`big_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mcc_type
-- ----------------------------
INSERT INTO `mcc_type` VALUES ('1', '餐饮、宾馆、娱乐、珠宝金饰、工艺美术品类', '1');
INSERT INTO `mcc_type` VALUES ('2', '房产汽车类', '1');
INSERT INTO `mcc_type` VALUES ('3', '百货、中介、培训、景区门票等', '2');
INSERT INTO `mcc_type` VALUES ('4', '批发类商户', '2');
INSERT INTO `mcc_type` VALUES ('5', '加油、超市类', '3');
INSERT INTO `mcc_type` VALUES ('6', '交通运输售票', '3');
INSERT INTO `mcc_type` VALUES ('7', '水电气缴费', '3');
INSERT INTO `mcc_type` VALUES ('8', '政府类', '3');
INSERT INTO `mcc_type` VALUES ('9', '便民类', '3');
INSERT INTO `mcc_type` VALUES ('10', '公立医院、公立学校、慈善', '4');
INSERT INTO `mcc_type` VALUES ('11', '宾馆餐娱类', '5');
INSERT INTO `mcc_type` VALUES ('12', '房产汽车类', '5');
INSERT INTO `mcc_type` VALUES ('13', '批发类', '5');
INSERT INTO `mcc_type` VALUES ('14', '超市加油类', '5');
INSERT INTO `mcc_type` VALUES ('15', '一般类商户', '5');
INSERT INTO `mcc_type` VALUES ('16', '三农商户', '5');
INSERT INTO `mcc_type` VALUES ('17', '信用卡还款', '6');
