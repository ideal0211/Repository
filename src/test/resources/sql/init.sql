/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.36 : Database - fabric
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`fabric` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `fabric`;

/*Table structure for table `t_category` */

DROP TABLE IF EXISTS `t_category`;

CREATE TABLE `t_category` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `category_name` varchar(100) NOT NULL COMMENT '分类名称',
  `order_by` int(11) NOT NULL COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_commodity` */

DROP TABLE IF EXISTS `t_commodity`;

CREATE TABLE `t_commodity` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `category_id` int(11) NOT NULL COMMENT '分类ID',
  `name` varchar(100) NOT NULL COMMENT '商品名称',
  `intro` varchar(500) NOT NULL COMMENT '商品简介',
  `images` varchar(3000) NOT NULL COMMENT '商品图片',
  `price` decimal(10,2) NOT NULL COMMENT '商品价格',
  `is_release` tinyint(1) DEFAULT '0' COMMENT '是否发布：1=是，0=否',
  `order_by` int(11) NOT NULL COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `mobile` int(11) NOT NULL COMMENT '手机号',
  `nick_name` varchar(100) DEFAULT NULL COMMENT '昵称',
  `icon` varchar(100) DEFAULT NULL COMMENT '头像',
  `sex` tinyint(1) DEFAULT '3' COMMENT '性别：1=男，2=女，3=保密',
  `open_id` varchar(100) NOT NULL COMMENT '微信openid',
  `union_id` varchar(100) NOT NULL COMMENT '微信unionid',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
