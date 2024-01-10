CREATE TABLE packet (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  packetNum INT,
  packetDate DATETIME,
  dstMac VARCHAR(20),
  srcMac VARCHAR(20),
  etherType VARCHAR(10),
  srcIP VARCHAR(50),
  dstIP VARCHAR(50),
  srcPort INT,
  dstPort INT,
  protocol VARCHAR(20),
  size INT,
  packetCaptureId INT,
  FOREIGN KEY (packetCaptureId) REFERENCES packet_capture (id)
);
