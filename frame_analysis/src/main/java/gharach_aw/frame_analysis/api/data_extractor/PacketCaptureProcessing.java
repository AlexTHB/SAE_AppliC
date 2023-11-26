package gharach_aw.frame_analysis.api.data_extractor;

import java.io.File;

import gharach_aw.frame_analysis.api.persistence.entity.PacketCapture;

/**
 * The `PacketCaptureProcessing` interface defines a contract for classes that process packet capture files
 * and extract properties related to packet capture.
 */
public interface PacketCaptureProcessing {

    /**
     * Extracts properties from a given packet capture file.
     *
     * @param file The {@code File} object representing the packet capture file to be processed.
     * @return A {@code PacketCapture} object containing extracted properties from the packet capture file.
     */
    public PacketCapture extractPropertiesPacketCapture(File file);
}

