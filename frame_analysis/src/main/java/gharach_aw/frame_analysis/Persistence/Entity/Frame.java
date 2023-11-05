package gharach_aw.frame_analysis.Persistence.Entity;

import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
@Entity
@Table(name = "frame")
public class Frame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "PacketNum")
    private int packetNum;

    @Column(name = "DatePacket")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePacket;

    @Column(name = "DstMac")
    private String dstMac;

    @Column(name = "SrcMac")
    private String srcMac;

    @Column(name = "EtherType")
    private String etherType;

    @Column(name = "SrcIP")
    private int srcIP;

    @Column(name = "DstIP")
    private int dstIP;

    @Column(name = "SrcPort")
    private int srcPort;

    @Column(name = "DstPort")
    private int dstPort;

    @Column(name = "TransportProtocol")
    private String transportProtocol;

    @Column(name = "ApplicationProtocol")
    private String applicationProtocol;

    @Column(name = "Size")
    private int size;

    @Column(name = "Info")
    private String info;

    @OneToMany(mappedBy = "capture")    
    @JoinColumn(name = "id_capture")
    private int capture;


    // Getters
    
    public int getId() {
        return id;
    }

    public int getPacketNum() {
        return packetNum;
    }

    public Date getDatePacket() {
        return datePacket;
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

    public int getSrcIP() {
        return srcIP;
    }

    public int getDstIP() {
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

    public String getInfo() {
        return info;
    }

    public int getCapture() {
        return capture;
    }
}

