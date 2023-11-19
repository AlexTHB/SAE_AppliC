-- Active: 1692532684514@@127.0.0.1@3306@frameanalysis
CREATE TABLE `frame` (
  `id` int NOT NULL AUTO_INCREMENT,
  `frameNum` int NOT NULL,
  `dateFrame` datetime NOT NULL,
  `DstMac` varchar(100) NOT NULL,
  `SrcMac` varchar(100) NOT NULL,
  `EtherType` varchar(100) NOT NULL,
  `SrcIP` varchar(100) NOT NULL,
  `DstIP` varchar(100) NOT NULL,
  `SrcPort` int NOT NULL,
  `DstPort` int NOT NULL,
  `TransportProtocol` varchar(100) DEFAULT NULL,
  `ApplicationProtocol` varchar(100) DEFAULT NULL,
  `Size` int NOT NULL,
  `capture_id` int NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`capture_id`) REFERENCES `capture` (`id`)
);
