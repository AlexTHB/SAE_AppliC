package gharach_aw.frame_analysis;

import java.io.File;
import java.util.List;

import gharach_aw.frame_analysis.api.data_extractor.PacketProcessingJSON;
import gharach_aw.frame_analysis.api.persistence.entity.Packet;

public class testExtractorDataPacketJSON {
     public static void main(String[] args) {

        // Read the JSON file into a JsonNode
        File jsonFile = new File("src\\test\\java\\gharach_aw\\frame_analysis\\b.json");
        PacketProcessingJSON extractor = new PacketProcessingJSON();

        List<Packet> frameList = extractor.packetsExtract(jsonFile);
    
        for (Packet frame : frameList) {
            System.out.println(frame.getPacketNum() + " " + frame.getPacketDate() + " " + frame.getDstMac() + " " +frame.getSrcMac() 
            + " " + frame.getEtherType() + " " + frame.getSrcIP() + " " +frame.getDstIP() + " " + frame.getSrcPort() + " " + 
            frame.getDstPort() + " " + frame.getTransportProtocol() + " " + frame.getApplicationProtocol() + " " +frame.getSize()            
            );  
        }

    }
}