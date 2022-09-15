package br.com.alexmdo.txanalyser.repository;

import br.com.alexmdo.txanalyser.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}