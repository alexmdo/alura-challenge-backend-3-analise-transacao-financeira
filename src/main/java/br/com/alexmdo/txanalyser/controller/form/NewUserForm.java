package br.com.alexmdo.txanalyser.controller.form;

import br.com.alexmdo.txanalyser.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record NewUserForm(@NotBlank String name, @NotBlank @Email String email) {

    public User toModel() {
        return new User(null, this.email, null, true, this.name);
    }

}
