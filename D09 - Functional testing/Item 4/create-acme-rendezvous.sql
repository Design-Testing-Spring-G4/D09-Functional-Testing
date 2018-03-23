start transaction;

drop database if exists `Acme-Rendezvous`;
create database `Acme-Rendezvous`;

use `Acme-Rendezvous`;

create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';
create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete on `Acme-Rendezvous`.* to 'acme-user'@'%';
grant select, insert, update, delete, create, drop, references, index, alter,
	create temporary tables, lock tables, create view, create routine,
	alter routine, execute, trigger, show view on `Acme-Rendezvous`.* to 'acme-manager'@'%';

-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: Acme-Rendezvous
-- ------------------------------------------------------
-- Server version	5.5.29

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
-- Table structure for table `actor`
--

DROP TABLE IF EXISTS `actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `birthDate` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_cgls5lrufx91ufsyh467spwa3` (`userAccount_id`),
  CONSTRAINT `FK_cgls5lrufx91ufsyh467spwa3` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `birthDate` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_idt4b4u259p6vs4pyr9lax4eg` (`userAccount_id`),
  CONSTRAINT `FK_idt4b4u259p6vs4pyr9lax4eg` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (537,0,'c/ Central 22, Madrid','1967-09-12','jose01@mail.com','José','618392172','Blanco',529);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `announcement`
--

DROP TABLE IF EXISTS `announcement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `announcement` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `rendezvous_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ff49x8lk2mmqi9noe8xwqg0ah` (`rendezvous_id`),
  KEY `FK_frbasihc59fobfwh11mlco2qn` (`user_id`),
  CONSTRAINT `FK_frbasihc59fobfwh11mlco2qn` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_ff49x8lk2mmqi9noe8xwqg0ah` FOREIGN KEY (`rendezvous_id`) REFERENCES `rendezvous` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `question_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_10g8xii7lw9kq0kcsobgmtw72` (`question_id`),
  CONSTRAINT `FK_10g8xii7lw9kq0kcsobgmtw72` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_p6elut499cl32in8b8j8sy2n4` (`parent_id`),
  CONSTRAINT `FK_p6elut499cl32in8b8j8sy2n4` FOREIGN KEY (`parent_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `category_children`
--

DROP TABLE IF EXISTS `category_children`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category_children` (
  `Category_id` int(11) NOT NULL,
  `children` int(11) DEFAULT NULL,
  KEY `FK_bnh35ilv8ip1nml6p18u5yntw` (`Category_id`),
  CONSTRAINT `FK_bnh35ilv8ip1nml6p18u5yntw` FOREIGN KEY (`Category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `moment` datetime DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_jhvt6d9ap8gxv67ftrmshdfhj` (`user_id`),
  CONSTRAINT `FK_jhvt6d9ap8gxv67ftrmshdfhj` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comment_comment`
--

DROP TABLE IF EXISTS `comment_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment_comment` (
  `Comment_id` int(11) NOT NULL,
  `replies_id` int(11) NOT NULL,
  UNIQUE KEY `UK_doncdtl6bk7b9ewtxqx25te2l` (`replies_id`),
  KEY `FK_2ou7258nxvxpiawhb7kud421i` (`Comment_id`),
  CONSTRAINT `FK_2ou7258nxvxpiawhb7kud421i` FOREIGN KEY (`Comment_id`) REFERENCES `comment` (`id`),
  CONSTRAINT `FK_doncdtl6bk7b9ewtxqx25te2l` FOREIGN KEY (`replies_id`) REFERENCES `comment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `companyName` varchar(255) DEFAULT NULL,
  `welcomeEN` varchar(255) DEFAULT NULL,
  `welcomeES` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration`
--

LOCK TABLES `configuration` WRITE;
/*!40000 ALTER TABLE `configuration` DISABLE KEYS */;
INSERT INTO `configuration` VALUES (552,0,'https://tinyurl.com/adventure-meetup','Adventure meetups','Your place to organise your adventure meetups!','Tu sitio para organizar quedadas de aventura');
/*!40000 ALTER TABLE `configuration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('DomainEntity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `birthDate` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) DEFAULT NULL,
  `vat` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_84bmmxlq61tiaoc7dy7kdcghh` (`userAccount_id`),
  CONSTRAINT `FK_84bmmxlq61tiaoc7dy7kdcghh` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  `rendezvous_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ejk14ged2t2jaquwya6f97awi` (`rendezvous_id`),
  CONSTRAINT `FK_ejk14ged2t2jaquwya6f97awi` FOREIGN KEY (`rendezvous_id`) REFERENCES `rendezvous` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rendezvous`
--

DROP TABLE IF EXISTS `rendezvous`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rendezvous` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `adultOnly` bit(1) NOT NULL,
  `coordinates` varchar(255) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `finalMode` bit(1) NOT NULL,
  `moment` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `creator_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_4cru16jpqbsxd0g6runtbwqlt` (`creator_id`),
  CONSTRAINT `FK_4cru16jpqbsxd0g6runtbwqlt` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rendezvous_announcement`
--

DROP TABLE IF EXISTS `rendezvous_announcement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rendezvous_announcement` (
  `Rendezvous_id` int(11) NOT NULL,
  `announcements_id` int(11) NOT NULL,
  UNIQUE KEY `UK_mlwhdfpnv7d4o5ww7gluddgrv` (`announcements_id`),
  KEY `FK_2y7rmn6v1bqn1lm9lbs1w28vr` (`Rendezvous_id`),
  CONSTRAINT `FK_2y7rmn6v1bqn1lm9lbs1w28vr` FOREIGN KEY (`Rendezvous_id`) REFERENCES `rendezvous` (`id`),
  CONSTRAINT `FK_mlwhdfpnv7d4o5ww7gluddgrv` FOREIGN KEY (`announcements_id`) REFERENCES `announcement` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rendezvous_comment`
--

DROP TABLE IF EXISTS `rendezvous_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rendezvous_comment` (
  `Rendezvous_id` int(11) NOT NULL,
  `comments_id` int(11) NOT NULL,
  UNIQUE KEY `UK_233yqm5jxngf9wj7xvwf8asem` (`comments_id`),
  KEY `FK_10l6y61x4rurnwre3uw62620y` (`Rendezvous_id`),
  CONSTRAINT `FK_10l6y61x4rurnwre3uw62620y` FOREIGN KEY (`Rendezvous_id`) REFERENCES `rendezvous` (`id`),
  CONSTRAINT `FK_233yqm5jxngf9wj7xvwf8asem` FOREIGN KEY (`comments_id`) REFERENCES `comment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rendezvous_rendezvous`
--

DROP TABLE IF EXISTS `rendezvous_rendezvous`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rendezvous_rendezvous` (
  `Rendezvous_id` int(11) NOT NULL,
  `links_id` int(11) NOT NULL,
  UNIQUE KEY `UK_bjys9l30cnk98s3e3mkmv3b8u` (`links_id`),
  KEY `FK_fuhs4y1oqtlba5sencebgmosa` (`Rendezvous_id`),
  CONSTRAINT `FK_fuhs4y1oqtlba5sencebgmosa` FOREIGN KEY (`Rendezvous_id`) REFERENCES `rendezvous` (`id`),
  CONSTRAINT `FK_bjys9l30cnk98s3e3mkmv3b8u` FOREIGN KEY (`links_id`) REFERENCES `rendezvous` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rendezvous_request`
--

DROP TABLE IF EXISTS `rendezvous_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rendezvous_request` (
  `Rendezvous_id` int(11) NOT NULL,
  `requests_id` int(11) NOT NULL,
  UNIQUE KEY `UK_d26gd7rok0g1277yv3dqcu80t` (`requests_id`),
  KEY `FK_lwolibup7rcoriskjgu5wsgyw` (`Rendezvous_id`),
  CONSTRAINT `FK_lwolibup7rcoriskjgu5wsgyw` FOREIGN KEY (`Rendezvous_id`) REFERENCES `rendezvous` (`id`),
  CONSTRAINT `FK_d26gd7rok0g1277yv3dqcu80t` FOREIGN KEY (`requests_id`) REFERENCES `request` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rendezvous_user`
--

DROP TABLE IF EXISTS `rendezvous_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rendezvous_user` (
  `attendance_id` int(11) NOT NULL,
  `attendants_id` int(11) NOT NULL,
  KEY `FK_38sxgvfuoa8bkkqfyahtfgj7l` (`attendants_id`),
  KEY `FK_19pxsw3ekrou1rmunv6ojt336` (`attendance_id`),
  CONSTRAINT `FK_19pxsw3ekrou1rmunv6ojt336` FOREIGN KEY (`attendance_id`) REFERENCES `rendezvous` (`id`),
  CONSTRAINT `FK_38sxgvfuoa8bkkqfyahtfgj7l` FOREIGN KEY (`attendants_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `request`
--

DROP TABLE IF EXISTS `request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `request` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `cvv` int(11) DEFAULT NULL,
  `expMonth` int(11) DEFAULT NULL,
  `expYear` int(11) DEFAULT NULL,
  `holder` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `rendezvous_id` int(11) NOT NULL,
  `service_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fqncf826cbbt5fber93wnxxwd` (`rendezvous_id`),
  KEY `FK_l1v2qq3n315obw8m24obhalup` (`service_id`),
  CONSTRAINT `FK_l1v2qq3n315obw8m24obhalup` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`),
  CONSTRAINT `FK_fqncf826cbbt5fber93wnxxwd` FOREIGN KEY (`rendezvous_id`) REFERENCES `rendezvous` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `service` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `cancelled` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `category_id` int(11) NOT NULL,
  `manager_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_aj8vdl7dlauw7ylj55c1fuboc` (`category_id`),
  KEY `FK_87be48k3rjrg7rsthpl9t55wd` (`manager_id`),
  CONSTRAINT `FK_87be48k3rjrg7rsthpl9t55wd` FOREIGN KEY (`manager_id`) REFERENCES `manager` (`id`),
  CONSTRAINT `FK_aj8vdl7dlauw7ylj55c1fuboc` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `birthDate` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_o6s94d43co03sx067ili5760c` (`userAccount_id`),
  CONSTRAINT `FK_o6s94d43co03sx067ili5760c` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_answer`
--

DROP TABLE IF EXISTS `user_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_answer` (
  `User_id` int(11) NOT NULL,
  `answers_id` int(11) NOT NULL,
  UNIQUE KEY `UK_edy1x9bio0x9cew0pe84p1tpk` (`answers_id`),
  KEY `FK_j778pvtp7v98nbds3dufwod98` (`User_id`),
  CONSTRAINT `FK_j778pvtp7v98nbds3dufwod98` FOREIGN KEY (`User_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_edy1x9bio0x9cew0pe84p1tpk` FOREIGN KEY (`answers_id`) REFERENCES `answer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `useraccount`
--

DROP TABLE IF EXISTS `useraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_csivo9yqa08nrbkog71ycilh5` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount`
--

LOCK TABLES `useraccount` WRITE;
/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
INSERT INTO `useraccount` VALUES (529,0,'21232f297a57a5a743894a0e4a801fc3','admin');
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount_authorities`
--

DROP TABLE IF EXISTS `useraccount_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount_authorities` (
  `UserAccount_id` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_b63ua47r0u1m7ccc9lte2ui4r` (`UserAccount_id`),
  CONSTRAINT `FK_b63ua47r0u1m7ccc9lte2ui4r` FOREIGN KEY (`UserAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount_authorities`
--

LOCK TABLES `useraccount_authorities` WRITE;
/*!40000 ALTER TABLE `useraccount_authorities` DISABLE KEYS */;
INSERT INTO `useraccount_authorities` VALUES (529,'ADMIN');
/*!40000 ALTER TABLE `useraccount_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-23 20:18:59

commit;
