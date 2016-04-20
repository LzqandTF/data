ALTER table execute_node
MODIFY COLUMN trade_collect INT DEFAULT 0;

ALTER table execute_node
MODIFY COLUMN dz_file_gain INT DEFAULT 0;

ALTER table execute_node
MODIFY COLUMN dz_handle INT DEFAULT 0;

ALTER table execute_node
MODIFY COLUMN error_handle INT DEFAULT 0;

ALTER table execute_node
MODIFY COLUMN dz_file_create INT DEFAULT 0;