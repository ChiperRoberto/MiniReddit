// === CommentController.java ===
package pl.coderslab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.entity.Comment;
import pl.coderslab.entity.Post;
import pl.coderslab.entity.User;
import pl.coderslab.repository.PostRepository;
import pl.coderslab.repository.UserRepository;
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

    public record CommentResponse(String username, String content) {}

    @GetMapping
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }

        List<Comment> comments = commentService.getCommentsForPost(post);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{commentId}/delete")
    public String deleteComment(@PathVariable Long postId,
                                @PathVariable Long commentId,
                                @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {

        Comment comment = commentService.getCommentById(commentId);
        if (comment == null || principal == null) {
            return "redirect:/forums";
        }

        User author = userRepository.findByUsername(principal.getUsername());
        if (author == null || (!author.getId().equals(comment.getAuthor().getId()) && !author.getRole().equals("ROLE_ADMIN"))) {
            return "redirect:/forums";
        }

        commentService.deleteComment(commentId);
        return "redirect:/forums/" + comment.getPost().getForum().getId();
    }

    @GetMapping("/{commentId}/edit")
    public String showEditForm(@PathVariable Long postId,
                               @PathVariable Long commentId,
                               Model model,
                               @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {

        Comment comment = commentService.findById(commentId);
        if (comment == null || !comment.getPost().getId().equals(postId)) {
            return "redirect:/forums";
        }

        User currentUser = userRepository.findByUsername(principal.getUsername());
        if (!comment.getAuthor().getId().equals(currentUser.getId()) &&
                !currentUser.getRole().equals("ROLE_ADMIN")) {
            return "redirect:/forums/" + postId;
        }

        model.addAttribute("comment", comment);
        model.addAttribute("postId", postId);
        return "comment-edit";
    }

    @PostMapping("/{commentId}/edit")
    public String updateComment(@PathVariable Long postId,
                                @PathVariable Long commentId,
                                @RequestParam String content,
                                @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {

        Comment comment = commentService.findById(commentId);
        if (comment == null || !comment.getPost().getId().equals(postId)) {
            return "redirect:/forums";
        }

        User currentUser = userRepository.findByUsername(principal.getUsername());
        if (!comment.getAuthor().getId().equals(currentUser.getId()) &&
                !currentUser.getRole().equals("ROLE_ADMIN")) {
            return "redirect:/forums/" + postId;
        }

        comment.setContent(content);
        commentService.addComment(comment);
        return "redirect:/forums/" + comment.getPost().getForum().getId();
    }
}