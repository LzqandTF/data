DROP PROCEDURE
IF EXISTS proce_duizhang_result_query;

/**

	执行：
	call proce_duizhang_result_query("2014-06-30","20140630","original_cups_lst","duizhang_cups_lst",1,11,@a,@b,@c,@d,@e,@f,@g,@h);
	select @a,@b,@c,@d,@e,@f,@g,@h;

   输入参数
	original_tradeTime : 原笔交易时间
  dz_tradeTime       ：对账交易时间
  original_table_name：原笔汇总交易表名称
  duizhang_table_name：对账文件交易表名称
				         flag：1 = 查询交易日期、2 = 查询清算日期
				deduct_sys_id：扣款渠道ID

   输出参数
  original_trade_count:原笔交易总数
  original_dz_success :原笔交易成功总数
  original_dz_error   :原笔交易差错总数
  original_dz_no      :原笔交易未对帐总数
  duizhang_trade_count:渠道交易总笔数
	original_dz_noNeed  :原始交易无需对账总数
*/
CREATE PROCEDURE proce_duizhang_result_query (
	in original_tradeTime VARCHAR(20),
	in dz_tradeTime VARCHAR(20),
	in original_table_name VARCHAR(40),
	in duizhang_table_name VARCHAR(40),
  in flag int,
  in deduct_sys_id int,
	out original_trade_count int,
  out original_dz_success int,
	out original_dz_error int,
	out original_dz_no int,
	out duizhang_trade_count int,
	out original_dz_noNeed int
)
BEGIN
    DECLARE original_column_name VARCHAR(30);
		DECLARE duizhang_column_name VARCHAR(30);
		/**
			1：查询交易日期
			2：查询清算日期
		*/
    if flag = 1 then 
			set original_column_name = "trade_time";
			if deduct_sys_id = 10 THEN
				set duizhang_column_name = "tradeTime";
			ELSE
				set duizhang_column_name = "reqTime";
      end if;
    ELSEIF flag = 2 then 
			set original_column_name = "deduct_stlm_date";
			set duizhang_column_name = "deduct_stlm_date";
		end if;

		set @total_count = 0;         -- 原始交易按日期统计
		set @dz_success_count = 0;    -- 原始交易按日期对账成功交易统计
		set @dz_error_count = 0;      -- 原始交易按日期差错交易交统计
		set @dz_no_count = 0;			    -- 原始交易按日期未对账交易统计
		set @dz_total_count = 0;      -- 对账交易按日期交易数据统计
		set @dz_noNeed_count = 0;     -- 原始交易按日期无需对账交易统计
		
		-- 原始交易按日期统计  
		set @total_count_Sql = concat("select count(*) into @total_count from ",original_table_name ," where ",original_column_name," BETWEEN DATE_FORMAT('",original_tradeTime," 00:00:00','%Y-%m-%d %T') and DATE_FORMAT('",original_tradeTime," 23:59:59','%Y-%m-%d %T');");
		PREPARE total_count_Sql from @total_count_Sql;
		EXECUTE total_count_Sql;
		select @total_count into original_trade_count;
		
		-- 原始交易按日期对账成功交易统计
		set @dz_success_count_Sql = concat("select count(*) into @dz_success_count from ",original_table_name ," where ",original_column_name," BETWEEN DATE_FORMAT('",original_tradeTime," 00:00:00','%Y-%m-%d %T') and DATE_FORMAT('",original_tradeTime," 23:59:59','%Y-%m-%d %T') and bk_chk = 1;");
		PREPARE dz_success_count_Sql from @dz_success_count_Sql;
		EXECUTE dz_success_count_Sql;
		select @dz_success_count into original_dz_success;

		-- 原始交易按日期差错交易交统计
		set @dz_error_count_Sql = concat("select count(*) into @dz_error_count from ",original_table_name ," where ",original_column_name," BETWEEN DATE_FORMAT('",original_tradeTime," 00:00:00','%Y-%m-%d %T') and DATE_FORMAT('",original_tradeTime," 23:59:59','%Y-%m-%d %T') and bk_chk = 2;");
		PREPARE dz_error_count_Sql from @dz_error_count_Sql;
		EXECUTE dz_error_count_Sql;
		select @dz_error_count into original_dz_error;

		-- 原始交易按日期未对账交易统计
		set @dz_no_count_Sql = concat("select count(*) into @dz_no_count from ",original_table_name ," where ",original_column_name," BETWEEN DATE_FORMAT('",original_tradeTime," 00:00:00','%Y-%m-%d %T') and DATE_FORMAT('",original_tradeTime," 23:59:59','%Y-%m-%d %T') and bk_chk = 0;");
		PREPARE dz_no_count_Sql from @dz_no_count_Sql;
		EXECUTE dz_no_count_Sql;
		select @dz_no_count into original_dz_no;	

		-- 对账交易按日期交易数据统计
		set @dz_total_count_Sql = concat("select count(*) into @dz_total_count from ",duizhang_table_name ," where ",duizhang_column_name," = '",dz_tradeTime,"';");
		PREPARE dz_total_count_Sql from @dz_total_count_Sql;
		EXECUTE dz_total_count_Sql;
		select @dz_total_count into duizhang_trade_count;
		
		-- 原始交易按日期无需对账交易统计
		set @dz_no_noNeed_count_Sql = concat("select count(*) into @dz_noNeed_count from ",original_table_name ," where ",original_column_name," BETWEEN DATE_FORMAT('",original_tradeTime," 00:00:00','%Y-%m-%d %T') and DATE_FORMAT('",original_tradeTime," 23:59:59','%Y-%m-%d %T') and bk_chk = 3;");
		PREPARE dz_no_noNeed_count_Sql from @dz_no_noNeed_count_Sql;
		EXECUTE dz_no_noNeed_count_Sql;
		select @dz_noNeed_count into original_dz_noNeed;
END;