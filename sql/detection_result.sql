USE attack_detection;

DROP TABLE IF EXISTS `detection_result`;

CREATE TABLE detection_result(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `str` TEXT NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
