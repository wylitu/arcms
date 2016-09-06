-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: localhost    Database: arcms
-- ------------------------------------------------------
-- Server version	5.7.9

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `id_gen`
--

DROP TABLE IF EXISTS `id_gen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `id_gen` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10009 DEFAULT CHARSET=utf8 COMMENT='id生成表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `id_gen`
--

LOCK TABLES `id_gen` WRITE;
/*!40000 ALTER TABLE `id_gen` DISABLE KEYS */;
INSERT INTO `id_gen` VALUES (10001),(10002),(10003),(10004),(10005),(10006),(10007),(10008);
/*!40000 ALTER TABLE `id_gen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lst_of_val`
--

DROP TABLE IF EXISTS `lst_of_val`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lst_of_val`
--

LOCK TABLES `lst_of_val` WRITE;
/*!40000 ALTER TABLE `lst_of_val` DISABLE KEYS */;
/*!40000 ALTER TABLE `lst_of_val` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource`
--

DROP TABLE IF EXISTS `resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `created_by` varchar(20) NOT NULL COMMENT '创建人',
  `modified_by` varchar(20) NOT NULL COMMENT '修改人',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：1(删除) 0(未删除)',
  `resource_id` bigint(11) NOT NULL COMMENT '资源id,作为@BizId',
  `parent_resource_id` bigint(11) NOT NULL COMMENT '上级资源id',
  `is_parent` tinyint(1) NOT NULL DEFAULT '0' COMMENT '该类目是否为父节点(即：该节点是否还有子节点)',
  `name` varchar(50) NOT NULL COMMENT '资源名称',
  `url` varchar(100) NOT NULL COMMENT '资源地址',
  `type` varchar(20) DEFAULT NULL COMMENT '资源类型',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `priority` int(5) DEFAULT NULL COMMENT '优先级',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可用（0不可用，1可用）',
  `style` varchar(200) DEFAULT NULL COMMENT '样式',
  PRIMARY KEY (`id`),
  UNIQUE KEY `resource_id` (`resource_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='系统资源表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource`
--

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;
INSERT INTO `resource` VALUES (1,'2016-09-04 12:00:00','2016-09-04 18:05:32','litu','1001',0,1,0,0,'用户管理','/admin/userManage','url','用户管理',1,1,NULL),(2,'2016-09-04 12:00:00','2016-09-04 18:05:41','litu','1001',0,2,0,1,'权限管理','','menu','权限管理',0,1,NULL),(3,'2016-09-04 12:00:00','2016-09-04 12:00:00','litu','litu',0,3,2,0,'模块管理','/admin/resourceManage','url','模块管理',0,1,NULL),(4,'2016-09-04 12:00:00','2016-09-04 12:00:00','litu','litu',0,4,2,0,'角色管理','/admin/roleManage','url','模块管理',0,1,NULL);
/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource_role`
--

DROP TABLE IF EXISTS `resource_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='资源角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource_role`
--

LOCK TABLES `resource_role` WRITE;
/*!40000 ALTER TABLE `resource_role` DISABLE KEYS */;
INSERT INTO `resource_role` VALUES (1,'2016-09-04 12:00:00','2016-09-04 12:00:00','litu','litu',0,1,1),(2,'2016-09-04 12:00:00','2016-09-04 12:00:00','litu','litu',0,2,1),(3,'2016-09-04 12:00:00','2016-09-04 12:00:00','litu','litu',0,3,1);
/*!40000 ALTER TABLE `resource_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'2016-09-04 12:00:00','2016-09-04 12:00:00','litu','litu',0,1,'管理员','管理员'),(2,'2016-09-04 17:32:00','2016-09-04 17:32:00','1001','1001',1,10001,'学生','学生'),(6,'2016-09-04 18:20:34','2016-09-04 18:20:34','1001','1001',0,10008,'学生','学生');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_token`
--

DROP TABLE IF EXISTS `sys_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='防表单重复提交token';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_token`
--

LOCK TABLES `sys_token` WRITE;
/*!40000 ALTER TABLE `sys_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '用户是可用：1(是) 0(否)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'2016-08-09 12:00:00','2016-09-04 17:01:06','litu','1001',0,1,'测试账号','测试账号','1001','827ccb0eea8a706c4c34a16891f84e7b','1',12,'18057138887',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,'2016-09-04 12:00:00','2016-09-04 12:00:00','litu','litu',0,1,1);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-06  9:29:58
