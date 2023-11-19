package gharach_aw.frame_analysis;


import java.io.File;
import java.io.IOException;

import gharach_aw.frame_analysis.extractor_data.ExtractorDataCapture;
import gharach_aw.frame_analysis.persistence.entity.Capture;


public class ExtractorDataCaptureTest {
      public static void main(String[] args) {

        ExtractorDataCapture extractorCapture = new ExtractorDataCapture();
        File filePath = new File("src\\test\\java\\gharach_aw\\frame_analysis\\test.json");
        try {
            Capture capture = extractorCapture.extractPropertiesCapture(filePath);
            System.out.println(capture.getFileName());
            System.out.println(capture.getFileDate());


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
