package gharach_aw.frame_analysis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gharach_aw.frame_analysis.exception.PacketCaptureNotFoundException;
import gharach_aw.frame_analysis.persistence.entity.Packet;
import gharach_aw.frame_analysis.persistence.entity.PacketCapture;
import gharach_aw.frame_analysis.persistence.repository.PacketCaptureRepository;
import gharach_aw.frame_analysis.persistence.repository.PacketRepository;

/**
 * The {@code PacketCaptureService} class provides business logic for handling {@link PacketCapture} entities and 
 * {@link Packet} entities,
 * utilizing the data access methods defined in the associated {@link PacketCaptureRepository} and {@link PacketRepository}.
 * 
 * This class is annotated with {@link Service} to indicate that it is a Spring service component.
 */
@Service
public class PacketCaptureServiceImpl implements PacketCaptureService {

    private final PacketCaptureRepository packetCaptureRepository;
    
    private final PacketRepository packetRepository;

    @Autowired
    public PacketCaptureServiceImpl(PacketCaptureRepository packetCaptureRepository, PacketRepository packetRepository) {
        this.packetCaptureRepository = packetCaptureRepository;
        this.packetRepository = packetRepository;
    }

    @Override
    public void save(PacketCapture packetCapture, List<Packet> packets) {
        packetCapture.setId(null);
        packetCaptureRepository.save(packetCapture);

        packets.forEach(packet -> {
            packet.setId(null);
            packet.setPacketCapture(packetCapture);
        });
        
        packetRepository.saveAll(packets);
    }

    @Override
    public List<PacketCapture> findAllPacketCaptures() {
        return packetCaptureRepository.findAll();
    }

    @Override
    public Optional<PacketCapture> findById(Long id) {
        return packetCaptureRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
         PacketCapture packetCapture = packetCaptureRepository.findById(id)
            .orElseThrow(() -> new PacketCaptureNotFoundException("PacketCapture not found with id: " + id));
        packetRepository.deletePacketByPacketCaptureId(id);
        // Delete PacketCapture
        packetCaptureRepository.deleteById(id);
    }
}