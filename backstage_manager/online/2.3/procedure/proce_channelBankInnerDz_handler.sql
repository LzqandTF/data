/*
渠道内部对账处理存储过程
startTime : yyyy-MM-dd HH:mm:ss
  endTime : yyyy-MM-dd HH:mm:ss
tableName : 渠道原始表名称
dateColumn: 查询日期字段
*/
DROP PROCEDURE
IF EXISTS proce_channelBankInnerDz_handler;
create PROCEDURE proce_channelBankInnerDz_handler(
	in startTime varchar(20),
	in endTime VARCHAR(20),
  in tableName varchar(50),
  in dateColumn VARCHAR(20)
)
BEGIN
	DECLARE _trade_id varchar(32);
	DECLARE _req_sys_stance varchar(32);
  DECLARE _deduct_sys_stance varchar(32);
  DECLARE _deduct_result int(11);
  DECLARE _req_process varchar(6);
	DECLARE _trademsg_type int(3);
	DECLARE _deduct_roll_bk tinyint(1);
	DECLARE _deduct_roll_bk_result smallint(6);
	DECLARE _original_trans_info varchar(32);
  DECLARE _original_stance VARCHAR(32);
	DECLARE _trade_amount bigint(20);
	DECLARE _whetherQs tinyint(1) DEFAULT 1;
	DECLARE _deduct_sys_reference varchar(12);


	DECLARE sz_deductStmlDate varchar(10);
	DECLARE done INT;
  DECLARE channel_inner_dz_cursor  cursor for select * from channel_inner_dz_view;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

	DROP view IF EXISTS channel_inner_dz_view;
	set @create_view_sql = concat("create view channel_inner_dz_view as select trade_id,req_sys_stance,deduct_sys_stance,deduct_result,req_process,trademsg_type,deduct_roll_bk,deduct_roll_bk_result,original_trans_info,trade_amount,deduct_sys_reference
from ",tableName," where ",dateColumn," BETWEEN DATE_FORMAT('",startTime,"','%Y-%m-%d %T') and DATE_FORMAT('",endTime,"','%Y-%m-%d %T') and whetherValid = 1 and trademsg_type in(18,26,28,58,80,82);");
	PREPARE create_view_sql from @create_view_sql;
	EXECUTE create_view_sql;
	COMMIT;

	open channel_inner_dz_cursor;
		channel_inner_dz_loop:LOOP
			FETCH channel_inner_dz_cursor into _trade_id,_req_sys_stance,_deduct_sys_stance,_deduct_result,_req_process,_trademsg_type,_deduct_roll_bk,_deduct_roll_bk_result,_original_trans_info,_trade_amount,_deduct_sys_reference;
			-- 避免循环多次
			IF done = 1 THEN
				LEAVE channel_inner_dz_loop;
			END IF;

			if _deduct_roll_bk = 1 THEN
				-- 消费冲正 or 预授权完成冲正
				if _deduct_roll_bk_result = 0 and (_trademsg_type = 26 or _trademsg_type = 80)  THEN
						set @update_cz_sql = concat("update ",tableName," set bk_chk = 1,whetherQs = 1,whtherInnerDz = 1 where ",dateColumn," BETWEEN DATE_FORMAT('",startTime,"','%Y-%m-%d %T') and DATE_FORMAT('",endTime,"','%Y-%m-%d %T') and req_sys_stance = '",_req_sys_stance,"';");
						PREPARE update_cz_sql from @update_cz_sql;
						EXECUTE update_cz_sql;
				-- 撤销冲正 or 预授权完成撤销冲正
				ELSEIF _deduct_roll_bk_result = 0 and (_trademsg_type = 28 or _trademsg_type = 82) THEN
						set @update_cz_sql = concat("update ",tableName," set bk_chk = 1,whetherQs = 1,whtherInnerDz = 1 where ",dateColumn," BETWEEN DATE_FORMAT('",startTime,"','%Y-%m-%d %T') and DATE_FORMAT('",endTime,"','%Y-%m-%d %T') and req_sys_stance = '",_req_sys_stance,"';");
						PREPARE update_cz_sql from @update_cz_sql;
						EXECUTE update_cz_sql;
				end if;
			ELSE
				-- 消费撤销、预授权完成撤销
				if _deduct_result = 0 and (_trademsg_type = 18 or _trademsg_type = 58) THEN 
						-- select CONCAT("撤销",_req_sys_stance," 原笔",RIGHT(_original_trans_info,6));
						set @update_cx_sql = concat("update ",tableName," set bk_chk = 1,whetherQs = 1,whtherInnerDz = 1 where ",dateColumn," BETWEEN DATE_FORMAT('",startTime,"','%Y-%m-%d %T') and DATE_FORMAT('",endTime,"','%Y-%m-%d %T') and deduct_sys_stance = '",_req_sys_stance,"';");
						PREPARE update_cx_sql from @update_cx_sql;
						EXECUTE update_cx_sql;

						set @update_cx_ori_sql = concat("update ",tableName," set bk_chk = 1,whetherQs = 1,whtherInnerDz = 1 where ",dateColumn," BETWEEN DATE_FORMAT('",startTime,"','%Y-%m-%d %T') and DATE_FORMAT('",endTime,"','%Y-%m-%d %T') and deduct_sys_stance = '",RIGHT(_original_trans_info,6),"';");
						PREPARE update_cx_ori_sql from @update_cx_ori_sql;
						EXECUTE update_cx_ori_sql;
				-- 退货交易
				/*ELSEIF _deduct_result = 0 and _trademsg_type = 20 THEN
						-- 获取原笔流水号
						set _original_stance = SUBSTRING(_original_trans_info,LENGTH(_original_trans_info)-10+1,6);
						-- select CONCAT("退货",_req_sys_stance," 原笔",_original_stance);
						set @select_th_ori_sql = concat("select trade_amount into @ori_amount from ",tableName," where ",dateColumn," BETWEEN DATE_FORMAT('",startTime,"','%Y-%m-%d %T') and DATE_FORMAT('",endTime,"','%Y-%m-%d %T') and deduct_sys_stance = '",_original_stance,"';");
						PREPARE select_th_ori_sql from @select_th_ori_sql;
						EXECUTE select_th_ori_sql;
						-- 判断金额是否相等，如果等于就内部勾对
						set _trade_amount = 0 - _trade_amount;
						if @ori_amount = _trade_amount THEN
								set @update_th_sql = concat("update ",tableName," set bk_chk = 1,whetherQs = 1 where ",dateColumn," BETWEEN DATE_FORMAT('",startTime,"','%Y-%m-%d %T') and DATE_FORMAT('",endTime,"','%Y-%m-%d %T') and deduct_sys_stance = '",_req_sys_stance,"';");
								PREPARE update_th_sql from @update_th_sql;
								EXECUTE update_th_sql;

								set @update_th_ori_sql = concat("update ",tableName," set bk_chk = 1,whetherQs = 1 where ",dateColumn," BETWEEN DATE_FORMAT('",startTime,"','%Y-%m-%d %T') and DATE_FORMAT('",endTime,"','%Y-%m-%d %T') and deduct_sys_stance = '",_original_stance,"';");
								PREPARE update_th_ori_sql from @update_th_ori_sql;
								EXECUTE update_th_ori_sql;
						END IF;*/
				END IF;
			END IF;
	  end loop channel_inner_dz_loop;
	close channel_inner_dz_cursor;
	-- 修改内部对账反交易为退款
	/*set @update_whetherTk = concat("update ",tableName," set whetherTk = 1 where ",dateColumn," BETWEEN DATE_FORMAT('",startTime,"','%Y-%m-%d %T') and DATE_FORMAT('",endTime,"','%Y-%m-%d %T') and trademsg_type in(18,26,28,58,80,82) and whetherQs = 1");
	PREPARE update_whetherTk from @update_whetherTk;
	EXECUTE update_whetherTk;
	DROP view channel_inner_dz_view;
	*/
end;