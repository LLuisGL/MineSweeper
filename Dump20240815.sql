CREATE DATABASE  IF NOT EXISTS `buscaminas` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `buscaminas`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: buscaminas
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `amigos`
--

DROP TABLE IF EXISTS `amigos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `amigos` (
  `IdUsuario` int NOT NULL,
  `IdUsuarioAmigo` int NOT NULL,
  `nombreAmigo` varchar(50) DEFAULT NULL,
  `estado` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`IdUsuario`,`IdUsuarioAmigo`),
  KEY `IdUsuarioAmigo` (`IdUsuarioAmigo`),
  CONSTRAINT `amigos_ibfk_1` FOREIGN KEY (`IdUsuario`) REFERENCES `usuario` (`id`),
  CONSTRAINT `amigos_ibfk_2` FOREIGN KEY (`IdUsuarioAmigo`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `amigos`
--

LOCK TABLES `amigos` WRITE;
/*!40000 ALTER TABLE `amigos` DISABLE KEYS */;
INSERT INTO `amigos` VALUES (3,4,'Admin',1),(3,5,'Prueba',1);
/*!40000 ALTER TABLE `amigos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `juego`
--

DROP TABLE IF EXISTS `juego`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `juego` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idUsuario` int DEFAULT NULL,
  `coordenadas` varchar(500) DEFAULT NULL,
  `fecha` varchar(60) DEFAULT NULL,
  `visitas` int DEFAULT NULL,
  `activo` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_juego` (`idUsuario`),
  CONSTRAINT `fk_juego` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `juego`
--

LOCK TABLES `juego` WRITE;
/*!40000 ALTER TABLE `juego` DISABLE KEYS */;
INSERT INTO `juego` VALUES (5,1,'002 01m 02m 031 10m 114 122 131 20m 214 222 231 302 31m 32m 331 ','2024-08-12',5,0),(7,1,'00m 01m 023 03m 04m 052 061 072 08m 092 10m 115 12m 135 14m 153 162 17m 185 19m 20m 214 221 234 24m 253 262 27m 285 29m 30m 312 320 332 34m 352 361 372 38m 392 401 411 420 431 441 451 460 471 481 491 502 512 522 532 542 551 561 572 583 592 60m 61m 623 63m 64m 652 662 67m 68m 69m 70m 715 72m 735 74m 753 763 77m 785 792 80m 814 821 834 84m 853 863 87m 885 892 90m 912 920 932 94m 952 962 97m 98m 99m ','2024-08-14',3,0),(8,3,'00m 012 022 03m 042 050 10m 113 123 13m 144 151 20m 213 223 23m 245 25m 30m 313 323 33m 346 35m 40m 415 424 43m 445 45m 50m 51m 52m 533 54m 552 ','2024-08-14',1,0),(9,3,'00m 01m 02m 03m 04m 05m 06m 073 081 090 10m 11m 12m 13m 14m 15m 16m 17m 181 190 20m 218 22m 23m 24m 25m 26m 274 282 290 30m 31m 32m 33m 34m 35m 366 37m 381 390 40m 41m 42m 438 44m 45m 46m 473 481 490 50m 51m 52m 53m 54m 55m 56m 573 580 590 604 61m 62m 63m 64m 65m 66m 673 680 690 704 71m 72m 73m 74m 75m 76m 772 780 790 80m 81m 82m 83m 84m 85m 864 871 880 890 903 91m 92m 93m 94m 95m 962 970 980 990 ','2024-08-14',0,0),(10,3,'00m 012 020 030 10m 113 120 130 20m 215 222 231 30m 31m 32m 331 ','2024-08-14',0,0),(11,3,'000 010 020 030 040 050 060 070 080 090 100 111 121 131 140 150 160 170 180 190 200 212 22m 232 240 250 260 270 280 290 300 313 32m 333 340 350 360 370 380 390 400 413 42m 433 440 450 460 470 480 490 500 513 52m 535 543 552 561 570 580 590 600 612 62m 63m 64m 65m 661 670 680 690 700 711 722 733 743 752 761 770 780 790 800 810 820 830 840 850 860 870 880 890 900 910 920 930 940 950 960 970 980 990 ','2024-08-15',0,0),(12,4,'002 01m 02m 032 042 05m 062 071 080 090 10m 114 122 133 14m 156 16m 172 180 190 20m 214 222 234 24m 25m 26m 273 280 290 302 31m 32m 333 34m 355 36m 372 380 390 403 415 424 434 442 453 462 473 483 492 50m 51m 52m 534 54m 552 562 57m 58m 59m 60m 617 62m 635 64m 653 663 67m 687 69m 70m 71m 723 735 74m 754 764 77m 787 79m 80m 814 82m 833 84m 85m 863 87m 88m 89m 901 912 921 932 942 952 962 972 983 992 ','2024-08-15',1,0),(13,4,'000 010 020 030 040 050 060 070 080 090 100 110 120 130 140 150 160 170 180 190 200 210 220 230 240 250 260 270 280 290 300 310 321 332 343 353 362 371 380 390 402 413 424 43m 44m 45m 46m 474 483 492 50m 51m 52m 53m 54m 55m 56m 57m 58m 59m 602 613 624 63m 64m 65m 66m 674 683 692 700 710 721 732 743 753 762 771 780 790 800 810 820 830 840 850 860 870 880 890 900 910 920 930 940 950 960 970 980 990 ','2024-08-15',2,0),(14,5,'001 011 022 031 102 11m 124 13m 203 21m 227 23m 302 31m 32m 33m ','2024-08-15',1,0);
/*!40000 ALTER TABLE `juego` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `score`
--

DROP TABLE IF EXISTS `score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `score` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idUsuario` int DEFAULT NULL,
  `puntuacion` varchar(100) DEFAULT NULL,
  `fecha` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_score` (`idUsuario`),
  CONSTRAINT `fk_score` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `score`
--

LOCK TABLES `score` WRITE;
/*!40000 ALTER TABLE `score` DISABLE KEYS */;
INSERT INTO `score` VALUES (1,3,'575032','2024-08-15'),(2,4,'573801','2024-08-15');
/*!40000 ALTER TABLE `score` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solicitudamistad`
--

DROP TABLE IF EXISTS `solicitudamistad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `solicitudamistad` (
  `IdSolicitud` int NOT NULL AUTO_INCREMENT,
  `IdUsuarioS` int NOT NULL,
  `IdUsuarioR` int NOT NULL,
  `EstadoSolicitud` char(1) NOT NULL DEFAULT 'P',
  PRIMARY KEY (`IdSolicitud`),
  KEY `IdUsuarioS` (`IdUsuarioS`),
  KEY `IdUsuarioR` (`IdUsuarioR`),
  CONSTRAINT `solicitudamistad_ibfk_1` FOREIGN KEY (`IdUsuarioS`) REFERENCES `usuario` (`id`),
  CONSTRAINT `solicitudamistad_ibfk_2` FOREIGN KEY (`IdUsuarioR`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitudamistad`
--

LOCK TABLES `solicitudamistad` WRITE;
/*!40000 ALTER TABLE `solicitudamistad` DISABLE KEYS */;
INSERT INTO `solicitudamistad` VALUES (1,4,3,'A'),(2,5,3,'A');
/*!40000 ALTER TABLE `solicitudamistad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `contrasena` varchar(100) DEFAULT NULL,
  `activo` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Luis','1234',0),(3,'Mome','Prueba',0),(4,'Admin','admin',0),(5,'Prueba','Prueba123',0);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-15  5:24:53
