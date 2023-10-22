CREATE TABLE `frame` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_frame` datetime NOT NULL,
  `mac_src` varchar(100) NOT NULL,
  `mac_dest` varchar(100) NOT NULL,
  `EtherType` varchar(100) NOT NULL,
  `ip_src` varchar(100) NOT NULL,
  `ip_dest` varchar(100) NOT NULL,
  `port_src` int NOT NULL,
  `port_dst` int NOT NULL,
  `prtocole_transport` varchar(100) NOT NULL,
  `protocole_application` varchar(100) NOT NULL,
  `size` int NOT NULL,
  `info` varchar(200) NOT NULL,
  `id_capture` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_capture` (`id_capture`),
  CONSTRAINT `frame_ibfk_1` FOREIGN KEY (`id_capture`) REFERENCES `captures` (`id`)
) 
