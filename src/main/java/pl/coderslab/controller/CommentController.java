package pl.coderslab.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import pl.coderslab.entity.User;
import pl.coderslab.repository.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.entity.Comment;
import pl.coderslab.entity.Post;
import pl.coderslab.repository.PostRepository;
import pl.coderslab.service.CommentService;

import java.util.List;

@Controller
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentController(CommentService commentService, PostRepository postRepository, UserRepository userRepository) {
        this.commentService = commentService;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public String addCommentForm(@PathVariable Long postId,
                                 @RequestParam("content") String content,
                                 @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {

        Post post = postRepository.findById(postId).orElse(null);
        if (post == null || principal == null) {
            return "redirect:/forums";
        }

        User author = userRepository.findByUsername(principal.getUsername());
        if (author == null) {
            return "redirect:/login";
        }

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setAuthor(author);
        comment.setContent(content);
        commentService.addComment(comment);

        return "redirect:/forums/" + post.getForum().getId();
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }

        List<Comment> comments = commentService.getCommentsForPost(post);
        return ResponseEntity.ok(comments);
    }
}