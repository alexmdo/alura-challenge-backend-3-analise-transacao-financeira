package br.com.alexmdo.txanalyser.controller.form;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record SuspectTransactionForm(@DateTimeFormat(pattern = "yyyy-MM-dd") @NotNull LocalDate date) {
}
