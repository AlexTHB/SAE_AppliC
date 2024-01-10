package gharach_aw.frame_analysis.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gharach_aw.frame_analysis.persistence.entity.PacketCapture;
/**
 * Repository interface for managing PacketCaptures in the database.
 * Extends the JpaRepository interface to inherit basic CRUD operations and additional
 * query methods provided by Spring Data JPA.
 *
 * This interface is designed to interact with the underlying data store to perform
 * operations related to PacketCaptures, such as saving, updating, and retrieving them.
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see PacketCapture
 * @see org.springframework.stereotype.Repository
 */
public interface PacketCaptureRepository extends JpaRepository<PacketCapture, Long>{

}