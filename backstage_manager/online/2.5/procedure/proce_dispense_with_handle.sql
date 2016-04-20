
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
	  DECLARE _req_sys_stance VARCHAR(32);
		DECLARE done INT;
		DECLARE deduct_roll_bk_cursor cursor for select req_sys_stance from deduct_roll_bk_view;
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
		/**
			自动定时任务执行(数据汇总)
		*/
		IF flag = 1 then 
				/*操作非冲正交易*/
				set @updateSql = concat("update ",table_name," set bk_chk = 3,whetherValid = 0 where deduct_sys_response not in('00','N1','N2','WT','EN','SI')
																	and deduct_roll_bk = 0 and trade_time BETWEEN DATE_FORMAT('",tradeTime," 00:00:00','%Y-%m-%d %T') and DATE_FORMAT('",tradeTime," 23:59:59','%Y-%m-%d %T');");
				PREPARE updateSql from @updateSql;
				EXECUTE updateSql;
				COMMIT;
				/*操作冲正交易*/
				set @updateRollBkSql = concat("update ",table_name," set bk_chk = 3,whetherValid = 0 where deduct_roll_bk_response not in('00','N1')
																	and deduct_roll_bk = 1 and trade_time BETWEEN DATE_FORMAT('",tradeTime," 00:00:00','%Y-%m-%d %T') and DATE_FORMAT('",tradeTime," 23:59:59','%Y-%m-%d %T');");
				PREPARE updateRollBkSql from @updateRollBkSql;
				EXECUTE updateRollBkSql;
				COMMIT;

				DROP view IF EXISTS deduct_roll_bk_view;
				set @create_view_sql = concat("create view deduct_roll_bk_view as select a.req_sys_stance from ",table_name," a where a.trade_time BETWEEN DATE_FORMAT('",tradeTime," 00:00:00','%Y-%m-%d %T') and DATE_FORMAT('",tradeTime," 23:59:59','%Y-%m-%d %T') and a.whetherValid = 1 and a.deduct_roll_bk = 1;");
				PREPARE create_view_sql from @create_view_sql;
				EXECUTE create_view_sql;
				COMMIT;
				
				select count(*) into @view_count from deduct_roll_bk_view;
				-- 查询该视图是否存在冲正成功的交易
				if @view_count>0 THEN
						-- into column
						open deduct_roll_bk_cursor;
								deduct_roll_bk_loop : loop
										-- 获取内容
										FETCH deduct_roll_bk_cursor into _req_sys_stance;
										-- 避免循环多次
										IF done = 1 THEN
												LEAVE deduct_roll_bk_loop;
										END IF;
										select _req_sys_stance;
										-- 查询原笔交易是否为无需处理
										set @_sql = concat("select count(*) into @deductRollCount from ",table_name," where trade_time BETWEEN DATE_FORMAT('",tradeTime," 00:00:00','%Y-%m-%d %T') and DATE_FORMAT('",tradeTime," 23:59:59','%Y-%m-%d %T') and req_sys_stance = '",_req_sys_stance,"' and deduct_roll_bk = 0 and bk_chk = 3;");
										PREPARE _sql from @_sql;
										EXECUTE _sql;
										if @deductRollCount > 0 THEN
												set @rollbk_sql = concat("update ",table_name," set bk_chk = 3,whetherValid = 0 where trade_time BETWEEN DATE_FORMAT('",tradeTime," 00:00:00','%Y-%m-%d %T') and DATE_FORMAT('",tradeTime," 23:59:59','%Y-%m-%d %T') and req_sys_stance = '",_req_sys_stance,"' and deduct_roll_bk = 1;");
												PREPARE rollbk_sql from @rollbk_sql;
												EXECUTE rollbk_sql;
										end if;
								end LOOP deduct_roll_bk_loop;
						CLOSE deduct_roll_bk_cursor;
            DROP view IF EXISTS deduct_roll_bk_view;
						COMMIT;
				end if;
				
		/**
			手动还原数据执行
		*/
		ELSEIF flag = 2 THEN
				/*操作非冲正交易*/  
				set @manualSql = concat("update ",table_name," set whetherJs = 0,bk_chk = 0,whetherErroeHandle = 0,whetherQs = 0,whetherTk = 0,mer_fee = 0,zf_fee = 0,zf_file_fee = '0',zf_fee_bj = 0
										where deduct_roll_bk = 0 and deduct_sys_response in('00','N1','N2','WT','EN','SI') and deduct_stlm_date BETWEEN DATE_FORMAT('",tradeTime," 00:00:00','%Y-%m-%d %T') and DATE_FORMAT('",tradeTime," 23:59:59','%Y-%m-%d %T');");
				PREPARE manualSql from @manualSql;
				EXECUTE manualSql;
				COMMIT;
				/*操作冲正交易*/
				set @manualRollSql = concat("update ",table_name," set whetherJs = 0,bk_chk = 0,whetherErroeHandle = 0,whetherQs = 0,whetherTk = 0,mer_fee = 0,zf_fee = 0,zf_file_fee = '0',zf_fee_bj = 0
										where deduct_roll_bk = 1 and deduct_roll_bk_response in('00','N1') and deduct_stlm_date BETWEEN DATE_FORMAT('",tradeTime," 00:00:00','%Y-%m-%d %T') and DATE_FORMAT('",tradeTime," 23:59:59','%Y-%m-%d %T');");
				PREPARE manualRollSql from @manualRollSql;
				EXECUTE manualRollSql;
				COMMIT;
		end if;
		
end;