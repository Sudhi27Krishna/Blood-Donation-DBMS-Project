TYPE=VIEW
query=select `blood-db`.`person`.`pid` AS `pid`,`blood-db`.`person`.`name` AS `name`,`blood-db`.`person`.`age` AS `age`,`blood-db`.`person`.`dob` AS `dob`,`blood-db`.`person`.`gender` AS `gender`,`blood-db`.`person`.`bldgrp` AS `bldgrp`,`blood-db`.`person`.`city` AS `city`,`blood-db`.`person`.`phone` AS `phone`,`blood-db`.`person`.`qty` AS `qty`,`blood-db`.`person`.`role` AS `role` from `blood-db`.`person` where `blood-db`.`person`.`role` = \'donate\'
md5=d17b217a780d757f342295709e9ec798
updatable=1
algorithm=0
definer_user=
definer_host=
suid=2
with_check_option=0
timestamp=2022-12-30 08:58:09
create-version=2
source=SELECT * FROM person WHERE role="donate"
client_cs_name=utf8mb4
connection_cl_name=utf8mb4_unicode_ci
view_body_utf8=select `blood-db`.`person`.`pid` AS `pid`,`blood-db`.`person`.`name` AS `name`,`blood-db`.`person`.`age` AS `age`,`blood-db`.`person`.`dob` AS `dob`,`blood-db`.`person`.`gender` AS `gender`,`blood-db`.`person`.`bldgrp` AS `bldgrp`,`blood-db`.`person`.`city` AS `city`,`blood-db`.`person`.`phone` AS `phone`,`blood-db`.`person`.`qty` AS `qty`,`blood-db`.`person`.`role` AS `role` from `blood-db`.`person` where `blood-db`.`person`.`role` = \'donate\'
mariadb-version=100425
