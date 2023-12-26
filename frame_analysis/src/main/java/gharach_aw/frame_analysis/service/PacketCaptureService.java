package gharach_aw.frame_analysis.service;


import java.util.List;
import java.util.Optional;

import gharach_aw.frame_analysis.persistence.entity.Packet;
import gharach_aw.frame_analysis.persistence.entity.PacketCapture;

public interface PacketCaptureService {

    void save(PacketCapture packetCapture, List<Packet> packets);

    List<PacketCapture> findAllPacketCaptures();

    Optional<PacketCapture> findById(Long id);

    void deleteById(Long id);

}