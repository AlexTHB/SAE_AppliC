package gharach_aw.frame_analysis.api.persistence.data_access;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import gharach_aw.frame_analysis.api.exception.PacketCaptureNotFoundException;
import gharach_aw.frame_analysis.api.persistence.entity.PacketCapture;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
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

    public PacketCapture updateEntity(PacketCapture updatedEntity) {
        return entityManager.merge(updatedEntity);
    }
  
    /**
     * Updates the file name of a PacketCapture entity in the database.
     *
     * This method executes a JPQL (Java Persistence Query Language) update query to change
     * the file name of a PacketCapture entity from the specified ancientName to the specified newName.
     *
     * @param ancientName The ancient file name to be updated.
     * @param newName     The new file name to be set.
     *
     * @throws IllegalArgumentException If ancientName or newName is null.
     * @throws EntityNotFoundException  If no PacketCapture entity with the specified ancientName is found.
     */
    public void updatePacketCaptureName(File ancientName, String newName) {
        // Update the file name with JPQL
        String updateQuery = "UPDATE PacketCapture pc SET pc.fileName = :newName WHERE pc.fileName = :ancientName";

        // Execute the update query
        int rowsAffected = entityManager.createQuery(updateQuery)
            .setParameter("newName", newName)
            .setParameter("ancientName", ancientName.getName())
            .executeUpdate();

        // Check if any rows were affected
        if (rowsAffected == 0) {
            throw new PacketCaptureNotFoundException("No PacketCapture entity found with the specified ancientName: " + ancientName.getName());
        }
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