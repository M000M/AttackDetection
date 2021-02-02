/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : 127.0.0.1:3306
 Source Schema         : attack_detection

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 31/01/2021 21:15:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for containers_info
-- ----------------------------
DROP TABLE IF EXISTS `containers_info`;
CREATE TABLE `containers_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET latin1 NOT NULL COMMENT 'Container Name',
  `container_id` varchar(200) CHARACTER SET latin1 NOT NULL COMMENT 'Container ID',
  `image` varchar(200) CHARACTER SET latin1 NOT NULL COMMENT 'Image of Container',
  `state` varchar(20) CHARACTER SET latin1 NOT NULL COMMENT 'Current State of Container',
  `status` varchar(100) CHARACTER SET latin1 NOT NULL COMMENT 'Description of Container State',
  `log_path` varchar(255) CHARACTER SET latin1 NOT NULL COMMENT 'Volume Path of Container',
  `private_port` int(11) NOT NULL COMMENT 'Container Private Port',
  `public_port` int(11) NOT NULL COMMENT 'Exposed Port',
  `host` varchar(50) CHARACTER SET latin1 NOT NULL COMMENT 'Where is the Container',
  `create_time` datetime NOT NULL,
  `valid` int(2) NOT NULL COMMENT '1: valid; 0: deleted',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
