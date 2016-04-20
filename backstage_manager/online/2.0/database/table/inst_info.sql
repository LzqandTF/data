alter table inst_info
add column dz_file_path varchar(128) DEFAULT NULL;
alter table inst_info
add column dz_file_name_pattern varchar(128) DEFAULT NULL;
alter table inst_info
add column ftp_dz_file_path varchar(128) DEFAULT NULL;
alter table inst_info
add column inner_clear_file_path varchar(128) DEFAULT NULL;
alter table inst_info
add column inst_entity_name varchar(128) DEFAULT NULL;
alter table inst_info
add column parse_dz_file_class varchar(128) DEFAULT NULL;


INSERT INTO `inst_info` (`inst_id`, `whether_inner_dz`, `trade_dz_impl_class`, `name_`, `whether_inner_js`, `innner_dz_level`, `active`, `whether_outer_error_dz`, `whether_outer_dz`, `remark`, `dz_data_tableName`, `original_data_tableName`, `error_dz_data_tableName`, `error_original_data_tableName`, `riqie_original_tableName`, `receivi_name`, `receivi_id`, `inst_type`, `dz_file_path`, `dz_file_name_pattern`, `ftp_dz_file_path`, `inner_clear_file_path`, `inst_entity_name`, `parse_dz_file_class`) VALUES (14, 0, 'cn.com.chinaebi.jh.dz.deal.JhTradeDzHandler', '大连交行', 0, 0, 1, 1, 1, '大连交行', 'duizhang_dljh_lst', 'original_dljh_lst', '1', '1', 'riqie_dljh_lst', '大连交行', 14, 0, '/var/www/apps/java/data/dljh', 'bankcomm_212_*.del', NULL, '/var/www/apps/java/data/innerClearFile/dljh/sucess;/var/www/apps/java/data/innerClearFile/dljh/timeout', 'cn.com.chinaebi.dz.object.OriginalDljhLst', 'cn.com.chinaebi.dz.file.parsing.DalianbocomParsing');
INSERT INTO `inst_info` (`inst_id`, `whether_inner_dz`, `trade_dz_impl_class`, `name_`, `whether_inner_js`, `innner_dz_level`, `active`, `whether_outer_error_dz`, `whether_outer_dz`, `remark`, `dz_data_tableName`, `original_data_tableName`, `error_dz_data_tableName`, `error_original_data_tableName`, `riqie_original_tableName`, `receivi_name`, `receivi_id`, `inst_type`, `dz_file_path`, `dz_file_name_pattern`, `ftp_dz_file_path`, `inner_clear_file_path`, `inst_entity_name`, `parse_dz_file_class`) VALUES (3, 1, 'cn.com.chinaebi.zh.dz.deal.ZhTradeDzHandler', '深圳中行', 1, 0, 1, 0, 1, '深圳中行', 'duizhang_szzh_lst', 'original_szzh_lst', '0', '0', 'riqie_szzh_lst', '深圳中行', 9, 0, '/var/www/apps/java/data/szzh', 'SHDY_*.txt', '', NULL, 'cn.com.chinaebi.dz.object.OriginalSzzhLst', 'cn.com.chinaebi.dz.file.parsing.ShenzhenbocomParsing');
