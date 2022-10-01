package br.com.alexmdo.txanalyser.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

public record BankDto(@NotBlank String bank, @NotBlank String agency, @NotBlank String account) {

}
