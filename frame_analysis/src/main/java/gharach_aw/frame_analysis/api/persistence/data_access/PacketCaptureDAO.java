package gharach_aw.frame_analysis.api.persistence.data_access;

import java.util.List;

import org.springframework.stereotype.Repository;

import gharach_aw.frame_analysis.api.exception.PacketCaptureNotFoundException;
import gharach_aw.frame_analysis.api.persistence.entity.PacketCapture;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;


/**
 * The {@code PacketCaptureDAO} class serves as a Data Access Object (DAO) for performing CRUD operations
 * on {@link PacketCapture} entities in the database.
 * 
 * This class is annotated with {@link Repository} to indicate that it is a Spring-managed repository bean
 * responsible for data access and is annotated with {@link Transactional} to ensure that methods are
 * executed within a transactional context.
 */
@Repository
@Transactional
public class PacketCaptureDAO {

    /**
     * JPA EntityManager for interacting with the database.
     */
    @PersistenceContext    
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
     * Retrieves a list of all {@link PacketCapture} entities from the database.
     *
     * @return A list of {@link PacketCapture} entities.
     */
    public List<PacketCapture> findAllPacketCaptures() {
        // JPQL query to select all PacketCapture entities
        String jpql = "SELECT pc FROM PacketCapture pc";

        // Create a TypedQuery for executing the JPQL query
        TypedQuery<PacketCapture> query = entityManager.createQuery(jpql, PacketCapture.class);

        // Execute the query and return the list of entities
        return query.getResultList();
    }


    /**
     * Retrieves a {@link PacketCapture} entity from the database based on its unique identifier.
     *
     * @param id The unique identifier of the {@link PacketCapture} entity.
     * @return The retrieved {@link PacketCapture} entity, or null if not found.
     * @throws PacketCaptureNotFoundException If the entity with the specified ID is not found.
     */
    public PacketCapture findById(Long id) {
        PacketCapture packetCapture = entityManager.find(PacketCapture.class, id);
        if (packetCapture == null) {
            throw new PacketCaptureNotFoundException("PacketCapture entity with ID " + id + " not found");
        }
        return packetCapture;
    }

    /**
     * Retrieves a {@link PacketCapture} entity from the database based on its name.
     *
     * @param fileName The name of the {@link PacketCapture} entity.
     * @return The retrieved {@link PacketCapture} entity, or null if not found.
     * @throws PacketCaptureNotFoundException If the entity with the specified name is not found.
     */
    public PacketCapture findByFileName(String fileName) {
        String jpql = "SELECT pc FROM PacketCapture pc WHERE pc.fileName = :fileName";
        TypedQuery<PacketCapture> query = entityManager.createQuery(jpql, PacketCapture.class);
        query.setParameter("fileName", fileName);

        List<PacketCapture> results = query.getResultList();
        if (results.isEmpty()) {
            throw new PacketCaptureNotFoundException("PacketCapture entity with file name '" + fileName + "' not found");
        }
        return results.get(0);
    }


    /**
     * Updates a {@link PacketCapture} entity in the database with the provided changes.
     * 
     * This method uses the {@link EntityManager#merge(Object)} operation to update the state
     * of the given {@link PacketCapture} entity in the persistence context. If the entity
     * does not exist in the database, a new persistent instance is created with the state of
     * the provided entity, and the new instance is returned.
     * 
     * @param updatedEntity The {@link PacketCapture} entity with updated data.
     * @return The updated or merged {@link PacketCapture} entity.
     * 
     * @throws IllegalArgumentException If the updatedEntity is not an entity or is not
     *                                  associated with the persistence context.
     * @throws TransactionRequiredException If there is no active transaction when the
     *                                      operation is invoked.
     * @throws EntityNotFoundException If the entity does not exist in the database and
     *                                 cannot be created as a new entity.
     * @throws PersistenceException If any other error occurs during the merge operation.
     */
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
     * @throws PacketCaptureNotFoundException  If no PacketCapture entity with the specified ancientName is found.
     */
    public String updatePacketCaptureName(String ancientName, String newName) {
        // Update the file name with JPQL
        String updateQuery = "UPDATE PacketCapture pc SET pc.fileName = :newName WHERE pc.fileName = :ancientName";

        // Execute the update query
        int rowsAffected = entityManager.createQuery(updateQuery)
            .setParameter("newName", newName)
            .setParameter("ancientName", ancientName)
            .executeUpdate();

        // Check if any rows were affected
        if (rowsAffected == 0) {
            throw new PacketCaptureNotFoundException("No PacketCapture entity found with the specified ancientName: " + ancientName);
        }

        // Fetch the updated entity's name
        String updatedNameQuery = "SELECT pc.fileName FROM PacketCapture pc WHERE pc.fileName = :newName";
        TypedQuery<String> updatedNameTypedQuery = entityManager.createQuery(updatedNameQuery, String.class);
        updatedNameTypedQuery.setParameter("newName", newName);
        List<String> updatedNames = updatedNameTypedQuery.getResultList();

        // Check if any updated names were found
        if (updatedNames.isEmpty()) {
            throw new PacketCaptureNotFoundException("Unable to fetch the updated name for ancientName: " + ancientName);
        }

        // Return the first updated name (assuming unique names)
        return updatedNames.get(0);
    }

    /**
     * Deletes a {@link PacketCapture} entity from the database based on its unique identifier.
     *
     * @param id The unique identifier of the {@link PacketCapture} entity to be deleted.
     * 
     * @throws PacketCaptureNotFoundException
     */
    public void deleteById(Long id) {
        PacketCapture packetCapture = findById(id);
        if (packetCapture != null) {
            entityManager.remove(packetCapture);
        } else {
            throw new PacketCaptureNotFoundException("PacketCapture entity with ID " + id + " not found");
        }
    }
}