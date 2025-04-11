package pl.coderslab.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Optional;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Username-ul nu poate fi gol.")
    @Size(min = 3, max = 30, message = "Username-ul trebuie să aibă între 3 și 30 de caractere.")
    private String username;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Emailul nu poate fi gol.")
    @Email(message = "Emailul nu este valid.")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Parola nu poate fi goală.")
    @Size(min = 6, message = "Parola trebuie să aibă cel puțin 6 caractere.")
    private String password;

    // Adăugăm un câmp pentru detalii utilizator
    @Column
    private String details;

    @Column
    private String role;

    public User() {
    }

    public User(String username, String email, String password, String details, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.details = details;
        this.role = role;
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

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

}