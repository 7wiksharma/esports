package com.indoreesports.esports.config;
import com.indoreesports.esports.repository.UserRepository;
import com.indoreesports.esports.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity (enable in production)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/api/contact").permitAll() // Public endpoints
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/api/auth/login") // Login endpoint
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout") // Logout endpoint
                        .logoutSuccessUrl("/api/auth/login?logout") // Redirect after logout
                        .invalidateHttpSession(true) // Invalidate session on logout
                        .deleteCookies("JSESSIONID") // Remove session cookie
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .maximumSessions(1) // Only one session per user
                        .expiredUrl("/api/auth/login?expired") // Redirect if session expires
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new CustomUserDetailsService(userRepository);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}