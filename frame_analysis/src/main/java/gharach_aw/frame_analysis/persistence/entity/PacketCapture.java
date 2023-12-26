package gharach_aw.frame_analysis.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * The {@code PacketCapture} class represents a collection of network packets captured from Wireshark
 * and is mapped to the "packet_capture" table in the database.
 */
@Entity
@Table(name = "packet_capture")
public class PacketCapture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Long id;

    @Column(name = "packetCaptureName")    
    private String packetCaptureName;

    @Column(name = "uploadDate")    
    private String uploadDate;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getPacketCaptureName() {
        return packetCaptureName;
    }

    public void setPacketCaptureName(String packetCaptureName) {
        this.packetCaptureName = packetCaptureName;
    }

    public String getUploadDate() {
        return uploadDate;
    }    
    
    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     * Returns a string representation of the packet capture.
     *
     * @return A string representation of the packet capture.
     */
    @Override
    public String toString() {
        return "PacketCapture{" +
                "id=" + id +
                ", packetCaptureName='" + packetCaptureName + '\'' +
                ", uploadDate='" + uploadDate + '\'' +
                '}';
    }
}