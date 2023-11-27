package gharach_aw.frame_analysis.api.data_extractor;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gharach_aw.frame_analysis.api.persistence.entity.Packet;
import gharach_aw.frame_analysis.api.persistence.entity.PacketCapture;

/**
 * It is responsible for processing packet capture files, extracting properties, and creating a {@link PacketCapture} object.
 *
 * This class is annotated with {@link Component} to be automatically detected and registered as a Spring bean.
 */
@Component
public class PacketCaptureProcessing{

    private PacketCapture packetCapture;
    private String fileName;
    private String fileDate;
    private List<Packet> packets;

    private final PacketProcessing packetProcessing;

    /**
     * Constructs a new {@code PacketCaptureProcessingImpl} instance.
     *
     * @param packetProcessing The {@link PacketProcessing} implementation used for extracting packets.
     */
    @Autowired
    public PacketCaptureProcessing(PacketProcessing packetProcessing) {
        this.packetProcessing = packetProcessing;
    }

    /**
     * Extracts properties from a given packet capture file.
     *
     * @param file The {@code File} object representing the packet capture file to be processed.
     * @return A {@link PacketCapture} object containing extracted properties from the packet capture file.
     */
    public PacketCapture extractPropertiesPacketCapture(File file){
        packetCapture = new PacketCapture();
        extractFileName(file);
        packetCapture.setFileName(fileName);    
        try {
            extractFileDate(file);
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
    public String extractFileName (File file){
        fileName = file.getName() ;
        return fileName;
    }
    
    /**
     * Extracts the creation date of the provided {@code File}.
     *
     * @param file The {@code File} object representing the packet capture file.
     * @return The formatted creation date of the file.
     * @throws IOException If an I/O error occurs during file attribute extraction.
     */
    public String extractFileDate(File file) throws IOException{
        Path path = FileSystems.getDefault().getPath(file.getAbsolutePath());
        BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
        Date fileDateBrut = new Date(attributes.creationTime().toMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fileDate = dateFormat.format(fileDateBrut);
        return fileDate;
    }

    /**
     * Extracts the list of packets from the provided {@code File}.
     *
     * @param file The {@code File} object representing the packet capture file.
     * @return The list of extracted packets.
     */
    public List<Packet> extractPacketsList(File file){
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
