/*
Navicat MySQL Data Transfer

Source Server         : 128
Source Server Version : 50644
Source Host           : 192.168.200.128:3306
Source Database       : changgou_business

Target Server Type    : MYSQL
Target Server Version : 50644
File Encoding         : 65001

Date: 2019-07-01 16:10:31
*/

CREATE DATABASE  IF NOT EXISTS `changgou_business`  DEFAULT CHARACTER SET utf8 ;
USE `changgou_business`;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_activity
-- ----------------------------
DROP TABLE IF EXISTS `tb_activity`;
CREATE TABLE `tb_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(200) DEFAULT NULL COMMENT '活动标题',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `status` char(1) DEFAULT NULL COMMENT '状态',
  `content` text COMMENT '活动内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_activity
-- ----------------------------

-- ----------------------------
-- Table structure for tb_ad
-- ----------------------------
DROP TABLE IF EXISTS `tb_ad`;
CREATE TABLE `tb_ad` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) DEFAULT NULL COMMENT '广告名称',
  `position` varchar(50) DEFAULT NULL COMMENT '广告位置',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '到期时间',
  `status` char(1) DEFAULT NULL COMMENT '状态',
  `image` varchar(100) DEFAULT NULL COMMENT '图片地址',
  `url` varchar(100) DEFAULT NULL COMMENT 'URL',
  `remarks` varchar(1000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_ad
-- ----------------------------
INSERT INTO `tb_ad` VALUES ('1', '轮播图1', 'web_index_lb', '2019-02-12 15:28:51', '2019-02-16 15:28:56', '1', 'img/banner1.jpg', 'img/banner1.jpg', null);
INSERT INTO `tb_ad` VALUES ('2', '轮播图2', 'web_index_lb', '2019-02-14 15:29:42', '2019-02-16 15:29:45', '1', 'img/banner2.jpg', 'img/banner2.jpg', null);
INSERT INTO `tb_ad` VALUES ('3', '轮播图3', 'web_index_lb', '2019-02-06 15:29:59', '2019-02-23 15:30:02', '1', 'img/banner3.jpg', 'img/banner3.jpg', null);
INSERT INTO `tb_ad` VALUES ('4', '轮播图4', 'web_index_lb', '2019-02-06 15:29:59', '2029-07-13 17:46:09', '1', 'img/banner1.jpg', 'img/banner1.jpg', null);
INSERT INTO `tb_ad` VALUES ('5', '轮播图5', 'web_index_lb', '2019-02-06 15:29:59', '2029-07-13 17:46:09', '1', 'img/banner2.jpg', 'img/banner2.jpg', null);
INSERT INTO `tb_ad` VALUES ('6', '轮播图6', 'web_index_lb', '2019-02-06 15:29:59', '2029-07-13 17:46:09', '1', 'img/banner3.jpg', 'img/banner3.jpg', null);

-- ----------------------------
-- Table structure for tb_content
-- ----------------------------
DROP TABLE IF EXISTS `tb_content`;
CREATE TABLE `tb_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `categoryId` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `pic` varchar(500) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `sortOrder` int(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_content
-- ----------------------------
INSERT INTO `tb_content` VALUES ('1', '1', '1元秒月饼', 'http://www.163.com', 'http://img10.360buyimg.com/babel/s590x470_jfs/t1/74905/33/1073/45881/5cf4fad9E5d088a29/38027cf306a69a7a.jpg!q90!cc_590x470.webp', 'qwerqwerqewrqwerqwerqwer', '1', '1');
INSERT INTO `tb_content` VALUES ('2', '1', '自行车', 'http://www.baidu.com', 'http://img1.360buyimg.com/pop/s590x470_jfs/t1/33037/16/5207/90009/5cb991bbE28dd89f4/7b435018f5639cad.jpg!q90!cc_590x470.webp', '11111111122222222', '1', '2');
