package gharach_aw.frame_analysis.api.service;


import gharach_aw.frame_analysis.api.persistence.entity.PacketCapture;

public interface PacketCaptureService {

    // Create
    void savePacketCapture(PacketCapture packetCapture);

    // Read
    PacketCapture findPacketCaptureById(int id);
    
    PacketCapture findPacketCaptureByName(String fileName);

    // Update
    void updatePacketCapture(PacketCapture packetCapture);

    // Delete
    void deletePacketCaptureById(int id);
}
