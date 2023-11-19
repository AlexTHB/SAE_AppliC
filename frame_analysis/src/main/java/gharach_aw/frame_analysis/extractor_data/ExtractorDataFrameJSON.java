package gharach_aw.frame_analysis.extractor_data;


import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import gharach_aw.frame_analysis.persistence.entity.Frame;


public class ExtractorDataFrameJSON implements ExtractorDataFrame{

    private List<Frame> frameList = new ArrayList<>();

    private int frameNum;

    private String dateFrame;

    private String dstMac;

    private String srcMac;

    private String etherType;

    private String srcIP;

    private String dstIP;

    private int srcPort;

    private int dstPort;

    private String transportProtocol;

    private String applicationProtocol;

    private int size;

    private Frame frame;

    public String formatDate(String dateString) throws ParseException {
        String inputFormat = "MMM dd, yyyy HH:mm:ss.SSSSSSSSS zzzz";
        String targetTimeZone = "Europe/Paris"; // CET (Central European Time)

        // Replace "Romance Summer Time" with "CET"
        dateString = dateString.replace("Romance Summer Time", "CET");

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputFormat);
        ZonedDateTime zonedDateTime = ZonedDateTime
        .parse(dateString, inputFormatter.withZone(java.util.TimeZone.getTimeZone(targetTimeZone).toZoneId()));

        // Format the ZonedDateTime object as needed
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        dateFrame = outputFormatter.format(zonedDateTime);
        return dateFrame;
    }

    public void extractProtocols(String[] parts){
        if (parts.length == 4 ) {
        int transport_Index = 3;
        transportProtocol = parts[transport_Index];
        frame.setTransportProtocol(transportProtocol);
        } else if (parts.length >= 5) {
            int transport_Index = 3;
            transportProtocol = parts[transport_Index];
            frame.setTransportProtocol(transportProtocol);
            int application_Index = 4;
            applicationProtocol = parts[application_Index];
            frame.setApplicationProtocol(applicationProtocol);
        }
    }

    public void frameLayerExtract(JsonNode layersNode) throws ParseException {
        JsonNode frameNode = layersNode.path("frame");
        frameNum = frameNode.path("frame.number").asInt();
        frame.setFrameNum(frameNum);
        String dateString = frameNode.path("frame.time").asText();
        dateFrame = formatDate(dateString);
        frame.setDateFrame(dateFrame);        
        size = frameNode.path("frame.len").asInt();
        frame.setSize(size);
        String protocols = frameNode.path("frame.protocols").asText();   
        // Split the string based on the colon separator
        String[] parts = protocols.split(":");
        extractProtocols(parts);
    }
        
    public void ethernetLayerExtract(JsonNode layersNode) {
        JsonNode ethNode = layersNode.path("eth");
        dstMac = ethNode.path("eth.dst").asText();
        frame.setDstMac(dstMac);
        srcMac = ethNode.path("eth.src").asText();
        frame.setSrcMac(srcMac);
        etherType = ethNode.path("eth.type").asText();
        frame.setEtherType(etherType);
    }

    public void networkLayerExtract(JsonNode layersNode) {
        if (etherType.equals("0x0800")){
            JsonNode ipNode = layersNode.path("ip");
            srcIP = ipNode.path("ip.src").asText();
            frame.setSrcIP(srcIP);
            dstIP = ipNode.path("ip.dst").asText();
            frame.setDstIP(dstIP);
        } else if (etherType.equals("0x86dd")){
            JsonNode ipv6Node = layersNode.path("ipv6");
            srcIP = ipv6Node.path("ipv6.src").asText();
            frame.setSrcIP(srcIP);
            dstIP = ipv6Node.path("ipv6.dst").asText();
            frame.setDstIP(dstIP);
        }
    }

    public void transportLayerExtract(JsonNode layersNode) {   
        if (transportProtocol.equals("tcp")) {       
            JsonNode tcpNode = layersNode.path("tcp");
            srcPort = tcpNode.path("tcp.srcport").asInt();
            frame.setSrcPort(srcPort);
            dstPort = tcpNode.path("tcp.dstport").asInt();
            frame.setDstPort(dstPort);
        } else if (transportProtocol.equals("udp")) {
            JsonNode udpNode = layersNode.path("udp");
            srcPort = udpNode.path("udp.srcport").asInt();
            frame.setSrcPort(srcPort);
            dstPort = udpNode.path("udp.dstport").asInt();
            frame.setDstPort(dstPort);
        }        
    }

    @Override
    public List<Frame> framesExtract(File jsonfile) {
        try {
            // Create an ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Get the '_source' object
            JsonNode arrayNode = objectMapper.readTree(jsonfile);

            // Iterate through each element in the array
            for (JsonNode element : arrayNode) {
                frame = new Frame();
                // Access each element and extract information
                JsonNode sourceNode = element.path("_source");
                JsonNode layersNode = sourceNode.path("layers");
                frameLayerExtract(layersNode);
                ethernetLayerExtract(layersNode);
                networkLayerExtract(layersNode);
                transportLayerExtract(layersNode);
                frameList.add(frame);
            }

        } catch (IOException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return frameList;
    }
    
}