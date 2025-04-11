
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.coderslab.entity.Comment;
import pl.coderslab.entity.Post;
import pl.coderslab.repository.CommentRepository;
import pl.coderslab.service.CommentService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddComment() {
        Comment comment = new Comment();
        comment.setContent("Test comment");
        when(commentRepository.save(comment)).thenReturn(comment);

        Comment result = commentService.addComment(comment);
        assertEquals("Test comment", result.getContent());
    }

    @Test
    void testGetCommentsForPost() {
        Post post = new Post();
        when(commentRepository.findByPost(post)).thenReturn(Arrays.asList(new Comment(), new Comment()));

        List<Comment> comments = commentService.getCommentsForPost(post);
        assertEquals(2, comments.size());
    }

    @Test
    void testDeleteComment() {
        commentService.deleteComment(1L);
        verify(commentRepository).deleteById(1L);
    }
}
