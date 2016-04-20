DROP PROCEDURE
IF EXISTS proce_pos_head_handle;
CREATE PROCEDURE `proce_pos_head_handle`(
  IN trade_time VARCHAR(20),
	IN tableName VARCHAR(128),
	IN deduct_stlm_date int,
	IN inst_id INT,
	IN inst_type INT
)
BEGIN
	DECLARE _bil_bank VARCHAR(32);
  DECLARE _bil_bankname VARCHAR(32);
  DECLARE _bil_type int(11);
  DECLARE _trade_amount bigint(20);
	DECLARE _mer_code VARCHAR(32);
  DECLARE _bil_account varchar(50);
	DECLARE _bil_accountname varchar(50);
	DECLARE _id varchar(32);
  DECLARE done INT;
  DECLARE mer_code_cursor  cursor for select req_mer_code from channel_mer_view;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
	
	DROP view IF EXISTS channel_mer_view;
	set @create_view_sql = concat("create view channel_mer_view as select DISTINCT a.req_mer_code from ",tableName," a where a.deduct_sys_time BETWEEN DATE_FORMAT('",trade_time," 00:00:00','%Y-%m-%d %T') and DATE_FORMAT('",trade_time," 23:59:59','%Y-%m-%d %T')
 and a.whetherValid = 1;");
	PREPARE create_view_sql from @create_view_sql;
	EXECUTE create_view_sql;
	COMMIT;
	
	open mer_code_cursor;
		mer_code_loop:LOOP
			-- 提取商户号
			FETCH mer_code_cursor into _mer_code;
			-- 避免循环多次
			IF done = 1 THEN
				LEAVE mer_code_loop;
			END IF;
			
		  set @_sql = concat("select IFNULL(sum(trade_amount),0)*0.01 into @trade_amount from ",tableName," where req_mer_code = '",_mer_code,"' and req_time 
BETWEEN DATE_FORMAT('",trade_time," 00:00:00','%Y-%m-%d %T') and DATE_FORMAT('",trade_time," 23:59:59','%Y-%m-%d %T');");
			PREPARE _sql from @_sql;
			EXECUTE _sql;
			insert ignore into tmoney(id,_name,total_money,settle_way,deduct_stlm_date,mer_code,bil_account,bil_accountname,inst_id,inst_type) 
			select a.bil_bank,bil_bankname,concat(@trade_amount,''),a.bil_type,deduct_stlm_date,a.mer_code,a.bil_account,a.bil_accountname, inst_id, inst_type from mer_billing a where a.mer_code = _mer_code; 
	  end loop mer_code_loop;
		DROP view IF EXISTS channel_mer_view;
	close mer_code_cursor;
END