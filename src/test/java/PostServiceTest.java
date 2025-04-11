// Test pentru PostService
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.coderslab.entity.Forum;
import pl.coderslab.entity.Post;
import pl.coderslab.repository.PostRepository;
import pl.coderslab.service.PostService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePost() {
        Post post = new Post();
        post.setTitle("Test");
        when(postRepository.save(post)).thenReturn(post);

        Post result = postService.createPost(post);
        assertEquals("Test", result.getTitle());
    }


    @Test
    void testFindByForum() {
        Forum forum = new Forum();
        when(postRepository.findByForum(forum)).thenReturn(Arrays.asList(new Post(), new Post()));

        List<Post> posts = postService.findByForum(forum);
        assertEquals(2, posts.size());
    }
}
