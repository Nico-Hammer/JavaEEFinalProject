package com.example.FinalProject_CampusJobBoard.config;

import com.example.FinalProject_CampusJobBoard.Security.user.CustomUserDetailsService;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Collection;

@Configuration
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            // Check user role and redirect accordingly
            if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                response.sendRedirect("/admin/pending-jobs");
            } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYER"))) {
                response.sendRedirect("/employer/myjobs");
            } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"))) {
                response.sendRedirect("/student/jobs");
            } else {
                response.sendRedirect("/home");
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/home", "/jobBoard/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/employer/**").hasAnyRole("EMPLOYER", "ADMIN")
                        .requestMatchers("/student/**").hasAnyRole("STUDENT", "ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/jobBoard/login")
                        .successHandler(authenticationSuccessHandler())
                        .permitAll())
                .userDetailsService(userDetailsService);
        return http.build();
    }
}