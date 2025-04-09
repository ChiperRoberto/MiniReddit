package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.entity.Reaction;
import pl.coderslab.entity.Post;
import pl.coderslab.entity.User;

import java.util.Optional;
import java.util.List;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    Optional<Reaction> findByUserAndPost(User user, Post post);
    List<Reaction> findByPost(Post post);
}