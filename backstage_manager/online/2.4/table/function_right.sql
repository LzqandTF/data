insert into function_right(id,func_name,url.level.parent_id) values(113,'银行机构维护','sysConfig/bank_inst.jsp',2,8);

insert into function_right(id,func_name,url.level.parent_id) values(114,'差错手动对账','duizhangData/manual_upload_error_data.jsp',2,2);

update function_right set url = 'duizhangData/manual_upload_dz_data.jsp' and func_name='手动对账' where id = 69 and level = 2 and parent_id = 2;