package by.elinext.victory.medical.base.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class PositionService {
    @Inject
    private PositionRepository positionRepository;

    public Position getById(int id) {
        return positionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No position with such id."));
    }

    public List<Position> getAllPositions(){
        return positionRepository.findAll();
    }
}
