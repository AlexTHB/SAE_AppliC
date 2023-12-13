package gharach_aw.frame_analysis.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import gharach_aw.frame_analysis.persistence.entity.PacketCapture;
import jakarta.transaction.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class PacketCaptureProcessingServiceTest {

    @Autowired
    private PacketCaptureProcessingService packetCaptureProcessingService;

    private PacketCapture packetCapture; 

    @Test
    public void testExtractPropertiesPacketCapture() throws Exception{

        System.out.println("------------------------------");
        System.out.println("Test of PacketCaptureProcessingService method");
        System.out.println("------------------------------");

       // Assuming 'path' is the correct path to the JSON file
       Path path = Paths.get("src\\test\\java\\gharach_aw\\frame_analysis\\test.json");

       // Create HttpHeaders with Content-Disposition header
       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_JSON);
       headers.add(HttpHeaders.CONTENT_DISPOSITION, "form-data; filename=test.json");

        // Create a MockMultipartFile
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
            "file",
            "test.json",
            "application/json",
            Files.readAllBytes(path)
        );
        // Call the method with the MockMultipartFile
        packetCapture = packetCaptureProcessingService.extractPropertiesPacketCapture(mockMultipartFile);  

        System.out.println(packetCapture);

    }
}
