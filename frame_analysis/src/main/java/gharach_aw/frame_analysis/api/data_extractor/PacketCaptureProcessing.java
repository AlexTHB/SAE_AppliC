package gharach_aw.frame_analysis.api.data_extractor;

import java.io.File;

import gharach_aw.frame_analysis.api.persistence.entity.PacketCapture;

public interface PacketCaptureProcessing {
     public PacketCapture extractPropertiesPacketCapture(File file);
}
