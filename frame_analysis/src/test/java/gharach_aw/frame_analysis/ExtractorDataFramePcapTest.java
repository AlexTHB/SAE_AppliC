package gharach_aw.frame_analysis;

import org.jnetpcap.PcapException;

import gharach_aw.frame_analysis.ExtractorData.ExtractorDataFramePcap;

public class ExtractorDataFramePcapTest {
        public ExtractorDataFramePcapTest () throws PcapException {
        String file = "src\\test\\java\\gharach_aw\\frame_analysis\\test.pcapng";
        ExtractorDataFramePcap extractor = new ExtractorDataFramePcap();
        extractor.main(file);
        }

    public static void main(String[] ags) throws Exception {
        ExtractorDataFramePcapTest a = new ExtractorDataFramePcapTest();
    }
}

