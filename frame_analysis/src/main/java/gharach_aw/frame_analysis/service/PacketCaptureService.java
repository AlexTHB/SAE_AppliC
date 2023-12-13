package gharach_aw.frame_analysis.service;


import java.util.List;

import gharach_aw.frame_analysis.persistence.entity.PacketCapture;
import gharach_aw.frame_analysis.persistence.entity.PacketCaptureDTO;

public interface PacketCaptureService {

    void save(PacketCapture packetCapture);

    List<PacketCapture> findAllPacketCaptures();

    List<PacketCaptureDTO> convertToPacketCaptureDTOList(List<PacketCapture> packetCaptures);

    PacketCapture findById(Long id);

    PacketCapture findByFileName(String fileName);

    PacketCapture updatePacketCapture(PacketCapture updatedPacketCapture);

    void deleteById(Long id);

}