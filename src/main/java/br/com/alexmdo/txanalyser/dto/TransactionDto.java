package br.com.alexmdo.txanalyser.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDto {

    @NotNull @Valid
    private final BankDto originBank;
    @NotNull @Valid
    private final BankDto destinationBank;
    @NotNull
    private final BigDecimal amount;
    @NotNull
    private final LocalDateTime transactionDate;

}
