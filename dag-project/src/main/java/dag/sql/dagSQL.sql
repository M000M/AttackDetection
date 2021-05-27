CREATE DATABASE IF NOT EXISTS `dag`;

USE `dag`;

DROP TABLE IF EXISTS `dag_block`;

CREATE TABLE dag_block(
    `id`   INT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `pre1` VARCHAR(100),
    `pre2` VARCHAR(100),
    `data` TEXT,
    `hash` VARCHAR(100),
    `num`  INT(20) NOT NULL DEFAULT 0,
    `timestamp` bigint(20) NOT NULL
)ENGINE=INNODB, DEFAULT CHARSET=utf8;