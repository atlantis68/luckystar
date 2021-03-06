-- MySQL dump 10.13  Distrib 5.7.19, for osx10.12 (x86_64)
--
-- Host: localhost    Database: luckystar1
-- ------------------------------------------------------
-- Server version	5.7.19

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
-- Table structure for table `chicken_info`
--

DROP TABLE IF EXISTS `chicken_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chicken_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(10) NOT NULL COMMENT '真名',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '艺名',
  `star_id` bigint(20) NOT NULL COMMENT '繁星id',
  `reg_date` date NOT NULL COMMENT '注册时间',
  `cookie` varchar(10480) DEFAULT NULL COMMENT '登录后的cookie信息，需要人工定期维护',
  `time_rate` float(2,1) NOT NULL DEFAULT '1.0' COMMENT '考勤倍率',
  `bean_rate` float(2,1) NOT NULL DEFAULT '1.0' COMMENT '星豆倍率',
  `state` varchar(255) NOT NULL COMMENT '0：停用 1：在用',
  `labor_union_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_chicken_info_labor_union_id` (`labor_union_id`),
  CONSTRAINT `fk_chicken_info_labor_union_id` FOREIGN KEY (`labor_union_id`) REFERENCES `labor_union` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chicken_info`
--

LOCK TABLES `chicken_info` WRITE;
/*!40000 ALTER TABLE `chicken_info` DISABLE KEYS */;
INSERT INTO `chicken_info` VALUES (1,'陈怡','JD走运求秒榜一',365997694,'2017-08-09','kg_mid=f245e8b93bd77a4079dba4d535a4dd04; KuGoo=KugooID=1067527567&KugooPwd=35F3FC5E282631877D7E900040ADED45&NickName=%u0031%u0030%u0036%u0037%u0035%u0032%u0037%u0035%u0036%u0037&Pic=http://imge.kugou.com/kugouicon/165/20100101/20100101192931478054.jpg&RegState=1&RegFrom=&t=56959ea30d774b684f31dc64589d8116098e2d7cec637052b6b0497ae6cf3e4f&a_id=1010&ct=1502243400&UserName=%u0031%u0038%u0036%u0038%u0033%u0034%u0034%u0033%u0036%u0036%u0035; _fxNickName=JD%E8%B5%B0%E8%BF%90%E6%B1%82%E7%A7%92%E6%A6%9C%E4%B8%80; _fxRichLevel=6; Hm_lvt_a8cea520bce1646202b6709aa9f11956=1502002639,1502105592,1502158620,1502243401; _fx_coin=1548.00; fxClientInfo=%7B%22userId%22%3A%22365997694%22%2C%22kugouId%22%3A%221067527567%22%2C%22ip%22%3A%220.0.0.0%22%7D; ACK_SERVER_10004=%7B%22list%22%3A%5B%5B%22apibj.fanxing.kugou.com%22%5D%5D%7D; FANXING=%257B%2522kugouId%2522%253A%25221067527567%2522%252C%2522coin%2522%253A%25221548.00%2522%252C%2522atime%2522%253A1502274853%252C%2522isRemember%2522%253A0%252C%2522sign%2522%253A%2522fe349936b65d1fa012f713b762f18e31%2522%257D; FANXING_COIN=1548.00; ACK_SERVER_10013=%7B%22list%22%3A%5B%5B%22visitor.fanxing.kugou.com%22%5D%2C%5B%22visitorfx2.kugou.com%22%5D%5D%7D; ACK_SERVER_10021=%7B%22list%22%3A%5B%5B%22service01.fanxing.com%22%5D%2C%5B%22service03.fanxing.com%22%5D%2C%5B%22service02.fanxing.com%22%5D%2C%5B%22service04.fanxing.com%22%5D%5D%7D; ACK_SERVER_10012=%7B%22list%22%3A%5B%5B%22task.fanxing.kugou.com%22%5D%2C%5B%22taskfx2.kugou.com%22%5D%5D%7D; ACK_SERVER_10009=%7B%22list%22%3A%5B%5B%22act.fanxing.kugou.com%22%5D%2C%5B%22actfx2.kugou.com%22%5D%5D%7D; ACK_SERVER_10011=%7B%22list%22%3A%5B%5B%22fanxing.kugou.com%22%5D%2C%5B%22sparefx2.kugou.com%22%5D%5D%7D; ACK_SERVER_10020=%7B%22list%22%3A%5B%5B%22fx.service.kugou.com%22%5D%2C%5B%22fxservice1.kugou.com%22%5D%2C%5B%22fxservice2.kugou.com%22%5D%5D%7D; ACK_SERVER_10010=%7B%22list%22%3A%5B%5B%22api.fanxing.kugou.com%22%5D%2C%5B%22apifx2.kugou.com%22%5D%5D%7D; ACK_SERVER_10014=%7B%22list%22%3A%5B%5B%22service.fanxing.kugou.com%22%5D%2C%5B%22servicefx2.kugou.com%22%5D%5D%7D; fx_manifest=1; Hm_lvt_9903bd4a6f77b8537ecb17a75dd2e187=1502105130,1502158616,1502243398,1502274852; Hm_lpvt_9903bd4a6f77b8537ecb17a75dd2e187=1502274852; Hm_lvt_e0a7c5eaf6994884c4376a64da96825a=1502105129,1502158615,1502243398,1502274853; Hm_lpvt_e0a7c5eaf6994884c4376a64da96825a=1502274853; Hm_lvt_52e69492bce68bf637c6f3a2f099ae08=1502105129,1502158616,1502243398,1502274853; Hm_lpvt_52e69492bce68bf637c6f3a2f099ae08=1502274853',1.0,1.0,'ON',1),(2,'测试','多点test',1131331064,'2017-08-11','kg_mid=f245e8b93bd77a4079dba4d535a4dd04; _fxNickName=JD%E8%B5%B0%E8%BF%90%E6%B1%82%E7%A7%92%E6%A6%9C%E4%B8%80; _fxRichLevel=6; Hm_lvt_a8cea520bce1646202b6709aa9f11956=1502002639,1502105592,1502158620,1502243401; _fx_coin=3.00; fxClientInfo=%7B%22userId%22%3A%22365997694%22%2C%22kugouId%22%3A%221067527567%22%2C%22ip%22%3A%220.0.0.0%22%7D; FANXING=%257B%2522kugouId%2522%253A%25221067527567%2522%252C%2522coin%2522%253A%25223.00%2522%252C%2522atime%2522%253A1502439688%252C%2522isRemember%2522%253A0%252C%2522sign%2522%253A%252201deff1f4fa33914409b19f866831b8d%2522%257D; FANXING_COIN=3.00; ACK_SERVER_10004=%7B%22list%22%3A%5B%5B%22apibj.fanxing.kugou.com%22%5D%5D%7D; ACK_SERVER_10013=%7B%22list%22%3A%5B%5B%22visitor.fanxing.kugou.com%22%5D%2C%5B%22visitorfx2.kugou.com%22%5D%5D%7D; ACK_SERVER_10021=%7B%22list%22%3A%5B%5B%22service01.fanxing.com%22%5D%2C%5B%22service03.fanxing.com%22%5D%2C%5B%22service02.fanxing.com%22%5D%2C%5B%22service04.fanxing.com%22%5D%5D%7D; ACK_SERVER_10012=%7B%22list%22%3A%5B%5B%22task.fanxing.kugou.com%22%5D%2C%5B%22taskfx2.kugou.com%22%5D%5D%7D; ACK_SERVER_10009=%7B%22list%22%3A%5B%5B%22act.fanxing.kugou.com%22%5D%2C%5B%22actfx2.kugou.com%22%5D%5D%7D; ACK_SERVER_10011=%7B%22list%22%3A%5B%5B%22fanxing.kugou.com%22%5D%2C%5B%22sparefx2.kugou.com%22%5D%5D%7D; ACK_SERVER_10020=%7B%22list%22%3A%5B%5B%22fx.service.kugou.com%22%5D%2C%5B%22fxservice1.kugou.com%22%5D%2C%5B%22fxservice2.kugou.com%22%5D%5D%7D; ACK_SERVER_10010=%7B%22list%22%3A%5B%5B%22api.fanxing.kugou.com%22%5D%2C%5B%22apifx2.kugou.com%22%5D%5D%7D; ACK_SERVER_10014=%7B%22list%22%3A%5B%5B%22service.fanxing.kugou.com%22%5D%2C%5B%22servicefx2.kugou.com%22%5D%5D%7D; fx_manifest=1; Hm_lvt_e0a7c5eaf6994884c4376a64da96825a=1502158615,1502243398,1502274853,1502439687; Hm_lpvt_e0a7c5eaf6994884c4376a64da96825a=1502439687; Hm_lvt_9903bd4a6f77b8537ecb17a75dd2e187=1502158616,1502243398,1502274852,1502439687; Hm_lpvt_9903bd4a6f77b8537ecb17a75dd2e187=1502439688; Hm_lvt_52e69492bce68bf637c6f3a2f099ae08=1502158616,1502243398,1502274853,1502439687; Hm_lpvt_52e69492bce68bf637c6f3a2f099ae08=1502439688; LoginCheckCode=128e84ebeabc434ba8573ee668b36c992eb9; KuGoo=KugooID=1131331064&KugooPwd=E602088D7DC2F53AADF6BB29E38A5D86&NickName=%u0031%u0031%u0033%u0031%u0033%u0033%u0031%u0030%u0036%u0034&Pic=http://imge.kugou.com/kugouicon/165/20100101/20100101192931478054.jpg&RegState=1&RegFrom=&t=8b3daf543e5664ee2d47c7170b84fcd66e74b5eb6f88d9632bb5f92c76308c56&a_id=1010&ct=1502439725&UserName=%u0031%u0038%u0039%u0038%u0030%u0038%u0036%u0038%u0030%u0039%u0036',1.0,1.0,'ON',1);
/*!40000 ALTER TABLE `chicken_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-11 21:16:52
