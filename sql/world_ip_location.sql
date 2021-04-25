CREATE DATABASE IF NOT EXISTS `attack_detection`;

USE `attack_detection`;

DROP TABLE IF EXISTS `world_ip_location`;

CREATE TABLE world_ip_location(
    `id` BIGINT(10) NOT NULL AUTO_INCREMENT,
    `country` VARCHAR(50) NOT NULL COMMENT '所在的国家',
    `count` INT(11) NOT NULL DEFAULT 0 COMMENT '数量',
    PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;