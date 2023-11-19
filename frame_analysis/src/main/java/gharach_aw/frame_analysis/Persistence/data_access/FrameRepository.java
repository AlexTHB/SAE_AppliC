package gharach_aw.frame_analysis.persistence.data_access;

import org.springframework.data.jpa.repository.JpaRepository;

import gharach_aw.frame_analysis.persistence.entity.Frame;

public interface FrameRepository extends JpaRepository<Frame, Long> {
    // Additional custom query methods can be added here
}
