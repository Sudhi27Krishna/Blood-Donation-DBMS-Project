TYPE=TRIGGERS
triggers='CREATE DEFINER=`` TRIGGER inc_qty\nAFTER INSERT ON person\nFOR EACH ROW\nBEGIN\n  IF NEW.role = \'donate\' THEN\n    UPDATE bank\n    SET qty = qty + NEW.qty\n    WHERE bldgrp = NEW.bldgrp;\n  END IF;\nEND' 'CREATE DEFINER=`` TRIGGER dec_qty BEFORE DELETE ON person\n FOR EACH ROW BEGIN\n  IF OLD.role = \'need\' THEN\n    UPDATE bank\n    SET qty = qty - OLD.qty\n    WHERE bldgrp = OLD.bldgrp;\n  END IF;\nEND'
sql_modes=1098907648 1098907648
definers='@' '@'
client_cs_names='utf8mb4' 'utf8mb4'
connection_cl_names='utf8mb4_unicode_ci' 'utf8mb4_unicode_ci'
db_cl_names='utf8mb4_general_ci' 'utf8mb4_general_ci'
created=167239567442 167541825354
