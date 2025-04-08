package pl.coderslab.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pl.coderslab.entity.User;
import pl.coderslab.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1) Bean pentru BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2) Bean pentru UserDetailsService
    //    (Varianta cu lambda – dacă nu vrei CustomUserDetailsService separat)
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> {
            User user = userRepo.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("No user with username: " + username);
            }
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    // user.getRole() poate fi, ex: "ROLE_USER"
                    // dacă user.getRole() vine deja cu prefix, folosim .authorities(user.getRole())
                    .roles(user.getRole().replace("ROLE_", ""))
                    .build();
        };
    }

    // 3) SecurityFilterChain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Permităm forward-uri interne
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        // Permităm accesul la /login, /register, /css, /js, /images etc.
//                        .requestMatchers("/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/**").permitAll()
                        // Apoi orice alt request trebuie să fie autentificat
                        .anyRequest().authenticated()
                )
                // CSRF – opțional să-l dezactivezi (în general e bine să-l lași activ)
                // .csrf(csrf -> csrf.disable())

                .formLogin(form -> form
                        .loginPage("/login")      // definim pagina custom de login
                        .defaultSuccessUrl("/forums", true) // după login reușit
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }
}