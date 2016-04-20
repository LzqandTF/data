DROP TABLE IF EXISTS `mer_balance`;
CREATE TABLE `mer_balance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mer_code` varchar(20) NOT NULL,
  `mer_category` int(11) NOT NULL,
  `mer_balance` varchar(40) NOT NULL,
  `mer_state` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `mer_code` (`mer_code`),
  CONSTRAINT `FK_mer_balance_1` FOREIGN KEY (`mer_code`) REFERENCES `mer_basic` (`mer_code`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;



CREATE  PROCEDURE `copy_mer_balance`()
BEGIN
	
 declare done int;
 declare mer_code VARCHAR(20);
 declare mer_balance DOUBLE;
 declare category Tinyint;   -- 商户类别  0–RYF商户 1–VAS商户 2–POS商户
 declare mstate  Tinyint; -- 状态标志 0 – Normal 1 – Close

 
   declare otherDatacur CURSOR FOR  select a.aid,b.category,IFNULL((select a.all_balance + a.balance + a.freeze_amt),0.00),b.mstate from acc_infos a INNER JOIN minfo b on a.aid = b.id and b.category = 2;

   DECLARE EXIT HANDLER FOR NOT FOUND SET done = 1;
   OPEN otherDatacur;
		 otherData:
			  LOOP
					FETCH otherDatacur INTO mer_code,category,mer_balance,mstate;
					 IF done = 1 THEN
						LEAVE otherData;
					END IF;
				         -- 融易付商户状态 0-正常 1-关闭  pos对账商户状态 5：正常/6：关闭
                 IF mstate = 0 THEN
                    SET mstate = 5;
                 ELSEIF mstate = 1 THEN
                    SET mstate = 6;
                 END IF;

                  -- 插入商户余额信息表中
                 insert into mer_balance(mer_code,mer_category,mer_balance,mer_state)
			           values (mer_code,category,mer_balance / 100.00,mstate);
               
                 
               
			END LOOP otherData;
   CLOSE otherDatacur;

END
