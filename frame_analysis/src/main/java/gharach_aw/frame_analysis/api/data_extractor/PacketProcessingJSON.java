package gharach_aw.frame_analysis.api.data_extractor;


import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Iterator;
import java.util.Map;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import gharach_aw.frame_analysis.api.persistence.entity.Packet;

@Component
@ComponentScan(basePackages = "gharach_aw.frame_analysis.api.data_extractor")
public class PacketProcessingJSON implements PacketProcessing{

    private List<Packet> packetsList = new ArrayList<>();

    private int packetNum;

    private String packetDate;

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

    private Packet packet;

    @Override
    public List<Packet> packetsExtract(File jsonfile) {
        try {
            // Create an ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Get the '_source' object
            JsonNode rootNode = objectMapper.readTree(jsonfile);

            // Iterate through each element in the array
            for (JsonNode element : rootNode) {
                packet = new Packet();
                // Access each element and extract information
                JsonNode sourcNode = element.path("_source");
                JsonNode layersNode = sourcNode.path("layers");
                frameLayerExtract(layersNode);
                ethernetLayerExtract(layersNode);
                networkLayerExtract(layersNode);
                transportLayerExtract(layersNode);
                applicationLayerExtract(layersNode);
                packetsList.add(packet);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }    
        return packetsList;
    }

    public void frameLayerExtract(JsonNode layersNode) throws ParseException {
        JsonNode frameNode = layersNode.path("frame");
        packetNum = frameNode.path("frame.number").asInt();
        packet.setPacketNum(packetNum);
        String dateString = frameNode.path("frame.time").asText();
        packetDate = formatDate(dateString);
        packet.setPacketDate(packetDate);        
        size = frameNode.path("frame.len").asInt();
        packet.setSize(size);
    }
        
    public void ethernetLayerExtract(JsonNode layersNode) {
        JsonNode ethNode = layersNode.path("eth");
        dstMac = ethNode.path("eth.dst").asText();
        packet.setDstMac(dstMac);
        srcMac = ethNode.path("eth.src").asText();
        packet.setSrcMac(srcMac);
        etherType = ethNode.path("eth.type").asText();
        packet.setEtherType(etherType);
    }

    public void networkLayerExtract(JsonNode layersNode) {
        switch (etherType) {
            case "0x0800":   
                JsonNode ipNode = layersNode.path("ip");
                srcIP = ipNode.path("ip.src").asText();
                packet.setSrcIP(srcIP);
                dstIP = ipNode.path("ip.dst").asText();
                packet.setDstIP(dstIP);  
                break;   
            case "0x86dd":
                JsonNode ipv6Node = layersNode.path("ipv6");
                srcIP = ipv6Node.path("ipv6.src").asText();
                packet.setSrcIP(srcIP);
                dstIP = ipv6Node.path("ipv6.dst").asText();
                packet.setDstIP(dstIP);
                break;
        }
    }

    public void transportLayerExtract(JsonNode layersNode) {   
        if (layersNode.has("tcp")) {
            transportProtocol = "tcp";
            packet.setTransportProtocol(transportProtocol);
            JsonNode tcpNode = layersNode.path(transportProtocol);
            srcPort = tcpNode.path("tcp.srcport").asInt();
            packet.setSrcPort(srcPort);
            dstPort = tcpNode.path("tcp.dstport").asInt();
            packet.setDstPort(dstPort);        
        }

        if (layersNode.has("udp")) {
            transportProtocol = "udp";
            packet.setTransportProtocol(transportProtocol);        
            JsonNode udpNode = layersNode.path(transportProtocol);
            srcPort = udpNode.path("udp.srcport").asInt();
            packet.setSrcPort(srcPort);
            dstPort = udpNode.path("udp.dstport").asInt();
            packet.setDstPort(dstPort);  
        }
    }

    public void applicationLayerExtract(JsonNode layersNode){
        // Get the size of the fields in the "layersNode"
        int size = layersNode.size();
        // Get an iterator for the fields in the "layersNode"
        Iterator<Map.Entry<String, JsonNode>> fieldsIterator = layersNode.fields();

        // Iterate through the fields to find the last one
        for (int i = 0; i < size; i++) {
            Map.Entry<String, JsonNode> fieldEntry = fieldsIterator.next();
            applicationProtocol = fieldEntry.getKey();
        }

        packet.setApplicationProtocol(applicationProtocol);

        switch (applicationProtocol) {
            case "tcp": 
                if (srcPort == 443 || dstPort == 443) {
                    applicationProtocol = "https";
                    packet.setApplicationProtocol(applicationProtocol);
                }
                break;
            case "data":
                packet.setApplicationProtocol(applicationProtocol);
                break;
            }
    }

    public String formatDate(String dateString) throws ParseException {
        String inputFormat = "MMM dd, yyyy HH:mm:ss.SSSSSSSSS zzzz";
        String targetTimeZone = "Europe/Paris"; // CET (Central European Time)

       
        // Define a regular expression pattern to match the time zone
        Pattern timeZonePattern = Pattern.compile("\\b[A-Za-z]+ [A-Za-z]+ Time\\b");

        // Create a matcher for the input date string
        Matcher matcher = timeZonePattern.matcher(dateString);

        // Check if the time zone pattern is found
        if (matcher.find()) {
            // Extract the time zone from the matched pattern
            String timeZone = matcher.group();

            // Replace the timeZone with CET
            packetDate = dateString.replace(timeZone, "CET");
        } else {
            System.out.println("Time zone not found in the input string.");
        }

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputFormat);
        ZonedDateTime zonedDateTime = ZonedDateTime
        .parse(packetDate, inputFormatter.withZone(java.util.TimeZone.getTimeZone(targetTimeZone).toZoneId()));

        // Format the ZonedDateTime object as needed
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        packetDate = outputFormatter.format(zonedDateTime);
        return packetDate;
    }
}