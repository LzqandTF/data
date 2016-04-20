CREATE TABLE `custom_inst_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `object_id` int(11) NOT NULL,
  `inst_id` int(11) NOT NULL,
  `inst_type` int(1) NOT NULL,
  `inst_name` varchar(32) NOT NULL DEFAULT 'null',
  PRIMARY KEY (`id`),
  KEY `object_id` (`object_id`),
  CONSTRAINT `FK_custom_inst_config_1` FOREIGN KEY (`object_id`) REFERENCES `custom_object` (`object_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

