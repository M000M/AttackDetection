USE attack_detection;

DROP TABLE IF EXISTS `position`;

CREATE TABLE `position`(
    `id` INT NOT NULL AUTO_INCREMENT,
    `last_position` BIGINT DEFAULT 0 NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
