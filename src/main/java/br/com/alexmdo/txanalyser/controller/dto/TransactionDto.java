package br.com.alexmdo.txanalyser.controller.dto;

import br.com.alexmdo.txanalyser.model.Transaction;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record TransactionDto(@NotNull @Valid BankDto originBank, @NotNull @Valid BankDto destinationBank,
                             @NotNull BigDecimal amount, @NotNull LocalDateTime transactionDate) {

    public static List<Transaction> toModel(List<TransactionDto> transactionDtos) {
        return transactionDtos.stream().map(dto -> new Transaction(
                null,
                dto.originBank().bank(),
                dto.originBank().agency(),
                dto.originBank().account(),
                dto.destinationBank().bank(),
                dto.destinationBank().agency(),
                dto.destinationBank().account(),
                dto.amount(),
                dto.transactionDate,
                null
        )).collect(Collectors.toList());
    }
}
