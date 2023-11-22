package gharach_aw.frame_analysis.api.persistence.data_access;

import org.springframework.stereotype.Repository;

import gharach_aw.frame_analysis.api.persistence.entity.PacketCapture;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


@Repository
@Transactional
public class PacketCaptureDAO {

    @PersistenceContext    
    private EntityManager entityManager;

    // Create
    public void save(PacketCapture packetCapture) {
        entityManager.persist(packetCapture);
    }

    // Read
    public PacketCapture findById(int id) {
        return entityManager.find(PacketCapture.class, id);
    }

    public PacketCapture findByName(String fileName) {
        return entityManager.find(PacketCapture.class, fileName);
    }

    // Update
    public void update(PacketCapture packetCapture) {
        entityManager.merge(packetCapture);
    }

    // Delete
    public void deleteById(int id) {
        PacketCapture packetCapture = findById(id);
        if (packetCapture != null) {
            entityManager.remove(packetCapture);
        }
    }
    
}
