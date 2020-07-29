package by.elinext.victory.medical.base.booking;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class BookingService {
    @Inject
    private BookingRepository bookingRepository;

    public Booking getById(String id) {
        return bookingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No booking with such name."));
    }
}
