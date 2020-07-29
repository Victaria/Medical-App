package by.elinext.victory.medical.demo.base.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class UserService {

    @Inject
    private UserRepository userRepository;

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No user with such id."));
    }
}
