package gharach_aw.frame_analysis.data_extractor;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import gharach_aw.frame_analysis.persistence.entity.PacketCapture;
import gharach_aw.frame_analysis.service.PacketCaptureService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class PacketCaptureProcessingTest {

    @Autowired
    private PacketCaptureProcessing packetCaptureProcessing;

    @Autowired 
    private PacketCaptureService packetCaptureService;

    @PersistenceContext
    private EntityManager entityManager;

    private PacketCapture packetCapture; 

    @Test
    public void testExtractPropertiesPacketCapture(){

        System.out.println("------------------------------");
        System.out.println("Test of PacketCaptureProcessing method");
        System.out.println("------------------------------");

        File file = new File("src\\test\\java\\gharach_aw\\frame_analysis\\test2.json");

        packetCapture = packetCaptureProcessing.extractPropertiesPacketCapture(file);  
        
        System.out.println(packetCapture);

    }
}
