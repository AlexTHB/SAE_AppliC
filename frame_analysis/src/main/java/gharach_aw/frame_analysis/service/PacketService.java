package gharach_aw.frame_analysis.api.service;

import gharach_aw.frame_analysis.api.persistence.entity.Packet;

import java.util.List;

public interface PacketService {

    List<Packet> findAllPacketsByCaptureId(Long captureId);

    List<Packet> findAllPacketsByCaptureName(String captureName);
}

