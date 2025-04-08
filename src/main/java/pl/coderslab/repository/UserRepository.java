package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Găsește un singur user după username (nume de utilizator).
     * Dacă există mai mulți cu același username, va returna primul (sau aruncă excepție
     * dacă e configurat altfel).
     */
    User findByUsername(String username);

    /**
     * Găsește un singur user după email.
     * Email-ul e unic, deci de obicei vei obține un singur user.
     */
    User findByEmail(String email);

    /**
     * Găsește toți userii la care 'username' conține un anumit substring.
     * Exemplu: findByUsernameContaining("abc")
     * va returna userii cu 'username' care include "abc".
     */
    List<User> findByUsernameContaining(String substring);

    /**
     * Metodă custom cu JPQL: caută useri unde câmpul 'details' conține un anumit keyword.
     */
    @Query("SELECT u FROM User u WHERE u.details LIKE %:keyword%")
    List<User> findByDetailsContaining(@Param("keyword") String keyword);

}