package gharach_aw.frame_analysis.exception;

public class PacketCaptureNotFoundException extends RuntimeException{
    
    public PacketCaptureNotFoundException (String message) {
        super(message);
    }   
}