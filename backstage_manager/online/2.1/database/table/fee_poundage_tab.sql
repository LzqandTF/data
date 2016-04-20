/*
Navicat MySQL Data Transfer

Source Server         : 192.168.20.130
Source Server Version : 50613
Source Host           : 192.168.20.130:3306
Source Database       : data_manager

Target Server Type    : MYSQL
Target Server Version : 50613
File Encoding         : 65001

Date: 2015-05-22 14:35:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `fee_poundage_tab`
-- ----------------------------
DROP TABLE IF EXISTS `fee_poundage_tab`;
CREATE TABLE `fee_poundage_tab` (
  `fee_poundage` varchar(128) NOT NULL,
  `poundage_detail` varchar(256) NOT NULL,
  PRIMARY KEY (`fee_poundage`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fee_poundage_tab
-- ----------------------------
INSERT INTO `fee_poundage_tab` VALUES ('AMT*R', '(固定交易费率，无上下限)其中R代表费率<br> 如费率为0.008<br>则计费公式为AMT*0.008');
INSERT INTO `fee_poundage_tab` VALUES ('BKFEE(X,D,R,I)', '专门计算银行手续费的格式<br>X：最小值，D：最大值，R:费率,I：第三位&gt;= I 时候进一，<br> 如：BKFEE(0.1,100,0.008,2)<br>表示该手续费按照0.008的费率计算，最小为0.1元，最大为100元，当小数点后第三位是&gt;=2时进一');
INSERT INTO `fee_poundage_tab` VALUES ('FIX(x1,R1,x2,R2,R3)', '(分段固定手续费)其中x1,x2…..代表分界档，R1,R2,R3…..代表分界档内的固定手续费。<br>其中不包含上分界档.如计费费率为:&lt;20000,手续费为2,20000-50000,手续费为3,50000-100000,手续费为5,<br>&gt;=100000,费率为10<br>则计费公式为：FIX(20000,2,50000,3,100000,5,10)');
INSERT INTO `fee_poundage_tab` VALUES ('FLO(x1,R1,x2,R2,R3)', '(单笔浮动费率)其中x1,x2…..代表分界档，R1,R2,R3…..代表分界档内的费率。<br>其中不包含上分界档如: &lt;500,费率为0.08,500-5000费率为0.06,5000-50000费率为0.05 ,50000-500000费率为0.03,<br>&gt;=5000000,费率为0.01 则计费公式为：FLO(500,0.08,5000,0.06,50000,0.05,500000,0.03,0.01)');
INSERT INTO `fee_poundage_tab` VALUES ('IFBIG(x1,x2)', '(最低额计费)x1为最低计费额，x2为其它组合公式<br>只有在该额度以上才能计费，否则为零,在大于该额度以上，计费公式为X2.<br>如: 只有在5000以上才能计费，计费的规则为：每笔费率为0.008<br>则计费公式为IFBIG(5000, AMT*0.08)');
INSERT INTO `fee_poundage_tab` VALUES ('INC(X,N)', '(增量计费)最低手续费为X，每增加N的金额，手续费就增加X<br>如：最低手续费为2.5元，交易金额每增加20000元，手续费就增加2.5元<br>则计费公式为INC(2.5，20000)');
INSERT INTO `fee_poundage_tab` VALUES ('IPSMAX(X1,AMT*R)', '环讯专用计费公式(固定交易费率，有下限)其中X1代表下限，R代表费率<br>如费率为0.005，下限为0.2<br>则计费公式为MAX(0.2,AMT*0.005),进一法');
INSERT INTO `fee_poundage_tab` VALUES ('MAX(X1,AMT*R)', '(固定交易费率，有下限)其中X1代表下限，R代表费率<br>如费率为0.005，下限为0.2<br>则计费公式为MAX(0.2,AMT*0.005)');
INSERT INTO `fee_poundage_tab` VALUES ('MIN(X1,AMT*R)', '(固定交易费率，有上限)其中X1代表上限，R代表费率<br>如费率为0.005，上限为50<br>则计费公式为MIN(50,AMT*0.00');
INSERT INTO `fee_poundage_tab` VALUES ('MTM(x1,x2,AMT*R)', '(固定交易费率，有下限和上限)其中x1,x2分别代表下限和上限，R代表费率<br>如费率为0.006，下限为0.8，上限为500<br>则计费公式为MTM(0.8,500,AMT*0.006)');
INSERT INTO `fee_poundage_tab` VALUES ('SGL(X)', '(单笔固定费用)其中X代表单笔固定手续费<br>如单笔固定为2.5元<br>则计费公式为SGL(2.5)');
