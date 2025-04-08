package pl.coderslab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.entity.User;
import pl.coderslab.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService() {
    }

    /**
     * Metodă dedicată pentru înregistrare, inspirată de Baeldung.
     */
    public User registerNewUserAccount(User user) {
        // Verificăm dacă există deja un utilizator cu acest email.
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Există deja un utilizator cu acest email.");
        }

        // Verificăm dacă există deja un utilizator cu acest username.
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Există deja un utilizator cu acest username.");
        }

        // Criptăm parola
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Setăm rolul implicit (dacă e gol)
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("ROLE_USER");
        }

        // Salvăm utilizatorul în DB
        return userRepository.save(user);
    }

    /**
     * Crează (sau salvează) un user - exemplu simplu.
     * Folosit dacă vrei să adaugi useri altfel decât prin "înregistrare".
     */
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Găsește un user după ID. Returnează null dacă nu există.
     */
    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    /**
     * Returnează toți userii din baza de date.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Actualizează userul existent (dacă există).
     */
    public User updateUser(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User ID must not be null for update");
        }
        // Poți face validări suplimentare
        return userRepository.save(user);
    }

    /**
     * Șterge userul cu ID-ul dat.
     */
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    // ----- Metode personalizate -----

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getUsersByUsernameContaining(String substring) {
        return userRepository.findByUsernameContaining(substring);
    }
}