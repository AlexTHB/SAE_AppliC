package gharach_aw.frame_analysis.persistence.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * The {@code PacketCapture} class represents a collection of network packets captured from Wireshark
 * and is mapped to the "packet_capture" table in the database.
 */
@Entity
@Table(name = "packet_capture", uniqueConstraints = @UniqueConstraint(columnNames = "fileName"))
public class PacketCapture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fileName", unique = true)
    private String fileName;

    @Column(name = "fileDate")
    private String fileDate;

    @OneToMany(mappedBy = "packetCapture", cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.LAZY)
    private List<Packet> packets;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDate() {
        return fileDate;
    }    
    
    public void setFileDate(String fileDate) {
        this.fileDate = fileDate;
    }

    public List<Packet> getPackets(){
        return packets;
    }

    public void setPackets(List<Packet> packets){
        this.packets = packets;
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
                ", fileName='" + fileName + '\'' +
                ", fileDate='" + fileDate + '\'' +
                '}';
    }
}
