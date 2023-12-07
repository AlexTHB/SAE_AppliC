package gharach_aw.frame_analysis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import gharach_aw.frame_analysis.data_extractor.PacketCaptureProcessing;
import gharach_aw.frame_analysis.exception.PacketCaptureNotFoundException;
import gharach_aw.frame_analysis.persistence.entity.Packet;
import gharach_aw.frame_analysis.persistence.entity.PacketCapture;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class PacketCaptureServiceImplTest {

    @Autowired
    private PacketCaptureProcessing packetCaptureProcessing;

    @Autowired
    private PacketCaptureService packetCaptureService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testPacketCaptureServiceMethods() {
        
        System.out.println("------------------------------");
        System.out.println("Test of PacketCaptureService methods");
        System.out.println("------------------------------");

        File file = new File("src\\test\\java\\gharach_aw\\frame_analysis\\test2.json");

        PacketCapture packetCapture = packetCaptureProcessing.extractPropertiesPacketCapture(file);
        packetCaptureService.save(packetCapture);

        assertNotNull(packetCapture.getId());

        String updatedName = "NewCaptureName.json";
        packetCaptureService.updatePacketCaptureName(packetCapture.getFileName(), updatedName);

        PacketCapture retrievedCapture = packetCaptureService.findById(packetCapture.getId());

        // Expcitly refresh the entity from the database
        entityManager.refresh(retrievedCapture);

        assertEquals(updatedName, retrievedCapture.getFileName());

        PacketCapture retrievedByName = packetCaptureService.findByFileName(updatedName);
        assertEquals(updatedName, retrievedByName.getFileName());

        assertTrue(packetCaptureService.findAllPacketCaptures().contains(packetCapture));

        List<Packet> packets = packetCaptureService.findAllPacketsByCaptureId(packetCapture.getId());
        assertNotNull(packets);

        List<Packet> packetsByName = packetCaptureService.findAllPacketsByCaptureName(updatedName);
        assertNotNull(packetsByName);

        packetCaptureService.deleteById(packetCapture.getId());

       // Attempt to find the nonexistent PacketCapture and assert that PacketCaptureNotFoundException is thrown
        PacketCaptureNotFoundException exception = assertThrows(PacketCaptureNotFoundException.class, () -> {
            packetCaptureService.findById(packetCapture.getId());
        });

        assertTrue(packetCaptureService.findAllPacketsByCaptureId(packetCapture.getId()).isEmpty());

    }
}