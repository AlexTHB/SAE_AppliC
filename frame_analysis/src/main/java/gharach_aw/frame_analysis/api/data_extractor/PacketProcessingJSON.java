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

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import gharach_aw.frame_analysis.api.persistence.entity.Packet;

@Component
@ComponentScan("gharach_aw.frame_analysis.api.data_extractor")
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
            JsonNode arrayNode = objectMapper.readTree(jsonfile);

            // Iterate through each element in the array
            for (JsonNode element : arrayNode) {
                packet = new Packet();
                // Access each element and extract information
                JsonNode sourceNode = element.path("_source");
                JsonNode layersNode = sourceNode.path("layers");
                frameLayerExtract(layersNode);
                ethernetLayerExtract(layersNode);
                networkLayerExtract(layersNode);
                transportLayerExtract(layersNode);
                packetsList.add(packet);
            }

        } catch (IOException | ParseException e) {
            // TODO Auto-generated catch block
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
        String protocols = frameNode.path("frame.protocols").asText();   
        // Split the string based on the colon separator
        String[] parts = protocols.split(":");
        protocolsExtract(parts);
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
        if (etherType.equals("0x0800")){
            JsonNode ipNode = layersNode.path("ip");
            srcIP = ipNode.path("ip.src").asText();
            packet.setSrcIP(srcIP);
            dstIP = ipNode.path("ip.dst").asText();
            packet.setDstIP(dstIP);
        } else if (etherType.equals("0x86dd")){
            JsonNode ipv6Node = layersNode.path("ipv6");
            srcIP = ipv6Node.path("ipv6.src").asText();
            packet.setSrcIP(srcIP);
            dstIP = ipv6Node.path("ipv6.dst").asText();
            packet.setDstIP(dstIP);
        }
    }

    public void transportLayerExtract(JsonNode layersNode) {   
        if (transportProtocol.equals("tcp")) {       
            JsonNode tcpNode = layersNode.path("tcp");
            srcPort = tcpNode.path("tcp.srcport").asInt();
            packet.setSrcPort(srcPort);
            dstPort = tcpNode.path("tcp.dstport").asInt();
            packet.setDstPort(dstPort);
        } else if (transportProtocol.equals("udp")) {
            JsonNode udpNode = layersNode.path("udp");
            srcPort = udpNode.path("udp.srcport").asInt();
            packet.setSrcPort(srcPort);
            dstPort = udpNode.path("udp.dstport").asInt();
            packet.setDstPort(dstPort);
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

    public void protocolsExtract(String[] parts){
        if (parts.length == 4 ) {
        int transport_Index = 3;
        transportProtocol = parts[transport_Index];
        packet.setTransportProtocol(transportProtocol);
        } else if (parts.length >= 5) {
            int transport_Index = 3;
            transportProtocol = parts[transport_Index];
            packet.setTransportProtocol(transportProtocol);
            int application_Index = 4;
            applicationProtocol = parts[application_Index];
            packet.setApplicationProtocol(applicationProtocol);
        }
    }
    
}