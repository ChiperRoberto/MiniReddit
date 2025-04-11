// Test pentru ForumService

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.coderslab.entity.Forum;
import pl.coderslab.entity.User;
import pl.coderslab.repository.ForumRepository;
import pl.coderslab.service.ForumService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ForumServiceTest {

    @Mock
    private ForumRepository forumRepository;

    @InjectMocks
    private ForumService forumService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateForum() {
        Forum forum = new Forum("Java", new User());
        when(forumRepository.save(forum)).thenReturn(forum);

        Forum result = forumService.createForum(forum);

        assertEquals("Java", result.getName());
    }

    @Test
    void testGetForumById() {
        Forum forum = new Forum();
        forum.setId(1L);
        when(forumRepository.findById(1L)).thenReturn(Optional.of(forum));

        Forum found = forumService.getForumById(1L);
        assertNotNull(found);
    }

    @Test
    void testGetAllForums() {
        when(forumRepository.findAll()).thenReturn(Arrays.asList(new Forum(), new Forum()));
        List<Forum> forums = forumService.getAllForums();
        assertEquals(2, forums.size());
    }

    @Test
    void testDeleteForum() {
        forumService.deleteForumById(1L);
        verify(forumRepository).deleteById(1L);
    }
}