package gharach_aw.frame_analysis.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gharach_aw.frame_analysis.persistence.data_access.PacketCaptureDAO;
import gharach_aw.frame_analysis.persistence.data_access.PacketDAO;
import gharach_aw.frame_analysis.persistence.entity.Packet;
import gharach_aw.frame_analysis.persistence.entity.PacketCapture;
import gharach_aw.frame_analysis.persistence.entity.PacketCaptureDTO;

/**
 * The {@code PacketCaptureService} class provides business logic for handling {@link PacketCapture} entities and 
 * {@link Packet} entities,
 * utilizing the data access methods defined in the associated {@link PacketCaptureDAO} and {@link PacketDAO}.
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
    public void save(PacketCapture packetCapture) {
        packetCaptureDAO.save(packetCapture);
    }

   
    @Override
    public List<PacketCapture> findAllPacketCaptures() {
        return packetCaptureDAO.findAllPacketCaptures();
    }

    @Override
    public List<PacketCaptureDTO> convertToPacketCaptureDTOList(List<PacketCapture> packetCaptures) {
        return packetCaptures.stream()
        .map(PacketCapture::convertToDTO)  // Use the existing convertToDTO method
        .collect(Collectors.toList());
    }

    @Override
    public PacketCapture findById(Long id) {
        return packetCaptureDAO.findById(id);
    }

    @Override
    public PacketCapture findByFileName(String fileName) {
        return packetCaptureDAO.findByFileName(fileName);
    } 

    @Override
    public PacketCapture updatePacketCapture(PacketCapture updatedPacketCapture) {
        return packetCaptureDAO.updatePacketCapture(updatedPacketCapture);
    }

    @Override
    public void deleteById(Long id) {
        packetCaptureDAO.deleteById(id);
    }
}