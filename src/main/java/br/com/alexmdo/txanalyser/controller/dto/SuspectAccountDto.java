package br.com.alexmdo.txanalyser.controller.dto;

import java.math.BigDecimal;

public record SuspectAccountDto(BankDto bank, BigDecimal amount, MovimentTypeDto movimentType) {
}
