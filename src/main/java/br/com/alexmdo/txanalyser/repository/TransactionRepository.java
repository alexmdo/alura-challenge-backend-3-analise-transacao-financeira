package br.com.alexmdo.txanalyser.repository;

import br.com.alexmdo.txanalyser.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByTransactionDate(LocalDateTime transactionDate);

}