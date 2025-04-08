package pl.coderslab.service;

import org.springframework.stereotype.Service;
import pl.coderslab.entity.Forum;
import pl.coderslab.entity.Post;
import pl.coderslab.repository.PostRepository;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    public List<Post> findByForum(Forum forum) {
        return postRepository.findByForum(forum);
    }

    public List<Post> findByTitleContaining(String title){
        return postRepository.findByTitleContaining(title);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

}