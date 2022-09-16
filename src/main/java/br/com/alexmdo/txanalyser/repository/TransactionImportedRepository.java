package br.com.alexmdo.txanalyser.repository;

import br.com.alexmdo.txanalyser.model.TransactionImported;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionImportedRepository extends JpaRepository<TransactionImported, Long> {
}