package pl.coderslab.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.entity.User;
import pl.coderslab.service.UserService;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // Obiect gol, binding cu formularul
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                                      Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userService.registerNewUserAccount(user);
        } catch (IllegalArgumentException e) {
            // Dăm un mesaj de eroare în pagină
            model.addAttribute("errorMessage", e.getMessage());
            return "register"; // reafișăm formularul
        }
        // După ce se înregistrează cu succes, redirect la pagina de login
        return "redirect:/login?registerSuccess";
    }
}