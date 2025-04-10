package pl.coderslab.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pl.coderslab.entity.User;
import pl.coderslab.repository.UserRepository;

import java.io.IOException;

@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Obținem username-ul utilizatorului logat
        String username = authentication.getName();

        // Căutăm userul în DB
        User user = userRepository.findByUsername(username);

        // Setăm userul logat în sesiune
        request.getSession().setAttribute("currentUser", user);

        // Debug: confirmăm că userul a fost salvat în sesiune
        System.out.println("User autentificat: " + user.getUsername() + ", ID: " + user.getId() + ", Rol: " + user.getRole());

        // Redirectăm către /forums (sau altă pagină default)
        response.sendRedirect("/forums");
    }
}