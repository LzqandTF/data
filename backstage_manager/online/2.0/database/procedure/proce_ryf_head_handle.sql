DROP PROCEDURE
IF EXISTS proce_ryf_head_handle;
/*
头寸调拨-T+1日统计存储过程处理
trade_time ：交易日期
 tableName ：表名称
deduct_stlm_date ：清算日期 int 类型
*/
CREATE PROCEDURE proce_ryf_head_handle(
  IN trade_time int,
	IN tableName VARCHAR(128),
  IN tableColumn VARCHAR(32),
  IN amountColumn VARCHAR(32),
	IN deduct_stlm_date int
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
  DECLARE mer_code_cursor  cursor for select mid from channel_mer_view;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
	
	DROP view IF EXISTS channel_mer_view;
	set @create_view_sql = concat("create view channel_mer_view as select DISTINCT a.mid from ",tableName," a where ",tableColumn," = ",trade_time," and a.whetherValid = 1;");
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
			
		  set @_sql = concat("select IFNULL(sum(",amountColumn,"),0)*0.01 into @trade_amount from ",tableName," where mid = '",_mer_code,"' and ",tableColumn," = ",trade_time," and whetherValid = 1");
			PREPARE _sql from @_sql;
			EXECUTE _sql;

			insert ignore into tmoney(id,_name,total_money,settle_way,deduct_stlm_date,mer_code,bil_account,bil_accountname) 
			select a.bil_bank,bil_bankname,concat(@trade_amount,''),a.bil_type,deduct_stlm_date,a.mer_code,a.bil_account,a.bil_accountname from mer_billing a where a.mer_code = _mer_code; 
	  end loop mer_code_loop;
		DROP view IF EXISTS channel_mer_view;
	close mer_code_cursor;
END;