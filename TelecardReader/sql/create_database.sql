create database if not exists KARTY
default character set = utf8
default collate = utf8_czech_ci;

create user 'kartar'@'%' identified by 'kartar';
GRANT ALL ON KARTY.* TO 'kartar'@'%';
create user 'kartar'@'localhost' identified by 'kartar';
GRANT ALL ON KARTY.* TO 'kartar'@'localhost';
