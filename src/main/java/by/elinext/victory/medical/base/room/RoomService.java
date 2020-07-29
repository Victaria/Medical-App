package by.elinext.victory.medical.base.room;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class RoomService {
    @Inject
    private RoomRepository roomRepository;

    public Room getById(int id) {
        return roomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No room with such id."));
    }

}
