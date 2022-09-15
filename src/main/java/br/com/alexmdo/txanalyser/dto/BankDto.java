package br.com.alexmdo.txanalyser.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BankDto {

    @NotBlank
    private final String sourceBank;
    @NotBlank
    private final String sourceAgency;
    @NotBlank
    private final String sourceAccount;

}
