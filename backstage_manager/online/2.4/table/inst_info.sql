alter table inst_info
drop COLUMN dz_data_tableName;

alter table inst_info
drop COLUMN original_data_tableName;

alter table inst_info
drop COLUMN riqie_original_tableName;

alter table inst_info
drop COLUMN receivi_id;

alter table inst_info
drop COLUMN dz_file_path;

alter table inst_info
drop COLUMN ftp_dz_file_path;

alter table inst_info
drop COLUMN dz_file_name_pattern;

alter table inst_info
drop COLUMN inner_clear_file_path;

alter table inst_info
drop COLUMN inst_entity_name;

alter table inst_info
drop COLUMN parse_dz_file_class;

alter table inst_info
drop COLUMN isDeleteDzFileTk;

alter table inst_info
drop COLUMN tk_type;

alter table inst_info
drop COLUMN tk_context;

alter table inst_info
drop COLUMN tk_column;

alter table inst_info
drop COLUMN start_row;

alter table inst_info
add COLUMN bank_id int(11) not null;

UPDATE inst_info SET dz_data_column = 'deduct_sys_reference', bank_id = 4 WHERE inst_id = 3 AND inst_type = 0;
UPDATE inst_info SET bank_id = 6 WHERE inst_id = 6 AND inst_type = 0;
UPDATE inst_info SET bank_id = 7 WHERE inst_id = 8 AND inst_type = 0;
UPDATE inst_info SET dz_data_column = 'req_sys_stance', bank_id = 2 WHERE inst_id = 11 AND inst_type = 0;
UPDATE inst_info SET bank_id = 8 WHERE inst_id = 12 AND inst_type = 0;
UPDATE inst_info SET dz_data_column = 'req_sys_stance', bank_id = 9 WHERE inst_id = 14 AND inst_type = 0;
UPDATE inst_info SET dz_data_column = 'additional_response_data', bank_id = 5 WHERE inst_id = 15 AND inst_type = 0;
UPDATE inst_info SET dz_data_column = 'req_sys_stance', bank_id = 3 WHERE inst_id = 70001 AND inst_type = 0;

UPDATE inst_info SET dz_data_column = 'custom_define_info', bank_id = 1 WHERE inst_id = 4 AND inst_type = 1;
UPDATE inst_info SET dz_data_column = 'custom_define_info', bank_id = 1 WHERE inst_id = 10 AND inst_type = 1;
UPDATE inst_info SET dz_data_column = 'custom_define_info', bank_id = 1 WHERE inst_id = 11 AND inst_type = 1;
UPDATE inst_info SET dz_data_column = 'custom_define_info', bank_id = 10 WHERE inst_id = 30004 AND inst_type = 1;
UPDATE inst_info SET dz_data_column = 'custom_define_info', bank_id = 22 WHERE inst_id = 55000 AND inst_type = 1;
UPDATE inst_info SET dz_data_column = 'custom_define_info', bank_id = 13 WHERE inst_id = 55001 AND inst_type = 1;
UPDATE inst_info SET dz_data_column = 'custom_define_info', bank_id = 11 WHERE inst_id = 90000 AND inst_type = 1;
UPDATE inst_info SET dz_data_column = 'custom_define_info', bank_id = 12 WHERE inst_id = 90004 AND inst_type = 1;
UPDATE inst_info SET dz_data_column = 'custom_define_info', bank_id = 14 WHERE inst_id = 90007 AND inst_type = 1;
UPDATE inst_info SET dz_data_column = 'custom_define_info', bank_id = 15 WHERE inst_id = 90009 AND inst_type = 1;
UPDATE inst_info SET dz_data_column = 'custom_define_info', bank_id = 16 WHERE inst_id = 90012 AND inst_type = 1;
UPDATE inst_info SET dz_data_column = 'custom_define_info', bank_id = 17 WHERE inst_id = 90016 AND inst_type = 1;
UPDATE inst_info SET dz_data_column = 'custom_define_info', bank_id = 18 WHERE inst_id = 90022 AND inst_type = 1;

