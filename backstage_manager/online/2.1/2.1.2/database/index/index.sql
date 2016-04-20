alter table original_beijingbank_lst
add index deduct_stlm_date_index(deduct_stlm_date);

alter table original_cups_lst
add index deduct_stlm_date_index(deduct_stlm_date);

alter table original_szgh_lst
add index deduct_stlm_date_index(deduct_stlm_date);

alter table original_dljh_lst
add index deduct_stlm_date_index(deduct_stlm_date);

alter table original_szzh_lst
add index deduct_stlm_date_index(deduct_stlm_date);

alter table original_beijingbank_lst
add index whetherQs_index(whetherQs);

alter table original_cups_lst
add index whetherQs_index(whetherQs);

alter table original_szgh_lst
add index whetherQs_index(whetherQs);

alter table original_dljh_lst
add index whetherQs_index(whetherQs);

alter table original_szzh_lst
add index whetherQs_index(whetherQs);