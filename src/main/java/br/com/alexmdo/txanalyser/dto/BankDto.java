package br.com.alexmdo.txanalyser.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BankDto {

    @NotBlank
    private final String bank;
    @NotBlank
    private final String agency;
    @NotBlank
    private final String account;

}
