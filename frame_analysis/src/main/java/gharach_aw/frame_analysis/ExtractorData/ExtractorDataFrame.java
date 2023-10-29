package gharach_aw.frame_analysis.ExtractorData;

import java.util.ArrayList;
import java.util.List;

public class ExtractorDataFrame<T> {
    public List<List<T>> packetsList;


    public ExtractorDataFrame() {
        this.packetsList = new ArrayList<>();    
    }

    public ExtractorDataFrame(List<List<T>> packetsList) {
        this.packetsList = packetsList;

    }

    public List<List<T>> getPackets(String file)  {
        // Your logic to extract packets goes here
        // Return a list of packets of type T
        return packetsList;
    }
}
