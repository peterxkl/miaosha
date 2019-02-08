/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50637
Source Host           : localhost:3306
Source Database       : miaosha

Target Server Type    : MYSQL
Target Server Version : 50637
File Encoding         : 65001

Date: 2019-02-08 09:34:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '//商品名称',
  `price` double(10,0) NOT NULL DEFAULT '0' COMMENT '//商品价格',
  `description` varchar(500) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `sales` int(11) NOT NULL DEFAULT '0' COMMENT '//销量',
  `img_url` varchar(500) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES ('2', 'ThinkPadX1', '15600', '隐士X1', '25', 'http://img3.imgtn.bdimg.com/it/u=3437380768,1128125133&fm=26&gp=0.jpg');
INSERT INTO `item` VALUES ('6', 'macBook', '12000', '笔记本', '13', 'http://img0.imgtn.bdimg.com/it/u=2652736280,3152543228&fm=26&gp=0.jpg');

-- ----------------------------
-- Table structure for item_stock
-- ----------------------------
DROP TABLE IF EXISTS `item_stock`;
CREATE TABLE `item_stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stock` int(11) NOT NULL DEFAULT '0',
  `item_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of item_stock
-- ----------------------------
INSERT INTO `item_stock` VALUES ('2', '98', '2');
INSERT INTO `item_stock` VALUES ('6', '99', '6');

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `userId` int(11) NOT NULL DEFAULT '0' COMMENT '//用户id',
  `itemId` int(11) NOT NULL COMMENT '//商品id',
  `item_price` double NOT NULL DEFAULT '0' COMMENT '//商品价格',
  `amount` int(11) NOT NULL DEFAULT '0' COMMENT '//购买数量',
  `order_price` double NOT NULL DEFAULT '0' COMMENT '//订单价格',
  `promo_id` int(11) NOT NULL DEFAULT '0' COMMENT '//非0则为秒杀价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('2019020700000400', '34', '2', '9999', '1', '9999', '1');
INSERT INTO `order_info` VALUES ('2019020800000500', '34', '2', '9999', '1', '9999', '1');

-- ----------------------------
-- Table structure for promo
-- ----------------------------
DROP TABLE IF EXISTS `promo`;
CREATE TABLE `promo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `promo_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '//秒杀活动商品名称',
  `start_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '//活动开始时间',
  `item_id` int(11) NOT NULL DEFAULT '0' COMMENT '//秒杀活动商品id',
  `promo_item_price` double NOT NULL DEFAULT '0' COMMENT '//秒杀活动商品价格',
  `end_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '//秒杀活动结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of promo
-- ----------------------------
INSERT INTO `promo` VALUES ('1', 'ThinkPadX2抢购', '2019-02-07 19:06:00', '2', '9999', '2019-02-20 00:00:00');

-- ----------------------------
-- Table structure for sequence_info
-- ----------------------------
DROP TABLE IF EXISTS `sequence_info`;
CREATE TABLE `sequence_info` (
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `current_value` int(11) NOT NULL DEFAULT '0',
  `step` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of sequence_info
-- ----------------------------
INSERT INTO `sequence_info` VALUES ('order_info', '6', '1');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `gender` tinyint(4) NOT NULL COMMENT '//1代表男性;2代表女性',
  `age` int(11) NOT NULL,
  `telphone` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '//手机号',
  `register_mode` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '//byphone,bywechat,byalipay',
  `third_party_id` varchar(64) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '//第三方',
  PRIMARY KEY (`id`),
  UNIQUE KEY `telphone_unique_index` (`telphone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', 'hongjun500', '1', '21', '17607120963', 'byWechat', '');
INSERT INTO `user_info` VALUES ('33', 'we', '2', '24', '1321321', 'byphone', '');
INSERT INTO `user_info` VALUES ('34', '1', '1', '1', '1', 'byphone', '');

-- ----------------------------
-- Table structure for user_password
-- ----------------------------
DROP TABLE IF EXISTS `user_password`;
CREATE TABLE `user_password` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `encrpt_password` varchar(128) COLLATE utf8_unicode_ci NOT NULL COMMENT '//密文密码',
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of user_password
-- ----------------------------
INSERT INTO `user_password` VALUES ('1', 'gregfergiujhvfdsnh', '1');
INSERT INTO `user_password` VALUES ('15', 'hgtDJlJQT6YPjalFOY4g3g==', '33');
INSERT INTO `user_password` VALUES ('16', 'xMpCOKC5I4INzFCab3WEmw==', '34');
