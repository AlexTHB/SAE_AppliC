package gharach_aw.frame_analysis.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gharach_aw.frame_analysis.api.persistence.data_access.PacketCaptureDAO;
import gharach_aw.frame_analysis.api.persistence.entity.PacketCapture;

@Service
@Transactional
public class PacketCaptureServiceImpl implements PacketCaptureService {

    private final PacketCaptureDAO packetCaptureDAO;

    @Autowired
    public PacketCaptureServiceImpl(PacketCaptureDAO packetCaptureDAO) {
        this.packetCaptureDAO = packetCaptureDAO;
    }

    // Create

    @Override
    public void savePacketCapture(PacketCapture packetCapture) {
        packetCaptureDAO.save(packetCapture);
    }

    // Read
    @Override
    public PacketCapture findPacketCaptureById(int id) {
        return packetCaptureDAO.findById(id);
    }

    @Override
    public PacketCapture findPacketCaptureByName(String fileName){
        return packetCaptureDAO.findByName(fileName);
    }

    // Update
    @Override
    public void updatePacketCapture(PacketCapture packetCapture) {
        packetCaptureDAO.update(packetCapture);
    }

    // Delete
    @Override
    public void deletePacketCaptureById(int id) {
        packetCaptureDAO.deleteById(id);
    }
    

}
