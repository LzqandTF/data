insert into function_right(id,func_name,url,level,parent_id) values(116,'对账明细下载','duizhangResultData/duizhangDetailsDownload.jsp',2,3);
INSERT INTO `function_right` (`id`, `func_name`, `url`, `level`, `parent_id`, `rel`) VALUES (14, '退款管理', '#', 1, NULL, NULL);
INSERT INTO `function_right` (`func_name`, `url`, `level`, `parent_id`, `rel`) VALUES ('退款查询', 'refundManager/refundSelect.jsp', 2, 14, NULL);
INSERT INTO `function_right` (`func_name`, `url`, `level`, `parent_id`, `rel`) VALUES ('退款经办', 'refundManager/refundHandle.jsp', 2, 14, NULL);
INSERT INTO `function_right` (`func_name`, `url`, `level`, `parent_id`, `rel`) VALUES ('联机退款', 'refundManager/onlineRefund.jsp', 2, 14, NULL);
INSERT INTO `function_right` (`func_name`, `url`, `level`, `parent_id`, `rel`) VALUES ('退款审核', 'refundManager/refundCheck.jsp', 2, 14, NULL);

