package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.entity.Forum;
import pl.coderslab.entity.Post;
import pl.coderslab.service.ForumService;
import pl.coderslab.service.PostService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/forums")
public class ForumController {

    private final ForumService forumService;
    private final PostService postService;

    // Constructor injection
    public ForumController(ForumService forumService, PostService postService) {
        this.forumService = forumService;
        this.postService = postService;
    }

    /**
     * 1. Afișează pagina cu lista de forumuri
     * /forums
     */
    @GetMapping
    public String listForums(@RequestParam(value = "keyword", required = false) String keyword, Model model)
    {

        List<Forum> forums;
        if (keyword != null && !keyword.isEmpty()) {
            forums = forumService.findByNameContaining(keyword);
        } else {
            forums = forumService.getAllForums();
        }
        model.addAttribute("forums", forums);
        return "forum-list"; // Thymeleaf template
    }

    /**
     * 2. Formular pentru crearea unui forum (GET)
     * /forums/create
     */
    @GetMapping("/create")
    public String showCreateForumForm(Model model) {
        model.addAttribute("forum", new Forum());
        return "forum-create"; // Thymeleaf template
    }

    /**
     * 2.1. Salvare forum (POST)
     * /forums/create
     */
    @PostMapping("/create")
    public String createForum(@ModelAttribute("forum") Forum forum) {
        // forum NU are autor, deci setAuthor(null) sau îl lași necompletat
        forumService.createForum(forum);
        return "redirect:/forums"; // după crearea forumului, mergi la listă
    }

    /**
     * 3. Pagină de detalii pentru un forum
     * /forums/{id}
     * - afișează forumul curent
     * - afișează lista postărilor din el
     */
    @GetMapping("/{id}")
    public String showForum(@PathVariable("id") Long id,@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        Forum forum = forumService.getForumById(id);
        if (forum == null) {
            // Dacă forumul nu există, redirect la listă
            return "redirect:/forums";
        }
        // Obținem postările forumului (fie direct forum.getPosts() dacă e lazy/eager,
        // fie postService.getPostsByForum(forum))
        List<Post> posts;
        if(keyword != null && !keyword.isEmpty()){
            posts = postService.findByTitleContaining(keyword);
        }else{
            posts = postService.findByForum(forum);
        }
        model.addAttribute("forum", forum);
        model.addAttribute("posts", posts);
        return "forum-detail"; // Thymeleaf template cu detalii forum
    }

    // ForumController.java
    @GetMapping("/{id}/edit")
    public String showEditForumForm(@PathVariable("id") Long forumId, Model model) {
        Forum forum = forumService.getForumById(forumId);
        if (forum == null) {
            return "redirect:/forums";
        }
        model.addAttribute("forum", forum);
        return "forum-edit"; // JSP-ul cu formularul de editare
    }

    @PostMapping("/{id}/edit")
    public String editForum(@PathVariable("id") Long forumId,
                            @ModelAttribute("forum") Forum updatedForum) {
        Forum dbForum = forumService.getForumById(forumId);
        if (dbForum == null) {
            return "redirect:/forums";
        }
        // actualizăm doar câmpurile care te interesează
        dbForum.setName(updatedForum.getName());
        // ... alte câmpuri, dacă aveai autor etc.
        forumService.updateForum(dbForum);

        return "redirect:/forums/" + forumId;
    }


    @GetMapping("/{id}/delete")
    public String deleteForum(@PathVariable("id") Long id) {
        forumService.deleteForumById(id);
        return "redirect:/forums";
    }

    /**
     * 4. Formular pentru crearea unei postări
     * /forums/{id}/posts/create (GET)
     */
    @GetMapping("/{id}/posts/create")
    public String showCreatePostForm(@PathVariable("id") Long forumId, Model model) {
        Forum forum = forumService.getForumById(forumId);
        if (forum == null) {
            return "redirect:/forums";
        }
        Post post = new Post();
        // asociem forumul deja, ca să îl afișăm în formular dacă vrei
        post.setForum(forum);

        model.addAttribute("post", post);
        return "post-create"; // Thymeleaf template
    }

    /**
     * 4.1. Salvare postare (POST)
     * /forums/{id}/posts/create
     */
    // 4.1) Salvare postare (title + content)
    @PostMapping("/{forumId}/posts/create")
    public String createPost(@PathVariable("forumId") Long forumId,
                             @RequestParam("title") String title,
                             @RequestParam("content") String content) {
        System.out.println("DEBUG - Title: " + title);
        System.out.println("DEBUG - Content: " + content);

        Forum forum = forumService.getForumById(forumId);
        if (forum == null) {
            return "redirect:/forums";
        }
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setForum(forum);

        postService.createPost(post);
        return "redirect:/forums/" + forumId;
    }

    @GetMapping("/{forumId}/posts/{postId}/delete")
    public String deletePost(@PathVariable Long forumId, @PathVariable Long postId) {
        postService.deletePostById(postId);
        return "redirect:/forums/" + forumId;
    }


    @GetMapping("/{forumId}/posts/{postId}/edit")
    public String showEditPostForm(@PathVariable Long forumId,
                                   @PathVariable Long postId,
                                   Model model) {
        Forum forum = forumService.getForumById(forumId);
        if (forum == null) {
            return "redirect:/forums";
        }
        Post post = postService.getPostById(postId);
        if (post == null) {
            return "redirect:/forums/" + forumId;
        }
        model.addAttribute("forum", forum);
        model.addAttribute("post", post);
        return "post-edit"; // JSP cu formularul de editare
    }

    @PostMapping("/{forumId}/posts/{postId}/edit")
    public String editPost(@PathVariable Long forumId,
                           @PathVariable Long postId,
                           @ModelAttribute("post") Post updatedPost) {
        Forum forum = forumService.getForumById(forumId);
        if (forum == null) {
            return "redirect:/forums";
        }
        Post dbPost = postService.getPostById(postId);
        if (dbPost == null) {
            return "redirect:/forums/" + forumId;
        }

        dbPost.setTitle(updatedPost.getTitle());
        dbPost.setContent(updatedPost.getContent());
        // restul câmpurilor
        postService.updatePost(dbPost);

        return "redirect:/forums/" + forumId;
    }

    // Upload imagine Quill
    @PostMapping("{forumId}/upload")
    @ResponseBody
    public Map<String, String> handleImageUpload(@RequestParam("file") MultipartFile file,@PathVariable Long forumId) {
        Forum forum = forumService.getForumById(forumId);
        try {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isEmpty()) {
                throw new RuntimeException("Numele fișierului este gol!");
            }
            // Salvezi local
            Path path = Paths.get("uploads/" + originalFilename);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // URL accesibil (ex: /uploads/poza.png),
            // asigură-te că ai configurat resource handling la "/uploads/**"
            String fileUrl = "/uploads/" + originalFilename;

            // Returnezi JSON cu { "url": "..." }
            Map<String, String> response = new HashMap<>();
            response.put("url", fileUrl);
            return response;

        } catch (IOException e) {
            throw new RuntimeException("Eroare la salvarea fișierului: " + e.getMessage(), e);
        }
    }
}