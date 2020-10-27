/*
Navicat MySQL Data Transfer

Source Server         : 128
Source Server Version : 50644
Source Host           : 192.168.200.128:3306
Source Database       : changgou_config

Target Server Type    : MYSQL
Target Server Version : 50644
File Encoding         : 65001

Date: 2019-07-01 16:10:44
*/
CREATE DATABASE  IF NOT EXISTS `changgou_config`  DEFAULT CHARACTER SET utf8 ;

USE `changgou_config`;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_freight_template
-- ----------------------------
DROP TABLE IF EXISTS `tb_freight_template`;
CREATE TABLE `tb_freight_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) DEFAULT NULL COMMENT '模板名称',
  `type` char(1) DEFAULT NULL COMMENT '计费方式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_freight_template
-- ----------------------------
