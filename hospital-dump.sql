-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: hospital
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `consultatie`
--
DROP TABLE IF EXISTS `consultatie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consultatie` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_programare` int NOT NULL,
  `diagnostic` text,
  `simptome` text,
  `cost` decimal(10,2) DEFAULT NULL,
  `data_consultatie` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `id_programare` (`id_programare`),
  CONSTRAINT `consultatie_ibfk_1` FOREIGN KEY (`id_programare`) REFERENCES `programare` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consultatie`
--

LOCK TABLES `consultatie` WRITE;
/*!40000 ALTER TABLE `consultatie` DISABLE KEYS */;
/*!40000 ALTER TABLE `consultatie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factura`
--

DROP TABLE IF EXISTS `factura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factura` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_consultatie` int NOT NULL,
  `data_emitere` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `suma` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_consultatie` (`id_consultatie`),
  CONSTRAINT `factura_ibfk_1` FOREIGN KEY (`id_consultatie`) REFERENCES `consultatie` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factura`
--

LOCK TABLES `factura` WRITE;
/*!40000 ALTER TABLE `factura` DISABLE KEYS */;
/*!40000 ALTER TABLE `factura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prescriptie`
--

DROP TABLE IF EXISTS `prescriptie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prescriptie` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_consultatie` int NOT NULL,
  `medicament` varchar(100) NOT NULL,
  `doza_zilnica` int DEFAULT NULL,
  `durata_tratament_in_zile` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_consultatie` (`id_consultatie`),
  CONSTRAINT `prescriptie_ibfk_1` FOREIGN KEY (`id_consultatie`) REFERENCES `consultatie` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prescriptie`
--

LOCK TABLES `prescriptie` WRITE;
/*!40000 ALTER TABLE `prescriptie` DISABLE KEYS */;
/*!40000 ALTER TABLE `prescriptie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programare`
--

DROP TABLE IF EXISTS `programare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `programare` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_pacient` int NOT NULL,
  `id_doctor` int NOT NULL,
  `data_programare` datetime NOT NULL,
  `status` enum('in asteptare','aprobata','respinsa') DEFAULT 'in asteptare',
  PRIMARY KEY (`id`),
  KEY `id_pacient` (`id_pacient`),
  KEY `id_doctor` (`id_doctor`),
  CONSTRAINT `programare_ibfk_1` FOREIGN KEY (`id_pacient`) REFERENCES `utilizator` (`id`) ON DELETE CASCADE,
  CONSTRAINT `programare_ibfk_2` FOREIGN KEY (`id_doctor`) REFERENCES `utilizator` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programare`
--

LOCK TABLES `programare` WRITE;
/*!40000 ALTER TABLE `programare` DISABLE KEYS */;
/*!40000 ALTER TABLE `programare` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utilizator`
--

DROP TABLE IF EXISTS `utilizator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utilizator` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tip` enum('administrator','doctor','pacient','asistent') DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `parola` varchar(50) NOT NULL,
  `nume` varchar(50) NOT NULL,
  `prenume` varchar(50) NOT NULL,
  `numar_telefon` varchar(10) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `data_nasterii` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utilizator`
--

LOCK TABLES `utilizator` WRITE;
/*!40000 ALTER TABLE `utilizator` DISABLE KEYS */;
INSERT INTO `utilizator` VALUES (1,'administrator','admin1','admin123','Ionescu','Mihai','0711111111','mihai.ionescu@hospital.ro','19800101'),(2,'doctor','drpopescu','medic123','Popescu','Andrei','0722222222','andrei.popescu@hospital.ro','19751215'),(3,'doctor','drmarin','marin123','Marin','Ioana','0722333444','ioana.marin@hospital.ro','19830824'),(4,'asistent','asistelaura','asist123','Dumitrescu','Laura','0733333333','laura.dumitrescu@hospital.ro','19900120'),(5,'asistent','asistdan','asistdan','Stan','Dan','0734444444','dan.stan@hospital.ro','19920819'),(6,'pacient','pacientalex','alex123','Georgescu','Alex','0741111111','alex.georgescu@gmail.com','19950610'),(7,'pacient','pacientana','ana123','Iliescu','Ana','0742222222','ana.iliescu@gmail.com','19981225'),(8,'pacient','pacientvlad','vlad123','Petrescu','Vlad','0743333333','vlad.petrescu@gmail.com','19891109');
/*!40000 ALTER TABLE `utilizator` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-07 11:33:06
