/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : xacml

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2022-04-09 10:00:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for attributeassignment
-- ----------------------------
DROP TABLE IF EXISTS `attributeassignment`;
CREATE TABLE `attributeassignment` (
  `AttID` varchar(255) DEFAULT NULL,
  ` AttDt` varchar(255) DEFAULT NULL,
  ` AttDesId` varchar(255) DEFAULT NULL,
  `AttDesDt` varchar(255) DEFAULT NULL,
  `RCP` varchar(255) DEFAULT NULL,
  `RCPDT` varchar(255) DEFAULT NULL,
  `OblID_fk` varchar(255) DEFAULT NULL,
  KEY `OblID_fk` (`OblID_fk`),
  CONSTRAINT `attributeassignment_ibfk_1` FOREIGN KEY (`OblID_fk`) REFERENCES `obligations` (`OblID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for conditions
-- ----------------------------
DROP TABLE IF EXISTS `conditions`;
CREATE TABLE `conditions` (
  `FuncID` varchar(255) NOT NULL,
  `SAttDesgDT` varchar(255) DEFAULT NULL,
  `SAttDesgID` varchar(255) DEFAULT NULL,
  `RCPath` varchar(255) DEFAULT NULL,
  `AttSeleDT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`FuncID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for obligations
-- ----------------------------
DROP TABLE IF EXISTS `obligations`;
CREATE TABLE `obligations` (
  `OblID` varchar(255) NOT NULL,
  `FulfillOn` varchar(255) DEFAULT NULL,
  `PolicyID_fk` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`OblID`),
  KEY `PolicyID_fk` (`PolicyID_fk`),
  CONSTRAINT `obligations_ibfk_1` FOREIGN KEY (`PolicyID_fk`) REFERENCES `policy` (`PolicyID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for policy
-- ----------------------------
DROP TABLE IF EXISTS `policy`;
CREATE TABLE `policy` (
  `PolicyID` varchar(255) NOT NULL,
  `RCAlgId` varchar(255) DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`PolicyID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for policyset
-- ----------------------------
DROP TABLE IF EXISTS `policyset`;
CREATE TABLE `policyset` (
  `PolicySetID` varchar(255) NOT NULL,
  `PCAlgID` varchar(255) DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`PolicySetID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for pol_pactions
-- ----------------------------
DROP TABLE IF EXISTS `pol_pactions`;
CREATE TABLE `pol_pactions` (
  `PolicyID` varchar(255) NOT NULL,
  `ActionMatchID` varchar(255) NOT NULL,
  PRIMARY KEY (`PolicyID`,`ActionMatchID`),
  KEY `ActionMatchID_fk` (`ActionMatchID`),
  CONSTRAINT `pol_pactions_ibfk_1` FOREIGN KEY (`PolicyID`) REFERENCES `policy` (`PolicyID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `pol_pactions_ibfk_2` FOREIGN KEY (`ActionMatchID`) REFERENCES `ptargetactions` (`ActionMatchID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for pol_penvironments
-- ----------------------------
DROP TABLE IF EXISTS `pol_penvironments`;
CREATE TABLE `pol_penvironments` (
  `PolicyID` varchar(255) NOT NULL,
  `EnvironmentsMatchID` varchar(255) NOT NULL,
  PRIMARY KEY (`PolicyID`,`EnvironmentsMatchID`),
  KEY `ActionMatchID_fk` (`EnvironmentsMatchID`),
  CONSTRAINT `pol_penvironments_ibfk_1` FOREIGN KEY (`PolicyID`) REFERENCES `policy` (`PolicyID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `pol_penvironments_ibfk_2` FOREIGN KEY (`EnvironmentsMatchID`) REFERENCES `ptargetenvironments` (`EnvMatchID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for pol_presources
-- ----------------------------
DROP TABLE IF EXISTS `pol_presources`;
CREATE TABLE `pol_presources` (
  `PolicyID` varchar(255) NOT NULL,
  `ResourcesMatchID` varchar(255) NOT NULL,
  PRIMARY KEY (`PolicyID`,`ResourcesMatchID`),
  KEY `ActionMatchID_fk` (`ResourcesMatchID`),
  CONSTRAINT `pol_presources_ibfk_1` FOREIGN KEY (`PolicyID`) REFERENCES `policy` (`PolicyID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `pol_presources_ibfk_2` FOREIGN KEY (`ResourcesMatchID`) REFERENCES `ptartgetresources` (`ResMatchID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for pol_psubjects
-- ----------------------------
DROP TABLE IF EXISTS `pol_psubjects`;
CREATE TABLE `pol_psubjects` (
  `PolicyID` varchar(255) NOT NULL,
  `SubjectMatchID` varchar(255) NOT NULL,
  PRIMARY KEY (`PolicyID`,`SubjectMatchID`),
  KEY `ActionMatchID_fk` (`SubjectMatchID`),
  CONSTRAINT `pol_psubjects_ibfk_1` FOREIGN KEY (`PolicyID`) REFERENCES `policy` (`PolicyID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `pol_psubjects_ibfk_2` FOREIGN KEY (`SubjectMatchID`) REFERENCES `ptargetsubjects` (`SubMatchID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for pol_rule
-- ----------------------------
DROP TABLE IF EXISTS `pol_rule`;
CREATE TABLE `pol_rule` (
  `PolicyID` varchar(255) NOT NULL,
  `RuleID` varchar(255) NOT NULL,
  PRIMARY KEY (`PolicyID`,`RuleID`),
  KEY `RuleID` (`RuleID`),
  CONSTRAINT `pol_rule_ibfk_1` FOREIGN KEY (`PolicyID`) REFERENCES `policy` (`PolicyID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `pol_rule_ibfk_2` FOREIGN KEY (`RuleID`) REFERENCES `rule` (`RuleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for ptargetactions
-- ----------------------------
DROP TABLE IF EXISTS `ptargetactions`;
CREATE TABLE `ptargetactions` (
  `ActionMatchID` varchar(255) NOT NULL,
  `AttV` varchar(255) DEFAULT NULL,
  `AttDT` varchar(255) DEFAULT NULL,
  `AttDesgID` varchar(255) DEFAULT NULL,
  `AttDesgDT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ActionMatchID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for ptargetenvironments
-- ----------------------------
DROP TABLE IF EXISTS `ptargetenvironments`;
CREATE TABLE `ptargetenvironments` (
  `EnvMatchID` varchar(255) NOT NULL,
  `AttV` varchar(255) DEFAULT NULL,
  `AttDT` varchar(255) DEFAULT NULL,
  `AttDesg` varchar(255) DEFAULT NULL,
  `AttDesgDT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`EnvMatchID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for ptargetsubjects
-- ----------------------------
DROP TABLE IF EXISTS `ptargetsubjects`;
CREATE TABLE `ptargetsubjects` (
  `SubMatchID` varchar(255) NOT NULL,
  `AttV` varchar(255) DEFAULT NULL,
  `AttDT` varchar(255) DEFAULT NULL,
  `AttDesgID` varchar(255) DEFAULT NULL,
  `AttDesgDT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`SubMatchID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for ptartgetresources
-- ----------------------------
DROP TABLE IF EXISTS `ptartgetresources`;
CREATE TABLE `ptartgetresources` (
  `ResMatchID` varchar(255) NOT NULL,
  `AttV` varchar(255) DEFAULT NULL,
  `AttDT` varchar(255) DEFAULT NULL,
  `AttDesgID` varchar(255) DEFAULT NULL,
  `AttDesgDT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ResMatchID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for rtargetactions
-- ----------------------------
DROP TABLE IF EXISTS `rtargetactions`;
CREATE TABLE `rtargetactions` (
  `ActionMatchID` varchar(255) NOT NULL,
  `AttV` varchar(255) DEFAULT NULL,
  `AttDT` varchar(255) DEFAULT NULL,
  `AttDesgID` varchar(255) DEFAULT NULL,
  `AttDesgDT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ActionMatchID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for rtargetenvironments
-- ----------------------------
DROP TABLE IF EXISTS `rtargetenvironments`;
CREATE TABLE `rtargetenvironments` (
  `EnvMatchID` varchar(255) NOT NULL,
  `AttV` varchar(255) DEFAULT NULL,
  `AttDT` varchar(255) DEFAULT NULL,
  `AttDesg` varchar(255) DEFAULT NULL,
  `AttDesgDT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`EnvMatchID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for rtargetsubjects
-- ----------------------------
DROP TABLE IF EXISTS `rtargetsubjects`;
CREATE TABLE `rtargetsubjects` (
  `SubMatchID` varchar(255) NOT NULL,
  `AttV` varchar(255) DEFAULT NULL,
  `AttDT` varchar(255) DEFAULT NULL,
  `AttDesgID` varchar(255) DEFAULT NULL,
  `AttDesgDT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`SubMatchID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for rtartgetresources
-- ----------------------------
DROP TABLE IF EXISTS `rtartgetresources`;
CREATE TABLE `rtartgetresources` (
  `ResMatchID` varchar(255) NOT NULL,
  `AttV` varchar(255) DEFAULT NULL,
  `AttDT` varchar(255) DEFAULT NULL,
  `AttDesgID` varchar(255) DEFAULT NULL,
  `AttDesgDT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ResMatchID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for rule
-- ----------------------------
DROP TABLE IF EXISTS `rule`;
CREATE TABLE `rule` (
  `RuleID` varchar(255) NOT NULL,
  `Effect` varchar(255) DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`RuleID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for rule_conditions
-- ----------------------------
DROP TABLE IF EXISTS `rule_conditions`;
CREATE TABLE `rule_conditions` (
  `RuleID` varchar(255) NOT NULL,
  `FuncID` varchar(255) NOT NULL,
  PRIMARY KEY (`RuleID`,`FuncID`),
  KEY `FuncID_fk` (`FuncID`),
  CONSTRAINT `rule_conditions_ibfk_1` FOREIGN KEY (`RuleID`) REFERENCES `rule` (`RuleID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `rule_conditions_ibfk_2` FOREIGN KEY (`FuncID`) REFERENCES `conditions` (`FuncID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for rule_ractions
-- ----------------------------
DROP TABLE IF EXISTS `rule_ractions`;
CREATE TABLE `rule_ractions` (
  `RuleID` varchar(255) NOT NULL,
  `ActionMatchID` varchar(255) NOT NULL,
  PRIMARY KEY (`RuleID`,`ActionMatchID`),
  KEY `ActionMatchID_fk` (`ActionMatchID`),
  CONSTRAINT `rule_ractions_ibfk_1` FOREIGN KEY (`RuleID`) REFERENCES `rule` (`RuleID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `rule_ractions_ibfk_2` FOREIGN KEY (`ActionMatchID`) REFERENCES `rtargetactions` (`ActionMatchID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for rule_renvironments
-- ----------------------------
DROP TABLE IF EXISTS `rule_renvironments`;
CREATE TABLE `rule_renvironments` (
  `EnvironmentsMatchID` varchar(255) NOT NULL,
  `RuleID` varchar(255) NOT NULL,
  PRIMARY KEY (`EnvironmentsMatchID`,`RuleID`),
  KEY `RuleID` (`RuleID`),
  CONSTRAINT `rule_renvironments_ibfk_1` FOREIGN KEY (`RuleID`) REFERENCES `rule` (`RuleID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `rule_renvironments_ibfk_2` FOREIGN KEY (`EnvironmentsMatchID`) REFERENCES `rtargetenvironments` (`EnvMatchID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for rule_rresources
-- ----------------------------
DROP TABLE IF EXISTS `rule_rresources`;
CREATE TABLE `rule_rresources` (
  `ResourcesMatchID` varchar(255) NOT NULL,
  `RuleID` varchar(255) NOT NULL,
  PRIMARY KEY (`ResourcesMatchID`,`RuleID`),
  KEY `RuleID` (`RuleID`),
  CONSTRAINT `rule_rresources_ibfk_2` FOREIGN KEY (`RuleID`) REFERENCES `rule` (`RuleID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `rule_rresources_ibfk_3` FOREIGN KEY (`ResourcesMatchID`) REFERENCES `rtartgetresources` (`ResMatchID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for rule_rsubjects
-- ----------------------------
DROP TABLE IF EXISTS `rule_rsubjects`;
CREATE TABLE `rule_rsubjects` (
  `SubjectMatchID` varchar(255) NOT NULL,
  `RuleID` varchar(255) NOT NULL,
  PRIMARY KEY (`SubjectMatchID`,`RuleID`),
  KEY `RuleID` (`RuleID`),
  CONSTRAINT `rule_rsubjects_ibfk_1` FOREIGN KEY (`SubjectMatchID`) REFERENCES `rtargetsubjects` (`SubMatchID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `rule_rsubjects_ibfk_2` FOREIGN KEY (`RuleID`) REFERENCES `rule` (`RuleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for set_pol
-- ----------------------------
DROP TABLE IF EXISTS `set_pol`;
CREATE TABLE `set_pol` (
  `PolicySetID` varchar(255) NOT NULL,
  `PolicyID` varchar(255) NOT NULL,
  PRIMARY KEY (`PolicySetID`,`PolicyID`),
  KEY `PolicyID` (`PolicyID`),
  CONSTRAINT `set_pol_ibfk_1` FOREIGN KEY (`PolicySetID`) REFERENCES `policyset` (`PolicySetID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `set_pol_ibfk_2` FOREIGN KEY (`PolicyID`) REFERENCES `policy` (`PolicyID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
