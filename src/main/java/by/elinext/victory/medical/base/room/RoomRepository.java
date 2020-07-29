package by.elinext.victory.medical.base.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query(value = "SELECT * FROM room r WHERE r.id NOT IN (SELECT room_id FROM booking b WHERE (NOW()) BETWEEN b.start_date AND b.end_date)", nativeQuery = true)
    List<Room> findFreeRooms();

}
