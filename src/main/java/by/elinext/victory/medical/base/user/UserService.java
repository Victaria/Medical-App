package by.elinext.victory.medical.base.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class UserService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private PasswordEncoder bcryptEncoder;

    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No user with such id."));
    }

    public User save(User user) {
        if (!user.getFirstname().isEmpty() && !user.getUsername().isEmpty() && !user.getLastname().isEmpty()) {
            user.setPassword(bcryptEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        return null;
    }
}
