package gharach_aw.frame_analysis.ExtractorData;


import org.jnetpcap.BpFilter;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapException;


public class ExtractorDataFramePcap extends ExtractorDataFrame<Object>{
    
    // public List<Object> getTimestamp(packet){

    // }

    public void main(String file) throws PcapException {
        try (Pcap pcap = Pcap.openOffline(file)) {

            BpFilter filter = pcap.compile("tcp", true);

            pcap.setFilter(filter);
        
        }
    }
}
