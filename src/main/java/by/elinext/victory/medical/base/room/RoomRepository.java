package by.elinext.victory.medical.base.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {

   // @Query(value = "SELECT * FROM room r LEFT JOIN booking b ON b.room_id = r.id WHERE (b.room_id IS NULL) OR ((NOW() + INTERVAL 3 HOUR) NOT BETWEEN start_date AND end_date)", nativeQuery = true)
    @Query(value = "SELECT * FROM room r WHERE r.id NOT IN (SELECT room_id FROM booking b WHERE (NOW() + INTERVAL 3 HOUR) BETWEEN b.start_date AND b.end_date)", nativeQuery = true)
    List<Room> findFreeRooms();
}
