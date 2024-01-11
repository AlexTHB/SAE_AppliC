package com.example.packetDisplayer;

public class PacketCapture {
    private int id;
    private String packetCaptureName;
    private String uploadDate;

    public PacketCapture(int id, String packetCaptureName, String uploadDate) {
        this.id = id;
        this.packetCaptureName = packetCaptureName;
        this.uploadDate = uploadDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPacketCaptureName() {
        return packetCaptureName;
    }

    public void setPacketCaptureName(String packetCaptureName) {
        this.packetCaptureName = packetCaptureName;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }
}
