package gharach_aw.frame_analysis.api.service;


import gharach_aw.frame_analysis.api.persistence.entity.PacketCapture;

import java.util.List;

public interface PacketCaptureService {

    void save(PacketCapture packetCapture);

    List<PacketCapture> findAllPacketCaptures();

    PacketCapture findById(Long id);

    PacketCapture findByFileName(String fileName);

    PacketCapture updateEntity(PacketCapture updatedEntity);

    String updatePacketCaptureName(String ancientName, String newName);

    void deleteById(Long id);
}