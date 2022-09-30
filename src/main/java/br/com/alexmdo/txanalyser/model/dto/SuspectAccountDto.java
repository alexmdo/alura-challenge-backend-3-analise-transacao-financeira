package br.com.alexmdo.txanalyser.model.dto;

import java.math.BigDecimal;

public record SuspectAccountDto(String bank, String agency, String account, BigDecimal amount, String movimentType) {
}
