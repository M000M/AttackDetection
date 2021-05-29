use attack_detection;

drop table if exists `attack_logs`;

create table `attack_logs`(
    `id` bigint not null auto_increment,
    `message` text not null,
    `hash` text not null,
    primary key(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;