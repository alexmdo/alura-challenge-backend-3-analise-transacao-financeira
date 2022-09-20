package br.com.alexmdo.txanalyser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/home")
    public String goToLogin() {
        return "login/home";
    }

}
