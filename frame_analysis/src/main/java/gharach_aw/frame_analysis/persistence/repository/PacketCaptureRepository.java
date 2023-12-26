package gharach_aw.frame_analysis.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gharach_aw.frame_analysis.persistence.entity.PacketCapture;

public interface PacketCaptureRepository extends JpaRepository<PacketCapture, Long>{

}