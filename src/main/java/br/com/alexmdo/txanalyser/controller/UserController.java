package br.com.alexmdo.txanalyser.controller;

import br.com.alexmdo.txanalyser.controller.form.NewUserForm;
import br.com.alexmdo.txanalyser.model.User;
import br.com.alexmdo.txanalyser.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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
        model.addAttribute("users", User.toDto(userService.findAllExceptDefaultUserAndItSelf()));
        return "users/home";
    }

    @PostMapping("/new")
    public String newUser(@Valid NewUserForm newUserForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/users/home";
        }

        try {
            User user = newUserForm.toModel();
            userService.save(user);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "/users/home";
        }

        return "redirect:/users/home";
    }

    @GetMapping( "/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        //TODO finalizar implementação da edição
        Optional<User> userOpt = userService.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            model.addAttribute("newUserForm", user.toForm());
        }
        model.addAttribute("users", User.toDto(userService.findAllExceptDefaultUserAndItSelf()));
        return "users/home";
    }

}
