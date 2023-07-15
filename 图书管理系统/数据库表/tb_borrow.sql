/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 80013
Source Host           : localhost:3306
Source Database       : db_lms

Target Server Type    : MYSQL
Target Server Version : 80013
File Encoding         : 65001

Date: 2023-07-15 16:22:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_borrow
-- ----------------------------
DROP TABLE IF EXISTS `tb_borrow`;
CREATE TABLE `tb_borrow` (
  `ISBN` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '书号',
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '借阅状态',
  `borrow_time` datetime DEFAULT NULL COMMENT '借阅时的时间',
  `return_due_time` datetime DEFAULT NULL COMMENT '应归还时间',
  `return_real_time` datetime DEFAULT NULL COMMENT '实际归还时间',
  PRIMARY KEY (`ISBN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of tb_borrow
-- ----------------------------
INSERT INTO `tb_borrow` VALUES ('11', '已借阅', '2023-08-08 10:00:00', '2023-09-08 10:00:00', null);
INSERT INTO `tb_borrow` VALUES ('6', '已借阅', '2023-07-07 10:00:00', '2023-08-07 10:00:00', null);
INSERT INTO `tb_borrow` VALUES ('8', '已借阅', '2023-07-13 10:37:20', '2023-07-15 08:12:50', null);
INSERT INTO `tb_borrow` VALUES ('9', '已借阅', '2023-07-07 10:00:00', '2023-08-07 10:00:00', null);
