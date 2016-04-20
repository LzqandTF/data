DROP PROCEDURE
IF EXISTS proce_handle_money;

CREATE PROCEDURE `proce_handle_money`(
	in tradeTime VARCHAR(10),
  in tableName VARCHAR(60),
  in deduct_sys_id int
)
BEGIN
	DECLARE _process varchar(6);
	DECLARE _trademsg_type int;
	DECLARE _trade_money_status tinyint(1);
	DECLARE done INT;
	DECLARE trade_amount_cursor  cursor for 
						select conf.process,conf.trademsg_type,conf.trade_money_status from trade_amount_conf conf 
								where conf.trade_money_status = 1;
	
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
	open trade_amount_cursor;
		trade_amount_loop:LOOP
			FETCH trade_amount_cursor into _process,_trademsg_type,_trade_money_status;
			IF done = 1 THEN
				LEAVE trade_amount_loop;
			END IF;
			-- 交易金额正负处理
			set @updateTradeAmountSql = concat("update ",tableName," t set t.trade_amount = if(t.trade_amount > 0 , 0-t.trade_amount , t.trade_amount) where t.trade_time BETWEEN DATE_FORMAT('",tradeTime," 00:00:00','%Y-%m-%d %T') and DATE_FORMAT('",tradeTime," 23:59:59','%Y-%m-%d %T') and t.deduct_sys_id = ",deduct_sys_id," and t.req_process = '",_process,"' and t.trademsg_type =",_trademsg_type,";");
			PREPARE updateTradeAmountSql from @updateTradeAmountSql;
			EXECUTE updateTradeAmountSql;
			-- 交易手续费正负处理
			-- CONCAT(LEFT(trade_fee,1),REPLACE(CONCAT('-',right(trade_fee,LENGTH(trade_fee)-1)),'--','-'))
			set @updateTradeFeeSql = concat("update ",tableName," t set t.trade_fee  = if(LOCATE('-',trade_fee),trade_fee,CONCAT('C-',right(trade_fee,LENGTH(trade_fee)-1))) where t.trade_time BETWEEN DATE_FORMAT('",tradeTime," 00:00:00','%Y-%m-%d %T') and DATE_FORMAT('",tradeTime," 23:59:59','%Y-%m-%d %T') and t.deduct_sys_id = ",deduct_sys_id," and t.trade_fee is not NULL and t.req_process = '",_process,"' and t.trademsg_type =",_trademsg_type,";");
			PREPARE updateTradeFeeSql from @updateTradeFeeSql;
			EXECUTE updateTradeFeeSql;
			COMMIT;
		end loop trade_amount_loop;
	
	CLOSE trade_amount_cursor;
END