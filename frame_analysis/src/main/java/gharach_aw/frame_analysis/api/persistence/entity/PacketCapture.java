package gharach_aw.frame_analysis.api.persistence.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "packetCapture")
public class PacketCapture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "fileDate")
    private String fileDate;

    @OneToMany(mappedBy = "packetCapture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Packet> packets;

    // Getters and setters

    public int getId() {
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

}
