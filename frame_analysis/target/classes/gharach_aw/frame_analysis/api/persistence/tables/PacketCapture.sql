-- Active: 1692532684514@@127.0.0.1@3306@frameanalysis
CREATE TABLE `packet_capture` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fileName` varchar(100) NOT NULL,
  `fileDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
)