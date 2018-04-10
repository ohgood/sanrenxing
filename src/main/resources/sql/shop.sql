/*
Navicat MySQL Data Transfer

Source Server         : JDY
Source Server Version : 50721
Source Host           : 116.196.80.176:3306
Source Database       : shop

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-10 15:29:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL COMMENT '书名',
  `description` text COMMENT '描述',
  `pic_url` varchar(2555) DEFAULT NULL COMMENT '照片url',
  `state` int(11) DEFAULT '0' COMMENT '0:未出售 1:已出售 2未发布',
  `type` int(11) DEFAULT '1' COMMENT '1:java  2:电子产品',
  `is_hot` int(11) DEFAULT NULL COMMENT '1:热门商品 0：普通商品',
  `color` int(11) DEFAULT NULL COMMENT '颜色',
  `amount` int(11) DEFAULT NULL COMMENT '数量',
  `cost` double(10,2) DEFAULT NULL COMMENT '成本',
  `price` double(10,2) DEFAULT NULL COMMENT '售价',
  `update_time` timestamp NULL DEFAULT NULL,
  `add_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `sale_area` varchar(11) DEFAULT NULL COMMENT '售卖地区',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for goods_detail
-- ----------------------------
DROP TABLE IF EXISTS `goods_detail`;
CREATE TABLE `goods_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_id` varchar(255) DEFAULT NULL COMMENT '商品id',
  `alipay_goods_id` varchar(255) DEFAULT NULL COMMENT '支付宝商品id',
  `goods_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `quantity` varchar(255) DEFAULT NULL COMMENT '商品数量',
  `price` double(11,2) DEFAULT NULL COMMENT '商品价格，此处单位为元，精确到小数点后2位',
  `goods_category` varchar(255) DEFAULT NULL,
  `body` varchar(255) DEFAULT NULL COMMENT '商品详情',
  `add_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for goods_type
-- ----------------------------
DROP TABLE IF EXISTS `goods_type`;
CREATE TABLE `goods_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(22) DEFAULT NULL COMMENT '产品类型',
  `type` int(1) DEFAULT NULL,
  `add_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `goods_id` int(11) DEFAULT NULL COMMENT 'goods表id',
  `pic_url` varchar(255) DEFAULT NULL COMMENT '轮播图片url',
  `context` varchar(255) NOT NULL COMMENT '内容',
  `add_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '添加日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` int(11) NOT NULL COMMENT '订单编号',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `operator` varchar(11) DEFAULT NULL COMMENT '提交人',
  `state` int(2) NOT NULL COMMENT '订单状态 0未付款',
  `amount` int(11) NOT NULL COMMENT '商品数量',
  `sum` double(11,2) DEFAULT NULL COMMENT '订单金额',
  `sale_area` varchar(100) DEFAULT NULL COMMENT '售卖地',
  `receiver` varchar(11) NOT NULL COMMENT '收货人',
  `add_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '下单时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `iphone` varchar(22) DEFAULT NULL COMMENT '电话',
  `address` varchar(100) DEFAULT NULL COMMENT '收货地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `order_no` varchar(255) NOT NULL COMMENT '订单编号',
  `good_id` int(11) NOT NULL COMMENT '商品id',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
