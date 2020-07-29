package by.elinext.victory.medical.base.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    @Query(value = "SELECT * FROM booking b WHERE b.user_id = ?1", nativeQuery = true)
    List<Booking> findByUserId(int userId);

    @Query(value = "SELECT * FROM booking b WHERE (((NOW()) BETWEEN b.start_date AND b.end_date) OR (b.start_date BETWEEN ?1 AND ?2 )) AND b.room_id = ?3", nativeQuery = true)
    List<Booking> isRoomFree(Timestamp startTime, Timestamp endTime, Integer id);

    @Query(value = "SELECT * FROM booking b WHERE (((NOW()) BETWEEN b.start_date AND b.end_date) OR (b.start_date BETWEEN ?1 AND ?2 )) AND b.user_id = ?3", nativeQuery = true)
    List<Booking> isUserFree(Timestamp startTime, Timestamp endTime, Integer id);
}
