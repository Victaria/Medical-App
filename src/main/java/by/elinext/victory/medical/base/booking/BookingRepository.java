package by.elinext.victory.medical.base.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    @Query(value = "SELECT * FROM booking b WHERE b.user_id = ?1", nativeQuery = true)
    List<Booking> findByUserId(int userId);
}
