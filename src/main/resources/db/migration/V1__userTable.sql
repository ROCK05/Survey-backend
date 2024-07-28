CREATE TABLE IF NOT EXISTS user (
id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
name VARCHAR(255) COMMENT 'name of user',
email VARCHAR(255) NOT NULL COMMENT 'email of user',
password VARCHAR(255) COMMENT 'password of user',
role VARCHAR(255) NOT NULL COMMENT 'role of user',
gender VARCHAR(255) COMMENT 'gender of user'
);