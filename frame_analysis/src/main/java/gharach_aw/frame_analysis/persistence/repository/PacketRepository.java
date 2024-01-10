package gharach_aw.frame_analysis.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gharach_aw.frame_analysis.persistence.entity.Packet;
import jakarta.transaction.Transactional;

/**
 * Repository interface for managing Packets in the database.
 * Extends the JpaRepository interface to inherit basic CRUD operations and additional
 * query methods provided by Spring Data JPA.
 *
 * This interface is designed to interact with the underlying data store to perform
 * operations related to Packets, such as saving, updating, and retrieving them.
 *
 * Additionally, it defines custom query methods for retrieving and deleting Packets
 * based on the associated PacketCaptureId.
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see Packet
 * @see org.springframework.stereotype.Repository
 */
public interface PacketRepository extends JpaRepository<Packet, Long>{
    
     /**
     * Retrieves a list of Packets associated with the given PacketCaptureId.
     *
     * @param packetCaptureId The ID of the PacketCapture to retrieve Packets for.
     * @return List of Packets associated with the specified PacketCaptureId.
     */
    List<Packet> findAllByPacketCaptureId(Long packetCaptureId);

    /**
     * Deletes Packets associated with the given PacketCaptureId.
     *
     * @param packetCaptureId The ID of the PacketCapture whose Packets should be deleted.
     */
    @Transactional
    void deletePacketByPacketCaptureId(Long packetCaptureId);
} 