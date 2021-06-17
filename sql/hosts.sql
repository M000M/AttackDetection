USE attack_detection;

DROP TABLE IF EXISTS `hosts`;

CREATE TABLE `hosts`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `ip` varchar(50) NOT NULL,
    `port` int NOT NULL,
    `status` int NOT NULL DEFAULT (0),
    `state` int NOT NULL DEFAULT (1),
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
