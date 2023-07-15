/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 80013
Source Host           : localhost:3306
Source Database       : db_lms

Target Server Type    : MYSQL
Target Server Version : 80013
File Encoding         : 65001

Date: 2023-07-15 16:22:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '姓名',
  `gender` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '性别',
  `age` int(4) DEFAULT NULL COMMENT '年龄',
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', '管理员', '草履虫', '99', 'admin', '123456');
INSERT INTO `tb_user` VALUES ('2', '小美', '女', '18', 'xiaomei', '123456');
INSERT INTO `tb_user` VALUES ('3', '小帅', '男', '18', 'xiaoshuai', '123456');
INSERT INTO `tb_user` VALUES ('4', '小画', '女', '27', 'xiaohua', '123456');
INSERT INTO `tb_user` VALUES ('6', '小呆', '男', '18', 'xiaodai', '123456');
