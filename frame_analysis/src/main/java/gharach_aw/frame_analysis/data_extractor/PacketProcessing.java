package gharach_aw.frame_analysis.data_extractor;

import java.io.File;
import java.util.List;

import gharach_aw.frame_analysis.persistence.entity.Packet;

/**
 * This interface defines a contract for processing packets.
 * You can create classes implementing this interface to extract packets
 * from specific file types.
 */
public interface PacketProcessing {

    /**
     * Extracts packets from the specified file. 
     *
     * @param file The file containing packet data.
     * @return A List of Packet objects extracted from the file.
     */
    List<Packet> packetsExtract(File file);
}