
/**
	处理明确失败的交易为无需对账
  改存储过程在定时任务时执行、需要在数据分割完成之后执行
*/
DROP PROCEDURE
IF EXISTS proce_ryt_dispense_with_handle;
CREATE PROCEDURE `proce_ryt_dispense_with_handle`(
	in tradeTime int(20),
  in receipttablename VARCHAR(100),
	in flag int
)
BEGIN
		/**
			自动定时任务执行
		*/
		IF flag = 1 then 
				/*操作收款交易*/
				set @updateSql = concat("update ",receipttablename," set bk_chk = 3,whetherValid = 0 where tstat not in(1,2) and sys_date = ",tradeTime,";");
				PREPARE updateSql from @updateSql;
				EXECUTE updateSql;
				COMMIT;
		/**
			手动还原数据执行
		*/
		ELSEIF flag = 2 THEN
				/*操作收款交易*/
				set @manualSql = concat("update ",receipttablename," set whetherQs = 0,bk_chk = 0,whetherErroeHandle = 0,zf_fee = 0,zf_file_fee = '0',zf_fee_bj = 0 where tstat in(1,2) and sys_date = ",tradeTime,";");
				PREPARE manualSql from @manualSql;
				EXECUTE manualSql;
				COMMIT;
		end if;
end