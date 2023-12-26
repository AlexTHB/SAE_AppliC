package gharach_aw.frame_analysis.service;


import java.io.IOException;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import gharach_aw.frame_analysis.persistence.entity.Packet;

/**
 * The {@code PacketProcessingJSON} class is an implementation of the {@link PacketProcessingService} interface.
 * It extracts informations of the JSON Packet Capture and populates {@code Packet} objects with relevant details.
 * 
 * This class is annotated with {@link Component} to be automatically detected and registered as a Spring bean.
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

    private String transportProtocol;

    private String applicationProtocol;

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
                applicationLayerExtract(layersNode);
                packets.add(packet);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }    
        return packets;
    }

    /**
     * Extracts information from the frame layer of the packet and sets relevant fields in the Packet object.
     *
     * @param layersNode The JsonNode representing the layers of the packet.
     * @throws ParseException If there is an error parsing date information.
     */
    public void frameLayerExtract(JsonNode layersNode) throws ParseException {
        JsonNode frameNode = layersNode.path("frame");
        packetNum = frameNode.path("frame.number").asInt();
        packet.setPacketNum(packetNum);
        packetDate = frameNode.path("frame.time").asText();
        packet.setPacketDate(packetDate);        
        size = frameNode.path("frame.len").asInt();
        packet.setSize(size);
    }

    /**
     * Extracts information from the Ethernet layer of the packet and sets relevant fields in the Packet object.
     *
     * @param layersNode The JsonNode representing the layers of the packet.
     */ 
    public void ethernetLayerExtract(JsonNode layersNode) {
        JsonNode ethNode = layersNode.path("eth");
        dstMac = ethNode.path("eth.dst").asText();
        packet.setDstMac(dstMac);
        srcMac = ethNode.path("eth.src").asText();
        packet.setSrcMac(srcMac);
        etherType = ethNode.path("eth.type").asText();
        packet.setEtherType(etherType);
    }

    /**
     * Extracts information from the network layer of the packet and sets relevant fields in the Packet object.
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
        }
    }

    /**
     * Extracts information from the transport layer of the packet and sets relevant fields in the Packet object.
     *
     * @param layersNode The JsonNode representing the layers of the packet.
     */
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

    /**
     * Extracts information from the application layer of the packet and sets relevant fields in the Packet object.
     *
     * @param layersNode The JsonNode representing the layers of the packet.
     */
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
        }
    }
}