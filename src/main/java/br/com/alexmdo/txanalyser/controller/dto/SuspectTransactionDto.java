package br.com.alexmdo.txanalyser.controller.dto;

import java.math.BigDecimal;

public record SuspectTransactionDto(BankDto sourceBank, BankDto destionationBank, BigDecimal totalAmount) {
}
