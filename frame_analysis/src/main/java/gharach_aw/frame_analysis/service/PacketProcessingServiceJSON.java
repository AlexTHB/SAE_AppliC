package gharach_aw.frame_analysis.service;


import java.io.IOException;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import gharach_aw.frame_analysis.persistence.entity.Packet;

/**
 * The {@code PacketProcessingJSON} class is an implementation of the {@link PacketProcessingService} interface.
 * It extracts informations of the JSON Packet Capture and populates {@code Packet} objects with relevant details.
 * 
 * This class is annotated with {@link Service} Spring service component.
 */

@Service
public class PacketProcessingServiceJSON implements PacketProcessingService{

    private List<Packet> packets;

    private int packetNum;

    private String packetDate;

    private String dstMac;

    private String srcMac;

    private String etherType;

    private String srcIP;

    private String dstIP;

    private int srcPort;

    private int dstPort;

    private String protocol;

    private int size;

    private Packet packet;

    /**
     * Extracts packets from the specified JSON file and populates a list of Packet objects.
     *
     * @param jsonfile The JSON file containing packet data.
     * @return A List of Packet objects extracted from the JSON file.
     */

    @Override
    public List<Packet> packetsExtract(MultipartFile jsonfile) {
        try {

            packets = new ArrayList<>();

            // Create an ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Get the JSON content from MultipartFile
            JsonNode rootNode = objectMapper.readTree(jsonfile.getInputStream());

            // Iterate through each element in the array
            for (JsonNode element : rootNode) {
                packet = new Packet();
                // Access each element and extract information
                JsonNode sourceNode = element.path("_source");
                JsonNode layersNode = sourceNode.path("layers");
                frameLayerExtract(layersNode);
                ethernetLayerExtract(layersNode);
                networkLayerExtract(layersNode);
                transportLayerExtract(layersNode);
                highestLayerProtocolExtract(layersNode);
                packets.add(packet);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }    
        return packets;
    }

    /**
     * Extracts information from the frame layer of the packet and sets packetNum, packetDate and packet size fields in the Packet object.
     *
     * @param layersNode The JsonNode representing the layers of the packet.
     * @throws ParseException If there is an error parsing date information.
     */
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

    /**
     * Extracts information from the Ethernet layer of the packet and sets dstMac, srcMac and etherType fields in the Packet object.
     *
     * @param layersNode The JsonNode representing the layers of the packet.
     */ 
    public void ethernetLayerExtract(JsonNode layersNode) {
        if (layersNode.has("eth")) {
            JsonNode ethNode = layersNode.path("eth");
            dstMac = ethNode.path("eth.dst").asText();
            packet.setDstMac(dstMac);
            srcMac = ethNode.path("eth.src").asText();
            packet.setSrcMac(srcMac);
            etherType = ethNode.path("eth.type").asText();
            packet.setEtherType(etherType);
        } else {
            JsonNode sllNode = layersNode.path("sll");
            srcMac = sllNode.path("sll.src.eth").asText();
            packet.setSrcMac(srcMac);
            etherType = sllNode.path("sll.etype").asText();
            packet.setEtherType(etherType);
        } 
    }

    /**
     * Extracts information from the network layer of the packet and sets src and dst ip fields in the Packet object.
     *
     * @param layersNode The JsonNode representing the layers of the packet.
     */
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
            case "0x0806":
                JsonNode arpNode = layersNode.path("arp");
                srcIP = arpNode.path("arp.src.proto_ipv4").asText();
                packet.setSrcIP(srcIP);
                dstIP = arpNode.path("arp.dst.proto_ipv4").asText();
                packet.setDstIP(dstIP);
                break;
        }
    }

    /**
     * Extracts information from the transport layer of the packet and sets ports src and dst fields in the Packet object.
     *
     * @param layersNode The JsonNode representing the layers of the packet.
     */
    public void transportLayerExtract(JsonNode layersNode) {   
        if (layersNode.has("tcp")) {
            JsonNode tcpNode = layersNode.path("tcp");
            srcPort = tcpNode.path("tcp.srcport").asInt();
            packet.setSrcPort(srcPort);
            dstPort = tcpNode.path("tcp.dstport").asInt();
            packet.setDstPort(dstPort);        
        } else {
            JsonNode udpNode = layersNode.path("udp");
            srcPort = udpNode.path("udp.srcport").asInt();
            packet.setSrcPort(srcPort);
            dstPort = udpNode.path("udp.dstport").asInt();
            packet.setDstPort(dstPort);  
        }
    }

    /**
     * Extracts the hightest layer protocol of packet and sets protocol field in the Packet object.
     *
     * @param layersNode The JsonNode representing the layers of the packet.
     */
    public void highestLayerProtocolExtract(JsonNode layersNode){
        // Get the size of the fields in the "layersNode"
        int size = layersNode.size();
        // Get an iterator for the fields in the "layersNode"
        Iterator<Map.Entry<String, JsonNode>> fieldsIterator = layersNode.fields();

        // Iterate through the fields to find the last one
        for (int i = 0; i < size; i++) {
            Map.Entry<String, JsonNode> fieldEntry = fieldsIterator.next();
            protocol = fieldEntry.getKey();
        }

        packet.setProtocol(protocol);
    }

    /**
     * Formats the given date string to the desired format and time zone.
     *
     * @param dateString The date string to be formatted.
     * @return The formatted date string.
     * @throws ParseException If there is an error parsing date information.
     */
    public String formatDate(String dateString) throws ParseException {

        // Define a regular expression pattern to match the time zone
        Pattern timeZonePattern = Pattern.compile("\\b[A-Za-z]+ [A-Za-z]+ Time\\b");

        // Create a matcher for the input date string
        Matcher matcher = timeZonePattern.matcher(dateString);

        // Check if the time zone pattern is found
        if (matcher.find()) {
            // Extract the time zone from the matched pattern
            String timeZone = matcher.group();

            // Replace the timeZone with CET
            dateString = dateString.replace(timeZone, "CET");
        } 

        String inputFormat = "MMM dd, yyyy HH:mm:ss.SSSSSSSSS z";
        String targetTimeZone = "Europe/Paris"; // CET (Central European Time)

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputFormat, Locale.ENGLISH);
        ZonedDateTime zonedDateTime = ZonedDateTime
        .parse(dateString, inputFormatter.withZone(java.util.TimeZone.getTimeZone(targetTimeZone).toZoneId()));

        // Format the ZonedDateTime object as needed
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        packetDate = outputFormatter.format(zonedDateTime);
        return packetDate;
    }
}