package gharach_aw.frame_analysis.persistence.entity;

public class PacketDTO {

    private final Long id;
    private final int packetNum;
    private final String packetDate;
    private final String dstMac;
    private final String srcMac;
    private final String etherType;
    private final String srcIP;
    private final String dstIP;
    private final int srcPort;
    private final int dstPort;
    private final String transportProtocol;
    private final String applicationProtocol;
    private final int size;

    // Constructor

    public PacketDTO(
            Long id,
            int packetNum,
            String packetDate,
            String dstMac,
            String srcMac,
            String etherType,
            String srcIP,
            String dstIP,
            int srcPort,
            int dstPort,
            String transportProtocol,
            String applicationProtocol,
            int size
    ) {
        this.id = id;
        this.packetNum = packetNum;
        this.packetDate = packetDate;
        this.dstMac = dstMac;
        this.srcMac = srcMac;
        this.etherType = etherType;
        this.srcIP = srcIP;
        this.dstIP = dstIP;
        this.srcPort = srcPort;
        this.dstPort = dstPort;
        this.transportProtocol = transportProtocol;
        this.applicationProtocol = applicationProtocol;
        this.size = size;
    }


    // Getters (no setters as attributes are final)

    public Long getId() {
        return id;
    }

    public int getPacketNum() {
        return packetNum;
    }

    public String getPacketDate() {
        return packetDate;
    }

    public String getDstMac() {
        return dstMac;
    }

    public String getSrcMac() {
        return srcMac;
    }

    public String getEtherType() {
        return etherType;
    }

    public String getSrcIP() {
        return srcIP;
    }

    public String getDstIP() {
        return dstIP;
    }

    public int getSrcPort() {
        return srcPort;
    }

    public int getDstPort() {
        return dstPort;
    }

    public String getTransportProtocol() {
        return transportProtocol;
    }

    public String getApplicationProtocol() {
        return applicationProtocol;
    }

    public int getSize() {
        return size;
    }

    public static PacketDTO convertToDTO(Packet packet) {
        if (packet == null) {
            return null;
        }

        return new PacketDTO(
                packet.getId(),
                packet.getPacketNum(),
                packet.getPacketDate(),
                packet.getDstMac(),
                packet.getSrcMac(),
                packet.getEtherType(),
                packet.getSrcIP(),
                packet.getDstIP(),
                packet.getSrcPort(),
                packet.getDstPort(),
                packet.getTransportProtocol(),
                packet.getApplicationProtocol(),
                packet.getSize()
        );
    }
}

