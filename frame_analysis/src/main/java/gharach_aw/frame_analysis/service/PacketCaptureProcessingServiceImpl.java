package gharach_aw.frame_analysis.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import gharach_aw.frame_analysis.data_extractor.PacketProcessing;
import gharach_aw.frame_analysis.persistence.entity.Packet;
import gharach_aw.frame_analysis.persistence.entity.PacketCapture;

/**
 * It is responsible for processing packet capture files, extracting properties, and creating a {@link PacketCapture} object.
 *
 * This class is annotated with {@link Component} to indicate that it is a Spring service component.
 */
@Service
public class PacketCaptureProcessingServiceImpl implements PacketCaptureProcessingService{

    private PacketCapture packetCapture;
    private String fileName;
    private String fileDate;
    private List<Packet> packets;

    private final PacketProcessing packetProcessing;

    /**
     * Constructs a new {@code PacketCaptureProcessing} instance.
     *
     * @param packetProcessing The {@link PacketProcessing} implementation used for extracting packets.
     */
    @Autowired
    public PacketCaptureProcessingServiceImpl(PacketProcessing packetProcessing) {
        this.packetProcessing = packetProcessing;
    }

    /**
     * Extracts properties from a given packet capture file.
     *
     * @param file The {@code File} object representing the packet capture file to be processed.
     * @return A {@link PacketCapture} object containing extracted properties from the packet capture file.
     */
    @Override
    public PacketCapture extractPropertiesPacketCapture(MultipartFile file){
        packetCapture = new PacketCapture();
        fileName = extractFileName(file);
        packetCapture.setFileName(fileName);    
        try {
            fileDate = extractFileDate(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        packetCapture.setFileDate(fileDate);
        extractPacketsList(file);
        packetCapture.setPackets(packets);
        return packetCapture;
    }
    
    /**
     * Extracts the file name from the provided {@code File}.
     *
     * @param file The {@code File} object representing the packet capture file.
     * @return The file name.
     */
    public String extractFileName (MultipartFile file){
        String fileName = file.getOriginalFilename();        
        return fileName;
    }
    
     /**
     * Extracts the creation date of the provided {@code MultipartFile}.
     *
     * @param multipartFile The {@code MultipartFile} representing the packet capture file.
     * @return The formatted creation date of the file.
     * @throws IOException If an I/O error occurs during file attribute extraction.
     */
    public String extractFileDate(MultipartFile multipartFile) throws IOException {
        // Create a temporary file
        Path tempFile = Files.createTempFile("temp", null);

        try {
            // Transfer the content from MultipartFile to the temporary file
            multipartFile.transferTo(tempFile);

            // Read file attributes and extract creation time
            BasicFileAttributes attributes = Files.readAttributes(tempFile, BasicFileAttributes.class);
            Date fileDateBrut = new Date(attributes.creationTime().toMillis());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            fileDate = dateFormat.format(fileDateBrut);
            return fileDate;
        } finally {
            // Delete the temporary file after usage
            Files.deleteIfExists(tempFile);
        }
    }


    /**
     * Extracts the list of packets from the provided {@code File}.
     *
     * @param file The {@code File} object representing the packet capture file.
     * @return The list of extracted packets.
     */
    public List<Packet> extractPacketsList(MultipartFile file){
       // Extract packets using the PacketProcessing implementation
        packets = packetProcessing.packetsExtract(file);
        // Establish a one-to-many relationship by setting the PacketCapture for each extracted packet
        for (Packet packet : packets) {
            packet.setPacketCapture(packetCapture);
            // Setting the PacketCapture establishes a relationship where
            // each packet belongs to the same PacketCapture instance (one-to-many).
        }
        return packets;
    }
}
