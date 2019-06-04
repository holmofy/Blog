/*
Navicat MySQL Data Transfer

Source Server         : LocalMySQL
Source Server Version : 50715
Source Host           : localhost:3306
Source Database       : db_my_blog

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2018-06-18 22:01:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_article
-- ----------------------------
DROP TABLE IF EXISTS `tb_article`;
CREATE TABLE `tb_article` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL COMMENT '标题',
  `created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `views` int(10) NOT NULL DEFAULT '0' COMMENT '查看量',
  `category_id` int(10) DEFAULT NULL COMMENT '文章分类',
  `author_id` int(10) NOT NULL COMMENT '作者',
  `is_deny_comment` tinyint(4) DEFAULT '0' COMMENT '是否禁止评论',
  `content` mediumtext COMMENT '内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_category`;
CREATE TABLE `tb_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '分类名',
  `parent_id` int(11) DEFAULT '0' COMMENT '父分类，可构成一个分类树，树根parent_id为0',
  `created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `article_id` int(10) NOT NULL COMMENT '评论的文章ID',
  `reply_id` int(10) DEFAULT NULL COMMENT '回复评论的ID，首发评论无回复此值为NULL',
  `name` varchar(48) DEFAULT NULL COMMENT '评论人姓名',
  `email` varchar(255) DEFAULT NULL COMMENT '评论人Email',
  `ip` char(15) DEFAULT NULL COMMENT '评论人IP地址',
  `website` varchar(255) DEFAULT NULL COMMENT '评论人网站',
  `agree` int(10) DEFAULT '0' COMMENT '赞同数',
  `disagree` int(10) DEFAULT '0' COMMENT '不赞同数',
  `created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  `content` text COMMENT '评论内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(48) NOT NULL,
  `passwd_md5` varchar(48) DEFAULT NULL,
  `nick_name` varchar(48) DEFAULT NULL,
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
