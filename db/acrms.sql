/*
Navicat MySQL Data Transfer

Source Server         : arcms数据库
Source Server Version : 50537
Source Host           : 121.40.58.169:3306
Source Database       : icard

Target Server Type    : MYSQL
Target Server Version : 50537
File Encoding         : 65001

Date: 2015-10-29 09:43:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- -------------------------------
-- Table structure for lst_of_val
-- -------------------------------
DROP TABLE IF EXISTS `lst_of_val`;
CREATE TABLE `lst_of_val` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `created_by` varchar(20) NOT NULL COMMENT '创建人',
  `modified_by` varchar(20) NOT NULL COMMENT '修改人',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：1(删除) 0(未删除)',
  `type` varchar(50) DEFAULT NULL COMMENT '类型',
  `name` varchar(50) NOT NULL COMMENT 'Key',
  `value` varchar(500) NOT NULL COMMENT '值',
  `big_value` mediumtext COMMENT '大值',
  `desc_text` varchar(50) DEFAULT NULL COMMENT '描述',
  `value_01` varchar(500) DEFAULT NULL COMMENT '一个key对应多个值时需要',
  `value_02` varchar(500) DEFAULT NULL COMMENT '一个key对应多个值时需要',
  `value_03` varchar(500) DEFAULT NULL COMMENT '一个key对应多个值时需要',
  `orde_by` int(4) DEFAULT NULL COMMENT '排序字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1171 DEFAULT CHARSET=utf8 COMMENT='配置表';

-- ----------------------------
-- Table structure for sys_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_token`;
CREATE TABLE `sys_token` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `created_by` varchar(20) NOT NULL COMMENT '创建人',
  `modified_by` varchar(20) NOT NULL COMMENT '修改人',
  `is_deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除：1(删除) 0(未删除)',
  `user_id` bigint(11) NOT NULL COMMENT '用户id',
  `user_name` varchar(64) NOT NULL COMMENT '用户名',
  `type` varchar(64) NOT NULL COMMENT '类型，login:作为登陆session、repeat_submit：防止表单重复提交',
  `token` varchar(32) NOT NULL COMMENT 'token 值',
  `gmt_expired` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='token表';

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `created_by` varchar(20) NOT NULL COMMENT '创建人',
  `modified_by` varchar(20) NOT NULL COMMENT '修改人',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：1(删除) 0(未删除)',
  `resource_id` bigint(11) NOT NULL COMMENT '资源id,作为@BizId',
  `parent_resource_id` bigint(11) NOT NULL COMMENT '上级资源id',
  `is_parent` tinyint(1) NOT NULL DEFAULT 0  COMMENT '该类目是否为父节点(即：该节点是否还有子节点)',
  `name` varchar(50) NOT NULL COMMENT '资源名称',
  `url` varchar(100) NOT NULL COMMENT '资源地址',
  `type` varchar(20) DEFAULT NULL COMMENT '资源类型',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `priority` int(5) DEFAULT NULL COMMENT '优先级',
  `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否可用（0不可用，1可用）',
  `style` varchar(200) DEFAULT NULL COMMENT '样式',
  PRIMARY KEY (`id`),
  UNIQUE KEY `resource_id` (`resource_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='系统资源表';

-- ----------------------------
-- Table structure for resource_role
-- ----------------------------
DROP TABLE IF EXISTS `resource_role`;
CREATE TABLE `resource_role` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `created_by` varchar(20) NOT NULL COMMENT '创建人',
  `modified_by` varchar(20) NOT NULL COMMENT '修改人',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：1(删除) 0(未删除)',
  `resource_id` bigint(11) NOT NULL COMMENT '角色id',
  `role_id` bigint(11) NOT NULL COMMENT '资源id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源角色表';

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `created_by` varchar(20) NOT NULL COMMENT '创建人',
  `modified_by` varchar(20) NOT NULL COMMENT '修改人',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：1(删除) 0(未删除)',
  `role_id` bigint(11) NOT NULL COMMENT '后台角色id,@BizId',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `created_by` varchar(20) NOT NULL COMMENT '创建人',
  `modified_by` varchar(20) NOT NULL COMMENT '修改人',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：1(删除) 0(未删除)',
  `user_id` bigint(11) NOT NULL COMMENT '用户id,作为@BizId',
  `user_nick` varchar(64) DEFAULT NULL COMMENT '昵称',
  `name` varchar(64) NOT NULL COMMENT '姓名',
  `user_no` varchar(64) DEFAULT NULL COMMENT '用户编号',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `sex` varchar(1) DEFAULT NULL COMMENT '性别1:男；2:女',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `phone` varchar(12) DEFAULT NULL COMMENT '座机号码',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `identity_card` varchar(20) DEFAULT NULL COMMENT '身份证号码',
  `province` varchar(16) DEFAULT NULL COMMENT '省',
  `city` varchar(16) DEFAULT NULL COMMENT '市',
  `district` varchar(16) DEFAULT NULL COMMENT '区',
  `wx_openid` varchar(32) DEFAULT NULL COMMENT '微信openid',
  `wx_subscribe` varchar(8) DEFAULT NULL COMMENT '公众号关注标识',
  `wx_headimgurl` varchar(32) DEFAULT NULL COMMENT '微信头像',
  `wx_subscribe_time` varchar(32) DEFAULT NULL COMMENT '关注时间',
  `bedroom_no` varchar(32) DEFAULT NULL COMMENT '寝室编号',
  `address` varchar(512) DEFAULT NULL COMMENT '详细地址',
  `ac_open_date` datetime DEFAULT NULL COMMENT '空调开通时间',
  `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '用户是可用：1(是) 0(否)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';


-- ----------------------------
-- Table structure for id_gen
-- ----------------------------
DROP TABLE IF EXISTS `id_gen`;
CREATE TABLE `id_gen` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8 COMMENT='id生成表';
-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `created_by` varchar(20) NOT NULL COMMENT '创建人',
  `modified_by` varchar(20) NOT NULL COMMENT '修改人',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：1(删除) 0(未删除)',
  `user_id` bigint(11) NOT NULL COMMENT '用户id',
  `role_id` bigint(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Table structure for ac_area
-- ----------------------------
DROP TABLE IF EXISTS `ac_area`;
CREATE TABLE `ac_area` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `area_name` varchar(255)  DEFAULT NULL COMMENT '区域名称',
  `memo` varchar(255)  DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：1(删除) 0(未删除)',
  `gmt_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间,非空',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间,非空',
  `created_by` varchar(32)  NOT NULL COMMENT '创建人',
  `modified_by` varchar(32)  NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='区域信息';


-- ----------------------------
-- Table structure for ac_park
-- ----------------------------
DROP TABLE IF EXISTS `ac_park`;
CREATE TABLE `ac_park` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `area_id` bigint(11) NOT NULL COMMENT '所属区域',
  `park_name` varchar(255)  DEFAULT NULL COMMENT '园区名称',
  `memo` varchar(255)  DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：1(删除) 0(未删除)',
  `gmt_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间,非空',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间,非空',
  `created_by` varchar(32)  NOT NULL COMMENT '创建人',
  `modified_by` varchar(32)  NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='园区信息';

-- ----------------------------
-- Table structure for ac_building
-- ----------------------------
DROP TABLE IF EXISTS `ac_building`;
CREATE TABLE `ac_building` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `park_id` bigint(11) NOT NULL COMMENT '所属园区',
  `building_name` varchar(255)  DEFAULT NULL COMMENT '楼号名称',
  `memo` varchar(255)  DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：1(删除) 0(未删除)',
  `gmt_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间,非空',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间,非空',
  `created_by` varchar(32)  NOT NULL COMMENT '创建人',
  `modified_by` varchar(32)  NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='楼号信息';


-- ----------------------------
-- Table structure for ac_room
-- ----------------------------
DROP TABLE IF EXISTS `ac_room`;
CREATE TABLE `ac_room` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `building_id` bigint(11) NOT NULL COMMENT '所属楼号',
  `bedroom_no` varchar(32)  DEFAULT NULL COMMENT '寝室号',
  `ac_id` bigint(11)  DEFAULT NULL COMMENT '空调编号',
  `address` varchar(512)  DEFAULT NULL COMMENT '详细地址',
  `memo` varchar(255)  DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：1(删除) 0(未删除)',
  `gmt_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间,非空',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间,非空',
  `created_by` varchar(32)  NOT NULL COMMENT '创建人',
  `modified_by` varchar(32)  NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='寝室信息';
-- ----------------------------
-- Table structure for ac_info
-- ----------------------------
DROP TABLE IF EXISTS `ac_info`;
CREATE TABLE `ac_info` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `brand` varchar(255)  DEFAULT NULL COMMENT '品牌',
  `power` varchar(10)  DEFAULT NULL COMMENT '匹数',
  `room_addr` varchar(255)  DEFAULT NULL COMMENT '寝室',
  `status` tinyint(4) DEFAULT NULL COMMENT '1-已入库 2-已安装 3-报修 4-报废',
  `memo` varchar(255)  DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：1(删除) 0(未删除)',
  `gmt_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间,非空',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间,非空',
  `created_by` varchar(32)  NOT NULL COMMENT '创建人',
  `modified_by` varchar(32)  NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='空调信息';

-- ----------------------------
-- Table structure for ac_package
-- ----------------------------
DROP TABLE IF EXISTS `ac_package`;
CREATE TABLE `ac_package` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(256)  DEFAULT NULL COMMENT '套餐名称',
  `price` decimal(5,2) DEFAULT NULL COMMENT '套餐价格',
  `desc` varchar(1024)  DEFAULT NULL COMMENT '描述',
  `memo` varchar(256)  DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：1(删除) 0(未删除)',
  `gmt_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间,非空',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间,非空',
  `created_by` varchar(32)  NOT NULL COMMENT '创建人',
  `modified_by` varchar(32)  NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='套餐信息';


-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `gmt_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间,非空',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间,非空',
  `created_by` varchar(32) NOT NULL COMMENT '创建人',
  `modified_by` varchar(32) NOT NULL COMMENT '修改人',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：1(删除) 0(未删除)',
  `order_no` bigint(20) NOT NULL COMMENT '订单编号',
  `package_id` bigint(20) NOT NULL COMMENT '套餐ID',
  `package_name` varchar(100) NOT NULL COMMENT '套餐名称',
  `area_name` varchar(100) NOT NULL COMMENT '区域名称(学校或后勤公司)',
  `student_name` varchar(20) NOT NULL COMMENT '学生姓名',
  `user_no` varchar(20) NOT NULL COMMENT '学号',
  `student_mobile` varchar(16) NOT NULL COMMENT '学生手机号码',
  `install_personnel_no` varchar(20) DEFAULT NULL COMMENT '安装人员编号',
  `install_personnel_name` varchar(20) DEFAULT NULL COMMENT '安装人姓名',
  `order_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  `room_addr` varchar(255) NOT NULL COMMENT '寝室地址',
  `status` smallint(2) NOT NULL COMMENT '订单状态,1:待支付;2.待开通;3.待安装;4.开通中;5.安装中;6:已完成',
  `ac_no` bigint(11) DEFAULT NULL COMMENT '空调编号',
  `price` decimal(5,2) DEFAULT NULL COMMENT '套餐价格',
  `memo` varchar(255) NOT NULL COMMENT '安装备注',
  `apply_times` INT DEFAULT 1 COMMENT '安装申请次数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单信息表';


DROP TABLE IF EXISTS `ac_repair`;
CREATE TABLE `ac_repair`(
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `gmt_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间,非空',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间,非空',
  `created_by` varchar(32) NOT NULL COMMENT '创建人',
  `modified_by` varchar(32) NOT NULL COMMENT '修改人',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：1(删除) 0(未删除)',
  `repair_no` VARCHAR(20) NOT NULL COMMENT '维修单号(时间+4位序列号)',
  `room_address` VARCHAR(512) NOT NULL COMMENT '寝室地址',
  `bedroom_no` varchar(32)  DEFAULT NULL COMMENT '寝室号',
  `trouble_desc` VARCHAR(512) NOT NULL COMMENT '故障描述',
  `user_no` bigint(11) NOT NULL COMMENT '学生学号',
  `user_nick` varchar(64) DEFAULT NULL COMMENT '昵称',
  `name` varchar(64) NOT NULL COMMENT '报修人姓名',
  `ac_no` bigint(11) DEFAULT NULL COMMENT '空调编号',
  `status` TINYINT(1) NOT NULL COMMENT '报修状态，1-申请 2-维修中 3-待用户确认 4-完成',
  `repair_user_no` VARCHAR(64) DEFAULT NULL COMMENT '维修人编号',
  `repair_user_name` VARCHAR(64) DEFAULT NULL COMMENT '维修人',
  `repair_time` DATETIME DEFAULT NULL COMMENT '处理时间',
  `apply_times` INT DEFAULT 1 COMMENT '申请次数',
  `repair_desc` VARCHAR(512) DEFAULT NULL COMMENT '处理描述',
  `memo` varchar(255) NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB COMMENT '报修订单';
