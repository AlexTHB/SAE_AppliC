package gharach_aw.frame_analysis;

import java.io.File;
import java.util.List;

import gharach_aw.frame_analysis.extractor_data.ExtractorDataFrameJSON;
import gharach_aw.frame_analysis.persistence.entity.Frame;

public class ExtractorDataFrameJSONtest {
     public static void main(String[] args) {

        // Read the JSON file into a JsonNode
        File jsonFile = new File("src\\test\\java\\gharach_aw\\frame_analysis\\test.json");
        ExtractorDataFrameJSON extractor = new ExtractorDataFrameJSON();

        List<Frame> frameList = extractor.framesExtract(jsonFile);
    
        for (Frame frame : frameList) {
            System.out.println(frame.getPacketNum() + " " + frame.getDatePacket() + " " + frame.getDstMac() + " " +frame.getSrcMac() 
            + " " + frame.getEtherType() + " " + frame.getSrcIP() + " " +frame.getDstIP() + " " + frame.getSrcPort() + " " + 
            frame.getDstPort() + " " + frame.getTransportProtocol() + " " + frame.getApplicationProtocol() + " " +frame.getSize()            
            );  
        }

    }
}