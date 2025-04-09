package pl.coderslab.service;

import org.springframework.stereotype.Service;
import pl.coderslab.entity.Comment;
import pl.coderslab.entity.Post;
import pl.coderslab.repository.CommentRepository;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsForPost(Post post) {
        return commentRepository.findByPost(post);
    }
}