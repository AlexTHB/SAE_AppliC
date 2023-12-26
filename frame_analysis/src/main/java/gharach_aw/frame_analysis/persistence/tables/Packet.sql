CREATE TABLE packet (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  packetNum INT NOT NULL,
  packetDate DATETIME NOT NULL,
  dstMac VARCHAR(20) NOT NULL,
  srcMac VARCHAR(20) NOT NULL,
  etherType VARCHAR(10) NOT NULL,
  srcIP VARCHAR(50) NOT NULL,
  dstIP VARCHAR(50) NOT NULL,
  srcPort INT NOT NULL,
  dstPort INT NOT NULL,
  transportProtocol VARCHAR(20),
  applicationProtocol VARCHAR(20),
  size INT NOT NULL,
  packetCaptureId INT NOT NULL,
  FOREIGN KEY (packetCaptureId) REFERENCES packet_capture (id)
);
