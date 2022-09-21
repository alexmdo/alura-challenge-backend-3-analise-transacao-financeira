package br.com.alexmdo.txanalyser.repository;

import br.com.alexmdo.txanalyser.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}