package gharach_aw.frame_analysis.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gharach_aw.frame_analysis.persistence.entity.Packet;
import jakarta.transaction.Transactional;

public interface PacketRepository extends JpaRepository<Packet, Long>{
    
    // Custom method to find packets by packetCaptureId
    List<Packet> findAllByPacketCaptureId(Long packetCaptureId);

    @Transactional
    void deletePacketByPacketCaptureId(Long packetCaptureId);
} 