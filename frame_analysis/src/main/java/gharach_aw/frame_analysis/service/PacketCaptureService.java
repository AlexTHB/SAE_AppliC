package gharach_aw.frame_analysis.service;


import java.util.List;

import gharach_aw.frame_analysis.persistence.entity.Packet;
import gharach_aw.frame_analysis.persistence.entity.PacketCapture;
import gharach_aw.frame_analysis.persistence.entity.PacketCaptureDTO;
import gharach_aw.frame_analysis.persistence.entity.PacketDTO;

public interface PacketCaptureService {

    void save(PacketCapture packetCapture);

    List<PacketCapture> findAllPacketCaptures();

    List<PacketCaptureDTO> convertToPacketCaptureDTOList(List<PacketCapture> packetCaptures);

    PacketCapture findById(Long id);

    PacketCapture findByFileName(String fileName);

    PacketCapture updatePacketCapture(PacketCapture updatedPacketCapture);

    void updatePacketCaptureName(String ancientName, String newName);

    void deleteById(Long id);

    List<Packet> findAllPacketsByCaptureId(Long captureId);

    List<Packet> findAllPacketsByCaptureName(String captureName);

    List<PacketDTO> convertToPacketDTOList(List<Packet> packets);    
}