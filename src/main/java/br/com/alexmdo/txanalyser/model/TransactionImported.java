package br.com.alexmdo.txanalyser.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "transactions_imported")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionImported {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate transactionDate;
    private LocalDateTime importDate;

    @ManyToMany
    @JoinTable(
            name = "transactions_transactions_imported",
            joinColumns = @JoinColumn(name = "transaction_imported_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id")
    )
    private List<Transaction> transactions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Setter
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionImported that = (TransactionImported) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TransactionImported{" +
                "id=" + id +
                ", transactionDate=" + transactionDate +
                ", importDate=" + importDate +
                '}';
    }
}