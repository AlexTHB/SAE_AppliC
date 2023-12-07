package gharach_aw.frame_analysis.persistence.entity;

public class PacketCaptureDTO {

    private final Long id;
    private final String fileName;
    private final String fileDate;

    // Constructor with parameters
    public PacketCaptureDTO(Long id, String fileName, String fileDate) {
        this.id = id;
        this.fileName = fileName;
        this.fileDate = fileDate;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileDate() {
        return fileDate;
    }

    public static PacketCaptureDTO convertToDTO(PacketCapture packetCapture) {
        return new PacketCaptureDTO(
            packetCapture.getId(),
            packetCapture.getFileName(),
            packetCapture.getFileDate()
        );
    }
}
