package gharach_aw.frame_analysis.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gharach_aw.frame_analysis.persistence.entity.Packet;
import gharach_aw.frame_analysis.persistence.repository.PacketRepository;

@Service
public class PacketServiceImpl implements PacketService{

    private final PacketRepository packetRepository;

    @Autowired
    public PacketServiceImpl(PacketRepository packetRepository){
        this.packetRepository = packetRepository;
    }

    @Override
    public List<Packet> findAllPacketsByCaptureId(Long captureId) {
        return packetRepository.findAllByPacketCaptureId(captureId);
    }
}