-- Active: 1692532684514@@127.0.0.1@3306@frameanalysis
CREATE TABLE packet_capture (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  packetCaptureName varchar(100) NOT NULL,
  uploadDate DATETIME NOT NULL
);
