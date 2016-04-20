DROP TABLE IF EXISTS `mcc_big_type`;
CREATE TABLE `mcc_big_type` (
  `big_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(128) NOT NULL,
  PRIMARY KEY (`big_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mcc_big_type
-- ----------------------------
INSERT INTO `mcc_big_type` VALUES ('1', '餐娱类');
INSERT INTO `mcc_big_type` VALUES ('2', '一般类');
INSERT INTO `mcc_big_type` VALUES ('3', '民生类');
INSERT INTO `mcc_big_type` VALUES ('4', '公益类');
INSERT INTO `mcc_big_type` VALUES ('5', '县乡优惠类');
INSERT INTO `mcc_big_type` VALUES ('6', '特殊类');
