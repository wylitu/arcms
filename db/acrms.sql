
/*
Navicat MySQL Data Transfer

Source Server         : icard数据库
Source Server Version : 50537
Source Host           : 121.40.58.169:3306
Source Database       : icard

Target Server Type    : MYSQL
Target Server Version : 50537
File Encoding         : 65001

Date: 2015-10-29 09:43:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for lst_of_val
-- ----------------------------
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
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：1(删除) 0(未删除)',
  `seller_id` bigint(11) NOT NULL COMMENT '商家id',
  `user_id` bigint(11) NOT NULL COMMENT '用户id',
  `source` varchar(64) NOT NULL COMMENT 'token来源；foreground:前台；background后台',
  `token` varchar(32) NOT NULL COMMENT 'token 值',
  `gmt_expired` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1215 DEFAULT CHARSET=utf8 COMMENT='防表单重复提交token';


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
  `user_id` bigint(11) NOT NULL COMMENT '后台用户id,作为@BizId',
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `user_no` varchar(64) NOT NULL COMMENT '用户编号',
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
  `address` varchar(64) DEFAULT NULL COMMENT '详细地址',
  `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '用户是可用：1(是) 0(否)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

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
-- Table structure for trade
-- ----------------------------
DROP TABLE IF EXISTS `trade`;
CREATE TABLE `trade` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `created_by` varchar(20) NOT NULL COMMENT '创建人',
  `modified_by` varchar(20) NOT NULL COMMENT '修改人',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：1(删除) 0(未删除)',
  `seller_id` bigint(11) NOT NULL COMMENT '商户号',
  `user_id` bigint(11) NOT NULL COMMENT '用户id',
  `tid` bigint(11) DEFAULT NULL COMMENT ' 交易id,作为@BizId',
  `status` varchar(32) NOT NULL COMMENT '交易状态：WAIT_BUYER_PAY WAIT_SELLER_SEND_GOODS WAIT_BUYER_CONFIRM_GOODS TRADE_FINISHED TRADE_CLOSED TRADE_CLOSED_BY_TAOBAO',
  `pay_time` datetime DEFAULT NULL COMMENT '		付款时间',
  `created` datetime NOT NULL COMMENT '		创建时间',
  `modified` datetime NOT NULL COMMENT '		修改时间',
  `consign_time` datetime DEFAULT NULL COMMENT '		发货时间',
  `end_time` datetime DEFAULT NULL COMMENT '		结束时间',
  `receiver_id` bigint(11) DEFAULT NULL COMMENT '		收件人id',
  `fee` decimal(8,2) DEFAULT NULL COMMENT '		应付款（以元为单位,保留两位小数）',
  `fee_virtual_currency` decimal(8,2) DEFAULT NULL COMMENT '应付虚拟币，这里是爱卡币',
  `payment` decimal(8,2) DEFAULT NULL COMMENT '		实付款（以元为单位,保留两位小数）',
  `payment_virtual_currency`decimal(8,2) DEFAULT NULL COMMENT '实付虚拟币，这里是爱卡币',
  `shipping_type` varchar(16) DEFAULT NULL COMMENT ' 快递方式：free(卖家包邮) post(平邮) express(快递) ems(EMS) virtual(虚拟发货)',
  `shipping_id` varchar(32) DEFAULT NULL COMMENT ' 运单号',
  `shipping_company` varchar(16) DEFAULT NULL COMMENT ' 快递公司',
  `shipping_fee` bigint(11) DEFAULT NULL COMMENT ' 快递费',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tid` (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='主交易表';

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `created_by` varchar(20) NOT NULL COMMENT '创建人',
  `modified_by` varchar(20) NOT NULL COMMENT '修改人',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：1(删除) 0(未删除)',
  `seller_id` bigint(11) NOT NULL COMMENT '商户号',
  `user_id` bigint(11) NOT NULL COMMENT '用户id',
  `oid` bigint(11) DEFAULT NULL COMMENT ' 子交易id,作为@BizId',
  `tid` bigint(11) NOT NULL COMMENT ' 主交易id',
  `goods_id` bigint(11) NOT NULL COMMENT '商品id',
  `sku_id` bigint(11) DEFAULT NULL COMMENT ' sku id，可为空',
  `quantity` int(8) NOT NULL COMMENT '数量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `oid` (`oid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='子交易表';

