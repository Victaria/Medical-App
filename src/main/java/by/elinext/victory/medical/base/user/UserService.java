package by.elinext.victory.medical.base.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class UserService {

    @Inject
    private UserRepository userRepository;

    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No user with such id."));
    }
}
