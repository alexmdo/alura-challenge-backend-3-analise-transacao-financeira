package br.com.alexmdo.txanalyser.model.dto;

import java.math.BigDecimal;

public record SuspectAgencyDto(String bank, String agency, BigDecimal amount, String movimentType) {
}
