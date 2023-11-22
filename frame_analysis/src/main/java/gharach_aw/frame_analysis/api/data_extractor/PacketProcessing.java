package gharach_aw.frame_analysis.api.data_extractor;

import java.io.File;

import java.util.List;


import gharach_aw.frame_analysis.api.persistence.entity.Packet;

public interface PacketProcessing {
    public List<Packet> packetsExtract(File file);
}