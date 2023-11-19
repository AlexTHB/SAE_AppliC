-- Active: 1692532684514@@127.0.0.1@3306@frameanalysis
CREATE TABLE `capture` (
  `id` int NOT NULL AUTO_INCREMENT,
  `file_name` varchar(100) NOT NULL,
  `file_date` date NOT NULL,
  PRIMARY KEY (`id`)
)