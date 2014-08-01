/*
SQLyog Trial v11.51 (32 bit)
MySQL - 5.5.19 : Database - agentdb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`agentdb` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `agentdb`;

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `FullName` varchar(30) NOT NULL,
  `ShortName` varchar(20) DEFAULT NULL,
  `Status` char(1) NOT NULL,
  `Image` varchar(30) DEFAULT NULL,
  `Description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `product` */

/*Table structure for table `sku` */

DROP TABLE IF EXISTS `sku`;

CREATE TABLE `sku` (
  `Sku` varchar(20) NOT NULL,
  `ProductId` int(11) NOT NULL,
  `Status` char(1) NOT NULL,
  `Price` decimal(18,0) NOT NULL,
  `Image` varchar(30) DEFAULT NULL,
  `Description` varchar(100) DEFAULT NULL,
  `Link` varchar(200) NOT NULL,
  PRIMARY KEY (`Sku`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sku` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(10) NOT NULL,
  `LastName` varchar(10) NOT NULL,
  `Password` varchar(30) NOT NULL,
  `EmailAddress` varchar(50) NOT NULL,
  `Phone` varchar(20) DEFAULT NULL,
  `Address` varchar(100) DEFAULT NULL,
  `ZipCode` varchar(10) DEFAULT NULL,
  `Permission` varchar(10) NOT NULL DEFAULT 'Customer',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `UN_EmailAddress` (`EmailAddress`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
