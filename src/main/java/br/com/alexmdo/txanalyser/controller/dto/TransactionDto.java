package br.com.alexmdo.txanalyser.controller.dto;

import br.com.alexmdo.txanalyser.model.Transaction;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<Transaction> toModel(List<TransactionDto> transactionDtos) {
        return transactionDtos.stream().map(dto -> new Transaction(
                null,
                dto.getOriginBank().getBank(),
                dto.getOriginBank().getAgency(),
                dto.getOriginBank().getAccount(),
                dto.getDestinationBank().getBank(),
                dto.getDestinationBank().getAgency(),
                dto.getDestinationBank().getAccount(),
                dto.getAmount(),
                dto.transactionDate,
                null
        )).collect(Collectors.toList());
    }
}
