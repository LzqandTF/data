update timing_task_conf set inst_type = 1 where gather_data_time_name = 'errorUpmpParsing' and channel_id = 55001;
update timing_task_conf set inst_type = 1 where dz_handler_time_name = 'dzRytUpmpTask' and channel_id = 55001;
update timing_task_conf set inst_type = 1 where gather_data_time_name = 'upmpParsing' and channel_id = 55001;
