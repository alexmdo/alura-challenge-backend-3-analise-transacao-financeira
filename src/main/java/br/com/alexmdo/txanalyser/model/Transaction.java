package br.com.alexmdo.txanalyser.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Transaction {

    private final Bank originBank;
    private final Bank destinationBank;
    private final BigDecimal amount;
    private final LocalDateTime transactionDate;

}
