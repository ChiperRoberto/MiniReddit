package pl.coderslab.service;

import org.springframework.stereotype.Service;
import pl.coderslab.entity.Post;
import pl.coderslab.entity.Reaction;
import pl.coderslab.entity.ReactionType;
import pl.coderslab.entity.User;
import pl.coderslab.repository.ReactionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReactionService {

    private final ReactionRepository reactionRepository;

    public ReactionService(ReactionRepository reactionRepository) {
        this.reactionRepository = reactionRepository;
    }

    public void addOrUpdateReaction(Post post, User user, ReactionType type) {
        Optional<Reaction> existingReaction = reactionRepository.findByUserAndPost(user, post);

        if (existingReaction.isPresent()) {
            Reaction reaction = existingReaction.get();
            if (!reaction.getType().equals(type)) {
                reaction.setType(type); // schimbăm tipul reacției
                reactionRepository.save(reaction);
            }
        } else {
            Reaction reaction = new Reaction();
            reaction.setPost(post);
            reaction.setUser(user);
            reaction.setType(type);
            reactionRepository.save(reaction);
        }
    }

    public long countReactions(Post post, ReactionType type) {
        List<Reaction> reactions = reactionRepository.findByPost(post);
        return reactions.stream().filter(r -> r.getType() == type).count();
    }

    public Optional<Reaction> getUserReactionForPost(Post post, User user) {
        return reactionRepository.findByUserAndPost(user, post);
    }
}