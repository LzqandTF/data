-- 修改对账文件解析类名称
update inst_info set parse_dz_file_class = 'cn.com.chinaebi.dz.file.parsing.Pos_SzzhParsing' where inst_id = 3 and inst_type = 0;
update inst_info set parse_dz_file_class = 'cn.com.chinaebi.dz.file.parsing.Ryf_KuaiqianParsing' where inst_id = 4 and inst_type = 1;
update inst_info set parse_dz_file_class = 'cn.com.chinaebi.dz.file.parsing.Pos_ZhongxingbankParsing' where inst_id = 10 and inst_type = 0;
update inst_info set parse_dz_file_class = 'cn.com.chinaebi.dz.file.parsing.Ryf_KuaiqianghParsing' where inst_id = 10 and inst_type = 1;
update inst_info set parse_dz_file_class = 'cn.com.chinaebi.dz.file.parsing.Pos_CupsParsing;cn.com.chinaebi.dz.file.parsing.Pos_CupsErrorParsing' where inst_id = 11 and inst_type = 0;
update inst_info set parse_dz_file_class = 'cn.com.chinaebi.dz.file.parsing.Ryf_KuaiqianhhParsing' where inst_id = 11 and inst_type = 1;
update inst_info set parse_dz_file_class = 'cn.com.chinaebi.dz.file.parsing.Pos_DljhParsing' where inst_id = 14 and inst_type = 0;
update inst_info set parse_dz_file_class = 'cn.com.chinaebi.dz.file.parsing.Ryf_JiaohangvasParsing' where inst_id = 30004 and inst_type = 1;
update inst_info set parse_dz_file_class = 'cn.com.chinaebi.dz.file.parsing.Ryf_YlShoujizhifuParsing' where inst_id = 55000 and inst_type = 1;
update inst_info set parse_dz_file_class = 'cn.com.chinaebi.dz.file.parsing.Ryf_UpmpParsing;cn.com.chinaebi.dz.file.parsing.Ryf_UpmpErrorParsing' where inst_id = 55001 and inst_type = 1;
update inst_info set parse_dz_file_class = 'cn.com.chinaebi.dz.file.parsing.Pos_BeijingbankParsing' where inst_id = 70001 and inst_type = 0;
update inst_info set parse_dz_file_class = 'cn.com.chinaebi.dz.file.parsing.Ryf_ZhaohangParsing' where inst_id = 90000 and inst_type = 1;
update inst_info set parse_dz_file_class = 'cn.com.chinaebi.dz.file.parsing.Ryf_ZhonghangwapbankParsing' where inst_id = 90004 and inst_type = 1;
update inst_info set parse_dz_file_class = 'cn.com.chinaebi.dz.file.parsing.Ryf_ShanghaibankwszfParsing' where inst_id = 90007 and inst_type = 1;
update inst_info set parse_dz_file_class = 'cn.com.chinaebi.dz.file.parsing.Ryf_ZfbhaihangParsing' where inst_id = 90009 and inst_type = 1;
update inst_info set parse_dz_file_class = 'cn.com.chinaebi.dz.file.parsing.Ryf_GonghangwapParsing' where inst_id = 90012 and inst_type = 1;
update inst_info set parse_dz_file_class = 'cn.com.chinaebi.dz.file.parsing.Ryf_YlwapLstParsing' where inst_id = 90016 and inst_type = 1;
update inst_info set parse_dz_file_class = 'cn.com.chinaebi.dz.file.parsing.Ryf_PufabankParsing' where inst_id = 90022 and inst_type = 1;

-- 新添加字段
alter table inst_info
add COLUMN bankftp_download tinyint(1) DEFAULT '0';

alter table inst_info
add COLUMN bankftp_path VARCHAR(64) DEFAULT null;

alter table inst_info
add COLUMN bankftp_ip VARCHAR(24) DEFAULT null;

alter table inst_info 
add COLUMN bankftp_port VARCHAR(2) DEFAULT null;

alter table inst_info
add COLUMN bankftp_username VARCHAR(32) DEFAULT null;

alter table inst_info
add COLUMN bankftp_password VARCHAR(32) DEFAULT null;

alter table inst_info
add COLUMN whether_t_1 tinyint(1) DEFAULT '0';

--添加深圳工行渠道配置数据
INSERT INTO `inst_info` (`inst_id`, `whether_inner_dz`, `trade_dz_impl_class`, `name_`, `whether_inner_js`, `innner_dz_level`, `active`, `whether_outer_error_dz`, `whether_outer_dz`, `remark`, `dz_data_tableName`, `original_data_tableName`, `error_dz_data_tableName`, `error_original_data_tableName`, `riqie_original_tableName`, `receivi_name`, `receivi_id`, `inst_type`, `dz_file_path`, `dz_file_name_pattern`, `ftp_dz_file_path`, `inner_clear_file_path`, `inst_entity_name`, `parse_dz_file_class`, `isDeleteDzFileTk`, `tk_type`, `tk_context`, `tk_column`, `dz_data_column`, `start_row`, `gate`, `bankftp_download`, `bankftp_path`, `bankftp_ip`, `bankftp_port`, `bankftp_username`, `bankftp_password`, `whether_t_1`) VALUES (15, 1, 'cn.com.chinaebi.gh.dz.deal.GhTradeDzHandler', '深圳工行', 1, 1, 1, 0, 1, '深圳工行', 'duizhang_szgh_lst', 'original_szgh_lst', '', '', 'riqie_szgh_lst', '深圳工行', 11, 0, '/var/www/apps/java/data/szgh', 'yyyyMMdd-00129-bfhzfdtl.bin', '/szgh', NULL, 'cn.com.chinaebi.dz.object.OriginalSzghLst', 'cn.com.chinaebi.dz.file.parsing.Pos_SzghParsing', 0, 0, '', 0, 'reqSysStance', 0, 0, 1, '/jigou/account/yyyyMMdd/', '192.168.30.229', '21', 'pos', 'pos123', 1);

-- 修改深圳工行主动拉取对账文件ftp配置
update inst_info set bankftp_download = 1, bankftp_path = '/jigou/account/yyyyMMdd/', bankftp_ip = '10.248.2.9', bankftp_port = 21, 
bankftp_username = 'dianyin', bankftp_password = 'password' where inst_id = 15 and inst_type = 0;

-- 修改深圳工行网关号
update inst_info set gate = 30024  where inst_id = 15 and inst_type = 0;

-- 银联cups、北京银行、大连交行、深圳工行
UPDATE inst_info set whether_t_1 = 1 where inst_id in(11,70001,14,15) and inst_type = 0;
-- 深圳工行
UPDATE inst_info set whether_t_1 = 0 where inst_id = 3 and inst_type = 0;
-- 银联UPMP
UPDATE inst_info set whether_t_1 = 1 where inst_id = 55001 and inst_type = 1;
