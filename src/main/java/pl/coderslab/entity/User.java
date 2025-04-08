package pl.coderslab.entity;

import jakarta.persistence.*;

import java.util.Optional;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // exemplu de nume de coloană, în general nu e obligatoriu dacă e identic cu atributul
    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // Adăugăm un câmp pentru detalii utilizator
    @Column
    private String details;

    public User() {
    }

    public User(String username, String email, String password, String details) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.details = details;
    }

    // Getters și Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}