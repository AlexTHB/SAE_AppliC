package gharach_aw.frame_analysis.ExtractorData;


import java.sql.Date;
import java.util.List;
import gharach_aw.frame_analysis.Persistence.Entity.Frame;


public class ExtractorDataFrameJSON extends ExtractorDataFrame{

    public List<Frame> frameList;

    private int packetNum;

    private Date datePacket;

    private String dstMac;

    private String srcMac;

    private String etherType;

    private int srcIP;

    private int dstIP;

    private int srcPort;

    private int dstPort;

    private String transportProtocol;

    private String applicationProtocol;

    private int size;

    private String info;

    public int extractPacketNum() {
        return packetNum;
    }

    public Date extractDatePacket() {
        return datePacket;
    }

    public String extractDstMac() {
        return dstMac;

    }

    public String extractSrcMac() {
        return srcMac;

    }

    public String extractEtherType() {
        return etherType;

    }

    public int extractSrcIP() {
        return srcIP;

    }

    public int extractDstIP() {
        return dstIP;

    }

    public int extractSrcPort() {
        return srcPort;

    }

    public int extractDstPort() {
        return dstPort;
    }

    public String extractTransportProtocol() {
        return transportProtocol;

    }

    public String extractApplicationProtocol() {
        return applicationProtocol;

    }

    public int extractSize() {
        return size;
    }
    
    public String extractInfo() {
        return info;
    }

    public List<Frame> extractPackets(String file) {
        return frameList;
    }
    
}