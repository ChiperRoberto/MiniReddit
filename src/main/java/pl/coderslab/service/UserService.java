package pl.coderslab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.entity.User;
import pl.coderslab.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService() {
    }

    /**
     * Crează un nou user în baza de date.
     * Poți adăuga validări, criptarea parolei etc. înainte de save.
     */
    public User createUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
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
     * Actualizează userul existent (dacă există) și îl returnează.
     * Dacă userul nu există (după ID), returnează null sau poți arunca excepție.
     */
    public User updateUser(User user) {
        // aici poți face check dacă există deja user cu ID-ul respectiv
        if (user.getId() == null) {
            // fie arunci excepție, fie îl creezi
            throw new IllegalArgumentException("User ID must not be null for update");
        }

        // Poți face validări suplimentare, ex: email unic etc.
        return userRepository.save(user);
    }

    /**
     * Șterge userul cu ID-ul dat.
     * Poți adăuga logica suplimentară (ex: logging, notificări etc.)
     */
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    // ----- Metode personalizate -----

    /**
     * Găsește user după username.
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Găsește user după email.
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Găsește toți userii la care username conține un anumit substring.
     */
    public List<User> getUsersByUsernameContaining(String substring) {
        return userRepository.findByUsernameContaining(substring);
    }

    @Autowired
    private PasswordEncoder passwordEncoder; // Injectat din Bean-ul SecurityConfig

    public User registerNewUserAccount(User user) {
        // 1) Verifică dacă emailul/username există deja
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Există deja un user cu acest email!");
        }
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Există deja un user cu acest username!");
        }

        // 2) Encode parola
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 3) Salvează userul
        return userRepository.save(user);
    }
}