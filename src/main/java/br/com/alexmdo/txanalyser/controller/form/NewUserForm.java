package br.com.alexmdo.txanalyser.controller.form;

import br.com.alexmdo.txanalyser.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record NewUserForm(Long id, @NotBlank String name, @NotBlank @Email String email) {

    public User toModel() {
        return new User(id, this.email, null, true, this.name);
    }

}
