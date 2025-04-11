

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.coderslab.entity.User;
import pl.coderslab.repository.UserRepository;
import pl.coderslab.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterNewUserAccount_success() {
        User newUser = new User("roberto", "roberto@test.com", "pass123!", "Detalii", null);
        when(userRepository.findByEmail("roberto@test.com")).thenReturn(null);
        when(userRepository.findByUsername("roberto")).thenReturn(null);
        when(passwordEncoder.encode("pass123!")).thenReturn("encodedPass");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User saved = userService.registerNewUserAccount(newUser);

        assertEquals("encodedPass", saved.getPassword());
        assertEquals("ROLE_USER", saved.getRole());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testRegisterNewUserAccount_duplicateEmail() {
        User existing = new User();
        existing.setEmail("a@b.com");

        when(userRepository.findByEmail("a@b.com")).thenReturn(existing);

        User user = new User("new", "a@b.com", "pass", "", null);

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> userService.registerNewUserAccount(user));
        assertEquals("Există deja un utilizator cu acest email.", ex.getMessage());
    }

    @Test
    void testRegisterNewUserAccount_duplicateUsername() {
        User existing = new User();
        existing.setUsername("roberto");

        when(userRepository.findByUsername("roberto")).thenReturn(existing);

        User user = new User("roberto", "email@test.com", "pass", "", null);

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> userService.registerNewUserAccount(user));
        assertEquals("Există deja un utilizator cu acest username.", ex.getMessage());
    }
}