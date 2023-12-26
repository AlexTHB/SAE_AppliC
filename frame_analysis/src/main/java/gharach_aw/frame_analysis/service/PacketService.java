package gharach_aw.frame_analysis.service;

import java.util.List;

import gharach_aw.frame_analysis.persistence.entity.Packet;

public interface PacketService {
        
    List<Packet> findAllPacketsByCaptureId(Long captureId);

}