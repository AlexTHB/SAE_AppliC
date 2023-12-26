package gharach_aw.frame_analysis.service;


import org.springframework.web.multipart.MultipartFile;

import gharach_aw.frame_analysis.persistence.entity.PacketCapture;

public interface PacketCaptureProcessingService {

    public PacketCapture extractPropertiesPacketCapture(MultipartFile file);

}