package br.com.alexmdo.txanalyser.controller;

import br.com.alexmdo.txanalyser.controller.form.NewUserForm;
import br.com.alexmdo.txanalyser.model.User;
import br.com.alexmdo.txanalyser.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String goToHome(NewUserForm newUserForm, Model model) {
        model.addAttribute("users", User.toDto(userService.findAllExceptDefaultOrDisabledUserAndItSelf()));

        return "users/home";
    }

    @PostMapping("/save")
    public String saveUser(@Valid NewUserForm newUserForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/users/home";
        }

        try {
            User user = newUserForm.toModel();

            if (user.getId() == null) {
                userService.save(user);
            } else {
                userService.update(user);
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "/users/home";
        }

        return "redirect:/users/home";
    }

    @GetMapping( "/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("No user found!"));
        model.addAttribute("newUserForm", user.toForm());

        return "users/home";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);

        return "redirect:/users/home";
    }

}
