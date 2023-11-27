package gharach_aw.frame_analysis.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gharach_aw.frame_analysis.api.persistence.data_access.PacketDAO;
import gharach_aw.frame_analysis.api.persistence.entity.Packet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * The {@code PacketServiceImpl} class provides business logic for handling {@link Packet} entities,
 * utilizing the data access methods defined in the associated {@link PacketDAO}.
 *
 * This class is annotated with {@link Service} to indicate that it is a Spring service component.
 */
@Service
public class PacketServiceImpl implements PacketService {

    @PersistenceContext
    private EntityManager entityManager;

    private final PacketDAO packetDAO;

    @Autowired
    public PacketServiceImpl(PacketDAO packetDAO) {
        this.packetDAO = packetDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Packet> findAllPacketsByCaptureId(Long captureId) {
        return packetDAO.findAllPacketsByCaptureId(captureId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Packet> findAllPacketsByCaptureName(String captureName) {
        return packetDAO.findAllPacketsByCaptureName(captureName);
    }
}
