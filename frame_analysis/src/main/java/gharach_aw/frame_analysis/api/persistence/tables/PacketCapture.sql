-- Active: 1692532684514@@127.0.0.1@3306@frameanalysis
CREATE TABLE `packetCapture` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fileName` varchar(100) NOT NULL,
  `fileDate` date NOT NULL,
  PRIMARY KEY (`id`)
)