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

    public List<Booking> findUsersBookings(int userId){
        return bookingRepository.findByUserId(userId);
    }

    public Booking createAndSave(Booking booking) {

        booking.setStartDate(new Timestamp(Calendar.getInstance().getTime().getTime()));

        return bookingRepository.save(booking);
    }
}
