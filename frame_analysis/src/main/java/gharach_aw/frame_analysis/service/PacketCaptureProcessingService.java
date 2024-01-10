package gharach_aw.frame_analysis.service;

import org.springframework.web.multipart.MultipartFile;

import gharach_aw.frame_analysis.persistence.entity.PacketCapture;

/**
 * Service interface for processing PacketCaptures, providing methods for extracting
 * properties from a MultipartFile containing packet capture data.
 *
 * This interface defines a method to extract properties from the provided MultipartFile,
 * allowing for the processing of packet capture files and retrieval of relevant information.
 *
 * @see org.springframework.web.multipart.MultipartFile
 * @see PacketCapture
 */
public interface PacketCaptureProcessingService {

     /**
     * Extracts properties from the provided MultipartFile containing packet capture data.
     *
     * @param file The MultipartFile containing packet capture data.
     * @return PacketCapture object representing the extracted properties from the packet capture data.
     */
    public PacketCapture extractPropertiesPacketCapture(MultipartFile file);

}