package gharach_aw.frame_analysis.extractor_data;

import java.io.File;

import java.util.List;

import gharach_aw.frame_analysis.persistence.entity.Frame;

public interface ExtractorDataFrame {
    public List<Frame> framesExtract(File file);
}