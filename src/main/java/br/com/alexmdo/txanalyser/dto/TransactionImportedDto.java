package br.com.alexmdo.txanalyser.dto;

import br.com.alexmdo.txanalyser.model.TransactionImported;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record TransactionImportedDto(LocalDate transactionDate, LocalDateTime importDate, String name) {

    public static List<TransactionImportedDto> toDto(List<TransactionImported> transactionImporteds) {
        return transactionImporteds.stream().map(entity -> new TransactionImportedDto(
                entity.getTransactionDate(),
                entity.getImportDate(),
                entity.getUser().getName()
        )).collect(Collectors.toList());
    }
}
