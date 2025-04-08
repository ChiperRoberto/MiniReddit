package pl.coderslab.repository;


import pl.coderslab.entity.Forum;
import pl.coderslab.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class ForumDao {

    @Autowired
    private EntityManager entityManager;

    public void saveUser(Forum forum) {
        entityManager.persist(forum);
    }

}