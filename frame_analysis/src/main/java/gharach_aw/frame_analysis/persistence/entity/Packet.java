package gharach_aw.frame_analysis.persistence.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * The {@code Packet} class represents a network packet and is mapped to the "packet" table in the database.
 */
@Entity
@Table(name = "packet")
public class Packet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "packetNum")
    private int packetNum;

    @Column(name = "packetDate")
    private String packetDate;

    @Column(name = "dstMac")
    private String dstMac;

    @Column(name = "srcMac")
    private String srcMac;

    @Column(name = "etherType")
    private String etherType;

    @Column(name = "srcIP")
    private String srcIP;

    @Column(name = "dstIP")
    private String dstIP;

    @Column(name = "srcPort")
    private int srcPort;

    @Column(name = "dstPort")
    private int dstPort;

    @Column(name = "transportProtocol")
    private String transportProtocol;

    @Column(name = "applicationProtocol")
    private String applicationProtocol;

    @Column(name = "size")
    private int size;

    @ManyToOne    
    @JoinColumn(name = "packetCaptureId")
    private PacketCapture packetCapture;


    // Getters and setters
    
    public Long getId() {
        return id;
    }

    public int getPacketNum() {
        return packetNum;
    }

    public void setPacketNum(int frameNum) {
        this.packetNum = frameNum;
    }


    public String getPacketDate() {
        return packetDate;
    }
    
    public void setPacketDate(String packetDate) {
        this.packetDate = packetDate;
    }

    public String getDstMac() {
        return dstMac;
    }

    public void setDstMac(String dstMac) {
        this.dstMac = dstMac;
    }

    public String getSrcMac() {
        return srcMac;
    }
    
    public void setSrcMac(String srcMac) {
        this.srcMac = srcMac;
    }

    public String getEtherType() {
        return etherType;
    }

    public void setEtherType(String etherType) {
        this.etherType = etherType;
    }


    public String getSrcIP() {
        return srcIP;
    }

    
    public void setSrcIP(String srcIP) {
        this.srcIP = srcIP;
    }


    public String getDstIP() {
        return dstIP;
    }

    public void setDstIP(String dstIP) {
        this.dstIP = dstIP;
    }

    public int getSrcPort() {
        return srcPort;
    }

    public void setSrcPort(int srcPort) {
        this.srcPort = srcPort;
    }

    public int getDstPort() {
        return dstPort;
    }

    
    public void setDstPort(int dstPort) {
        this.dstPort = dstPort;
    }

    public String getTransportProtocol() {
        return transportProtocol;
    }

    public void setTransportProtocol(String transportProtocol) {
        this.transportProtocol = transportProtocol;
    }


    public String getApplicationProtocol() {
        return applicationProtocol;
    }

    public void setApplicationProtocol(String applicationProtocol) {
        this.applicationProtocol = applicationProtocol;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    /**
     * Gets the {@link PacketCapture} to which this packet belongs.
     *
     * @return The associated PacketCapture.
     */
    public PacketCapture getPacketCapture() {
        return packetCapture;
    }

    /**
     * Sets the {@link PacketCapture} to which this packet belongs.
     *
     * @param packetCapture The PacketCapture to associate with this packet.
     */
    public void setPacketCapture(PacketCapture packetCapture) {
        this.packetCapture = packetCapture;
    }

    
    /**
     * Returns a string representation of the packet.
     *
     * @return A string representation of the packet.
     */
    @Override
    public String toString() {
        return "Packet{id=" + id +
            ", packetNum='" + packetNum + '\'' +
            ", packetDate='" + packetDate + '\'' +
            ", dstMac='" + dstMac + '\'' +
            ", srcMac='" + srcMac + '\'' +
            ", etherType='" + etherType + '\'' +
            ", srcIP='" + srcIP + '\'' +
            ", dstIP='" + dstIP + '\'' +
            ", srcPort=" + srcPort +
            ", dstPort=" + dstPort +
            ", transportProtocol='" + transportProtocol + '\'' +
            ", applicationProtocol='" + applicationProtocol + '\'' +
            ", size=" + size +
            '}';
    }

}