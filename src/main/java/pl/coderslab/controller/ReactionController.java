package pl.coderslab.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.entity.Post;
import pl.coderslab.entity.ReactionType;
import pl.coderslab.entity.User;
import pl.coderslab.repository.PostRepository;
import pl.coderslab.repository.UserRepository;
import pl.coderslab.service.ReactionService;

@Controller
@RequestMapping("/posts")
public class ReactionController {

    private final ReactionService reactionService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public ReactionController(ReactionService reactionService,
                              PostRepository postRepository,
                              UserRepository userRepository) {
        this.reactionService = reactionService;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    /**
     * Adaugă sau actualizează o reacție a utilizatorului logat la postare
     */
    @PostMapping("/{postId}/react")
    public String reactToPost(@PathVariable Long postId,
                              @RequestParam("type") ReactionType type,
                              @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        // Caută postarea și userul real (din baza de date)
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return "redirect:/forums"; // fallback
        }

        User dbUser = userRepository.findByUsername(principal.getUsername());
        if (dbUser == null) {
            return "redirect:/login";
        }

        // Adaugă sau actualizează reacția
        reactionService.addOrUpdateReaction(post, dbUser, type);

        return "redirect:/forums/" + post.getForum().getId();
    }
}