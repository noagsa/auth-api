package io.github.noagsa.authapi.config;

import io.github.noagsa.authapi.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        String allowedPaths = "/api/auth/**";

        httpSecurity.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(allowedPaths).permitAll()
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin.disable())
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
