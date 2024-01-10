package com.example.packetDisplayer;

public class Packet {
    private int id;
    private int packetNum;
    private String packetDate;
    private String dstMac;
    private String srcMac;
    private String etherType;
    private String srcIP;
    private String dstIP;
    private int srcPort;
    private int dstPort;
    private String protocol;
    private int size;

    public  Packet(){};
    public Packet(int id, int packetNum, String packetDate, String dstMac, String srcMac, String etherType, String srcIP, String dstIP, int srcPort, int dstPort, String protocol, int size) {
        this.id = id;
        this.packetNum = packetNum;
        this.packetDate = packetDate;
        this.dstMac = dstMac;
        this.srcMac = srcMac;
        this.etherType = etherType;
        this.srcIP = srcIP;
        this.dstIP = dstIP;
        this.srcPort = srcPort;
        this.dstPort = dstPort;
        this.protocol = protocol;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPacketNum() {
        return packetNum;
    }

    public void setPacketNum(int packetNum) {
        this.packetNum = packetNum;
    }

    public String getPacketDate() {
        return packetDate;
    }

    public void setPacketDate(String packetDate) {
        this.packetDate = packetDate;
    }

    public String getDstMac() {
        return dstMac;
    }

    public void setDstMac(String dstMac) {
        this.dstMac = dstMac;
    }

    public String getSrcMac() {
        return srcMac;
    }

    public void setSrcMac(String srcMac) {
        this.srcMac = srcMac;
    }

    public String getEtherType() {
        return etherType;
    }

    public void setEtherType(String etherType) {
        this.etherType = etherType;
    }

    public String getSrcIP() {
        return srcIP;
    }

    public void setSrcIP(String srcIP) {
        this.srcIP = srcIP;
    }

    public String getDstIP() {
        return dstIP;
    }

    public void setDstIP(String dstIP) {
        this.dstIP = dstIP;
    }

    public int getSrcPort() {
        return srcPort;
    }

    public void setSrcPort(int srcPort) {
        this.srcPort = srcPort;
    }

    public int getDstPort() {
        return dstPort;
    }

    public void setDstPort(int dstPort) {
        this.dstPort = dstPort;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return ""+id ;
    }
}
