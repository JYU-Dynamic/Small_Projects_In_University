/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 80013
Source Host           : localhost:3306
Source Database       : db_lms

Target Server Type    : MYSQL
Target Server Version : 80013
File Encoding         : 65001

Date: 2023-07-15 16:21:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_book
-- ----------------------------
DROP TABLE IF EXISTS `tb_book`;
CREATE TABLE `tb_book` (
  `ISBN` int(10) NOT NULL AUTO_INCREMENT COMMENT '书号',
  `book_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '书名',
  `price` double(10,2) NOT NULL COMMENT '价格',
  `type` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '类型',
  `author` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '作者',
  `press` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '出版社',
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '借阅状态',
  PRIMARY KEY (`ISBN`)
) ENGINE=InnoDB AUTO_INCREMENT=100005 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of tb_book
-- ----------------------------
INSERT INTO `tb_book` VALUES ('1', '三体1：地球往事', '45.00', '科幻', '刘慈欣', '重庆出版社', '可借阅');
INSERT INTO `tb_book` VALUES ('2', '三体2：黑暗森林', '50.00', '科幻', '刘慈欣', '重庆出版社', '已借阅');
INSERT INTO `tb_book` VALUES ('3', '转生成为程序员', '899.00', '惊悚', '小龙', '嘉应计算机学院', '可借阅');
INSERT INTO `tb_book` VALUES ('4', '呆呆历险记', '99.00', '冒险', '小辉', '嘉应计算机学院', '可借阅');
INSERT INTO `tb_book` VALUES ('5', '小帅历险记', '50.00', '冒险', '小帅', '嘉应计算机学院', '可借阅');
INSERT INTO `tb_book` VALUES ('6', '原神之旅', '599.00', '冒险', '大伟哥', '米哈游', '已借阅');
INSERT INTO `tb_book` VALUES ('7', '蟑螂之广东拖鞋危机', '26.00', '热血', '蟑螂', '下水道出版社', '已借阅');
INSERT INTO `tb_book` VALUES ('8', '星穹铁道之旅', '499.00', '冒险', '大伟哥', '米哈游', '可借阅');
