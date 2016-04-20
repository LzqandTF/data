CREATE TABLE duizhang_jh_lst (
       id VARCHAR(100) NOT NULL
     , orderId VARCHAR(20)
     , orderTime VARCHAR(20)
     , reqTime VARCHAR(20)
     , reqSysStance VARCHAR(32)
     , cardType VARCHAR(20)
     , tradeAmount VARCHAR(20)
     , tradeFee VARCHAR(20)
     , settlementAmount VARCHAR(20)
     , mer_batch_no VARCHAR(32)
     , dz_file_name VARCHAR(32)
     , inst_name VARCHAR(50)
     , bk_chk INT(11)
     , deduct_stlm_date VARCHAR(20)
     , outAccount VARCHAR(32)
     , termId VARCHAR(20)
     , merCode VARCHAR(20)
     , process VARCHAR(20)
     , deductSysReference VARCHAR(20)
     , whetherTk TINYINT(1)
     , PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

