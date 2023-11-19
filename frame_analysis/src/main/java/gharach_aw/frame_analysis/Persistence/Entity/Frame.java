package gharach_aw.frame_analysis.persistence.entity;

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
    private Long id;

    @Column(name = "frameNum")
    private int frameNum;

    @Column(name = "dateFrame")
    @Temporal(TemporalType.TIMESTAMP)
    private String dateFrame;

    @Column(name = "DstMac")
    private String dstMac;

    @Column(name = "SrcMac")
    private String srcMac;

    @Column(name = "EtherType")
    private String etherType;

    @Column(name = "SrcIP")
    private String srcIP;

    @Column(name = "DstIP")
    private String dstIP;

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

    @OneToMany(mappedBy = "capture")    
    @JoinColumn(name = "capture_id")
    private int captureId;


    // Getters and setters
    
    public Long getId() {
        return id;
    }

    public int getPacketNum() {
        return frameNum;
    }

    public void setFrameNum(int frameNum) {
        this.frameNum = frameNum;
    }


    public String getDatePacket() {
        return dateFrame;
    }
    
    public void setDateFrame(String dateFrame) {
        this.dateFrame = dateFrame;
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

    public int getCaptureId() {
        return captureId;
    }

    public void setCaptureId(Capture capture){
        captureId = capture.getId();
    }
}