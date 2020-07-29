package by.elinext.victory.medical.base.room;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class RoomTypeService {

    @Inject
    private RoomTypeRepository roomTypeRepository;

    public RoomType getById(int id) {
        return roomTypeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No room's type with such id."));
    }

    public List<RoomType> getAllTypes() {
        return roomTypeRepository.findAll();
    }

    public RoomType save(RoomType roomType) {
        if (!roomType.getName().isEmpty()) {
            return roomTypeRepository.save(roomType);
        }
        return null;
    }
}
