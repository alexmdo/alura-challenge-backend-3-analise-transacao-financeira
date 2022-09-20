package br.com.alexmdo.txanalyser.repository;

import br.com.alexmdo.txanalyser.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}