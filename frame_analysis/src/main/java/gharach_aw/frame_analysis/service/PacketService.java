package gharach_aw.frame_analysis.service;

import java.util.List;

import gharach_aw.frame_analysis.persistence.entity.Packet;
import gharach_aw.frame_analysis.persistence.entity.PacketDTO;

public interface PacketService {
    
    List<Packet> findAllPacketsByCaptureId(Long captureId);

    List<Packet> findAllPacketsByCaptureName(String captureName);

    List<PacketDTO> convertToPacketDTOList(List<Packet> packets);    

}
