CREATE DATABASE  IF NOT EXISTS `mawellaresort` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `mawellaresort`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: mawellaresort
-- ------------------------------------------------------
-- Server version	5.7.9-log

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
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `createdAt` date DEFAULT NULL,
  `updatedAt` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'SOUPS','2023-12-11',NULL),(2,'STARTERS','2023-12-11',NULL),(3,'MAINS','2023-12-11',NULL),(4,'AL ITALIANA','2023-12-11',NULL),(5,'SWEET EXTRAVAGANZA','2023-12-11',NULL),(6,'SODAS','2023-12-11',NULL),(7,'FRESH JUICES','2023-12-11',NULL),(8,'STILL & SPARKLING WATER','2023-12-11',NULL),(9,'SELECTION OF TEA','2023-12-11',NULL),(10,'MILKSHAKES','2023-12-11',NULL);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `food`
--

DROP TABLE IF EXISTS `food`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food` (
  `serial_no` varchar(10) NOT NULL,
  `food_name` varchar(255) NOT NULL,
  `cost` double NOT NULL,
  `selling_price` double NOT NULL,
  `description` text,
  `created_At` date NOT NULL,
  `updated_At` date DEFAULT NULL,
  `category_id` int(11) NOT NULL,
  PRIMARY KEY (`serial_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food`
--

LOCK TABLES `food` WRITE;
/*!40000 ALTER TABLE `food` DISABLE KEYS */;
INSERT INTO `food` VALUES ('#038943','King Coconut',0,950,'','2023-12-11',NULL,7),('#04ee76','Chicken N’ POP',0,2350,'Sweet Chilli Chicken popcorn served with French fries','2023-12-11',NULL,2),('#055a14','Bottled Drinking Water (1000ml)',0,750,'','2023-12-11',NULL,8),('#069143','Passion fruit Juice',0,1150,'','2023-12-11',NULL,7),('#0694e9',' Ginger Beer',0,890,'','2023-12-11',NULL,6),('#09a32a','SRI LANKAN STYLE FRIED NOODLES',0,2190,'Mix of Sri Lankan vegetables and noodles in soya sauce','2023-12-11',NULL,3),('#0a755c','MBR SIGNATURE CHOCOLATE MOCHA TART',0,2590,'Served with homemade ice cream','2023-12-11',NULL,5),('#0f361c','SRI LANKAN RICE AND CURRY WITH FRESHLOCALLY SOURCED FISH, PRAWN OR CHICKEN',0,2950,'Served with three curries, red or white rice, mango chutney\nAnd papadam','2023-12-11',NULL,3),('#143ba0','HOT BUTTER CALAMARI',0,2450,'Spiced Calamari tossed with Fried Kathurumurunga Leaves,\nToasted Nuts and Dried Chili','2023-12-11',NULL,2),('#19fe07','BBQ CHICKEN AND MOZZARELLA PANINI ',0,2850,'With pan roasted onions, tomato, melted cheese\nAnd fresh greens','2023-12-11',NULL,3),('#1a73d3','BLACK PEPPER OR CHILLI GARLIC PRAWNS',0,2950,'Served with traditional Sri Lankan Kade Paan','2023-12-11',NULL,3),('#1b3cf0','KOTTU ROTI',0,2650,'Choose from Chicken, Fish, Prawn or Vegetable Kottu Roti','2023-12-11',NULL,3),('#221a66','Ginger Ale',0,890,'','2023-12-11',NULL,6),('#2e5017','SPAGHETTI BOLOGNESE',0,2950,'A classic ragout of ground meat and plum tomato,\nTossed with spaghetti','2023-12-11',NULL,4),('#2ed3bf','STUFFED ROTIS',0,2050,'Prawn or Fish Stuffed Rotis accompanied with Scallion, Green\nChili, Cheese and Egg, served with Curry Sauce','2023-12-11',NULL,2),('#31676e','IRRESISTABLE CHOCOLATE BROWNIE',0,2450,'Served with vanilla ice cream','2023-12-11',NULL,5),('#343e0b','Sprite',0,890,'','2023-12-11',NULL,6),('#370fae',' Fanta',0,890,'','2023-12-11',NULL,6),('#377d25','GRILLED CALAMARI WITH CHIPS',0,3450,'','2023-12-11',NULL,3),('#3c2639','Diet Coke ',0,890,'','2023-12-11',NULL,6),('#440f4a','Watermelon Juice',0,1350,'','2023-12-11',NULL,7),('#4b301f','SEAFOOD PLATTER (FOR TWO)',0,8950,'Grilled assortment of seafood served with lemon-butter sauce,\nFrench fries, tartare sauce & Coleslaw','2023-12-11',NULL,3),('#4ca4f3','PENNE AL FREDO',0,2600,'With creamy sauce of mushrooms and chicken','2023-12-11',NULL,4),('#4d53e7','Avocado',0,2690,'','2023-12-11',NULL,10),('#4f98ba','DEVILLED CHICKEN OR FISH',0,3050,'Sri Lankan-style stir-fried chicken or fish,\nchilli paste and steamed Basmati rice','2023-12-11',NULL,3),('#51977a','Pineapple Juice',0,1490,'','2023-12-11',NULL,7),('#57985c','Orange Juice',0,1450,'','2023-12-11',NULL,7),('#57c142','WATALAPPAM',0,1890,'Traditional Sri Lankan seamed custard pudding with fresh\nCoconut milk and jaggery','2023-12-11',NULL,5),('#61d4df','MIXED SEAFOOD FRIED RICE',0,2950,'Served with Mix of Sri Lankan vegetables, Prawns, Cuttlefish, Tuna','2023-12-11',NULL,3),('#6258ce','Banana',0,2490,'','2023-12-11',NULL,10),('#692875','Coke',0,890,'','2023-12-11',NULL,6),('#6a3fbd','BUFFALO CURD AND TRICKLE',0,1600,'','2023-12-11',NULL,5),('#704145','Lime Juice',0,1250,'','2023-12-11',NULL,7),('#72cf76','SEAFOOD SALAD ',0,3690,'Mixed green salad with marinated prawns, cuttlefish, tuna\n& vinaigrette','2023-12-11',NULL,3),('#7555fd','THAI GREEN CURRY',0,2650,'Braised chicken in green curry sauce with aubergine,\nSweet basil and steamed Basmati rice','2023-12-11',NULL,3),('#84796b','CATCH OF THE DAY',0,4800,'Your choice of cooking style: grilled / pan-fried served\nWith buttered vegetables and steamed Basmati rice','2023-12-11',NULL,3),('#84adea','Lime Soda',0,950,'','2023-12-11',NULL,6),('#8e1b0a','MBR SIGNATURE FISH AND CHIPS',0,3950,'Fillet of white fish served with French fries and\nHomemade tartar sauce & Crunchy Coleslaw','2023-12-11',NULL,3),('#92d1d5','Soda',0,890,'','2023-12-11',NULL,6),('#987340','Ceylon Regular Tea or Coffee',0,1350,'','2023-12-11',NULL,9),('#acc7b0','Tonic',0,890,'','2023-12-11',NULL,6),('#b38ecf','CHILLED GAZPACHO SOUP',0,2190,'Refreshing tomato soup drizzled with olive oil and served\nWith Bread','2023-12-11',NULL,1),('#b4d6a9','SPAGHETTI MARINARA',0,2750,'An assortment of locally sourced prawns, cuttlefish and white\nFish in plum tomato sauce with basil, tossed with spaghetti','2023-12-11',NULL,4),('#bcbb66','SWEET CORN AND MEAT SOUP ',0,2690,'Chinese style sweet corn soup with chicken/fish','2023-12-11',NULL,1),('#c014a4','Mango Juice',0,1350,'','2023-12-11',NULL,7),('#c260a4','LOBSTER THERMIDOR (SEASONAL)',0,9900,'Creamy mixture of lobster meat with seasonal vegetables','2023-12-11',NULL,3),('#c6acae','TOM YAM KUNG SOUP ',0,2390,'Thai style spicy seafood soup','2023-12-11',NULL,1),('#c6e325','Chocolate',0,2490,'','2023-12-11',NULL,10),('#d0158c','Strawberry',0,2690,'','2023-12-11',NULL,10),('#d67549','NASI GORENG KAMPUNG',0,2750,'Southeast Asian-Style fried rice, fried egg, chicken,\nShrimp crackers','2023-12-11',NULL,3),('#dae230','Papaya Juice',0,1390,'','2023-12-11','2023-12-11',7),('#e25521','Vanilla',0,2490,'','2023-12-11',NULL,10),('#e88ef6','GRILLED CHICKEN WITH CHIPS/VEGETABLES ',0,2950,'','2023-12-11',NULL,3),('#f14e01','ROASTED PUMPKIN SOUP ',0,2090,'A blend of pure pumpkin combined with meat','2023-12-11',NULL,1),('#f2bdae','MBR SIGNATURE CLUB SANDWICH',0,2450,'Served with French fries','2023-12-11',NULL,3),('#f81721','MBR WAFFLE’S',0,2150,'Homemade waffles served with chocolate sauce & Vanilla ice cream','2023-12-11',NULL,5),('#fc64db','MAWELLA TUNA TARTARE',0,2150,'Battered fried tuna served with tartare sauce and crispy bread ','2023-12-11',NULL,2);
/*!40000 ALTER TABLE `food` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice` (
  `invoice_no` varchar(10) NOT NULL,
  `discount` varchar(45) NOT NULL,
  `purchesed_date` date NOT NULL,
  `total` double NOT NULL,
  `user_id` varchar(10) NOT NULL,
  PRIMARY KEY (`invoice_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice_item`
--

DROP TABLE IF EXISTS `invoice_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `qty` varchar(45) NOT NULL,
  `unit_price` double NOT NULL,
  `invoice_no` varchar(10) NOT NULL,
  `food_serial_no` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_item`
--

LOCK TABLES `invoice_item` WRITE;
/*!40000 ALTER TABLE `invoice_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES (1,'Active'),(2,'Inactive');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` varchar(10) NOT NULL,
  `fullName` varchar(45) NOT NULL,
  `mobile` varchar(10) NOT NULL,
  `user_type_id` int(11) NOT NULL,
  `created_At` date DEFAULT NULL,
  `update_At` date DEFAULT NULL,
  `deleted_At` date DEFAULT NULL,
  `status_id` int(11) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('#f1e802','admin','0000000000',1,'2023-12-11',NULL,NULL,1,'$2a$10$1U9Ipa1HW9I/t8HyTxmFx.38qQhKfMcheMoB.hskq1FqcNiKrFCyO');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_type`
--

DROP TABLE IF EXISTS `user_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_type` (
  `user_type_Id` int(11) NOT NULL AUTO_INCREMENT,
  `user_type` varchar(45) NOT NULL,
  PRIMARY KEY (`user_type_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_type`
--

LOCK TABLES `user_type` WRITE;
/*!40000 ALTER TABLE `user_type` DISABLE KEYS */;
INSERT INTO `user_type` VALUES (1,'Admin'),(2,'Steward');
/*!40000 ALTER TABLE `user_type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-13 13:49:45
