package gharach_aw.frame_analysis.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import gharach_aw.frame_analysis.persistence.entity.Packet;
import gharach_aw.frame_analysis.persistence.entity.PacketCapture;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class PacketServiceImplTest {
    
    @Autowired
    private PacketCaptureProcessingService packetCaptureProcessingService;

    @Autowired
    private PacketCaptureService packetCaptureService;

    @Autowired
    private PacketService packetService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testPacketCaptureServiceMethods() throws Exception {
        
        System.out.println("------------------------------");
        System.out.println("Test of PacketService methods");
        System.out.println("------------------------------");

        // Path to your test JSON file
        Path path = Paths.get("src/test/java/gharach_aw/frame_analysis/test.json");
        
        // Create a MockMultipartFile
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "a.json",
                "test.json",
                "application/json",
                Files.readAllBytes(path)
        );

        // Call the method with the MockMultipartFile
        PacketCapture packetCapture = packetCaptureProcessingService.extractPropertiesPacketCapture(mockMultipartFile);

        packetCaptureService.save(packetCapture);

        List<Packet> packets = packetService.findAllPacketsByCaptureId(packetCapture.getId());
        assertNotNull(packets);

        List<Packet> packetsByName = packetService.findAllPacketsByCaptureName(packetCapture.getFileName());
        assertNotNull(packetsByName);

    }
}
