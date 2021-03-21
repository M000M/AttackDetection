CREATE DATABASE IF NOT EXISTS `attack_detection`;

USE `attack_detection`;

DROP TABLE IF EXISTS `china_ip_location`;

CREATE TABLE china_ip_location(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `ip` varchar(20) NOT NULL COMMENT 'IP地址',
    `province` varchar(10) NOT NULL COMMENT '中国的省份',
    `count` int(11) NOT NULL DEFAULT 0 COMMENT '同一个IP的数量',
    PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;