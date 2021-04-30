USE attack_detection;

DROP TABLE IF EXISTS `statistics`;

CREATE TABLE statistics(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `ip` VARCHAR(50) NOT NULL,
    `time` DATE NOT NULL,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;