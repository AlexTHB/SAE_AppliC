package gharach_aw.frame_analysis;

import java.util.List;

import ExtractorData.ExtractorDataFramePcap;

public class ExtractorDataFramePcapTest {
    public ExtractorDataFramePcapTest () {
     String file = "src\\test\\java\\gharach_aw\\frame_analysis\\test.pcapng";
        ExtractorDataFramePcap extractor = new ExtractorDataFramePcap();
        List<List<Object>> packets = extractor.getPackets(file);
        // Print the nested list
        for (List<Object> packet : packets) {
            System.out.println(packet);
        }
    }

    public static void main(String[] ags) throws Exception {
        ExtractorDataFramePcapTest a = new ExtractorDataFramePcapTest();
    }
}