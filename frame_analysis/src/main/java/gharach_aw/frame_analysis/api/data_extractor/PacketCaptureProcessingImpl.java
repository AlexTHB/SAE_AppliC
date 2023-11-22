package gharach_aw.frame_analysis.api.data_extractor;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import gharach_aw.frame_analysis.api.persistence.entity.Packet;
import gharach_aw.frame_analysis.api.persistence.entity.PacketCapture;

@Component
@ComponentScan("gharach_aw.frame_analysis.api.data_extractor")
public class PacketCaptureProcessingImpl implements PacketCaptureProcessing{

    private PacketCapture packetCapture;
    private String fileName;
    private String formattedDate;
    private List<Packet> packets;

    private final PacketProcessing packetProcessing;

    @Autowired
    public PacketCaptureProcessingImpl(PacketProcessing packetProcessing) {
        this.packetProcessing = packetProcessing;
    }

    @Override
    public PacketCapture extractPropertiesPacketCapture(File file){
        packetCapture = new PacketCapture();
        extractFileName(file);
        packetCapture.setFileName(fileName);    
        try {
            extractFileDate(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        packetCapture.setFileDate(formattedDate);
        extractPacketsList(file);
        packetCapture.setPackets(packets);
        return packetCapture;

    }
    
    public String extractFileName (File file){
        fileName = file.getName() ;
        return fileName;
    }
    
    public String extractFileDate(File file) throws IOException{
        Path path = FileSystems.getDefault().getPath(file.getAbsolutePath());
        BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
        Date fileDate = new Date(attributes.creationTime().toMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = dateFormat.format(fileDate);
        return formattedDate;
    }

    public List<Packet> extractPacketsList(File file){
        packets = packetProcessing.packetsExtract(file);
        System.out.println(packets);
        return packets;
    }
}
