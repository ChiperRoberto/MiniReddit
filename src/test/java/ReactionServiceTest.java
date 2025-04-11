import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.coderslab.entity.*;
import pl.coderslab.repository.ReactionRepository;
import pl.coderslab.service.ReactionService;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReactionServiceTest {

    @Mock
    private ReactionRepository reactionRepository;

    @InjectMocks
    private ReactionService reactionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddNewReaction() {
        Post post = new Post();
        User user = new User();
        ReactionType type = ReactionType.LIKE;

        when(reactionRepository.findByUserAndPost(user, post)).thenReturn(Optional.empty());

        reactionService.addOrUpdateReaction(post, user, type);

        verify(reactionRepository).save(any(Reaction.class));
    }

    @Test
    void testUpdateExistingReaction() {
        Post post = new Post();
        User user = new User();
        Reaction reaction = new Reaction();
        reaction.setType(ReactionType.SAD);

        when(reactionRepository.findByUserAndPost(user, post)).thenReturn(Optional.of(reaction));

        reactionService.addOrUpdateReaction(post, user, ReactionType.LIKE);

        verify(reactionRepository).save(reaction);
        assertEquals(ReactionType.LIKE, reaction.getType());
    }

    @Test
    void testCountReactions() {
        Post post = new Post();
        Reaction r1 = new Reaction(); r1.setType(ReactionType.LIKE);
        Reaction r2 = new Reaction(); r2.setType(ReactionType.LIKE);
        Reaction r3 = new Reaction(); r3.setType(ReactionType.SAD);

        when(reactionRepository.findByPost(post)).thenReturn(Arrays.asList(r1, r2, r3));

        long likeCount = reactionService.countReactions(post, ReactionType.LIKE);
        assertEquals(2, likeCount);
    }
}
