UPDATE function_right SET func_name = '商户信息查询', url = 'merInfo/queryMerInfo.jsp' WHERE id = 88;
UPDATE function_right SET func_name = '商户信息配置', url = 'merInfo/updateMerInfo.jsp' WHERE id = 101;
INSERT INTO `function_right` (`id`, `func_name`, `url`, `level`, `parent_id`, `rel`) VALUES (115, '网关对应商户交易表', 'merBillingManager/queryMsettleStatistics.jsp', 2, 6, NULL);