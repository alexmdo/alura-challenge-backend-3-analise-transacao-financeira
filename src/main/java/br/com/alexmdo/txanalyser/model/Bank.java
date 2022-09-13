package br.com.alexmdo.txanalyser.model;

import lombok.Data;

@Data
public class Bank {

    private final String sourceBank;
    private final String sourceAgency;
    private final String sourceAccount;

}
