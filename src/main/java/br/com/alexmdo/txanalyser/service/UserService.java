package br.com.alexmdo.txanalyser.service;

import br.com.alexmdo.txanalyser.model.User;
import br.com.alexmdo.txanalyser.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        generateRandomPassword(user);
        return userRepository.save(user);
    }

    private void generateRandomPassword(User user) {
        String randomPassword = "12345";
        user.setPassword(new BCryptPasswordEncoder().encode("randomPassword"));
        System.out.printf("email sent with password %s for user %s%n", randomPassword, user.getUsername());
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}
