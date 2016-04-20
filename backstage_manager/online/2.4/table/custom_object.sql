
alter table custom_object
drop COLUMN whether_create_inner_file int(1) NOT NULL DEFAULT '2'; 

alter table custom_object
drop COLUMN whether_create_settle_file int(1) NOT NULL DEFAULT '2'; 

alter table custom_object add column file_type int(1) DEFAULT '0';

alter table custom_object add column whether_create_file_by_inst tinyint(1) DEFAULT '0';

update custom_object set file_type = 1 where object_id in (1,2,3,4,6,7);

update custom_object set file_type = 2 where object_id = 5;



