package by.elinext.victory.medical.base.room;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class RoomTypeService {

    @Inject
    private RoomTypeRepository roomTypeRepository;

    public RoomType getById(int id) {
        return roomTypeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No room's type with such id."));
    }
}
