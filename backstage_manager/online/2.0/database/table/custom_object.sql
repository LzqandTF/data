alter table custom_object modify error_file_name varchar(124) DEFAULT NULL;
alter table custom_object modify generate_number int(1) NOT NULL DEFAULT '1';
alter table custom_object modify file_need_online_data int(1) NOT NULL DEFAULT '1';
alter table custom_object modify data_type int(1) NOT NULL DEFAULT '1';
alter table custom_object add column whether_create_error_file int(1) NOT NULL DEFAULT '1';
alter table custom_object add column whether_create_inner_file int(1) NOT NULL DEFAULT '1';
alter table custom_object add column whether_create_settle_file int(1) NOT NULL DEFAULT '1';
alter table custom_object add column whether_create_file_by_range tinyint(1) NOT NULL;
