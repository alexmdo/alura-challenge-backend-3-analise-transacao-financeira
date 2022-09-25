package br.com.alexmdo.txanalyser.controller.dto;

public enum MovimentTypeDto {

    ENTRADA("Entrada"), SAIDA("Saída");

    private final String description;

    MovimentTypeDto(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
