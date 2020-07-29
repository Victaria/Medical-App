package by.elinext.victory.medical.base.booking;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class BookingService {

    @Inject
    private BookingRepository bookingRepository;

    public Booking getById(String id) {
        return bookingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No booking with such name."));
    }

    public List<Booking> findUsersBookings(int userId) {
        return bookingRepository.findByUserId(userId);
    }

    public Booking createAndSave(Booking booking) {

        booking.setStartDate(new Timestamp(Calendar.getInstance().getTime().getTime()));

        if (isRoomFreeAtTime(booking.getRoomID(), booking.getStartDate(), booking.getEndDate()) && isUserFreeAtTime(booking.getUserID(), booking.getStartDate(), booking.getEndDate())) {

            return bookingRepository.save(booking);
        }

        return null;
    }

    public Boolean isRoomFreeAtTime(Integer roomId, Timestamp startTime, Timestamp endTime) {

        return bookingRepository.isRoomFree(startTime, endTime, roomId).isEmpty();
    }

    public Boolean isUserFreeAtTime(Integer userId, Timestamp startTime, Timestamp endTime) {

        return bookingRepository.isUserFree(startTime, endTime, userId).isEmpty();
    }

    public Booking freeRoomNow(String id) {

        Booking booking = getById(id);
        Timestamp now = new Timestamp(Calendar.getInstance().getTime().getTime());

        if (booking.getStartDate().after(now)) {
            booking.setStartDate(now);
            booking.setEndDate(now);
        } else if (booking.getEndDate().before(now)) {
            return null;
        }

        return bookingRepository.save(booking);
    }
}
