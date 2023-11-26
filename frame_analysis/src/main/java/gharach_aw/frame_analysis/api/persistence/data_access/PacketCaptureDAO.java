package gharach_aw.frame_analysis.api.persistence.data_access;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import gharach_aw.frame_analysis.api.persistence.entity.PacketCapture;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;


/**
 * The {@code PacketCaptureDAO} class serves as a Data Access Object (DAO) for performing CRUD operations
 * on {@link PacketCapture} entities in the database.
 */
@Repository
@Transactional
public class PacketCaptureDAO {

    @Autowired
    private EntityManager entityManager;

    /**
     * Persists a new {@link PacketCapture} entity in the database.
     *
     * @param packetCapture The {@link PacketCapture} instance to be saved.
     */
    public void save(PacketCapture packetCapture) {
        entityManager.persist(packetCapture);
    }

    /**
     * Retrieves a {@link PacketCapture} entity from the database based on its unique identifier.
     *
     * @param id The unique identifier of the {@link PacketCapture} entity.
     * @return The retrieved {@link PacketCapture} entity, or null if not found.
     */
    public PacketCapture findById(int id) {
        return entityManager.find(PacketCapture.class, id);
    }

    /**
     * Retrieves a {@link PacketCapture} entity from the database based on its name.
     *
     * @param fileName The name of the {@link PacketCapture} entity.
     * @return The retrieved {@link PacketCapture} entity, or null if not found.
     */
    public PacketCapture findByFileName(String fileName) {
        String jpql = "SELECT pc FROM PacketCapture pc WHERE pc.fileName = :fileName";
        TypedQuery<PacketCapture> query = entityManager.createQuery(jpql, PacketCapture.class);
        query.setParameter("fileName", fileName);

        List<PacketCapture> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

  
    /**
     * Updates the name of a {@link PacketCapture} entity in the database.
     *
     * @param packetCapture The {@link PacketCapture} instance to be updated.
     * @param newName       The new name to be set for the {@link PacketCapture}.
     */
    public void updatePacketCaptureName(PacketCapture packetCapture, String newName) {
        packetCapture.setFileName(newName);
        entityManager.merge(packetCapture);
    }

    /**
     * Deletes a {@link PacketCapture} entity from the database based on its unique identifier.
     *
     * @param id The unique identifier of the {@link PacketCapture} entity to be deleted.
     */
    public void deleteById(int id) {
        PacketCapture packetCapture = findById(id);
        if (packetCapture != null) {
            entityManager.remove(packetCapture);
        }
    }
}