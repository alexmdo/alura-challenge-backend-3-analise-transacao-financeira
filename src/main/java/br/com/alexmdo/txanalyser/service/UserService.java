package br.com.alexmdo.txanalyser.service;

import br.com.alexmdo.txanalyser.model.Authority;
import br.com.alexmdo.txanalyser.model.User;
import br.com.alexmdo.txanalyser.repository.AuthorityRepository;
import br.com.alexmdo.txanalyser.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    public User save(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already registered!");
        }

        generateRandomPassword(user);
        authorityRepository.save(new Authority(user.getUsername(), "ROLE_ADM"));
        return userRepository.save(user);
    }

    private void generateRandomPassword(User user) {
        String randomPassword = getSixRandomNumber();
        user.setPassword(new BCryptPasswordEncoder().encode(randomPassword));
        System.out.printf("email sent with password %s for user %s%n", randomPassword, user.getUsername());
    }

    private String getSixRandomNumber() {
        Random rnd = new Random();
        String randomPassword = String.format("%06d", rnd.nextInt(999999));
        return randomPassword;
    }

    public List<User> findAllExceptDefaultUserAndItSelf() {
        List<User> users = userRepository.findAll();
        String loggedUsername = getLoggedUsername();
        users.removeIf(user -> user.getUsername().equals("admin@email.com.br") || loggedUsername.equals(user.getUsername()));
        return users;
    }

    private String getLoggedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
