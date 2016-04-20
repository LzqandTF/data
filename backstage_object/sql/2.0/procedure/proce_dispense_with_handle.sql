
/**
	处理明确失败的交易为无需对账
  改存储过程在定时任务时执行、需要在数据分割完成之后执行
*/
DROP PROCEDURE
IF EXISTS proce_dispense_with_handle;
CREATE PROCEDURE proce_dispense_with_handle(
	in tradeTime VARCHAR(20),
  in table_name VARCHAR(100),
	in flag int
)
BEGIN
		/**
			自动定时任务执行(数据汇总)
		*/
		IF flag = 1 then 
				/*操作非冲正交易*/
				set @updateSql = concat("update ",table_name," set bk_chk = 3,whetherValid = 0 where deduct_sys_response not in('00','N1','N2','WT','EN','SI')
																	and deduct_roll_bk = 0 and substring(trade_time,1,10) = '",tradeTime,"';");
				PREPARE updateSql from @updateSql;
				EXECUTE updateSql;
				COMMIT;
				/*操作冲正交易*/
				set @updateRollBkSql = concat("update ",table_name," set bk_chk = 3,whetherValid = 0 where deduct_roll_bk_response not in('00','N1')
																	and deduct_roll_bk = 1 and substring(trade_time,1,10) = '",tradeTime,"';");
				PREPARE updateRollBkSql from @updateRollBkSql;
				EXECUTE updateRollBkSql;
				COMMIT;
		/**
			手动还原数据执行
		*/
		ELSEIF flag = 2 THEN
				/*操作非冲正交易*/
				set @manualSql = concat("update ",table_name," set whetherJs = 0,bk_chk = 0,whetherErroeHandle = 0,whetherQs = 0,whetherTk = 0
										where deduct_roll_bk = 0 and deduct_sys_response in('00','N1','N2','WT','EN','SI') and substring(deduct_stlm_date,1,10) = '",tradeTime,"';");
				PREPARE manualSql from @manualSql;
				EXECUTE manualSql;
				COMMIT;
				/*操作冲正交易*/
				set @manualRollSql = concat("update ",table_name," set whetherJs = 0,bk_chk = 0,whetherErroeHandle = 0,whetherQs = 0,whetherTk = 0
										where deduct_roll_bk = 1 and deduct_roll_bk_response in('00','N1') and substring(deduct_stlm_date,1,10) = '",tradeTime,"';");
				PREPARE manualRollSql from @manualRollSql;
				EXECUTE manualRollSql;
				COMMIT;
		end if;
end;