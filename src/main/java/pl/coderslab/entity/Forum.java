package pl.coderslab.entity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "forums")
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Numele forumului este obligatoriu.")
    @Size(min = 3, max = 100, message = "Numele trebuie să aibă între 3 și 100 caractere.")
    private String name;

    // Autorul forumului
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    // Relația OneToMany cu Post: un forum poate avea mai multe postări
    // FetchType.LAZY e frecvent recomandat pentru colecții
//    Alternative la CascadeType.ALL
//
//    Dacă vrei să aplici doar anumite operațiuni, poți folosi:
//            •	CascadeType.PERSIST – Doar salvarea entității părinte va salva și copilul.
//            •	CascadeType.MERGE – Doar modificările asupra părintelui vor modifica și copilul.
//            •	CascadeType.REMOVE – Doar ștergerea părintelui va șterge și copilul.
//	          •	CascadeType.REFRESH – Doar reîncărcarea părintelui va reîncărca și copilul.
//	          •	CascadeType.DETACH – Dacă părintele este detașat, și copiii vor fi detașați.

    @OneToMany(mappedBy = "forum", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Post> posts;

    public Forum() {
    }

    public Forum(String name, User author) {
        this.name = name;
        this.author = author;
    }

    // Getters și Setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void setId(Long id) {
        this.id = id;
    }
}