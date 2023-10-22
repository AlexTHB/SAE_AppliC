package ExtractorData;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapPacket;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.EtherType;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class ExtractorDataFramePcap extends ExtractorDataFrame<Object> {
    
    public List<List<Object>> getPackets(String file) {
        try {
            // Open a pcap file
            PcapHandle nif = Pcaps.openOffline(file);
            
            // Loop through the packets and add them to the list
            while (true) {
                Packet packet = nif.getNextPacketEx();
                if (packet == null) break;

                List<Object> packetList = new ArrayList<>();

                // Get the packet timestamp
                Instant timestampPacket = ((PcapPacket) packet).getTimestamp();
                packetList.add(timestampPacket);

               if (packet.contains(EthernetPacket.class)) {
                    EthernetPacket ethernetPacket = packet.get(EthernetPacket.class);
                    if (ethernetPacket.getHeader().getType() == EtherType.IPV4) {
                        String destinationMacAddress = ethernetPacket.getHeader().getDstAddr().toString();
                        packetList.add(destinationMacAddress);
                    }
                } 
            
                packetsList.add(packetList);
            }

        } catch (IOException e) {
            System.err.println("Error reading pcap file: " + e.getMessage());
            e.printStackTrace(); // Print the full stack trace
        } catch (PcapNativeException e) {
            System.err.println("Error in native pcap library: " + e.getMessage());
        } catch (TimeoutException e) {
            System.err.println("Timeout while reading pcap file: " + e.getMessage());
        } catch (NotOpenException e) {
            System.err.println("Pcap handle not open: " + e.getMessage());
        }
        return packetsList;  

    }
}
