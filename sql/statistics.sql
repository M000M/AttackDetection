USE attack_detection;

DROP TABLE IF EXISTS `statistics`;

CREATE TABLE statistics(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `ip` VARCHAR(50) NOT NULL,
    `count` INT  NOT NULL DEFAULT 0,
    `date` DATE NOT NULL,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table statistics add index (date);