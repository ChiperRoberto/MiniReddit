package pl.coderslab.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.entity.User;
import pl.coderslab.repository.ForumRepository;
import pl.coderslab.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepo;
    private final ForumRepository forumRepo;

    public UserController(UserRepository userRepo, ForumRepository forumRepo) {
        this.userRepo = userRepo;
        this.forumRepo = forumRepo;
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication auth) {
        User user = userRepo.findByUsername(auth.getName());
        model.addAttribute("user", user);
        model.addAttribute("forums", forumRepo.findByAuthor(user));
        return "user-profile";
    }

    @PostMapping("/update-description")
    public String updateDescription(@RequestParam String description, Authentication auth) {
        User user = userRepo.findByUsername(auth.getName());
        user.setDetails(description);
        userRepo.save(user);
        return "redirect:/user/profile";
    }
}