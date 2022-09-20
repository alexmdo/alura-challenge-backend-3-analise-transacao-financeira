package br.com.alexmdo.txanalyser.controller;

import br.com.alexmdo.txanalyser.controller.dto.UserDto;
import br.com.alexmdo.txanalyser.controller.form.NewUserForm;
import br.com.alexmdo.txanalyser.model.User;
import br.com.alexmdo.txanalyser.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String goToHome(Model model) {
        model.addAttribute("newUserForm", new NewUserForm(null, null));
        model.addAttribute("users", User.toDto(userService.findAll()));
        return "users/home";
    }

    @PostMapping("/new")
    public String newUser(@Valid NewUserForm newUserForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/users/home";
        }

        User user = newUserForm.toModel();
        userService.save(user);
        return "redirect:/users/home";
    }

}
