/*
Navicat MySQL Data Transfer

Source Server         : JDY
Source Server Version : 50721
Source Host           : 116.196.80.176:3306
Source Database       : admin

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-10 15:29:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `name` varchar(30) NOT NULL COMMENT '资源名称',
  `type` int(11) NOT NULL COMMENT '资源类型  menu: 菜单    button：按钮',
  `url` varchar(255) NOT NULL COMMENT '资源路径',
  `permission` varchar(32) NOT NULL COMMENT '权限字符串',
  `parent_id` int(11) DEFAULT NULL COMMENT '父模块id',
  `parent_ids` varchar(64) NOT NULL,
  `priority` int(11) NOT NULL DEFAULT '0' COMMENT '显示顺序   数字越小优先级越高',
  `available` int(11) NOT NULL DEFAULT '1' COMMENT '是否可用   0: 不可用  1: 可用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_permission` (`permission`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(20) NOT NULL COMMENT '角色名称',
  `is_root` int(11) NOT NULL DEFAULT '0' COMMENT '1代表TRUE,0代表FALSE',
  `description` varchar(20) NOT NULL COMMENT '角色描述',
  `resource_ids` varchar(100) NOT NULL DEFAULT '' COMMENT '资源列表',
  `available` int(4) NOT NULL DEFAULT '1' COMMENT '是否可用  0: 不可用  1: 可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL COMMENT '用户id',
  `password` varchar(32) NOT NULL DEFAULT '' COMMENT '密码',
  `real_name` varchar(32) NOT NULL COMMENT '真实姓名',
  `role_ids` varchar(64) NOT NULL DEFAULT '' COMMENT '角色ids，逗号隔开',
  `salt` varchar(32) NOT NULL,
  `locked` int(4) NOT NULL DEFAULT '1' COMMENT '0: 未锁定    1: 锁定',
  `login_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '登录日期',
  `register_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
