package pl.coderslab.service;

import org.springframework.stereotype.Service;
import pl.coderslab.entity.Forum;
import pl.coderslab.repository.ForumRepository;

import java.util.List;

@Service
public class ForumService {

    private final ForumRepository forumRepository;

    public ForumService(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    public Forum createForum(Forum forum) {
        return forumRepository.save(forum);
    }

    public Forum updateForum(Forum forum) {
        return forumRepository.save(forum);
    }

    public void deleteForumById(Long forumId) {
        forumRepository.deleteById(forumId);
    }

    public Forum getForumById(Long id) {
        return forumRepository.findById(id).orElse(null);
    }

    public List<Forum> getAllForums() {
        return forumRepository.findAll();
    }

    public List<Forum> findByNameContaining(String name) {
        return forumRepository.findByNameContaining(name);
    }
}