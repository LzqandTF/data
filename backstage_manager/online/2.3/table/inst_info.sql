alter table inst_info
add column whether_parse_brank tinyint(1) DEFAULT '0';
update inst_info set whether_parse_brank = 1 where inst_id = 11 and inst_type = 0;

UPDATE inst_info SET dz_data_column = 'deduct_sys_reference' WHERE inst_id = 3 AND inst_type = 0;
UPDATE inst_info SET dz_data_column = 'req_sys_stance' WHERE inst_id = 4 AND inst_type = 1;
UPDATE inst_info SET dz_data_column = 'req_sys_stance' WHERE inst_id = 10 AND inst_type = 1;
UPDATE inst_info SET dz_data_column = 'deduct_sys_stance' WHERE inst_id = 11 AND inst_type = 0;
UPDATE inst_info SET dz_data_column = 'req_sys_stance' WHERE inst_id = 11 AND inst_type = 1;
UPDATE inst_info SET dz_data_column = 'deduct_sys_reference' WHERE inst_id = 14 AND inst_type = 0;
UPDATE inst_info SET dz_data_column = 'additional_response_data' WHERE inst_id = 15 AND inst_type = 0;
UPDATE inst_info SET dz_data_column = 'req_sys_stance' WHERE inst_id = 30004 AND inst_type = 1;
UPDATE inst_info SET dz_data_column = 'req_sys_stance' WHERE inst_id = 55000 AND inst_type = 1;
UPDATE inst_info SET dz_data_column = 'deduct_sys_reference' WHERE inst_id = 55001 AND inst_type = 1;
UPDATE inst_info SET dz_data_column = 'req_sys_stance' WHERE inst_id = 70001 AND inst_type = 0;
UPDATE inst_info SET dz_data_column = 'req_sys_stance' WHERE inst_id = 90000 AND inst_type = 1;
UPDATE inst_info SET dz_data_column = 'req_sys_stance' WHERE inst_id = 90004 AND inst_type = 1;
UPDATE inst_info SET dz_data_column = 'req_sys_stance' WHERE inst_id = 90007 AND inst_type = 1;
UPDATE inst_info SET dz_data_column = 'req_sys_stance' WHERE inst_id = 90009 AND inst_type = 1;
UPDATE inst_info SET dz_data_column = 'req_sys_stance' WHERE inst_id = 90012 AND inst_type = 1;
UPDATE inst_info SET dz_data_column = 'req_sys_stance' WHERE inst_id = 90016 AND inst_type = 1;
UPDATE inst_info SET dz_data_column = 'req_sys_stance' WHERE inst_id = 90022 AND inst_type = 1;


update inst_info a set trade_dz_impl_class = 'cn.com.chinaebi.pos.dz.deal.ZhTradeDzHandler' where a.inst_id = 3 and a.inst_type = 0;
update inst_info a set trade_dz_impl_class = 'cn.com.chinaebi.pos.dz.deal.ZhongxinTradeDzHandler' where a.inst_id = 10 and a.inst_type = 0;
update inst_info a set trade_dz_impl_class = 'cn.com.chinaebi.pos.dz.deal.CupsTradeDzHandler' where a.inst_id = 11 and a.inst_type = 0;
update inst_info a set trade_dz_impl_class = 'cn.com.chinaebi.pos.dz.deal.JhTradeDzHandler' where a.inst_id = 14 and a.inst_type = 0;
update inst_info a set trade_dz_impl_class = 'cn.com.chinaebi.pos.dz.deal.GhTradeDzHandler' where a.inst_id = 15 and a.inst_type = 0;
update inst_info a set trade_dz_impl_class = 'cn.com.chinaebi.pos.dz.deal.BeijingTradeDzHandler' where a.inst_id = 70001 and a.inst_type = 0;