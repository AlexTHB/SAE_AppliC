CREATE TABLE `captures` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom_fic` varchar(100) NOT NULL,
  `date_de_creation` date NOT NULL,
  `extension` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) 