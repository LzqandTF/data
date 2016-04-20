CREATE  PROCEDURE `copy_bank_branch_name`()
BEGIN
 declare done int;
 declare temp_bank_branch VARCHAR(60) CHARACTER set utf8; -- 结算支行名称，临时变量
 declare is_auto_df Tinyint ;-- 是否自动代付    自动代付：liq_obj=1,man_liq=0,其他情况为否
 declare bank_branch VARCHAR(60) CHARACTER set utf8; -- 自动结算支行名称
 declare pbk_branch VARCHAR(60);-- 自动代付支行名称
 declare mer_code Varchar(20); -- 商户号
 declare begin_date Integer;-- 合同开始日期
 declare exp_date  Integer;  -- 合同结束日期
 declare liq_limit Integer; -- 结算满足的额度
 declare man_liq Tinyint; -- 手工结算   0 - 关闭 1 - 开启
 declare liq_obj   Tinyint; -- 0-银行卡 1-电银账户
 declare bill_type Tinyint; -- 结算类型现定为和liq_obj值一样
 declare liq_state Tinyint; -- 0 - 正常 1 - 冻结
 
   declare otherDatacur CURSOR FOR  select a.bank_branch,a.pbk_branch,a.auto_df_state,a.id,a.begin_date,a.exp_date,a.liq_limit,a.man_liq,a.liq_obj,a.liq_state from minfo a ;

   DECLARE EXIT HANDLER FOR NOT FOUND SET done = 1;
   OPEN otherDatacur;
		 otherData:
			  LOOP
					FETCH otherDatacur INTO bank_branch,pbk_branch,is_auto_df,mer_code,begin_date,exp_date,liq_limit,man_liq,liq_obj,liq_state;
					 IF done = 1 THEN
						LEAVE otherData;
					END IF;
				  	
				  -- 自动代付状态  0 - 关闭 1 - 开启 default 0  如果自动代付开启就用自动代付这一套  否则用自动结算那一套数据
                  IF is_auto_df = 0 THEN
                     SET temp_bank_branch = bank_branch;
                  ELSE
                     SET temp_bank_branch = pbk_branch;
                  END IF;
                  
                   --  融易付手工结算 0-关闭  1-开启 但是pos对账  手工结算 1：开通/2:关闭
                   IF man_liq = 0 THEN
                    SET man_liq = 2;
                  ELSEIF man_liq = 1 THEN
                    SET man_liq = 1;
                   END IF;
                   
                   --  //融易付结算对象0-银行卡  1-电银账户，pos对账 结算类型 1:银行卡账号、2:电银账号   结算对象  1：银行账户/2：电银账户/3：账户系统-企业/4:账户系统-个人	
                 IF liq_obj = 0 THEN
                    SET liq_obj = 1;
                    SET bill_type = 1;
                  ELSEIF liq_obj = 1 THEN
                    SET liq_obj = 2;
                    SET bill_type = 2;
                  END IF;
                  
                  -- 融易付结算结算状态 0-正常  1-冻结    pos对账 1： 正常  2：冻结
                   IF liq_state = 0 THEN
                    SET liq_state = 1;
                  ELSEIF liq_state = 1 THEN
                    SET liq_state = 2;
                   END IF;
                   
                  -- 同步更新商户结算表中支行银行名称字段数据
                 update mer_billing a SET a.bank_branch = temp_bank_branch,a.bil_smallamt = liq_limit,a.bil_manual = man_liq,a.bil_object = liq_obj,a.bil_type = bill_type,a.bil_status = liq_state   where a.mer_code = mer_code;
                 update mer_basic b set b.startDate = begin_date,b.endDate = exp_date where b.mer_code = mer_code;
         END LOOP otherData;
   CLOSE otherDatacur;


END