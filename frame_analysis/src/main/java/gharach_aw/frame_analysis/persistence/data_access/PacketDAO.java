package gharach_aw.frame_analysis.api.persistence.data_access;

import java.util.List;

import org.springframework.stereotype.Repository;

import gharach_aw.frame_analysis.api.persistence.entity.Packet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

/**
 * The {@code PacketDAO} class serves as a Data Access Object (DAO) for performing CRUD operations
 * on {@link Packet} entities in the database.
 * 
 * This class is annotated with {@link Repository} to indicate that it is a Spring-managed repository bean
 * responsible for data access and is annotated with {@link Transactional} to ensure that methods are
 * executed within a transactional context.
 */
@Repository
@Transactional
public class PacketDAO {

     /**
     * JPA EntityManager for interacting with the database.
     */
    @PersistenceContext    
    private EntityManager entityManager;

    /**
     * Retrieves a list of Packet entities based on the specified PacketCaptureId.
     *
     * @param captureId The identifier of the PacketCapture for which packets are to be retrieved.
     * @return A list of Packet entities associated with the specified PacketCaptureId.
     * @throws IllegalArgumentException if the captureId is null or empty.
     */
    public List<Packet> findAllPacketsByCaptureId(Long captureId) {
        String jpql = "SELECT p FROM Packet p WHERE p.packetCapture.id = :captureId";
        TypedQuery<Packet> query = entityManager.createQuery(jpql, Packet.class);
        query.setParameter("captureId", captureId);
        return query.getResultList();
    }

    /**
     * Retrieves a list of Packet entities based on the specified PacketCaptureName.
     *
     * @param captureName The name of the PacketCapture for which packets are to be retrieved.
     * @return A list of Packet entities associated with the specified PacketCaptureName.
     * @throws IllegalArgumentException if the captureName is null or empty.
     */
    public List<Packet> findAllPacketsByCaptureName(String captureName) {
        String jpql = "SELECT p FROM Packet p WHERE p.packetCapture.fileName = :captureName";
        TypedQuery<Packet> query = entityManager.createQuery(jpql, Packet.class);
        query.setParameter("captureName", captureName);
        return query.getResultList();
    }
}