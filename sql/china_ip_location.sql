CREATE DATABASE IF NOT EXISTS `attack_detection`;

USE `attack_detection`;

DROP TABLE IF EXISTS `china_ip_location`;

CREATE TABLE china_ip_location(
    `id` BIGINT(10) NOT NULL AUTO_INCREMENT,
    `province` VARCHAR(10) NOT NULL COMMENT '中国的省份',
    `count` INT(11) NOT NULL DEFAULT 0 COMMENT '数量',
    PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;