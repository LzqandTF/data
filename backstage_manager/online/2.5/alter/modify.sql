alter table error_data_lst
MODIFY COLUMN trade_id VARCHAR(40) not null;

alter table error_tk_lst
MODIFY COLUMN trade_id VARCHAR(40) not null;

ALTER TABLE error_audit_records MODIFY COLUMN trade_id VARCHAR(40) NOT NULL;
ALTER TABLE error_audit_records MODIFY COLUMN nii VARCHAR(50) DEFAULT NULL;