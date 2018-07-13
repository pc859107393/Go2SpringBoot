/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost
 Source Database       : cc_db

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : utf-8

 Date: 07/13/2018 15:16:30 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `cc_user`
-- ----------------------------
DROP TABLE IF EXISTS `cc_user`;
CREATE TABLE `cc_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称',
  `login_name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '-1' COMMENT '用户登录名称',
  `password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '-1' COMMENT '用户密码，建议算法：sha256(MD5(明文)+salt)',
  `duty` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户职位',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `used` tinyint(1) NOT NULL DEFAULT '1' COMMENT '账户是否可用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name` (`login_name`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ----------------------------
--  Records of `cc_user`
-- ----------------------------
BEGIN;
INSERT INTO `cc_user` VALUES ('16', '超级管理员', 'pc859107393', '1b4d2f7c7438009f6bb8154b96e3c75f6a161912fc7ca8b6eb095776499363f7', 'admin', '2018-07-13 03:12:01', '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
