package gharach_aw.frame_analysis.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import gharach_aw.frame_analysis.persistence.entity.PacketCapture;

/**
 * It is responsible for processing packet capture files, extracting properties, and creating a {@link PacketCapture} object.
 *
 * This class is annotated with {@link Services} to indicate that it is a Spring service component.
 */
@Service
public class PacketCaptureProcessingServiceImpl implements PacketCaptureProcessingService{

    private PacketCapture packetCapture;
    private String packetCaptureName;
    private String uploadDate;
   
    /**
     * Extracts properties from a given packet capture file.
     *
     * @param file The {@code File} object representing the packet capture file to be processed.
     * @return A {@link PacketCapture} object containing extracted properties from the packet capture file.
     */
    @Override
    public PacketCapture extractPropertiesPacketCapture(MultipartFile file){
        packetCapture = new PacketCapture();
        packetCaptureName = extractPacketCaptureName(file);
        packetCapture.setPacketCaptureName(packetCaptureName); 
        uploadDate = extractUploadDate(file);
        packetCapture.setUploadDate(uploadDate);
        return packetCapture;
    }
    
    /**
     * Extracts the packet capture name from the provided {@code File}.
     *
     * @param file The {@code File} object representing the packet capture file.
     * @return The packet capture name.
     */
    public String extractPacketCaptureName (MultipartFile file){
        packetCaptureName = file.getOriginalFilename();        
        return packetCaptureName;
    }
    
    /**
     * Extracts the upload date from the provided MultipartFile by getting the current
     * date and time and formatting it in the "yyyy-MM-dd HH:mm:ss" format.
     *
     * @param multipartFile The MultipartFile from which to extract the upload date.
     * @return A formatted string representing the current date and time in the "yyyy-MM-dd HH:mm:ss" format.
     * @throws IOException If an I/O error occurs.
     */
    public String extractUploadDate(MultipartFile multipartFile){
        // Get the current date and time
        Date realTimeDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        uploadDate = dateFormat.format(realTimeDate);
        return uploadDate;
    }
}