INSERT INTO `inst_info` (`inst_id`, `whether_inner_dz`, `trade_dz_impl_class`, `name_`, `whether_inner_js`, `innner_dz_level`, `active`, `whether_outer_error_dz`, `whether_outer_dz`, `remark`, `error_dz_data_tableName`, `error_original_data_tableName`, `receivi_name`, `inst_type`, `dz_data_column`, `gate`, `bankftp_download`, `bankftp_path`, `bankftp_ip`, `bankftp_port`, `bankftp_username`, `bankftp_password`, `whether_t_1`, `whether_parse_brank`, `bank_id`) VALUES (5, 0, NULL, '交行', 1, 0, 1, 0, 1, '', '', '', '交行', 1, 'custom_define_info', 5, 0, '', '', '', '', '', 1, 0, 25);
INSERT INTO `inst_info` (`inst_id`, `whether_inner_dz`, `trade_dz_impl_class`, `name_`, `whether_inner_js`, `innner_dz_level`, `active`, `whether_outer_error_dz`, `whether_outer_dz`, `remark`, `error_dz_data_tableName`, `error_original_data_tableName`, `receivi_name`, `inst_type`, `dz_data_column`, `gate`, `bankftp_download`, `bankftp_path`, `bankftp_ip`, `bankftp_port`, `bankftp_username`, `bankftp_password`, `whether_t_1`, `whether_parse_brank`, `bank_id`) VALUES (51, 0, NULL, '邮政储蓄-B2C', 0, 0, 1, 0, 1, '邮政储蓄-B2C', '', '', '邮政储蓄银行', 1, 'custom_define_info', 0, 0, '', '', '', '', '', 1, 0, 21);
INSERT INTO `inst_info` (`inst_id`, `whether_inner_dz`, `trade_dz_impl_class`, `name_`, `whether_inner_js`, `innner_dz_level`, `active`, `whether_outer_error_dz`, `whether_outer_dz`, `remark`, `error_dz_data_tableName`, `error_original_data_tableName`, `receivi_name`, `inst_type`, `dz_data_column`, `gate`, `bankftp_download`, `bankftp_path`, `bankftp_ip`, `bankftp_port`, `bankftp_username`, `bankftp_password`, `whether_t_1`, `whether_parse_brank`, `bank_id`) VALUES (10500, 0, '', '农行网银（B2C借记卡）', 0, 0, 1, 1, 1, '', '', '', '农业银行', 1, 'custom_define_info', 0, 1, '', '', '', '', '', 0, 0, 19);
INSERT INTO `inst_info` (`inst_id`, `whether_inner_dz`, `trade_dz_impl_class`, `name_`, `whether_inner_js`, `innner_dz_level`, `active`, `whether_outer_error_dz`, `whether_outer_dz`, `remark`, `error_dz_data_tableName`, `error_original_data_tableName`, `receivi_name`, `inst_type`, `dz_data_column`, `gate`, `bankftp_download`, `bankftp_path`, `bankftp_ip`, `bankftp_port`, `bankftp_username`, `bankftp_password`, `whether_t_1`, `whether_parse_brank`, `bank_id`) VALUES (10501, 0, '', '农业网银（B2C贷记卡）', 0, 0, 1, 0, 1, '', '', '', '农业银行', 1, 'custom_define_info', 0, 0, '', '', '', '', '', 0, 0, 19);
INSERT INTO `inst_info` (`inst_id`, `whether_inner_dz`, `trade_dz_impl_class`, `name_`, `whether_inner_js`, `innner_dz_level`, `active`, `whether_outer_error_dz`, `whether_outer_dz`, `remark`, `error_dz_data_tableName`, `error_original_data_tableName`, `receivi_name`, `inst_type`, `dz_data_column`, `gate`, `bankftp_download`, `bankftp_path`, `bankftp_ip`, `bankftp_port`, `bankftp_username`, `bankftp_password`, `whether_t_1`, `whether_parse_brank`, `bank_id`) VALUES (10800, 0, NULL, '兴业银行', 0, 0, 1, 1, 1, '兴业银行', '', '', '兴业银行', 1, 'custom_define_info', 0, 1, '', '', '', '', '', 1, 0, 20);
INSERT INTO `inst_info` (`inst_id`, `whether_inner_dz`, `trade_dz_impl_class`, `name_`, `whether_inner_js`, `innner_dz_level`, `active`, `whether_outer_error_dz`, `whether_outer_dz`, `remark`, `error_dz_data_tableName`, `error_original_data_tableName`, `receivi_name`, `inst_type`, `dz_data_column`, `gate`, `bankftp_download`, `bankftp_path`, `bankftp_ip`, `bankftp_port`, `bankftp_username`, `bankftp_password`, `whether_t_1`, `whether_parse_brank`, `bank_id`) VALUES (30002, 0, NULL, '兴业银行-vas', 0, 0, 1, 0, 1, '兴业银行-vas', '', '', '兴业银行', 1, 'custom_define_info', 0, 0, '', '', '', '', '', 1, 0, 20);
INSERT INTO `inst_info` (`inst_id`, `whether_inner_dz`, `trade_dz_impl_class`, `name_`, `whether_inner_js`, `innner_dz_level`, `active`, `whether_outer_error_dz`, `whether_outer_dz`, `remark`, `error_dz_data_tableName`, `error_original_data_tableName`, `receivi_name`, `inst_type`, `dz_data_column`, `gate`, `bankftp_download`, `bankftp_path`, `bankftp_ip`, `bankftp_port`, `bankftp_username`, `bankftp_password`, `whether_t_1`, `whether_parse_brank`, `bank_id`) VALUES (30009, 0, NULL, '邮政银行-vas', 0, 0, 1, 0, 1, '邮政银行-vas', '', '', '邮政银行', 1, 'custom_define_info', 0, 0, '', '', '', '', '', 1, 0, 21);
INSERT INTO `inst_info` (`inst_id`, `whether_inner_dz`, `trade_dz_impl_class`, `name_`, `whether_inner_js`, `innner_dz_level`, `active`, `whether_outer_error_dz`, `whether_outer_dz`, `remark`, `error_dz_data_tableName`, `error_original_data_tableName`, `receivi_name`, `inst_type`, `dz_data_column`, `gate`, `bankftp_download`, `bankftp_path`, `bankftp_ip`, `bankftp_port`, `bankftp_username`, `bankftp_password`, `whether_t_1`, `whether_parse_brank`, `bank_id`) VALUES (30016, 0, NULL, '建行-vas', 0, 0, 1, 0, 1, '建行-vas', '', '', '建设银行', 1, 'custom_define_info', 34, 0, '', '', '', '', '', 0, 0, 23);
INSERT INTO `inst_info` (`inst_id`, `whether_inner_dz`, `trade_dz_impl_class`, `name_`, `whether_inner_js`, `innner_dz_level`, `active`, `whether_outer_error_dz`, `whether_outer_dz`, `remark`, `error_dz_data_tableName`, `error_original_data_tableName`, `receivi_name`, `inst_type`, `dz_data_column`, `gate`, `bankftp_download`, `bankftp_path`, `bankftp_ip`, `bankftp_port`, `bankftp_username`, `bankftp_password`, `whether_t_1`, `whether_parse_brank`, `bank_id`) VALUES (50100, 0, '', '工商银行-快捷', 0, 0, 1, 0, 1, '', '', '', '工商银行', 1, 'custom_define_info', 0, 0, '', '', '', '', '', 0, 0, 16);
INSERT INTO `inst_info` (`inst_id`, `whether_inner_dz`, `trade_dz_impl_class`, `name_`, `whether_inner_js`, `innner_dz_level`, `active`, `whether_outer_error_dz`, `whether_outer_dz`, `remark`, `error_dz_data_tableName`, `error_original_data_tableName`, `receivi_name`, `inst_type`, `dz_data_column`, `gate`, `bankftp_download`, `bankftp_path`, `bankftp_ip`, `bankftp_port`, `bankftp_username`, `bankftp_password`, `whether_t_1`, `whether_parse_brank`, `bank_id`) VALUES (90002, 0, NULL, '建设银行－wap－机票', 0, 0, 1, 0, 1, '建设银行－wap－机票', '', '', '建设银行', 1, 'custom_define_info', 34, 0, '', '', '', '', '', 0, 0, 23);
INSERT INTO `inst_info` (`inst_id`, `whether_inner_dz`, `trade_dz_impl_class`, `name_`, `whether_inner_js`, `innner_dz_level`, `active`, `whether_outer_error_dz`, `whether_outer_dz`, `remark`, `error_dz_data_tableName`, `error_original_data_tableName`, `receivi_name`, `inst_type`, `dz_data_column`, `gate`, `bankftp_download`, `bankftp_path`, `bankftp_ip`, `bankftp_port`, `bankftp_username`, `bankftp_password`, `whether_t_1`, `whether_parse_brank`, `bank_id`) VALUES (90005, 0, NULL, '兴业银行-wap', 0, 0, 1, 1, 1, '兴业银行-wap', '', '', '兴业银行', 1, 'custom_define_info', 0, 1, '', '', '', '', '', 1, 0, 20);
INSERT INTO `inst_info` (`inst_id`, `whether_inner_dz`, `trade_dz_impl_class`, `name_`, `whether_inner_js`, `innner_dz_level`, `active`, `whether_outer_error_dz`, `whether_outer_dz`, `remark`, `error_dz_data_tableName`, `error_original_data_tableName`, `receivi_name`, `inst_type`, `dz_data_column`, `gate`, `bankftp_download`, `bankftp_path`, `bankftp_ip`, `bankftp_port`, `bankftp_username`, `bankftp_password`, `whether_t_1`, `whether_parse_brank`, `bank_id`) VALUES (90006, 0, NULL, '深圳中行-wap', 0, 0, 1, 0, 1, '深圳中行-wap', '', '', '深圳中行', 1, 'custom_define_info', 35, 0, '', '', '', '', '', 0, 0, 24);
INSERT INTO `inst_info` (`inst_id`, `whether_inner_dz`, `trade_dz_impl_class`, `name_`, `whether_inner_js`, `innner_dz_level`, `active`, `whether_outer_error_dz`, `whether_outer_dz`, `remark`, `error_dz_data_tableName`, `error_original_data_tableName`, `receivi_name`, `inst_type`, `dz_data_column`, `gate`, `bankftp_download`, `bankftp_path`, `bankftp_ip`, `bankftp_port`, `bankftp_username`, `bankftp_password`, `whether_t_1`, `whether_parse_brank`, `bank_id`) VALUES (90008, 0, NULL, '上海银行-wap-vas2', 1, 0, 1, 1, 1, '上海银行-wap-vas2', '', '', '上海银行', 1, 'custom_define_info', 17, 0, '', '', '', '', '', 1, 0, 14);
INSERT INTO `inst_info` (`inst_id`, `whether_inner_dz`, `trade_dz_impl_class`, `name_`, `whether_inner_js`, `innner_dz_level`, `active`, `whether_outer_error_dz`, `whether_outer_dz`, `remark`, `error_dz_data_tableName`, `error_original_data_tableName`, `receivi_name`, `inst_type`, `dz_data_column`, `gate`, `bankftp_download`, `bankftp_path`, `bankftp_ip`, `bankftp_port`, `bankftp_username`, `bankftp_password`, `whether_t_1`, `whether_parse_brank`, `bank_id`) VALUES (90023, 0, NULL, '浦发wap-交通罚款', 1, 0, 1, 0, 1, '浦发wap-交通罚款', '', '', '浦发银行', 1, 'custom_define_info', 21, 0, '', '', '', '', '', 0, 0, 18);
