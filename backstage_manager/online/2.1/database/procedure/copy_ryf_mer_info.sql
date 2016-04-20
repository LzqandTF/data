CREATE TABLE `mer_basic` (
  `mer_code` varchar(32) NOT NULL,
  `mer_name` varchar(64) DEFAULT NULL,
  `mer_category` int(11) DEFAULT NULL,
  `mer_abbreviation` varchar(20) DEFAULT NULL,
  `mer_state` int(11) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `mer_type` int(11) DEFAULT NULL,
  `startDate` int(20) DEFAULT NULL,
  `endDate` int(20) DEFAULT NULL,
  `channel` varchar(50) DEFAULT NULL,
  `expand` varchar(255) DEFAULT NULL,
  `expandno` varchar(64) DEFAULT NULL,
  `provinces` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`mer_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `mer_billing` (
  `id` varchar(32) NOT NULL,
  `bil_object` int(11) DEFAULT NULL,
  `bil_bank` varchar(30) DEFAULT NULL,
  `bil_bankname` varchar(100) DEFAULT NULL,
  `bil_accountname` varchar(50) DEFAULT NULL,
  `bil_bankaccount` varchar(100) DEFAULT NULL,
  `bil_way` int(11) DEFAULT NULL,
  `bil_smallamt` varchar(20) DEFAULT NULL,
  `bil_cycle` int(20) DEFAULT NULL,
  `bil_manual` int(11) DEFAULT NULL,
  `bil_account` varchar(50) DEFAULT NULL,
  `bil_type` int(11) DEFAULT NULL,
  `mer_poundage` varchar(50) DEFAULT NULL,
  `mer_code` varchar(32) DEFAULT NULL,
  `bil_status` int(11) DEFAULT NULL,
  `last_liq_date` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `mer_code` (`mer_code`),
  CONSTRAINT `FK_mer_billing_1` FOREIGN KEY (`mer_code`) REFERENCES `mer_basic` (`mer_code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





CREATE  PROCEDURE `copy_ryf_mer_info`()
BEGIN

declare done int;
declare bank_acct Varchar(100); -- 结算银行账号      自动结算一套
declare bank_name Varchar(50); -- 结算银行名称     自动结算一套
declare bank_acct_name Varchar(50); -- 结算账户名称  自动结算一套
declare open_bk_no CHAR(14); -- 结算银行联行号   自动结算一套
declare pbk_no Varchar(50);    -- 结算银行联行号   自动代付那一套
declare bank_branch Varchar(60); -- 结算银行支行名
declare category Tinyint;   -- 商户类别  0–RYF商户 1–VAS商户 2–POS商户
declare exp_date  Integer;  -- 合同结束日期
declare gate_id Integer; -- 网关号
declare liq_limit Integer; -- 结算满足的额度
declare liq_obj   Tinyint; -- 0-银行卡 1-电银账户
declare liq_period Tinyint; -- 1–每天清算一次 2–每周清算一次 3–每周清算两次 4–每月清算一次 5–其它，
declare liq_state Tinyint; -- 0 - 正常 1 - 冻结
declare liq_type Tinyint; -- 1–全额结算 2–净额结算
declare mer_type  Tinyint; -- 0-企业 1-个人 2-集团
declare mer_name Varchar(40); -- 商户的名称
declare id Varchar(20); -- 商户号
declare pbk_acc_name Varchar(50); -- 结算账户名称      自动代付一套
declare pbk_acc_no Varchar(50); -- 结算银行账号           自动代付一套
declare pbk_name Varchar(50); -- 结算银行名称                 自动代付一套
declare prov_id smallint(2); -- 所在省编号
declare abbrev Varchar(16); -- 商户简称
declare begin_date Integer;-- 合同开始日期
declare mstate  Tinyint; -- 状态标志 0 – Normal 1 – Close
declare man_liq Tinyint; -- 手工结算   0 - 关闭 1 - 开启
declare bill_type Tinyint; -- 结算类型现定为和liq_obj值一样
declare is_auto_df Tinyint ;-- 是否自动代付    自动代付：liq_obj=1,man_liq=0,其他情况为否
declare temp_acct Varchar(100); -- 临时结算银行账号变量
declare temp_bank_name Varchar(50); -- 临时结算银行名称变量
declare temp_acct_name Varchar(50); -- 临时结算银行账号名称变量
declare temp_bank_no Varchar(50);-- 临时结算银行联行号
declare user_id Varchar(20);-- 电银账户号 账户系统userId 
declare last_liq_date int(11) DEFAULT '0';
   declare otherDatacur CURSOR FOR 
   select a.bank_acct,a.bank_name,a.bank_acct_name,a.pbk_no,a.bank_branch,a.bank_acct_name,a.category,a.exp_date,a.gate_id,a.liq_limit,a.liq_obj,a.liq_period,a.liq_state,a.liq_type,a.mer_type,a.name,a.id,a.pbk_acc_name,a.pbk_acc_no,a.pbk_name,a.prov_id,a.abbrev,a.begin_date,a.mstate,a.man_liq,a.auto_df_state,a.user_id,a.open_bk_no,a.last_liq_date from minfo a;

	 DECLARE EXIT HANDLER FOR NOT FOUND SET done = 1;
   OPEN otherDatacur;
		 otherData:
			  LOOP
					FETCH otherDatacur INTO bank_acct,bank_name,bank_acct_name,pbk_no,bank_branch,bank_acct_name,category,exp_date,gate_id,liq_limit,liq_obj,liq_period,liq_state,liq_type,mer_type,mer_name,id,pbk_acc_name,pbk_acc_no,pbk_name,prov_id,abbrev,begin_date,mstate,man_liq,is_auto_df,user_id,open_bk_no,last_liq_date;		
          IF done = 1 THEN
						LEAVE otherData;
					END IF;

				--  //融易付结算对象0-银行卡  1-电银账户，pos对账 结算类型 1:银行卡账号、2:电银账号   结算对象  1：银行账户/2：电银账户/3：账户系统-企业/4:账户系统-个人	
                 IF liq_obj = 0 THEN
                    SET liq_obj = 1;
                    SET bill_type = 1;
                  ELSEIF liq_obj = 1 THEN
                    SET liq_obj = 2;
                    SET bill_type = 2;
                  END IF;
 
                 -- 融易付结算周期 1–每天清算一次  2–每周清算一次 3–每周清算两次  4–每月清算一次  5–其它，Pos对账 结算周期 1:每日结算一次/2:每周结算一次/3:每月结算一次
                 -- 转换方式为：对应上的就对应处理，融易付结算周期为2、3的都对应Pos对账的2
                  IF liq_period = 1 THEN
                     SET liq_period = 1;
                  ELSEIF liq_period = 2 or liq_period = 3 THEN
                     SET liq_period = 2;
                  ELSEIF liq_period = 4 THEN
                     SET liq_period = 3;
                   END IF;

                  -- 融易付结算结算状态 0-正常  1-冻结    pos对账 1： 正常  2：冻结
                   IF liq_state = 0 THEN
                    SET liq_state = 1;
                  ELSEIF liq_state = 1 THEN
                    SET liq_state = 2;
                   END IF;

                  -- 融易付商户类型 0-企业1-个人2-集团    POS对账商户类型 1：企业/2：个人/3：集团
                  -- mer_type
                   IF mer_type = 0 THEN
                     SET mer_type = 1;
                   ELSEIF mer_type = 1 THEN
                     SET mer_type = 2;
                   ELSEIF mer_type = 2 THEN
                     SET mer_type = 3;
                    END IF;

                  -- 融易付商户状态 0-正常 1-关闭  pos对账商户状态 5：正常/6：关闭
                  IF mstate = 0 THEN
                    SET mstate = 5;
                  ELSEIF mstate = 1 THEN
                    SET mstate = 6;
                   END IF;

                  --  融易付手工结算 0-关闭  1-开启 但是pos对账  手工结算 1：开通/2:关闭
                   IF man_liq = 0 THEN
                    SET man_liq = 2;
                  ELSEIF man_liq = 1 THEN
                    SET man_liq = 1;
                   END IF;

                 -- 结算方式 1：全额/2：净额 为空的话就填0
                 IF liq_type is null THEN
                    SET liq_type = 0;
                 END IF;  
                 
                  -- 自动代付状态  0 - 关闭 1 - 开启 default 0  如果自动代付开启就用自动代付这一套  否则用自动结算那一套数据
                  IF is_auto_df = 0 THEN
                     SET temp_acct = bank_acct;
                     SET temp_bank_name = bank_name;
                     SET temp_acct_name = bank_acct_name;
                     SET temp_bank_no = open_bk_no;
                   ELSE
                     SET temp_acct = pbk_acc_no;
                     SET temp_bank_name = pbk_name;
                     SET temp_acct_name = pbk_acc_name;
                     SET temp_bank_no = pbk_no;
                   END IF;  
               -- 放在同一个事务里面提交
               START TRANSACTION;
                  -- 插入商户基本信息表数据 
                 insert into mer_basic(mer_code,mer_name,mer_category,mer_abbreviation,mer_state,mer_type,startDate,endDate,channel,provinces)
			           values (id,mer_name,category,abbrev,mstate,mer_type,begin_date,exp_date,null,prov_id);
                  -- 插入商户结算信息表数据 FIXME 目前电银结算账号 user_id minfo 数据库里面没有
                 insert into mer_billing(id,bil_object,bil_bank,bil_bankname,bil_accountname,bil_bankaccount,bil_way,bil_smallamt,bil_cycle,bil_manual,bil_account,bil_type,mer_code,bil_status,last_liq_date)
						     values (REPLACE(UUID(),'-',''),liq_obj,temp_bank_no,temp_bank_name,temp_acct_name,temp_acct,liq_type,liq_limit,liq_period,man_liq,user_id,bill_type,id,liq_state,last_liq_date);  
               COMMIT;
			END LOOP otherData;
   CLOSE otherDatacur;
 END