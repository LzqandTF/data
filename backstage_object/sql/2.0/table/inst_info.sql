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


cn.com.chinaebi.dz.object.OriginalSzzhLst
cn.com.chinaebi.dz.object.CreditcardpayTradeLst
cn.com.chinaebi.dz.object.CreditcardpayTradeLst
cn.com.chinaebi.dz.object.OriginalZhongxingbankLst
cn.com.chinaebi.dz.object.OriginalCupsLst
cn.com.chinaebi.dz.object.OriginalShengjingbankLst
cn.com.chinaebi.dz.object.OriginalDljhLst
cn.com.chinaebi.dz.object.RytUpmp
cn.com.chinaebi.dz.object.OriginalBeijingbankLst



3 cn.com.chinaebi.dz.file.parsing.ShenzhenbocomParsing
10 cn.com.chinaebi.dz.file.parsing.ZhongxingbankParsing
11 cn.com.chinaebi.dz.file.parsing.CupsParsing;cn.com.chinaebi.dz.file.parsing.CupsErrorParsing
14 cn.com.chinaebi.dz.file.parsing.DalianbocomParsing
55001 cn.com.chinaebi.dz.file.parsing.UpmpParsing;cn.com.chinaebi.dz.file.parsing.UpmpErrorParsing
70001 cn.com.chinaebi.dz.file.parsing.BeijingbankParsing