package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.entity.Forum;
import pl.coderslab.entity.User;

import java.util.List;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {

    /**
     * Găsește un forum după numele exact.
     */
    Forum findByName(String name);

    /**
     * Găsește toate forumurile a căror denumire conține textul 'partialName'.
     */
    List<Forum> findByNameContaining(String partialName);

    /**
     * Găsește toate forumurile create de un anumit user (autor).
     */
    List<Forum> findByAuthor(User author);

    /**
     * Sterge un forum dupa id si toate postarile asociate forumului
     */
    void deleteById(Long id);

    /**
     * Query custom – caută forumuri a căror denumire începe cu un prefix dat,
     * ordonându-le descendent după id.
     */
    @Query("SELECT f FROM Forum f WHERE f.name LIKE :prefix% ORDER BY f.id DESC")
    List<Forum> findByNameStartingWithOrderByIdDesc(@Param("prefix") String prefix);
}