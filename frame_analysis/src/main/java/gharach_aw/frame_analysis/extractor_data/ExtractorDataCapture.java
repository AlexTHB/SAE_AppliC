package gharach_aw.frame_analysis.extractor_data;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;

import gharach_aw.frame_analysis.persistence.entity.Capture;

public class ExtractorDataCapture {

    private Capture capture;

    public void extractFileName (File file){
        String fileName = file.getName() ;
        capture.setFileName(fileName);    
    }
    
    public void extractFileDate(File file) throws IOException{
        Path path = FileSystems.getDefault().getPath(file.getAbsolutePath());
        BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
        Date fileDate = new Date(attributes.creationTime().toMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(fileDate);
        capture.setFileDate(formattedDate);
    }

    public Capture extractPropertiesCapture(File filePath) throws IOException{
        capture = new Capture();
        extractFileName(filePath);
        extractFileDate(filePath);
        return capture;

    }
}
