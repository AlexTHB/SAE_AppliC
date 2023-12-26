package gharach_aw.frame_analysis.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import gharach_aw.frame_analysis.persistence.entity.Packet;

/**
 * This interface defines a contract for processing packets.
 * You can create classes implementing this interface to extract packets
 * from specific file types.
 */
public interface PacketProcessingService {

    /**
     * Extracts packets from the specified file. 
     *
     * @param file The file containing packet data.
     * @return A List of Packet objects extracted from the file.
     */
    List<Packet> packetsExtract(MultipartFile file);
}