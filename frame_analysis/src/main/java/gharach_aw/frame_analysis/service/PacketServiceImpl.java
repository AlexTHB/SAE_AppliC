package gharach_aw.frame_analysis.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gharach_aw.frame_analysis.persistence.data_access.PacketDAO;
import gharach_aw.frame_analysis.persistence.entity.Packet;
import gharach_aw.frame_analysis.persistence.entity.PacketDTO;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PacketServiceImpl implements PacketService{

    private final PacketDAO packetDAO;

    @Autowired
    public PacketServiceImpl(PacketDAO packetDAO){
        this.packetDAO = packetDAO;
    }

    @Override
    public List<Packet> findAllPacketsByCaptureId(Long captureId) {
        return packetDAO.findAllPacketsByCaptureId(captureId);
    }

    @Override
    public List<Packet> findAllPacketsByCaptureName(String captureName) {
        return packetDAO.findAllPacketsByCaptureName(captureName);
    }

    @Override
    public List<PacketDTO> convertToPacketDTOList(List<Packet> packets) {
        return packets.stream()
                .map(Packet::convertToDTO)
                .collect(Collectors.toList());
    }
}
