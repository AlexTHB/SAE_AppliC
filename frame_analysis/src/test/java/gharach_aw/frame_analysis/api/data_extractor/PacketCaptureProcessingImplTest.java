package gharach_aw.frame_analysis.api.data_extractor;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import gharach_aw.frame_analysis.api.persistence.entity.PacketCapture;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PacketCaptureProcessingImplTest {

    @Autowired
    private PacketCaptureProcessing packetCaptureProcessing;

    private PacketCapture packetCapture; 

    @Test
    public void testExtractPropertiesPacketCapture(){

        System.out.println("------------------------------");
        System.out.println("Test of PacketCaptureProcessing method");
        System.out.println("------------------------------");

        File file = new File("src\\test\\java\\gharach_aw\\frame_analysis\\test.json");

        packetCapture = packetCaptureProcessing.extractPropertiesPacketCapture(file);  
        
        System.out.println(packetCapture.getPackets());
        
    }
}
