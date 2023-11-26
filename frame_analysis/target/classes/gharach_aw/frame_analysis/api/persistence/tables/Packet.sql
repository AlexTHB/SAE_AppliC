-- Active: 1692532684514@@127.0.0.1@3306@frameanalysis
CREATE TABLE `packet` (
  `id` int NOT NULL AUTO_INCREMENT,
  `packetNum` int NOT NULL,
  `packetDate` datetime NOT NULL,
  `dstMac` varchar(100) NOT NULL,
  `srcMac` varchar(100) NOT NULL,
  `etherType` varchar(100) NOT NULL,
  `srcIP` varchar(100) NOT NULL,
  `dstIP` varchar(100) NOT NULL,
  `srcPort` int NOT NULL,
  `dstPort` int NOT NULL,
  `transportProtocol` varchar(100),
  `applicationProtocol` varchar(100),
  `size` int NOT NULL,
  `packetCaptureId` int NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`packetCaptureId`) REFERENCES `packet_capture` (`id`)
);
