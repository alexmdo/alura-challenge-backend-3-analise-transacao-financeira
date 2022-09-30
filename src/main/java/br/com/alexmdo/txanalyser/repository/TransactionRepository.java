package br.com.alexmdo.txanalyser.repository;

import br.com.alexmdo.txanalyser.model.Transaction;
import br.com.alexmdo.txanalyser.model.dto.SuspectAccountDto;
import br.com.alexmdo.txanalyser.model.dto.SuspectAgencyDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByTransactionDate(LocalDateTime transactionDate);
    @Query(value = "select t from Transaction t where year(t.transactionDate) = :year and month(t.transactionDate) = :month and t.amount >= :amount")
    List<Transaction> findByAmountGreaterThanEqual(Integer year, Integer month, BigDecimal amount);

    @Query(value = "select new br.com.alexmdo.txanalyser.model.dto.SuspectAccountDto(t.sourceBank, t.sourceAgency, t.sourceAccount, sum(t.amount), 'SAIDA') from Transaction t where t.amount >= :amount and year(t.transactionDate) = :year and month(t.transactionDate) = :month group by t.sourceBank, t.sourceAgency, t.sourceAccount")
    List<SuspectAccountDto> findOutcomeSuspectAccountByAmountGreaterThanEqual(Integer year, Integer month, BigDecimal amount);

    @Query(value = "select new br.com.alexmdo.txanalyser.model.dto.SuspectAccountDto(t.destinationBank, t.destinationAgency, t.destinationAccount, sum(t.amount), 'ENTRADA') from Transaction t where t.amount >= :amount and year(t.transactionDate) = :year and month(t.transactionDate) = :month group by t.destinationBank, t.destinationAgency, t.destinationAccount")
    List<SuspectAccountDto> findIncomeSuspectAccountByAmountGreaterThanEqual(Integer year, Integer month, BigDecimal amount);

    @Query(value = "select new br.com.alexmdo.txanalyser.model.dto.SuspectAgencyDto(t.sourceBank, t.sourceAgency, sum(t.amount), 'SAIDA') from Transaction t where t.amount >= :amount and year(t.transactionDate) = :year and month(t.transactionDate) = :month group by t.sourceBank, t.sourceAgency")
    List<SuspectAgencyDto> findOutcomeSuspectAgencyByAmountGreaterThanEqual(Integer year, Integer month, BigDecimal amount);

    @Query(value = "select new br.com.alexmdo.txanalyser.model.dto.SuspectAgencyDto(t.destinationBank, t.destinationAgency, sum(t.amount), 'ENTRADA') from Transaction t where t.amount >= :amount and year(t.transactionDate) = :year and month(t.transactionDate) = :month group by t.destinationBank, t.destinationAgency")
    List<SuspectAgencyDto> findIncomeSuspectAgencyByAmountGreaterThanEqual(Integer year, Integer month, BigDecimal amount);

}