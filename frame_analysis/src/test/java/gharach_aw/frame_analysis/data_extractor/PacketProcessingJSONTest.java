package gharach_aw.frame_analysis.data_extractor;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import gharach_aw.frame_analysis.persistence.entity.Packet;

import org.springframework.mock.web.MockMultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PacketProcessingJSONTest {

    @Autowired
    private PacketProcessing packetProcessing;

    private List<Packet> packets;

    @Test
    public void testPacketExtract() throws Exception {
        System.out.println("------------------------------");
        System.out.println("Test of PacketProcessing method");
        System.out.println("------------------------------");

        // Path to your test JSON file
        Path path = Paths.get("src/test/java/gharach_aw/frame_analysis/test.json");
        
        // Create a MockMultipartFile
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test.json",
                "test.json",
                "application/json",
                Files.readAllBytes(path)
        );

        // Call the method with the MockMultipartFile
        packets = packetProcessing.packetsExtract(mockMultipartFile);

        // Perform your assertions or print the results
        packets.forEach(System.out::println);
    }
}
