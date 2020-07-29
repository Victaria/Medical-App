package by.elinext.victory.medical.base.room;

import by.elinext.victory.medical.base.booking.Booking;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class RoomService {
    @Inject
    private RoomRepository roomRepository;

    public Room getById(int id) {
        return roomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No room with such id."));
    }

    public List<Room> getFreeRooms() {
        return roomRepository.findFreeRooms();
    }

}
