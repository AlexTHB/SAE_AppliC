package gharach_aw.frame_analysis.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import gharach_aw.frame_analysis.persistence.entity.PacketCapture;

/**
 * It is responsible for processing packet capture files, extracting properties, and creating a {@link PacketCapture} object.
 *
 * This class is annotated with {@link Component} to indicate that it is a Spring service component.
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
        try {
            packetCapture = new PacketCapture();
            packetCaptureName = extractPacketCaptureName(file);
            packetCapture.setPacketCaptureName(packetCaptureName); 
            uploadDate = extractUploadDate(file);
            packetCapture.setUploadDate(uploadDate);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packetCapture;
    }
    
    /**
     * Extracts the file name from the provided {@code File}.
     *
     * @param file The {@code File} object representing the packet capture file.
     * @return The file name.
     */
    public String extractPacketCaptureName (MultipartFile file){
        packetCaptureName = file.getOriginalFilename();        
        return packetCaptureName;
    }
    
     /**
     * Extracts the creation date of the provided {@code MultipartFile}.
     *
     * @param multipartFile The {@code MultipartFile} representing the packet capture file.
     * @return The formatted creation date of the file.
     * @throws IOException If an I/O error occurs during file attribute extraction.
     */
    public String extractUploadDate(MultipartFile multipartFile) throws IOException {
        // Create a temporary file
        Path tempFile = Files.createTempFile("temp", null);

        try {
            // Transfer the content from MultipartFile to the temporary file
            multipartFile.transferTo(tempFile);

            // Read file attributes and extract creation time
            BasicFileAttributes attributes = Files.readAttributes(tempFile, BasicFileAttributes.class);
            Date fileDateBrut = new Date(attributes.creationTime().toMillis());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            uploadDate = dateFormat.format(fileDateBrut);
            return uploadDate;
        } finally {
            // Delete the temporary file after usage
            Files.deleteIfExists(tempFile);
        }
    }
}