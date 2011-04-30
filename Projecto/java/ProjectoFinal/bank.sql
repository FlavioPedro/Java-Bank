-- MySQL dump 10.13  Distrib 5.1.54, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: Bank
-- ------------------------------------------------------
-- Server version	5.1.54-1ubuntu4

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
-- Database creation `Bank`
--
DROP DATABASE IF EXISTS `Bank`;
CREATE DATABASE IF NOT EXISTS `Bank`;

USE Bank;
--
-- Table structure for table `Bank`
--

DROP TABLE IF EXISTS `Bank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Bank` (
  `bankID` int(11) NOT NULL AUTO_INCREMENT,
  `bankName` varchar(45) NOT NULL,
  `refDate` date NOT NULL,
  PRIMARY KEY (`bankID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Bank`
--

LOCK TABLES `Bank` WRITE;
/*!40000 ALTER TABLE `Bank` DISABLE KEYS */;
INSERT INTO `Bank` VALUES (1,'Montepio','2010-03-20'),(2,'BPN','2009-11-03');
/*!40000 ALTER TABLE `Bank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Client`
--

DROP TABLE IF EXISTS `Client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Client` (
  `NIF` int(11) NOT NULL,
  `bankID` int(11) NOT NULL,
  `name` varchar(124) NOT NULL,
  `address` varchar(124) NOT NULL,
  `zipCode` varchar(45) NOT NULL,
  `mail` varchar(124) DEFAULT NULL,
  `contact` varchar(124) NOT NULL,
  `docType` varchar(45) NOT NULL,
  `docNr` varchar(60) NOT NULL,
  `birthDate` date NOT NULL,
  `sex` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`NIF`),
  KEY `fk_Client_Bank` (`bankID`),
  CONSTRAINT `fk_Client_Bank` FOREIGN KEY (`bankID`) REFERENCES `Bank` (`bankID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Client`
--

LOCK TABLES `Client` WRITE;
/*!40000 ALTER TABLE `Client` DISABLE KEYS */;
INSERT INTO `Client` VALUES (23445444,1,'Maria Santos','Praça de Alegria, 12 - 3º Esq.','2845-367 Azambuja','maria-santos@gmail.com','9XXXXXXXX','CC','13XXXXX','1990-10-16',2),(246710675,1,'Jorge Miguel','Rua das Flores, 12 - 3º Esq.','2845-367 Amora','o.jhorge@gmail.com','917932612','CC','13548841','1989-11-26',1),(342444343,2,'xxxx xxxxxx','Praça xxxxxxx, 12 - 3º Esq.','xxxx-xxx xxxxx','xxxxxx@gmail.com','9XXXXXXXX','CC','13XXXXX','1994-03-01',2);
/*!40000 ALTER TABLE `Client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Holder`
--

DROP TABLE IF EXISTS `Holder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Holder` (
  `holderID` int(11) NOT NULL,
  `clientID` int(11) NOT NULL,
  `currentAccountID` int(11) NOT NULL,
  `associationDate` date DEFAULT NULL,
  PRIMARY KEY (`holderID`),
  KEY `fk_Holder_Client1` (`clientID`),
  KEY `fk_Holder_CurrentAccount1` (`currentAccountID`),
  CONSTRAINT `fk_Holder_Client1` FOREIGN KEY (`clientID`) REFERENCES `Client` (`NIF`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Holder_CurrentAccount1` FOREIGN KEY (`currentAccountID`) REFERENCES `CurrentAccount` (`currentAccountID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Holder`
--

LOCK TABLES `Holder` WRITE;
/*!40000 ALTER TABLE `Holder` DISABLE KEYS */;
INSERT INTO `Holder` VALUES (1001,1,119042011,'2011-01-12'),(1002,2,119042011,'2011-01-11');
/*!40000 ALTER TABLE `Holder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CurrentAccount`
--

DROP TABLE IF EXISTS `CurrentAccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CurrentAccount` (
  `currentAccountID` int(11) NOT NULL,
  `bankID` int(11) NOT NULL,
  `openDate` date NOT NULL,
  `currentAmount` double NOT NULL,
  `initialAmount` double NOT NULL,
  PRIMARY KEY (`currentAccountID`),
  KEY `fk_CurrentAccount_Bank1` (`bankID`),
  CONSTRAINT `fk_CurrentAccount_Bank1` FOREIGN KEY (`bankID`) REFERENCES `Bank` (`bankID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CurrentAccount`
--

LOCK TABLES `CurrentAccount` WRITE;
/*!40000 ALTER TABLE `CurrentAccount` DISABLE KEYS */;
INSERT INTO `CurrentAccount` VALUES (119042011,1,'2011-01-03',500,15000);
/*!40000 ALTER TABLE `CurrentAccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CurrentMovementHistory`
--

DROP TABLE IF EXISTS `CurrentMovementHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CurrentMovementHistory` (
  `currentMovementHistoryID` int(11) NOT NULL,
  `currentAccountID` int(11) NOT NULL,
  `movementType` varchar(45) NOT NULL,
  `amountMoved` double NOT NULL,
  `movementDate` date NOT NULL,
  PRIMARY KEY (`currentMovementHistoryID`),
  KEY `fk_CurrentMovementHistory_CurrentAccount1` (`currentAccountID`),
  CONSTRAINT `fk_CurrentMovementHistory_CurrentAccount1` FOREIGN KEY (`currentAccountID`) REFERENCES `CurrentAccount` (`currentAccountID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CurrentMovementHistory`
--

LOCK TABLES `CurrentMovementHistory` WRITE;
/*!40000 ALTER TABLE `CurrentMovementHistory` DISABLE KEYS */;
/*!40000 ALTER TABLE `CurrentMovementHistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SavingsAccount`
--

DROP TABLE IF EXISTS `SavingsAccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SavingsAccount` (
  `savingsAccountID` int(11) NOT NULL,
  `currentAccountID` int(11) NOT NULL,
  `savingsAccountTypeID` int(11) NOT NULL,
  `openDate` date NOT NULL,
  `initialAmount` double NOT NULL,
  PRIMARY KEY (`savingsAccountID`),
  KEY `fk_SavingsAccount_CurrentAccount1` (`currentAccountID`),
  KEY `fk_SavingsAccount_SavingsAccountType1` (`savingsAccountTypeID`),
  CONSTRAINT `fk_SavingsAccount_CurrentAccount1` FOREIGN KEY (`currentAccountID`) REFERENCES `CurrentAccount` (`currentAccountID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_SavingsAccount_SavingsAccountType1` FOREIGN KEY (`savingsAccountTypeID`) REFERENCES `SavingsAccountType` (`savingsAccountTypeID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SavingsAccount`
--

LOCK TABLES `SavingsAccount` WRITE;
/*!40000 ALTER TABLE `SavingsAccount` DISABLE KEYS */;
/*!40000 ALTER TABLE `SavingsAccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SavingsAccountType`
--

DROP TABLE IF EXISTS `SavingsAccountType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SavingsAccountType` (
  `savingsAccountTypeID` int(11) NOT NULL,
  `type` varchar(10) NOT NULL,
  `interestRate` double NOT NULL,
  `duration` int(11) NOT NULL,
  PRIMARY KEY (`savingsAccountTypeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SavingsAccountType`
--

LOCK TABLES `SavingsAccountType` WRITE;
/*!40000 ALTER TABLE `SavingsAccountType` DISABLE KEYS */;
INSERT INTO `SavingsAccountType` VALUES (1,'Simples',0.04,365),(2,'Gold',0.09,1095);
/*!40000 ALTER TABLE `SavingsAccountType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SavingsMovementHistory`
--

DROP TABLE IF EXISTS `SavingsMovementHistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SavingsMovementHistory` (
  `savingsMovementHistoryID` int(11) NOT NULL,
  `savingsAccountID` int(11) NOT NULL,
  `movementType` varchar(45) NOT NULL,
  `amountMoved` double NOT NULL,
  `movementDate` date NOT NULL,
  PRIMARY KEY (`savingsMovementHistoryID`),
  KEY `fk_SavingsMovementHistory_SavingsAccount1` (`savingsAccountID`),
  CONSTRAINT `fk_SavingsMovementHistory_SavingsAccount1` FOREIGN KEY (`savingsAccountID`) REFERENCES `SavingsAccount` (`savingsAccountID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SavingsMovementHistory`
--

LOCK TABLES `SavingsMovementHistory` WRITE;
/*!40000 ALTER TABLE `SavingsMovementHistory` DISABLE KEYS */;
/*!40000 ALTER TABLE `SavingsMovementHistory` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-04-22 14:06:51
