package gharach_aw.frame_analysis.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gharach_aw.frame_analysis.api.persistence.data_access.PacketCaptureDAO;
import gharach_aw.frame_analysis.api.persistence.entity.PacketCapture;

/**
 * The {@code PacketCaptureService} class provides business logic for handling {@link PacketCapture} entities,
 * utilizing the data access methods defined in the associated {@link PacketCaptureDAO}.
 * 
 * This class is annotated with {@link Service} to indicate that it is a Spring service component.
 */
@Service
@Transactional
public class PacketCaptureServiceImpl implements PacketCaptureService {

    private final PacketCaptureDAO packetCaptureDAO;

    @Autowired
    public PacketCaptureServiceImpl(PacketCaptureDAO packetCaptureDAO) {
        this.packetCaptureDAO = packetCaptureDAO;
    }

    @Override
    @Transactional
    public void save(PacketCapture packetCapture) {
        packetCaptureDAO.save(packetCapture);
    }

   
    @Override
    @Transactional(readOnly = true)
    public List<PacketCapture> findAllPacketCaptures() {
        return packetCaptureDAO.findAllPacketCaptures();
    }

    @Override
    @Transactional(readOnly = true)
    public PacketCapture findById(Long id) {
        return packetCaptureDAO.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public PacketCapture findByFileName(String fileName) {
        return packetCaptureDAO.findByFileName(fileName);
    }

    @Override
    @Transactional
    public PacketCapture updateEntity(PacketCapture updatedEntity) {
        return packetCaptureDAO.updateEntity(updatedEntity);
    }

    @Override
    @Transactional
    public String updatePacketCaptureName(String ancientName, String newName) {
        return packetCaptureDAO.updatePacketCaptureName(ancientName, newName);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        packetCaptureDAO.deleteById(id);
    }
}

