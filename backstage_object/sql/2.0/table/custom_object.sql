alter table custom_object
add column whether_create_error_file (1) NOT NULL;
alter table custom_object
add column whether_create_inner_file (1) NOT NULL;

alter table custom_object modify error_file_name varchar(124) DEFAULT NULL;
alter table custom_object modify generate_number int(1) NOT NULL DEFAULT '1';
alter table custom_object modify file_need_online_data int(1) NOT NULL DEFAULT '1';
alter table custom_object modify data_type int(1) NOT NULL DEFAULT '1';
alter table custom_object modify whether_create_error_file int(1) NOT NULL DEFAULT '1';
alter table custom_object modify whether_create_inner_file int(1) NOT NULL DEFAULT '1';
alter table custom_object add column whether_create_settle_file int(1) NOT NULL DEFAULT '1';




CREATE TABLE `custom_object` (
  `object_id` int(11) NOT NULL AUTO_INCREMENT,
  `object_name` varchar(124) NOT NULL,
  `file_address` varchar(124) NOT NULL,
  `dz_file_name` varchar(124) NOT NULL,
  `error_file_name` varchar(124) DEFAULT NULL,
  `whether_upload` tinyint(1) NOT NULL,
  `ftp_ip` varchar(20) DEFAULT NULL,
  `ftp_address` varchar(64) DEFAULT NULL,
  `ftp_port` varchar(10) DEFAULT NULL,
  `ftp_username` varchar(20) DEFAULT NULL,
  `ftp_password` varchar(20) DEFAULT NULL,
  `file_suffix` varchar(10) DEFAULT NULL,
  `generate_number` int(1) NOT NULL DEFAULT '1',
  `file_need_online_data` int(1) DEFAULT '1',
  `data_type` int(1) DEFAULT '1',
  `whether_create_error_file` int(1) DEFAULT '2',
  `whether_create_inner_file` int(1) DEFAULT '2',
  `whether_create_settle_file` int(1) NOT NULL DEFAULT '1',
  `whether_create_file_by_range` tinyint(1) NOT NULL,
  PRIMARY KEY (`object_id`),
  UNIQUE KEY `file_address` (`file_address`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8;

