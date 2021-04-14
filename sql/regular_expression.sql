USE attack_detection;

DROP TABLE IF EXISTS `regular_expression`;

CREATE TABLE regular_expression(
    `id` INT NOT NULL AUTO_INCREMENT,
    `expression` varchar(100) NOT NULL,
    `type` INT default 0,
    `valid` INT default 1,
    `desc` VARCHAR(100) NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
