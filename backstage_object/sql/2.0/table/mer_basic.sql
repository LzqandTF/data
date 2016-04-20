alter table mer_basic modify startDate int(20) NOT NULL;
alter table mer_basic modify endDate int(20) NOT NULL;
alter table mer_basic add column settle_date int(11) NOT NULL ;



CREATE TABLE `mer_basic` (
  `mer_code` varchar(32) NOT NULL,
  `mer_name` varchar(64) DEFAULT NULL,
  `mer_category` int(11) DEFAULT NULL,
  `mer_abbreviation` varchar(20) DEFAULT NULL,
  `mer_state` int(11) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `mer_type` int(11) DEFAULT NULL,
  `startDate` int(20) DEFAULT NULL,
  `endDate` int(20) DEFAULT NULL,
  `channel` varchar(50) DEFAULT NULL,
  `expand` varchar(255) DEFAULT NULL,
  `expandno` varchar(64) DEFAULT NULL,
  `provinces` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`mer_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

