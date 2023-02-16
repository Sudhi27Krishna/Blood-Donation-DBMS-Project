TYPE=TRIGGERS
triggers='CREATE DEFINER=`` TRIGGER h_dec_qty\nBEFORE DELETE ON hospital\nFOR EACH ROW\nBEGIN\n    UPDATE bank\n    SET qty = qty - OLD.qty\n    WHERE bldgrp = OLD.bldgrp;\nEND'
sql_modes=1098907648
definers='@'
client_cs_names='utf8mb4'
connection_cl_names='utf8mb4_unicode_ci'
db_cl_names='utf8mb4_general_ci'
created=167248334934
