package br.com.alexmdo.txanalyser.repository;

import br.com.alexmdo.txanalyser.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

}