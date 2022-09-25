package br.com.alexmdo.txanalyser.controller.dto;

import java.math.BigDecimal;

public record SuspectAgencyDto(BankDto bank, BigDecimal amount, MovimentTypeDto movimentType) {
}
