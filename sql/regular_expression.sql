USE attack_detection;

DROP TABLE IF EXISTS `regular_expression`;

CREATE TABLE regular_expression(
    `id` INT NOT NULL AUTO_INCREMENT,
    `expression` text NOT NULL,
    `type` INT DEFAULT 0,
    `valid` INT DEFAULT 1,
    `desc` VARCHAR(100) NULL,
    `field` VARCHAR(20) DEFAULT "other" NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
