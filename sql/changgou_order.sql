/*
Navicat MySQL Data Transfer

Source Server         : 128
Source Server Version : 50644
Source Host           : 192.168.200.128:3306
Source Database       : changgou_order

Target Server Type    : MYSQL
Target Server Version : 50644
File Encoding         : 65001

Date: 2019-07-01 16:11:08
*/
CREATE DATABASE  IF NOT EXISTS `changgou_order`   DEFAULT CHARACTER SET utf8;

USE `changgou_order`;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_category_report
-- ----------------------------
DROP TABLE IF EXISTS `tb_category_report`;
CREATE TABLE `tb_category_report` (
  `category_id1` int(11) NOT NULL COMMENT '1级分类',
  `category_id2` int(11) NOT NULL COMMENT '2级分类',
  `category_id3` int(11) NOT NULL COMMENT '3级分类',
  `count_date` date NOT NULL COMMENT '统计日期',
  `num` int(11) DEFAULT NULL COMMENT '销售数量',
  `money` int(11) DEFAULT NULL COMMENT '销售额',
  PRIMARY KEY (`category_id1`,`category_id2`,`category_id3`,`count_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_category_report
-- ----------------------------
INSERT INTO `tb_category_report` VALUES ('1', '4', '5', '2019-01-26', '1', '300');
INSERT INTO `tb_category_report` VALUES ('74', '7', '8', '2019-01-26', '5', '900');

-- ----------------------------
-- Table structure for tb_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order` (
  `id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '订单id',
  `total_num` int(11) DEFAULT NULL COMMENT '数量合计',
  `total_money` int(11) DEFAULT NULL COMMENT '金额合计',
  `pre_money` int(11) DEFAULT NULL COMMENT '优惠金额',
  `post_fee` int(11) DEFAULT NULL COMMENT '邮费',
  `pay_money` int(11) DEFAULT NULL COMMENT '实付金额',
  `pay_type` varchar(1) COLLATE utf8_bin DEFAULT NULL COMMENT '支付类型，1、在线支付、0 货到付款',
  `create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '订单更新时间',
  `pay_time` datetime DEFAULT NULL COMMENT '付款时间',
  `consign_time` datetime DEFAULT NULL COMMENT '发货时间',
  `end_time` datetime DEFAULT NULL COMMENT '交易完成时间',
  `close_time` datetime DEFAULT NULL COMMENT '交易关闭时间',
  `shipping_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '物流名称',
  `shipping_code` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '物流单号',
  `username` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '用户名称',
  `buyer_message` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '买家留言',
  `buyer_rate` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否评价',
  `receiver_contact` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '收货人',
  `receiver_mobile` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '收货人手机',
  `receiver_address` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '收货人地址',
  `source_type` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '订单来源：1:web，2：app，3：微信公众号，4：微信小程序  5 H5手机页面',
  `transaction_id` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '交易流水号',
  `order_status` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '订单状态',
  `pay_status` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '支付状态',
  `consign_status` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '发货状态',
  `is_delete` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`),
  KEY `status` (`order_status`),
  KEY `payment_type` (`pay_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tb_order
-- ----------------------------
INSERT INTO `tb_order` VALUES ('1', null, null, null, null, null, '1', '2017-08-24 20:42:25', '2017-08-24 20:42:25', null, null, null, null, null, null, 'lijialong', null, null, '李嘉诚', '13900112222', '金燕龙办公楼', null, null, '0', null, null, '1');
INSERT INTO `tb_order` VALUES ('10', null, null, null, null, '267', '1', '2017-08-24 21:01:11', '2017-08-24 21:01:11', null, null, null, null, null, null, 'lijialong', null, null, '李嘉诚', '13900112222', '金燕龙办公楼', null, null, '0', null, null, null);
INSERT INTO `tb_order` VALUES ('1028501748186087424', null, null, null, null, '4329', '1', '2018-08-12 12:41:40', null, null, null, null, null, null, null, 'lijialong', null, null, '叶问', '999111', '咏春武馆总部', null, null, '1', '1', null, null);
INSERT INTO `tb_order` VALUES ('1028501749566013440', null, null, null, null, '0', '1', '2018-08-12 12:41:40', null, null, null, null, null, null, null, 'lijialong', null, null, '叶问', '999111', '咏春武馆总部', null, null, '1', '1', null, null);
INSERT INTO `tb_order` VALUES ('1028624753851629569', null, null, null, null, '0', '1', '2018-08-12 20:50:26', null, '2019-01-26 14:56:56', null, null, null, null, null, 'lijialong', null, null, '叶问', '999111', '咏春武馆总部', null, '1028624753851629568', '1', '1', null, null);
INSERT INTO `tb_order` VALUES ('1028625576669216769', '1', null, null, null, '0', '1', '2018-08-12 20:53:43', null, '2019-01-26 14:56:56', null, null, null, null, null, 'lijialong', null, null, '叶问', '999111', '咏春武馆总部', null, '1028625576669216768', '2', '1', null, null);
INSERT INTO `tb_order` VALUES ('1029536196264460289', null, null, null, null, '0', '1', '2018-08-15 09:12:11', null, '2019-01-26 14:56:51', null, null, null, null, null, 'lijialong', null, null, '李小龙', '11011011', '永春武馆', null, '1029536196264460288', '1', '1', null, null);
INSERT INTO `tb_order` VALUES ('1029540851476332545', '1', '1', null, null, '0', '1', '2018-08-15 09:30:41', null, '2018-08-15 09:30:48', null, null, null, null, null, 'lijialong', null, null, '李小龙', '11011011', '永春武馆', null, '1029540851476332544', '2', '1', null, null);
INSERT INTO `tb_order` VALUES ('11', null, null, null, null, '300', '1', '2017-08-24 21:01:11', '2017-08-24 21:01:11', null, null, null, null, null, null, 'lijialong', null, null, '李嘉诚', '13900112222', '金燕龙办公楼', null, null, '0', null, null, null);
INSERT INTO `tb_order` VALUES ('1106089444903096320', '5', '6995', null, null, null, '1', '2019-03-14 15:07:28', null, null, null, null, null, null, null, 'lijialong', null, null, '李嘉诚', '13900112222', '金燕龙办公楼', null, null, '0', '0', '0', null);
INSERT INTO `tb_order` VALUES ('1106089983590141952', '1', '1399', null, null, null, '1', '2019-03-14 15:09:36', null, null, null, null, null, null, null, 'lijialong', null, null, '李佳星', '13301212233', '中腾大厦', null, null, '0', '0', '0', null);
INSERT INTO `tb_order` VALUES ('1106104092389937152', '2', '298', null, null, '298', '1', '2019-03-14 16:05:40', null, null, null, null, null, null, null, 'lijialong', null, null, '李佳星', '13301212233', '中腾大厦', null, null, '0', '0', '0', null);
INSERT INTO `tb_order` VALUES ('1106107407773339648', '69', '10281', '0', null, '10281', '1', '2019-03-14 16:18:51', null, null, null, null, null, null, null, 'lijialong', null, null, '李佳星', '13301212233', '中腾大厦', null, null, '0', '0', '0', null);
INSERT INTO `tb_order` VALUES ('1106109621740900352', '6', '11394', '4000', null, '7394', '1', '2019-03-14 16:27:39', null, null, null, null, null, null, null, 'lijialong', null, null, '李佳星', '13301212233', '中腾大厦', null, null, '0', '0', '0', null);
INSERT INTO `tb_order` VALUES ('1106111554773979136', '7', '13293', '4000', null, '9293', '1', '2019-03-14 16:35:19', null, null, null, null, null, null, null, 'lijialong', null, null, '李佳星', '13301212233', '中腾大厦', null, null, '0', '0', '0', null);
INSERT INTO `tb_order` VALUES ('1106113685203587072', '7', '13293', '4000', null, '9293', '1', '2019-03-14 16:43:47', null, null, null, null, null, null, null, 'lijialong', null, null, '李佳星', '13301212233', '中腾大厦', null, null, '0', '0', '0', null);
INSERT INTO `tb_order` VALUES ('1142678950560923648', '1', null, '30000', null, '37100', '1', '2019-06-23 06:21:06', null, null, null, null, null, null, null, 'heima', null, null, '黑马收货', '13812312312', '北京市昌平区金燕龙办公楼', '1', null, '0', '0', '0', null);
INSERT INTO `tb_order` VALUES ('1142691199161729024', '1', null, '10000', null, '30800', '1', '2019-06-23 07:09:47', null, null, null, null, null, null, null, 'heima', null, null, '黑马收货', '13812312312', '北京市昌平区金燕龙办公楼', '1', null, '0', '0', '0', null);
INSERT INTO `tb_order` VALUES ('1142716996220092416', '1', null, '10000', null, '30800', null, '2019-06-23 08:52:17', null, null, null, null, null, null, null, 'heima', null, null, '冯提莫', '15922333201', '北京市海淀区三环内中关村软件园9号楼', '1', null, '0', '0', '0', null);
INSERT INTO `tb_order` VALUES ('12', null, null, null, null, '3', '1', '2017-08-24 21:05:56', '2017-08-24 21:05:56', null, null, null, null, null, null, 'lijialong', null, null, '李嘉诚', '13900112222', '金燕龙办公楼', null, null, '0', null, null, null);
INSERT INTO `tb_order` VALUES ('13', null, null, null, null, '267', '1', '2017-08-24 21:05:56', '2017-08-24 21:05:56', null, null, null, null, null, null, 'lijialong', null, null, '李嘉诚', '13900112222', '金燕龙办公楼', null, null, '0', null, null, null);
INSERT INTO `tb_order` VALUES ('14', null, null, null, null, '300', '1', '2017-08-24 21:05:56', '2017-08-24 21:05:56', null, null, null, null, null, null, 'lijialong', null, null, '李嘉诚', '13900112222', '金燕龙办公楼', null, null, '0', null, null, null);
INSERT INTO `tb_order` VALUES ('15', null, null, null, null, '3', '1', '2017-08-24 23:07:38', '2017-08-24 23:07:38', null, null, null, null, null, null, 'lijialong', null, null, '李嘉诚', '13900112222', '金燕龙办公楼', null, null, '0', null, null, null);
INSERT INTO `tb_order` VALUES ('16', null, null, null, null, '267', '1', '2017-08-24 23:07:38', '2017-08-24 23:07:38', null, null, null, null, null, null, 'lijialong', null, null, '李嘉诚', '13900112222', '金燕龙办公楼', null, null, '0', null, null, null);
INSERT INTO `tb_order` VALUES ('17', null, null, null, null, '300', '1', '2017-08-24 23:07:38', '2017-08-24 23:07:38', null, null, null, null, null, null, 'lijialong', null, null, '李嘉诚', '13900112222', '金燕龙办公楼', null, null, '0', null, null, null);
INSERT INTO `tb_order` VALUES ('18', null, null, null, null, '178', '1', '2017-08-25 11:59:03', '2017-08-25 11:59:03', null, null, null, null, null, null, 'lijialong', null, null, null, null, null, null, null, '1', null, null, null);
INSERT INTO `tb_order` VALUES ('19', null, null, null, null, '2', '1', '2017-08-25 11:59:03', '2017-08-25 11:59:03', null, null, null, null, null, null, 'lijialong', null, null, null, null, null, null, null, '1', null, null, null);
INSERT INTO `tb_order` VALUES ('2', null, null, null, null, null, '1', '2017-08-24 20:44:03', '2017-08-24 20:44:03', null, null, null, null, null, null, 'lijialong', null, null, '李嘉诚', '13900112222', '金燕龙办公楼', null, null, '4', null, null, '0');
INSERT INTO `tb_order` VALUES ('20', null, null, null, null, '178', '1', '2017-08-25 11:59:03', '2017-08-25 11:59:03', null, null, null, null, null, null, 'lijialong', null, null, null, null, null, null, null, '1', null, null, null);
INSERT INTO `tb_order` VALUES ('21', null, null, null, null, '178', '1', '2017-08-25 23:26:10', '2017-08-25 23:26:10', null, null, null, null, null, null, 'lijialong', null, null, '李佳星', '13301212233', '中腾大厦', null, null, '1', null, null, null);
INSERT INTO `tb_order` VALUES ('22', null, null, null, null, '2', '1', '2017-08-25 23:26:11', '2017-08-25 23:26:11', null, null, null, null, null, null, 'lijialong', null, null, '李佳星', '13301212233', '中腾大厦', null, null, '1', null, null, null);
INSERT INTO `tb_order` VALUES ('23', null, null, null, null, '178', '1', '2017-08-25 23:26:11', '2017-08-25 23:26:11', null, null, null, null, null, null, 'lijialong', null, null, '李佳星', '13301212233', '中腾大厦', null, null, '1', null, null, null);
INSERT INTO `tb_order` VALUES ('24', null, null, null, null, '2', '1', '2017-08-25 23:28:10', '2017-08-25 23:28:10', null, null, null, null, null, null, 'lijialong', null, null, '李佳星', '13301212233', '中腾大厦', null, null, '1', null, null, null);
INSERT INTO `tb_order` VALUES ('25', null, null, null, null, '1', '1', '2017-08-25 23:49:18', '2017-08-25 23:49:18', null, null, null, null, null, null, 'lijialong', null, null, '李佳星', '13301212233', '中腾大厦', null, null, '1', null, null, null);
INSERT INTO `tb_order` VALUES ('26', null, null, null, null, '0', '1', '2017-08-26 00:06:31', '2017-08-26 00:06:31', null, null, null, null, null, null, 'lijialong', null, null, '李佳红', '13700221122', '修正大厦', null, null, '1', null, null, null);
INSERT INTO `tb_order` VALUES ('27', null, null, null, null, '0', '1', '2017-08-26 00:10:13', '2017-08-26 00:10:13', null, null, null, null, null, null, 'lijialong', null, null, '李佳星', '13301212233', '中腾大厦', null, null, '1', null, null, null);
INSERT INTO `tb_order` VALUES ('28', null, null, null, null, '0', '1', '2017-08-26 00:17:53', '2017-08-26 00:17:53', null, null, null, null, null, null, 'lijialong', null, null, '李佳星', '13301212233', '中腾大厦', null, null, '1', null, null, null);
INSERT INTO `tb_order` VALUES ('29', null, null, null, null, '0', '1', '2017-08-26 00:19:56', '2017-08-26 00:19:56', null, null, null, null, null, null, 'lijialong', null, null, '李佳星', '13301212233', '中腾大厦', null, null, '1', null, null, null);
INSERT INTO `tb_order` VALUES ('3', null, null, null, null, '3', '1', '2017-08-24 20:46:10', '2017-08-24 20:46:10', null, null, null, null, null, null, 'lijialong', null, null, '李嘉诚', '13900112222', '金燕龙办公楼', null, null, '4', null, null, '0');
INSERT INTO `tb_order` VALUES ('30', null, null, null, null, '0', '1', '2017-08-26 00:37:47', '2017-08-26 00:37:47', null, null, null, null, null, null, 'lijialong', null, null, '李佳星', '13301212233', '中腾大厦', null, null, '1', null, null, null);
INSERT INTO `tb_order` VALUES ('31', null, null, null, null, '0', '1', '2017-08-26 00:37:47', '2017-08-26 00:37:47', null, null, null, null, null, null, 'lijialong', null, null, '李佳星', '13301212233', '中腾大厦', null, null, '1', null, null, null);
INSERT INTO `tb_order` VALUES ('32', null, null, null, null, '0', '1', '2017-08-26 00:41:13', '2017-08-26 00:41:13', null, null, null, null, null, null, 'lijialong', null, null, '李佳星', '13301212233', '中腾大厦', null, null, '2', null, null, null);
INSERT INTO `tb_order` VALUES ('33', null, null, null, null, '0', '1', '2017-08-26 00:41:14', '2017-08-26 00:41:14', null, null, null, null, null, null, 'lijialong', null, null, '李佳星', '13301212233', '中腾大厦', null, null, '2', null, null, null);
INSERT INTO `tb_order` VALUES ('34', null, null, null, null, '0', '1', '2017-08-26 11:57:26', '2017-08-26 11:57:26', null, null, null, null, null, null, 'lijialong', null, null, null, null, null, null, null, '1', null, null, null);
INSERT INTO `tb_order` VALUES ('35', null, null, null, null, '0', '1', '2017-08-26 12:21:39', '2017-08-26 12:21:39', null, null, null, null, null, null, 'lijialong', null, null, null, null, null, null, null, '1', null, null, null);
INSERT INTO `tb_order` VALUES ('36', null, null, null, null, '0', '1', '2017-08-26 12:34:46', '2017-08-26 12:34:46', null, null, null, null, null, null, 'lijialong', null, null, null, null, null, null, null, '1', null, null, null);
INSERT INTO `tb_order` VALUES ('37', null, null, null, null, '0', '1', '2017-08-26 12:47:44', '2017-08-26 12:47:44', null, null, null, null, null, null, 'lijialong', null, null, null, null, null, null, null, '2', null, null, null);
INSERT INTO `tb_order` VALUES ('38', null, null, null, null, '0', '1', '2017-08-26 12:47:44', '2017-08-26 12:47:44', null, null, null, null, null, null, 'lijialong', null, null, null, null, null, null, null, '2', null, null, null);
INSERT INTO `tb_order` VALUES ('4', null, null, null, null, '267', '1', '2019-01-26 20:46:11', '2017-08-24 20:46:11', null, null, null, null, null, null, 'lijialong', null, null, '李嘉诚', '13900112222', '金燕龙办公楼', null, null, '0', null, null, '0');
INSERT INTO `tb_order` VALUES ('5', null, null, null, null, '300', '1', '2017-08-24 20:46:11', '2017-08-24 20:46:11', null, null, null, null, null, null, 'lijialong', null, null, '李嘉诚', '13900112222', '金燕龙办公楼', null, null, '0', null, null, null);
INSERT INTO `tb_order` VALUES ('6', null, null, null, null, '3', '1', '2017-08-24 20:46:40', '2017-08-24 20:46:40', null, null, null, null, null, null, 'lijialong', null, null, '李嘉诚', '13900112222', '金燕龙办公楼', null, null, '0', null, null, null);
INSERT INTO `tb_order` VALUES ('7', null, null, null, null, '267', '1', '2017-08-24 20:46:40', '2017-08-24 20:46:40', null, null, null, null, null, null, 'lijialong', null, null, '李嘉诚', '13900112222', '金燕龙办公楼', null, null, '0', null, null, null);
INSERT INTO `tb_order` VALUES ('8', null, null, null, null, '300', '1', '2017-08-24 20:46:40', '2017-08-24 20:46:40', null, null, null, null, null, null, 'lijialong', null, null, '李嘉诚', '13900112222', '金燕龙办公楼', null, null, '0', null, null, null);
INSERT INTO `tb_order` VALUES ('9', null, null, null, null, '3', '1', '2017-08-24 21:01:10', '2017-08-24 21:01:10', null, null, null, null, null, null, 'lijialong', null, null, '李嘉诚', '13900112222', '金燕龙办公楼', null, null, '0', null, null, null);
INSERT INTO `tb_order` VALUES ('918159799198212096', null, null, null, null, '400', '1', '2017-10-12 01:02:09', '2017-10-12 01:02:09', null, null, null, null, null, null, 'lijialong', null, null, '叶问', '999111', '咏春武馆总部', null, null, '1', null, null, null);
INSERT INTO `tb_order` VALUES ('918334996291301376', null, null, null, null, '2004', '2', '2017-10-12 12:38:19', '2017-10-12 12:38:19', null, null, null, null, null, null, 'lijialong', null, null, '李小龙', '11011011', '永春武馆', '2', null, '1', null, null, null);
INSERT INTO `tb_order` VALUES ('918334996698148864', null, null, null, null, '1798', '2', '2017-10-12 12:38:19', '2017-10-12 12:38:19', null, null, null, null, null, null, 'lijialong', null, null, '李小龙', '11011011', '永春武馆', '2', null, '1', null, null, null);
INSERT INTO `tb_order` VALUES ('918773289399160832', null, null, null, null, '200', '1', '2017-10-13 17:39:56', '2017-10-13 17:39:56', null, null, null, null, null, null, 'lijialong', null, null, '李小龙', '11011011', '永春武馆', null, null, '1', null, null, null);
INSERT INTO `tb_order` VALUES ('918780408353546240', null, null, null, null, '0', '1', '2017-10-13 18:08:14', '2017-10-13 18:08:14', null, null, null, null, null, null, 'lijialong', null, null, '李小龙', '11011011', '永春武馆', null, null, '2', null, null, null);
INSERT INTO `tb_order` VALUES ('918806410454654976', null, null, null, null, '0', '1', '2017-10-13 19:51:33', '2017-10-13 19:51:33', null, null, null, null, null, null, 'lijialong', null, null, '李佳星', '13301212233', '中腾大厦', null, null, '2', null, null, null);
INSERT INTO `tb_order` VALUES ('918833485639081984', null, null, null, null, '0', '1', '2017-10-13 21:39:08', '2017-10-13 21:39:08', null, null, null, null, null, null, 'lijialong', null, null, '李佳星', '13301212233', '中腾大厦', null, null, '1', null, null, null);
INSERT INTO `tb_order` VALUES ('918835712441212928', null, null, null, null, '0', '1', '2017-10-13 21:47:59', '2017-10-13 21:47:59', null, null, null, null, null, null, 'lijialong', null, null, '李佳星', '13301212233', '中腾大厦', null, null, '2', '1', null, null);
INSERT INTO `tb_order` VALUES ('919055624854081536', null, null, null, null, '0', '1', '2017-10-14 12:21:50', '2017-10-14 12:21:50', null, null, null, null, null, null, 'lijialong', null, null, '李佳星', '13301212233', '中腾大厦', '2', null, '1', '1', null, null);
INSERT INTO `tb_order` VALUES ('919059760869863424', null, null, null, null, '0', '1', '2017-10-14 12:38:16', '2017-10-14 12:38:16', null, null, null, null, null, null, 'lijialong', null, null, '李嘉诚', '13900112222', '金燕龙办公楼', '2', null, '2', '1', null, null);

-- ----------------------------
-- Table structure for tb_order_config
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_config`;
CREATE TABLE `tb_order_config` (
  `id` int(11) NOT NULL COMMENT 'ID',
  `order_timeout` int(11) DEFAULT NULL COMMENT '正常订单超时时间（分）',
  `seckill_timeout` int(11) DEFAULT NULL COMMENT '秒杀订单超时时间（分）',
  `take_timeout` int(11) DEFAULT NULL COMMENT '自动收货（天）',
  `service_timeout` int(11) DEFAULT NULL COMMENT '售后期限',
  `comment_timeout` int(11) DEFAULT NULL COMMENT '自动五星好评',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_order_config
-- ----------------------------
INSERT INTO `tb_order_config` VALUES ('1', '60', '10', '15', '7', '7');

-- ----------------------------
-- Table structure for tb_order_item
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_item`;
CREATE TABLE `tb_order_item` (
  `id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT 'ID',
  `category_id1` int(11) DEFAULT NULL COMMENT '1级分类',
  `category_id2` int(11) DEFAULT NULL COMMENT '2级分类',
  `category_id3` int(11) DEFAULT NULL COMMENT '3级分类',
  `spu_id` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT 'SPU_ID',
  `sku_id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT 'SKU_ID',
  `order_id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '订单ID',
  `name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `price` int(20) DEFAULT NULL COMMENT '单价',
  `num` int(10) DEFAULT NULL COMMENT '数量',
  `money` int(20) DEFAULT NULL COMMENT '总金额',
  `pay_money` int(11) DEFAULT NULL COMMENT '实付金额',
  `image` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '图片地址',
  `weight` int(11) DEFAULT NULL COMMENT '重量',
  `post_fee` int(11) DEFAULT NULL COMMENT '运费',
  `is_return` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否退货',
  PRIMARY KEY (`id`),
  KEY `item_id` (`sku_id`),
  KEY `order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tb_order_item
-- ----------------------------
INSERT INTO `tb_order_item` VALUES ('1', null, null, null, '149187842867954', '19', '3', '3G 6', '1', '3', '3', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('10', null, null, null, '149187842867954', '19', '12', '3G 6', '1', '3', '3', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('1028501748186087425', null, null, null, '149187842867970', '1369296', '1028501748186087424', '精品女装111 联通2G 16G', '333', '13', '4329', null, 'http://192.168.25.133/group1/M00/00/00/wKgZhVteZyWAJnBZABpWgKKlOQM126.png', null, null, null);
INSERT INTO `tb_order_item` VALUES ('1028501749566013441', null, null, null, '149187842867960', '1369283', '1028501749566013440', '精品半身裙（秋款打折） 移动4G 32G', '0', '6', '0', null, 'http://192.168.25.133/group1/M00/00/00/wKgZhVnGbYuAO6AHAAjlKdWCzvg253.jpg', null, null, null);
INSERT INTO `tb_order_item` VALUES ('1028501749582790656', null, null, null, '149187842867960', '1369282', '1028501749566013440', '精品半身裙（秋款打折） 移动4G 16G', '0', '6', '0', null, 'http://192.168.25.133/group1/M00/00/00/wKgZhVnGbYuAO6AHAAjlKdWCzvg253.jpg', null, null, null);
INSERT INTO `tb_order_item` VALUES ('1028624753851629570', '1', '4', '5', '149187842867960', '1369280', '1028624753851629569', '精品半身裙（秋款打折） 移动3G 16G', '0', '1', '300', null, 'http://192.168.25.133/group1/M00/00/00/wKgZhVnGbYuAO6AHAAjlKdWCzvg253.jpg', null, null, null);
INSERT INTO `tb_order_item` VALUES ('1028625576669216770', '2', '7', '8', '149187842867960', '1369281', '1028625576669216769', '精品半身裙（秋款打折） 移动3G 32G', '0', '2', '400', null, 'http://192.168.25.133/group1/M00/00/00/wKgZhVnGbYuAO6AHAAjlKdWCzvg253.jpg', null, null, null);
INSERT INTO `tb_order_item` VALUES ('1029536196264460290', '2', '7', '8', '149187842867960', '1369281', '1029536196264460289', '精品半身裙（秋款打折） 移动3G 32G', '0', '3', '500', null, 'http://192.168.25.133/group1/M00/00/00/wKgZhVnGbYuAO6AHAAjlKdWCzvg253.jpg', null, null, null);
INSERT INTO `tb_order_item` VALUES ('1029540851476332546', '1', '2', '3', '149187842867960', '1369280', '1029540851476332545', '精品半身裙（秋款打折） 移动3G 16G', '0', '2', '1002', null, 'http://192.168.25.133/group1/M00/00/00/wKgZhVnGbYuAO6AHAAjlKdWCzvg253.jpg', null, null, null);
INSERT INTO `tb_order_item` VALUES ('11', null, null, null, '149187842867952', '18', '13', '3G 6', '89', '3', '267', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('1106089444903096321', null, null, '76', '1', '1025149', '1106089444903096320', '飞利浦（PHILIPS） 32PFL3045/T3 32英寸 高清LED液晶电视（黑色）', '1399', '5', '6995', null, 'http://img10.360buyimg.com/n1/s450x450_jfs/t3457/294/236823024/102048/c97f5825/58072422Ndd7e66c4.jpg', '0', null, null);
INSERT INTO `tb_order_item` VALUES ('1106089983590141953', null, null, '76', '1', '1025149', '1106089983590141952', '飞利浦（PHILIPS） 32PFL3045/T3 32英寸 高清LED液晶电视（黑色）', '1399', '1', '1399', null, 'http://img10.360buyimg.com/n1/s450x450_jfs/t3457/294/236823024/102048/c97f5825/58072422Ndd7e66c4.jpg', '0', null, null);
INSERT INTO `tb_order_item` VALUES ('1106104092524154880', null, null, '560', '1', '875724', '1106104092389937152', '诺基亚(NOKIA) 1050 (RM-908) 蓝色 移动联通2G手机', '149', '2', '298', '298', 'http://img10.360buyimg.com/n1/s450x450_jfs/t3457/294/236823024/102048/c97f5825/58072422Ndd7e66c4.jpg', '0', null, null);
INSERT INTO `tb_order_item` VALUES ('1106107407865614336', null, null, '560', '1', '875724', '1106107407773339648', '诺基亚(NOKIA) 1050 (RM-908) 蓝色 移动联通2G手机', '149', '69', '10281', '10281', 'http://img10.360buyimg.com/n1/s450x450_jfs/t3457/294/236823024/102048/c97f5825/58072422Ndd7e66c4.jpg', '0', null, null);
INSERT INTO `tb_order_item` VALUES ('1106109621740900353', null, null, '76', '1', '1008825', '1106109621740900352', 'TCL L40F3301B 40英寸 超窄边框蓝光LED液晶电视（珠光黑）', '1899', '6', '11394', '0', 'http://img10.360buyimg.com/n1/s450x450_jfs/t3457/294/236823024/102048/c97f5825/58072422Ndd7e66c4.jpg', '0', null, null);
INSERT INTO `tb_order_item` VALUES ('1106111554904002560', null, null, '76', '1', '1008825', '1106111554773979136', 'TCL L40F3301B 40英寸 超窄边框蓝光LED液晶电视（珠光黑）', '1899', '7', '13293', '0', 'http://img10.360buyimg.com/n1/s450x450_jfs/t3457/294/236823024/102048/c97f5825/58072422Ndd7e66c4.jpg', '0', null, null);
INSERT INTO `tb_order_item` VALUES ('1106113685337804800', null, null, '76', '1', '1008825', '1106113685203587072', 'TCL L40F3301B 40英寸 超窄边框蓝光LED液晶电视（珠光黑）', '1899', '7', '13293', '9293', 'http://img10.360buyimg.com/n1/s450x450_jfs/t3457/294/236823024/102048/c97f5825/58072422Ndd7e66c4.jpg', '0', null, null);
INSERT INTO `tb_order_item` VALUES ('1142678950783221760', null, null, '1124', '10000000616300', '100000006163', '1142678950560923648', '巴布豆(BOBDOG)柔薄悦动婴儿拉拉裤XXL码80片(15kg以上)', '67100', '1', '67100', null, 'https://m.360buyimg.com/mobilecms/s720x720_jfs/t23998/350/2363990466/222391/a6e9581d/5b7cba5bN0c18fb4f.jpg!q70.jpg.webp', '10', null, null);
INSERT INTO `tb_order_item` VALUES ('1142691200164167680', null, null, '106', '10000001516600', '100000015158', '1142691199161729024', '华为 HUAWEI 麦芒7 6G+64G 魅海蓝 全网通  前置智慧双摄  移动联通电信4G手机 双卡双待', '40800', '1', '40800', null, 'https://m.360buyimg.com/mobilecms/s720x720_jfs/t22642/312/2563982615/103706/1398b13d/5b865bb3N0409f0d0.jpg!q70.jpg.webp', '10', null, null);
INSERT INTO `tb_order_item` VALUES ('1142716997134450688', null, null, '106', '10000001516600', '100000015158', '1142716996220092416', '华为 HUAWEI 麦芒7 6G+64G 魅海蓝 全网通  前置智慧双摄  移动联通电信4G手机 双卡双待', '40800', '1', '40800', null, 'https://m.360buyimg.com/mobilecms/s720x720_jfs/t22642/312/2563982615/103706/1398b13d/5b865bb3N0409f0d0.jpg!q70.jpg.webp', '10', null, null);
INSERT INTO `tb_order_item` VALUES ('12', null, null, null, '149187842867952', '17', '14', '3G 5.5', '100', '3', '300', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('13', null, null, null, '149187842867954', '19', '15', '3G 6', '1', '3', '3', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('14', null, null, null, '149187842867952', '18', '16', '3G 6', '89', '3', '267', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('15', null, null, null, '149187842867952', '17', '17', '3G 5.5', '100', '3', '300', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('16', null, null, null, '149187842867952', '18', '18', '高端皮具护理 联通3G 6寸', '89', '2', '178', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('17', null, null, null, '149187842867954', '19', '19', '3G 6', '1', '2', '2', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('18', null, null, null, '149187842867952', '18', '20', '3G 6', '89', '2', '178', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('19', null, null, null, '149187842867952', '18', '21', '高端皮具护理 联通3G 6寸', '89', '2', '178', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('2', null, null, null, '149187842867952', '18', '4', '3G 6', '89', '3', '267', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('20', null, null, null, '149187842867954', '19', '22', '3G 6', '1', '2', '2', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('21', null, null, null, '149187842867952', '18', '23', '3G 6', '89', '2', '178', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('22', null, null, null, '149187842867954', '19', '24', '古董 移动3G 6寸', '1', '2', '2', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('23', null, null, null, '149187842867954', '19', '25', '古董 移动3G 6寸', '1', '1', '1', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('24', null, null, null, '149187842867954', '19', '26', '古董 移动3G 6寸', '0', '1', '0', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('25', null, null, null, '149187842867954', '19', '27', '古董 移动3G 6寸', '0', '1', '0', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('26', null, null, null, '149187842867954', '19', '28', '3G 6', '0', '1', '0', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('27', null, null, null, '149187842867954', '19', '29', '古董 移动3G 6寸', '0', '1', '0', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('28', null, null, null, '149187842867954', '19', '30', '古董 移动3G 6寸', '0', '1', '0', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('29', null, null, null, '149187842867952', '18', '31', '高端皮具护理 联通3G 6寸', '0', '1', '0', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('3', null, null, null, '149187842867952', '17', '5', '3G 5.5', '100', '3', '300', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('30', null, null, null, '149187842867952', '18', '32', '高端皮具护理 联通3G 6寸', '0', '1', '0', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('31', null, null, null, '149187842867954', '19', '33', '古董 移动3G 6寸', '0', '1', '0', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('32', null, null, null, '149187842867954', '19', '34', '古董 移动3G 6寸', '0', '1', '0', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('33', null, null, null, '149187842867954', '19', '35', '古董 移动3G 6寸', '0', '1', '0', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('34', null, null, null, '149187842867954', '19', '36', '古董 移动3G 6寸', '0', '1', '0', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('35', null, null, null, '149187842867954', '19', '37', '古董 移动3G 6寸', '0', '1', '0', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('36', null, null, null, '149187842867952', '18', '38', '高端皮具护理 联通3G 6寸', '0', '1', '0', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('4', null, null, null, '149187842867954', '19', '6', '3G 6', '1', '3', '3', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('5', null, null, null, '149187842867952', '18', '7', '3G 6', '89', '3', '267', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('6', null, null, null, '149187842867952', '17', '8', '3G 5.5', '100', '3', '300', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('7', null, null, null, '149187842867954', '19', '9', '3G 6', '1', '3', '3', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('8', null, null, null, '149187842867952', '18', '10', '3G 6', '89', '3', '267', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('9', null, null, null, '149187842867952', '17', '11', '3G 5.5', '100', '3', '300', null, null, null, null, null);
INSERT INTO `tb_order_item` VALUES ('918159799198212097', null, null, null, '149187842867960', '1369280', '918159799198212096', '精品半身裙（秋款打折） 移动3G 16G', '200', '2', '400', null, 'http://192.168.25.133/group1/M00/00/00/wKgZhVnGbYuAO6AHAAjlKdWCzvg253.jpg', null, null, null);
INSERT INTO `tb_order_item` VALUES ('918334996291301377', null, null, null, '149187842867960', '1369280', '918334996291301376', '精品半身裙（秋款打折） 移动3G 16G', '200', '8', '1600', null, 'http://192.168.25.133/group1/M00/00/00/wKgZhVnGbYuAO6AHAAjlKdWCzvg253.jpg', null, null, null);
INSERT INTO `tb_order_item` VALUES ('918334996622651392', null, null, null, '149187842867960', '1369281', '918334996291301376', '精品半身裙（秋款打折） 移动3G 32G', '202', '2', '404', null, 'http://192.168.25.133/group1/M00/00/00/wKgZhVnGbYuAO6AHAAjlKdWCzvg253.jpg', null, null, null);
INSERT INTO `tb_order_item` VALUES ('918334996698148865', null, null, null, '1', '1369278', '918334996698148864', '【联通合约惠机】小米 红米Note 增强版 象牙白 联通4G手机', '899', '2', '1798', null, 'http://img12.360buyimg.com/n1/s450x450_jfs/t3034/299/2060854617/119711/577e85cb/57d11b6cN1fd1194d.jpg', null, null, null);
INSERT INTO `tb_order_item` VALUES ('918773289399160833', null, null, null, '149187842867960', '1369280', '918773289399160832', '精品半身裙（秋款打折） 移动3G 16G', '200', '1', '200', null, 'http://192.168.25.133/group1/M00/00/00/wKgZhVnGbYuAO6AHAAjlKdWCzvg253.jpg', null, null, null);
INSERT INTO `tb_order_item` VALUES ('918780408353546241', null, null, null, '149187842867960', '1369280', '918780408353546240', '精品半身裙（秋款打折） 移动3G 16G', '0', '1', '0', null, 'http://192.168.25.133/group1/M00/00/00/wKgZhVnGbYuAO6AHAAjlKdWCzvg253.jpg', null, null, null);
INSERT INTO `tb_order_item` VALUES ('918806410454654977', null, null, null, '149187842867960', '1369280', '918806410454654976', '精品半身裙（秋款打折） 移动3G 16G', '0', '1', '0', null, 'http://192.168.25.133/group1/M00/00/00/wKgZhVnGbYuAO6AHAAjlKdWCzvg253.jpg', null, null, null);
INSERT INTO `tb_order_item` VALUES ('918833485639081985', null, null, null, '149187842867960', '1369280', '918833485639081984', '精品半身裙（秋款打折） 移动3G 16G', '0', '1', '0', null, 'http://192.168.25.133/group1/M00/00/00/wKgZhVnGbYuAO6AHAAjlKdWCzvg253.jpg', null, null, null);
INSERT INTO `tb_order_item` VALUES ('918835712441212929', null, null, null, '149187842867960', '1369280', '918835712441212928', '精品半身裙（秋款打折） 移动3G 16G', '0', '1', '0', null, 'http://192.168.25.133/group1/M00/00/00/wKgZhVnGbYuAO6AHAAjlKdWCzvg253.jpg', null, null, null);
INSERT INTO `tb_order_item` VALUES ('919055624854081537', null, null, null, '149187842867960', '1369280', '919055624854081536', '精品半身裙（秋款打折） 移动3G 16G', '0', '1', '0', null, 'http://192.168.25.133/group1/M00/00/00/wKgZhVnGbYuAO6AHAAjlKdWCzvg253.jpg', null, null, null);
INSERT INTO `tb_order_item` VALUES ('919059760869863425', null, null, null, '149187842867960', '1369280', '919059760869863424', '精品半身裙（秋款打折） 移动3G 16G', '0', '2', '0', null, 'http://192.168.25.133/group1/M00/00/00/wKgZhVnGbYuAO6AHAAjlKdWCzvg253.jpg', null, null, null);

-- ----------------------------
-- Table structure for tb_order_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_log`;
CREATE TABLE `tb_order_log` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `operater` varchar(50) DEFAULT NULL COMMENT '操作员',
  `operate_time` datetime DEFAULT NULL COMMENT '操作时间',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单ID',
  `order_status` char(1) DEFAULT NULL COMMENT '订单状态',
  `pay_status` char(1) DEFAULT NULL COMMENT '付款状态',
  `consign_status` char(1) DEFAULT NULL COMMENT '发货状态',
  `remarks` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_order_log
-- ----------------------------

-- ----------------------------
-- Table structure for tb_preferential
-- ----------------------------
DROP TABLE IF EXISTS `tb_preferential`;
CREATE TABLE `tb_preferential` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `buy_money` int(11) DEFAULT NULL COMMENT '消费金额',
  `pre_money` int(11) DEFAULT NULL COMMENT '优惠金额',
  `category_id` bigint(20) DEFAULT NULL COMMENT '品类ID',
  `start_time` date DEFAULT NULL COMMENT '活动开始日期',
  `end_time` date DEFAULT NULL COMMENT '活动截至日期',
  `state` varchar(1) DEFAULT NULL COMMENT '状态',
  `type` varchar(1) DEFAULT NULL COMMENT '类型1不翻倍 2翻倍',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_preferential
-- ----------------------------
INSERT INTO `tb_preferential` VALUES ('1', '10000', '3000', '106', '2019-01-16', '2029-07-13', '1', '1');
INSERT INTO `tb_preferential` VALUES ('2', '30000', '10000', '106', '2019-01-16', '2029-07-13', '1', '1');
INSERT INTO `tb_preferential` VALUES ('3', '60000', '30000', '1124', '2019-01-16', '2029-07-13', '1', '1');
INSERT INTO `tb_preferential` VALUES ('4', '10000', '4000', '1124', '2019-01-16', '2029-07-13', '1', '2');

-- ----------------------------
-- Table structure for tb_return_cause
-- ----------------------------
DROP TABLE IF EXISTS `tb_return_cause`;
CREATE TABLE `tb_return_cause` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cause` varchar(100) DEFAULT NULL COMMENT '原因',
  `seq` int(11) DEFAULT '1' COMMENT '排序',
  `status` char(1) DEFAULT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_return_cause
-- ----------------------------

-- ----------------------------
-- Table structure for tb_return_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_return_order`;
CREATE TABLE `tb_return_order` (
  `id` bigint(20) NOT NULL COMMENT '服务单号',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单号',
  `apply_time` datetime DEFAULT NULL COMMENT '申请时间',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `user_account` varchar(11) DEFAULT NULL COMMENT '用户账号',
  `linkman` varchar(20) DEFAULT NULL COMMENT '联系人',
  `linkman_mobile` varchar(11) DEFAULT NULL COMMENT '联系人手机',
  `type` char(1) DEFAULT NULL COMMENT '类型',
  `return_money` int(11) DEFAULT NULL COMMENT '退款金额',
  `is_return_freight` char(1) DEFAULT NULL COMMENT '是否退运费',
  `status` char(1) DEFAULT NULL COMMENT '申请状态',
  `dispose_time` datetime DEFAULT NULL COMMENT '处理时间',
  `return_cause` int(11) DEFAULT NULL COMMENT '退货退款原因',
  `evidence` varchar(1000) DEFAULT NULL COMMENT '凭证图片',
  `description` varchar(1000) DEFAULT NULL COMMENT '问题描述',
  `remark` varchar(1000) DEFAULT NULL COMMENT '处理备注',
  `admin_id` int(11) DEFAULT NULL COMMENT '管理员id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_return_order
-- ----------------------------

-- ----------------------------
-- Table structure for tb_return_order_item
-- ----------------------------
DROP TABLE IF EXISTS `tb_return_order_item`;
CREATE TABLE `tb_return_order_item` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `category_id` bigint(20) DEFAULT NULL COMMENT '分类ID',
  `spu_id` bigint(20) DEFAULT NULL COMMENT 'SPU_ID',
  `sku_id` bigint(20) NOT NULL COMMENT 'SKU_ID',
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `order_item_id` bigint(20) DEFAULT NULL COMMENT '订单明细ID',
  `return_order_id` bigint(20) NOT NULL COMMENT '退货订单ID',
  `title` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '标题',
  `price` int(20) DEFAULT NULL COMMENT '单价',
  `num` int(10) DEFAULT NULL COMMENT '数量',
  `money` int(20) DEFAULT NULL COMMENT '总金额',
  `pay_money` int(20) DEFAULT NULL COMMENT '支付金额',
  `image` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '图片地址',
  `weight` int(11) DEFAULT NULL COMMENT '重量',
  PRIMARY KEY (`id`),
  KEY `item_id` (`sku_id`),
  KEY `order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tb_return_order_item
-- ----------------------------

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_unionkey` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of undo_log
-- ----------------------------
