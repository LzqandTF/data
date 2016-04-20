CREATE DEFINER=`root`@`%` FUNCTION `next_value`(seq_name char (20)) RETURNS bigint(11)
begin  
 UPDATE next_value_tab SET val= CASE WHEN val+1 > 99999999999 THEN 1 ELSE last_insert_id(val+1) END WHERE name=seq_name;  
 RETURN last_insert_id();  
end