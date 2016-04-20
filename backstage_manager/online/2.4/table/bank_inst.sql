/*
Navicat MySQL Data Transfer

Source Server         : 192.168.30.33
Source Server Version : 50146
Source Host           : 192.168.30.33:3306
Source Database       : duiz

Target Server Type    : MYSQL
Target Server Version : 50146
File Encoding         : 65001

Date: 2015-10-28 13:12:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `bank_inst`
-- ----------------------------
DROP TABLE IF EXISTS `bank_inst`;
CREATE TABLE `bank_inst` (
  `bank_id` int(11) NOT NULL AUTO_INCREMENT,
  `bank_name` varchar(64) NOT NULL,
  `parse_dz_file_class` varchar(128) NOT NULL,
  `trade_dz_impl_class` varchar(64) DEFAULT NULL,
  `dz_data_tableName` varchar(64) NOT NULL,
  `ftp_dz_file_path` varchar(64) NOT NULL,
  `dz_file_name_pattern` varchar(64) NOT NULL,
  `dz_file_path` varchar(128) NOT NULL,
  `original_data_tableName` varchar(64) NOT NULL,
  `riqie_original_tableName` varchar(64) DEFAULT NULL,
  `tk_tableName` varchar(64) DEFAULT NULL,
  `inst_entity_name` varchar(128) NOT NULL,
  `bank_entity_name` varchar(128) NOT NULL,
  `start_row` int(11) NOT NULL DEFAULT '0',
  `isTk` tinyint(1) NOT NULL DEFAULT '0',
  `tk_type` int(11) DEFAULT NULL,
  `tk_context` varchar(64) DEFAULT NULL,
  `tk_column` int(11) DEFAULT '0',
  `bank_type` int(1) DEFAULT '1',
  `whether_outer_dz` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`bank_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

INSERT INTO `bank_inst` VALUES ('1', '快钱', 'cn.com.chinaebi.dz.file.parsing.Ryf_KuaiqianParsing', 'cn.com.chinaebi.ryt.kq.dz.deal.RytKqBankTradeDz', 'duizhang_kuaiqian_lst', '/kuaiqian', 'yyyy-MM-dd*.csv', '/var/www/apps/java/data/kq', 'ryt_kuaiqian', null, 'refund_log', 'cn.com.chinaebi.dz.object.RytKuaiqian', 'cn.com.chinaebi.dz.object.DuizhangKuaiqianLst', '2', '1', '2', '退款', '14', '1', '1');
INSERT INTO `bank_inst` VALUES ('2', 'POS-银联', 'cn.com.chinaebi.dz.file.parsing.Pos_CupsParsing', 'cn.com.chinaebi.pos.dz.deal.CupsTradeDzHandler', 'duizhang_cups_lst', '/chinaebi/48720000/', 'INDyyMMdd01ACOM', '/var/www/apps/java/data/cups', 'original_cups_lst', 'riqie_cups_lst', null, 'cn.com.chinaebi.dz.object.OriginalCupsLst', 'cn.com.chinaebi.dz.object.DuizhangCupsLst', '1', '0', '0', null, '0', '0', '1');
INSERT INTO `bank_inst` VALUES ('3', 'POS-北京银行', 'cn.com.chinaebi.dz.file.parsing.Pos_BeijingbankParsing', 'cn.com.chinaebi.pos.dz.deal.BeijingTradeDzHandler', 'duizhang_beijingbank_lst', '/bjyh/', 'INDyyMMdd01ACOM-dianyin', '/var/www/apps/java/data/bjyh', 'original_beijingbank_lst', 'riqie_beijingbank_lst', null, 'cn.com.chinaebi.dz.object.OriginalBeijingbankLst', 'cn.com.chinaebi.dz.object.DuizhangBeijingbankLst', '0', '0', '0', null, '0', '0', '1');
INSERT INTO `bank_inst` VALUES ('4', 'POS-深圳中行', 'cn.com.chinaebi.dz.file.parsing.Pos_SzzhParsing', 'cn.com.chinaebi.pos.dz.deal.ZhTradeDzHandler', 'duizhang_szzh_lst', '/szzh/', 'SHDY_yyyyMMdd.txt', '/var/www/apps/java/data/szzh', 'original_szzh_lst', 'riqie_szzh_lst', null, 'cn.com.chinaebi.dz.object.OriginalSzzhLst', 'cn.com.chinaebi.dz.object.DuizhangSzzhLst', '0', '0', '0', null, '0', '0', '1');
INSERT INTO `bank_inst` VALUES ('5', 'POS-深圳工行', 'cn.com.chinaebi.dz.file.parsing.Pos_SzghParsing', 'cn.com.chinaebi.pos.dz.deal.GhTradeDzHandler', 'duizhang_szgh_lst', '/szgh', 'yyyyMMdd-00129-bfhzfdtl.bin', '/var/www/apps/java/data/szgh', 'original_szgh_lst', 'riqie_szgh_lst', null, 'cn.com.chinaebi.dz.object.OriginalSzghLst', 'cn.com.chinaebi.dz.object.DuizhangSzghLst', '0', '0', '0', null, '0', '0', '1');
INSERT INTO `bank_inst` VALUES ('6', 'POS-华势', '', null, '', '', '', '', 'creditcardpay_trade_lst', null, null, 'cn.com.chinaebi.dz.object.CreditcardpayTradeLst', '', '0', '0', '0', null, null, '0', '0');
INSERT INTO `bank_inst` VALUES ('7', 'POS-德颐', '', null, '', '', '', '', 'creditcardpay_trade_lst', null, null, 'cn.com.chinaebi.dz.object.CreditcardpayTradeLst', '', '0', '0', '0', null, '0', '0', '0');
INSERT INTO `bank_inst` VALUES ('8', 'POS-盛京银行', '', null, '', '', '', '', 'original_shengjingbank_lst', null, null, 'cn.com.chinaebi.dz.object.OriginalShengjingbankLst', '', '0', '0', '0', null, null, '0', '0');
INSERT INTO `bank_inst` VALUES ('9', 'POS-大连交行', 'cn.com.chinaebi.dz.file.parsing.Pos_DljhParsing', 'cn.com.chinaebi.pos.dz.deal.JhTradeDzHandler', 'duizhang_dljh_lst', '/dljh/', 'bankcomm_212_yyyyMMdd.del', '/var/www/apps/java/data/dljh', 'original_dljh_lst', 'riqie_dljh_lst', null, 'cn.com.chinaebi.dz.object.OriginalDljhLst', 'cn.com.chinaebi.dz.object.DuizhangDljhLst', '0', '0', '0', null, '0', '0', '1');
INSERT INTO `bank_inst` VALUES ('10', '交通银行', 'cn.com.chinaebi.dz.file.parsing.Ryf_JiaohangParsing', 'cn.com.chinaebi.ryt.jtyh.dz.deal.RytJtyhBankTradeDz', 'duizhang_jiaohang_lst', '/jhvas', '*.txt', '/var/www/apps/java/data/jhvas', 'ryt_jh', null, 'refund_log', 'cn.com.chinaebi.dz.object.RytJh', 'cn.com.chinaebi.dz.object.DuizhangJiaohangLst', '11', '1', '1', null, '6', '1', '1');
INSERT INTO `bank_inst` VALUES ('11', '招商银行', 'cn.com.chinaebi.dz.file.parsing.Ryf_ZhaohangParsing', 'cn.com.chinaebi.ryt.zsyh.dz.deal.RytZsyhBankTradeDz', 'duizhang_zhaohang_lst', '/zsyh', '*.txt', '/var/www/apps/java/data/zsyh', 'ryt_zhaohang', null, 'refund_log', 'cn.com.chinaebi.dz.object.RytZhaohang', 'cn.com.chinaebi.dz.object.DuizhangZhaohangLst', '2', '1', '1', null, '5', '1', '1');
INSERT INTO `bank_inst` VALUES ('12', '中国银行融易通', 'cn.com.chinaebi.dz.file.parsing.Ryf_ZgyhRytParsing', 'cn.com.chinaebi.ryt.zgyh_ryt.dz.deal.RytZgyhRYTBankTradeDz', 'duizhang_zgyh_ryt_lst', '/zhyhRyt', '*.txt', '/var/www/apps/java/data/zhyhRyt', 'ryt_zgyh_ryt', null, 'refund_log', 'cn.com.chinaebi.dz.object.RytZgyhRyt', 'cn.com.chinaebi.dz.object.DuizhangZgyhRytLst', '2', '1', '1', null, '7', '1', '1');
INSERT INTO `bank_inst` VALUES ('13', '银联UPMP', 'cn.com.chinaebi.dz.file.parsing.Ryf_UpmpParsing', 'cn.com.chinaebi.ryt.yl.dz.deal.RytYlUpmpBankTradeDz', 'duizhang_upmp_lst', '/chinaebi/48722900/', 'INDyyMMdd01ACOM', '/var/www/apps/java/data/upmp', 'ryt_upmp', null, 'refund_log', 'cn.com.chinaebi.dz.object.RytUpmp', 'cn.com.chinaebi.dz.object.DuizhangUpmpLst', '1', '0', '0', null, '1', '1', '1');
INSERT INTO `bank_inst` VALUES ('14', '上海银行', 'cn.com.chinaebi.dz.file.parsing.Ryf_ShyhParsing', 'cn.com.chinaebi.ryt.shyh.dz.deal.RytShyhBankTradeDz', 'duizhang_shyh_lst', 'null', '*.txt', '/var/www/apps/java/shyhwyzf', 'ryt_shyh', null, 'refund_log', 'cn.com.chinaebi.dz.object.RytShyh', 'cn.com.chinaebi.dz.object.DuizhangShyhLst', '2', '1', '1', '失败', '6', '1', '1');
INSERT INTO `bank_inst` VALUES ('15', '支付宝', 'cn.com.chinaebi.dz.file.parsing.Ryf_ZfbParsing', 'cn.com.chinaebi.ryt.zfb.dz.deal.RytZfbBankTradeDz', 'duizhang_zfb_lst', 'null', '*.csv', '/var/www/apps/java/data/zfbhh', 'ryt_zfb', null, 'refund_log', 'cn.com.chinaebi.dz.object.RytZfb', 'cn.com.chinaebi.dz.object.RytZfb', '6', '1', '2', '退费', '11', '1', '1');
INSERT INTO `bank_inst` VALUES ('16', '工商银行', 'cn.com.chinaebi.dz.file.parsing.Ryf_GonghangParsing', 'cn.com.chinaebi.ryt.gsyh.dz.deal.RytGsyhBankTradeDz', 'duizhang_gonghang_lst', 'null', 'b2cpaydetailnew*', '/var/www/apps/java/data/ghwap', 'ryt_gonghang', null, 'refund_log', 'cn.com.chinaebi.dz.object.RytGonghang', 'cn.com.chinaebi.dz.object.RytGonghang', '5', '1', '2', '已经退货', '7', '1', '1');
INSERT INTO `bank_inst` VALUES ('17', '中国银联WAP', 'cn.com.chinaebi.dz.file.parsing.Ryf_YlLstParsing', 'cn.com.chinaebi.ryt.yl.dz.deal.RytYlWapBankTradeDz', 'duizhang_yl_lst', 'null', 'INNyyMMdd88ZM_872310045110201', '/var/www/apps/java/data/ylwap', 'ryt_yl', null, 'refund_log', 'cn.com.chinaebi.dz.object.RytYl', 'cn.com.chinaebi.dz.object.RytYl', '1', '1', '1', '04', '7', '1', '1');
INSERT INTO `bank_inst` VALUES ('18', '浦发银行', 'cn.com.chinaebi.dz.file.parsing.Ryf_PufabankParsing', 'cn.com.chinaebi.ryt.pfyh.dz.deal.RytPfyhBankTradeDz', 'duizhang_pufa_bank_lst', 'null', '*.txt', '/var/www/apps/java/data/pufa', 'ryt_pufa', null, 'refund_log', 'cn.com.chinaebi.dz.object.RytPufa', 'cn.com.chinaebi.dz.object.RytPufa', '1', '1', '1', null, '8', '1', '1');
INSERT INTO `bank_inst` VALUES ('19', '农业银行', 'cn.com.chinaebi.dz.file.parsing.Ryf_nonghangParsing', 'cn.com.chinaebi.ryt.nyyh.dz.deal.RytNyyhBankTradeDz', 'duizhang_nonghang_lst', 'null', 'yyyyMMdd*.txt', '/var/www/apps/java/data/nyyh', 'ryt_nh', null, 'refund_log', 'cn.com.chinaebi.dz.object.RytNh', 'cn.com.chinaebi.dz.object.DuizhangNonghangLst', '2', '1', '2', 'Refund', '2', '1', '1');
INSERT INTO `bank_inst` VALUES ('20', '兴业银行', 'cn.com.chinaebi.dz.file.parsing.Ryf_XyyhParsing', 'cn.com.chinaebi.ryt.xyyh.dz.deal.RytXyyhBankTradeDz', 'duizhang_xingye_lst', 'null', '*.txt', '/var/www/apps/java/data/xyyh', 'ryt_xyyh', null, 'refund_log', 'cn.com.chinaebi.dz.object.RytXyyh', 'cn.com.chinaebi.dz.object.DuizhangXingyeLst', '1', '1', '1', null, '6', '1', '1');
INSERT INTO `bank_inst` VALUES ('21', '邮政储蓄', 'cn.com.chinaebi.dz.file.parsing.Ryf_YzcxParsing', 'cn.com.chinaebi.ryt.yzcxyh.dz.deal.RytYzcxyhBankTradeDz', 'duizhang_yzcx_lst', 'null', '*.txt', '/var/www/apps/java/data/yzcx', 'ryt_yzcx', null, 'refund_log', 'cn.com.chinaebi.dz.object.RytYzcx', 'cn.com.chinaebi.dz.object.DuizhangYzcxLst', '2', '1', '1', null, '8', '1', '1');
INSERT INTO `bank_inst` VALUES ('22', '银联手机支付', 'cn.com.chinaebi.dz.file.parsing.Ryf_YlShoujizhifuParsing', 'cn.com.chinaebi.ryt.yl.dz.deal.RytYlShouJiZhiFuBankTradeDz', 'duizhang_yl_shoujizhifu_lst', '/ylsjzf', '*', '/var/www/apps/java/data/ylsjzf', 'ryt_cups_sjzf', null, 'refund_log', 'cn.com.chinaebi.dz.object.RytCupsSjzf', 'cn.com.chinaebi.dz.object.DuizhangYlShoujizhifuLst', '13', '1', '2', '退货', '11', '1', '1');
INSERT INTO `bank_inst` VALUES ('23', '建设银行', 'cn.com.chinaebi.dz.file.parsing.Ryf_JsyhParsing', 'cn.com.chinaebi.ryt.jsyh.dz.deal.RytJsyhBankTradeDz', 'duizhang_jsyh_lst', '/jsyh', '*.txt', '/var/www/apps/java/data/jsyh', 'ryt_jsyh', null, 'refund_log', 'cn.com.chinaebi.dz.object.RytJsyh', 'cn.com.chinaebi.dz.object.DuizhangJsyhLst', '5', '0', '1', null, '5', '1', '1');
INSERT INTO `bank_inst` VALUES ('24', '中国银行', 'cn.com.chinaebi.dz.file.parsing.Ryf_ZgyhParsing', 'cn.com.chinaebi.ryt.zgyh.dz.deal.RytZgyhBankTradeDz', 'duizhang_zgyh_lst', '/zgyh', '*txt', '/var/www/apps/java/data/zgyh', 'ryt_zgyh', null, 'refund_log', 'cn.com.chinaebi.dz.object.RytZgyh', 'cn.com.chinaebi.dz.object.DuizhangZgyhLst', '2', '1', '1', null, '7', '1', '1');
INSERT INTO `bank_inst` VALUES ('25', '交行', 'cn.com.chinaebi.dz.file.parsing.Ryf_JhParsing', 'cn.com.chinaebi.ryt.jtyh.dz.deal.RytJtyhBankTradeDz', 'duizhang_jh_lst', '/jh', '*.txt', '/var/www/apps/java/data/jh', 'ryt_jh', null, 'refund_log', 'cn.com.chinaebi.dz.object.RytJh', 'cn.com.chinaebi.dz.object.DuizhangJhLst', '11', '1', '1', null, '6', '1', '1');
