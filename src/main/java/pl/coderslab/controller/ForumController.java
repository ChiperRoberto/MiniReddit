package pl.coderslab.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.entity.Forum;
import pl.coderslab.entity.Post;
import pl.coderslab.entity.User;
import pl.coderslab.repository.UserRepository;
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
import java.util.UUID;

@Controller
@RequestMapping("/forums")
public class ForumController {

    private final ForumService forumService;
    private final PostService postService;
    private final UserRepository userRepository; // Avem nevoie de el pentru a găsi userul

    // Constructor injection
    public ForumController(ForumService forumService,
                           PostService postService,
                           UserRepository userRepository) {
        this.forumService = forumService;
        this.postService = postService;
        this.userRepository = userRepository;
    }

    /**
     * 1. Afișează pagina cu lista de forumuri
     * /forums
     */
    @GetMapping
    public String listForums(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Forum> forums;
        if (keyword != null && !keyword.isEmpty()) {
            forums = forumService.findByNameContaining(keyword);
        } else {
            forums = forumService.getAllForums();
        }
        model.addAttribute("forums", forums);
        return "forum-list";
    }

    /**
     * 2. Formular pentru crearea unui forum (GET)
     * /forums/create
     */
    @GetMapping("/create")
    public String createForum(@ModelAttribute("forum") @Valid Forum forum,
                              BindingResult result,
                              @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {
        if (result.hasErrors()) {
            return "forum-create";
        }

        User dbUser = userRepository.findByUsername(principal.getUsername());
        forum.setAuthor(dbUser);
        forumService.createForum(forum);

        return "redirect:/forums";
    }

    /**
     * 2.1. Salvare forum (POST)
     * /forums/create
     * - setăm autorul forumului ca fiind userul logat
     */
    @PostMapping("/create")
    public String createForum(@ModelAttribute("forum") Forum forum,
                              @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {
        if (principal == null) {
            // userul nu e logat - redirect la /login sau aruncă excepție
            return "redirect:/login";
        }

        // Căutăm entitatea user în DB
        String username = principal.getUsername();
        User dbUser = userRepository.findByUsername(username);
        if (dbUser == null) {
            return "redirect:/login"; // fallback, user inexistent
        }

        // Setăm autor
        forum.setAuthor(dbUser);
        forumService.createForum(forum);
        return "redirect:/forums";
    }

    /**
     * 3. Pagină de detalii pentru un forum
     * /forums/{id}
     */
    @GetMapping("/{id}")
    public String showForum(@PathVariable("id") Long id,
                            @RequestParam(value = "keyword", required = false) String keyword,
                            Model model,
                            @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal,
                            HttpSession session) {
        Forum forum = forumService.getForumById(id);
        if (forum == null) {
            return "redirect:/forums";
        }
        List<Post> posts;
        if (keyword != null && !keyword.isEmpty()) {
            posts = postService.findByTitleContaining(keyword);
        } else {
            posts = postService.findByForum(forum);
        }
        model.addAttribute("forum", forum);
        model.addAttribute("posts", posts);

        // Set currentUser in session
        if (principal != null) {
            String username = principal.getUsername();
            User dbUser = userRepository.findByUsername(username);
            if (dbUser != null) {
                session.setAttribute("currentUser", dbUser);
            }
        }

        return "forum-detail";
    }

    // ForumController.java
    @GetMapping("/{id}/edit")
    public String showEditForumForm(@PathVariable("id") Long forumId,
                                    @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal,
                                    Model model) {

        Forum forum = forumService.getForumById(forumId);
        if (forum == null) {
            return "redirect:/forums";
        }

        // Verificăm autorul
        if (!canEditForum(forum, principal)) {
            throw new AccessDeniedException("Nu ai dreptul să editezi acest forum!");
        }

        model.addAttribute("forum", forum);
        return "forum-edit";
    }

    @PostMapping("/{id}/edit")
    public String editForum(@PathVariable("id") Long forumId,
                            @ModelAttribute("forum") Forum updatedForum,
                            @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {

        Forum dbForum = forumService.getForumById(forumId);
        if (dbForum == null) {
            return "redirect:/forums";
        }

        // Verificăm autorul
        if (!canEditForum(dbForum, principal)) {
            throw new AccessDeniedException("Nu ai dreptul să editezi acest forum!");
        }

        dbForum.setName(updatedForum.getName());
        forumService.updateForum(dbForum);
        return "redirect:/forums/" + forumId;
    }

    @GetMapping("/{id}/delete")
    public String deleteForum(@PathVariable("id") Long id,
                              @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {
        Forum forum = forumService.getForumById(id);
        if (forum == null) {
            return "redirect:/forums";
        }

        // Verificăm autorul
        if (!canEditForum(forum, principal)) {
            throw new AccessDeniedException("Nu ai dreptul să ștergi acest forum!");
        }

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
        post.setForum(forum);
        model.addAttribute("post", post);
        return "post-create";
    }

    /**
     * 4.1. Salvare postare (POST)
     * /forums/{id}/posts/create
     */
    @PostMapping("/{forumId}/posts/create")
    public String createPost(@PathVariable("forumId") Long forumId,
                             @RequestParam("title") String title,
                             @RequestParam("content") String content,
                             @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {

        Forum forum = forumService.getForumById(forumId);
        if (forum == null) {
            return "redirect:/forums";
        }
        if (principal == null) {
            return "redirect:/login";
        }

        // Găsim user
        String username = principal.getUsername();
        User dbUser = userRepository.findByUsername(username);
        if (dbUser == null) {
            return "redirect:/login";
        }

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setForum(forum);
        post.setAuthor(dbUser);

        postService.createPost(post);
        return "redirect:/forums/" + forumId;
    }

    @GetMapping("/{forumId}/posts/{postId}/delete")
    public String deletePost(@PathVariable Long forumId,
                             @PathVariable Long postId,
                             @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {
        Forum forum = forumService.getForumById(forumId);
        if (forum == null) {
            return "redirect:/forums";
        }

        Post post = postService.getPostById(postId);
        if (post == null) {
            return "redirect:/forums/" + forumId;
        }

        // Verificăm autorul postării
        if (!canEditPost(post, principal)) {
            throw new AccessDeniedException("Nu ai dreptul să ștergi această postare!");
        }

        postService.deletePostById(postId);
        return "redirect:/forums/" + forumId;
    }

    @GetMapping("/{forumId}/posts/{postId}/edit")
    public String showEditPostForm(@PathVariable Long forumId,
                                   @PathVariable Long postId,
                                   Model model,
                                   @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {

        Forum forum = forumService.getForumById(forumId);
        if (forum == null) {
            return "redirect:/forums";
        }
        Post post = postService.getPostById(postId);
        if (post == null) {
            return "redirect:/forums/" + forumId;
        }

        // Verificăm autor post
        if (!canEditPost(post, principal)) {
            throw new AccessDeniedException("Nu ai dreptul să editezi această postare!");
        }

        model.addAttribute("forum", forum);
        model.addAttribute("post", post);
        return "post-edit";
    }

    @PostMapping("/{forumId}/posts/{postId}/edit")
    public String editPost(@PathVariable Long forumId,
                           @PathVariable Long postId,
                           @ModelAttribute("post") Post updatedPost,
                           @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {

        Forum forum = forumService.getForumById(forumId);
        if (forum == null) {
            return "redirect:/forums";
        }
        Post dbPost = postService.getPostById(postId);
        if (dbPost == null) {
            return "redirect:/forums/" + forumId;
        }

        // Verificăm autor post
        if (!canEditPost(dbPost, principal)) {
            throw new AccessDeniedException("Nu ai dreptul să editezi această postare!");
        }

        dbPost.setTitle(updatedPost.getTitle());
        dbPost.setContent(updatedPost.getContent());
        postService.updatePost(dbPost);

        return "redirect:/forums/" + forumId;
    }

    @PostMapping("{forumId}/upload")
    @ResponseBody
    public Map<String, String> handleImageUpload(@RequestParam("file") MultipartFile file,
                                                 @PathVariable Long forumId) {
        Forum forum = forumService.getForumById(forumId);
        if (forum == null) {
            throw new RuntimeException("Forum inexistent!");
        }

        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Fișierul este gol!");
            }

            if (!file.getContentType().startsWith("image/")) {
                throw new RuntimeException("Fișierul nu este o imagine validă!");
            }

            Path uploadDir = Paths.get("uploads");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFilename = UUID.randomUUID() + extension;
            Path filePath = uploadDir.resolve(uniqueFilename);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String fileUrl = "/uploads/" + uniqueFilename;
            Map<String, String> response = new HashMap<>();
            response.put("url", fileUrl);
            return response;

        } catch (IOException e) {
            throw new RuntimeException("Eroare la salvarea fișierului: " + e.getMessage(), e);
        }
    }

    // --------------------------------------------------------------
    // Metode private de verificare a autorului (sau admin)

    private boolean canEditForum(Forum forum,
                                 org.springframework.security.core.userdetails.User principal) {
        if (principal == null) return false;
        String username = principal.getUsername();
        User dbUser = userRepository.findByUsername(username);
        if (dbUser == null) return false;

        boolean isAuthor = dbUser.getId().equals(forum.getAuthor().getId());
        boolean isAdmin  = "ROLE_ADMIN".equals(dbUser.getRole());
        return (isAuthor || isAdmin);
    }

    private boolean canEditPost(Post post,
                                org.springframework.security.core.userdetails.User principal) {
        if (principal == null) return false;
        String username = principal.getUsername();
        User dbUser = userRepository.findByUsername(username);
        if (dbUser == null) return false;

        boolean isAuthor = dbUser.getId().equals(post.getAuthor().getId());
        boolean isAdmin  = "ROLE_ADMIN".equals(dbUser.getRole());
        return (isAuthor || isAdmin);
    }
}