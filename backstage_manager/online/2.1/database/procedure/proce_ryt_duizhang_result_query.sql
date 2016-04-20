DROP PROCEDURE
IF EXISTS proce_ryt_duizhang_result_query;
CREATE PROCEDURE `proce_ryt_duizhang_result_query`(
	in original_tradeTime VARCHAR(20),
	in dz_tradeTime VARCHAR(20),
	in receipttablename VARCHAR(50),
	in duizhang_table_name VARCHAR(40),
  in flag int,
	out original_trade_count int,
  out original_dz_success int,
	out original_dz_error int,
	out original_dz_no int,
	out original_dz_noNeed int,
	out duizhang_trade_count int
)
BEGIN
    DECLARE receipt_column_name VARCHAR(30);
	  DECLARE refund_column_name VARCHAR(30);
		DECLARE duizhang_column_name VARCHAR(30);
		/**
			1：查询交易日期
			2：查询清算日期
		*/
    if flag = 1 then 
			set receipt_column_name = "sys_date";
			set duizhang_column_name = "reqTime";
    ELSEIF flag = 2 then 
			set receipt_column_name = "sys_date";
			set duizhang_column_name = "deduct_stlm_date";
		end if;

		set @total_count = 0;         -- 原始交易按日期统计
		set @dz_success_count = 0;    -- 原始交易按日期对账成功交易统计
		set @dz_error_count = 0;      -- 原始交易按日期差错交易交统计
		set @dz_no_count = 0;			    -- 原始交易按日期未对账交易统计
		set @dz_total_count = 0;      -- 对账交易按日期交易数据统计
		set @dz_noNeed_count = 0;     -- 原始交易按日期无需对账交易统计
		
		-- 原始收款交易按日期统计
		set @total_receipt_count_Sql = concat("select count(*) into @total_receipt_count from ",receipttablename ," where ",receipt_column_name," = '",original_tradeTime,"';");
		PREPARE total_receipt_count_Sql from @total_receipt_count_Sql;
		EXECUTE total_receipt_count_Sql;
		set original_trade_count = @total_receipt_count;
		

		-- 原始收款交易按日期统计对账成功
		set @total_receipt_bkchk1_Sql = concat("select count(*) into @total_receipt_bkchk1 from ",receipttablename ," where ",receipt_column_name," = '",original_tradeTime,"' and bk_chk = 1;");
		PREPARE total_receipt_bkchk1_Sql from @total_receipt_bkchk1_Sql;
		EXECUTE total_receipt_bkchk1_Sql;
		set original_dz_success = @total_receipt_bkchk1;

		-- 原始收款交易按日期统计对账失败
		set @total_receipt_bkchk2_Sql = concat("select count(*) into @total_receipt_bkchk2 from ",receipttablename ," where ",receipt_column_name," = '",original_tradeTime,"' and bk_chk = 2;");
		PREPARE total_receipt_bkchk2_Sql from @total_receipt_bkchk2_Sql;
		EXECUTE total_receipt_bkchk2_Sql;
		set original_dz_error = @total_receipt_bkchk2;

		-- 原始收款交易按日期统计未对账
		set @total_receipt_bkchk0_Sql = concat("select count(*) into @total_receipt_bkchk0 from ",receipttablename ," where ",receipt_column_name," = '",original_tradeTime,"' and bk_chk = 0;");
		PREPARE total_receipt_bkchk0_Sql from @total_receipt_bkchk0_Sql;
		EXECUTE total_receipt_bkchk0_Sql;
		set original_dz_no = @total_receipt_bkchk0;

		-- 原始收款交易按日期统计无需对账
		set @total_receipt_bkchk3_Sql = concat("select count(*) into @total_receipt_bkchk3 from ",receipttablename ," where ",receipt_column_name," = '",original_tradeTime,"' and bk_chk = 3;");
		PREPARE total_receipt_bkchk3_Sql from @total_receipt_bkchk3_Sql;
		EXECUTE total_receipt_bkchk3_Sql;
		set original_dz_noNeed = @total_receipt_bkchk3;

		-- 对账交易按日期交易数据统计
		set @dz_total_count_Sql1 = concat("select count(*) into @dz_total_count1 from ",duizhang_table_name ," where whetherTk = 0 and  substring(",duizhang_column_name,",1,8) = '",dz_tradeTime,"';");
		PREPARE dz_total_count_Sql1 from @dz_total_count_Sql1;
		EXECUTE dz_total_count_Sql1;
		select @dz_total_count1 into duizhang_trade_count;
		
END