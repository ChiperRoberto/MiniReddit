package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.entity.Post;
import pl.coderslab.entity.User;
import pl.coderslab.entity.Forum;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Găsește un singur post după titlu exact.
     * Dacă există mai multe postări cu același titlu,
     * Spring Data JPA va returna prima găsită sau aruncă excepție (în funcție de setări).
     */
    Post findByTitle(String title);

    /**
     * Găsește toate postările care au exact un anumit titlu.
     */
    List<Post> findAllByTitle(String title);

    /**
     * Găsește toate postările la care titlul conține un anumit fragment (cuvânt cheie).
     */
    List<Post> findByTitleContaining(String partialTitle);

    /**
     * Găsește toate postările scrise de un anumit user.
     */
    List<Post> findByAuthor(User author);

    /**
     * Găsește toate postările scrise de un anumit user.
     */
    Post findById(long id);

    /**
     * Găsește toate postările dintr-un forum specific.
     */
    List<Post> findByForum(Forum forum);

    /**
     * Sterge o postare.
     */
    void deleteById(long id);

    /**
     * Query custom – caută postări dintr-un anumit forum
     * care conțin un anumit fragment în titlu.
     */
    @Query("SELECT p FROM Post p WHERE p.forum = :forum AND p.title LIKE %:titlePart%")
    List<Post> findByForumAndTitleContaining(@Param("forum") Forum forum,
                                             @Param("titlePart") String titlePart);
}