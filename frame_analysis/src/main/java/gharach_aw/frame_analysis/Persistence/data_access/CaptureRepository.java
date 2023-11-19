package gharach_aw.frame_analysis.persistence.data_access;


import org.springframework.data.jpa.repository.JpaRepository;

import gharach_aw.frame_analysis.persistence.entity.Capture;

public interface CaptureRepository extends JpaRepository<Capture, Integer> {
   
}