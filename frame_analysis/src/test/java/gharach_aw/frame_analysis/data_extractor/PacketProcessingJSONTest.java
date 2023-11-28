package gharach_aw.frame_analysis.data_extractor;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import gharach_aw.frame_analysis.persistence.entity.Packet;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PacketProcessingJSONTest {

    @Autowired
    private PacketProcessing packetProcessing;


    private List<Packet> packets;

    @Test
    public void testPacketExtract(){

        System.out.println("------------------------------");
        System.out.println("Test of PacketProcessing method");
        System.out.println("------------------------------");

        File file = new File("src\\test\\java\\gharach_aw\\frame_analysis\\test.json");
        packets = packetProcessing.packetsExtract(file);
        packets.forEach(System.out::println);
    }
}
