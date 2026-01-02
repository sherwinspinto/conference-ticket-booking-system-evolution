// src/main/java/com/sherwin/conference/bookingsystem/config/SecurityConfig.java
package com.sherwin.conference.bookingsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(authz -> authz
        .requestMatchers("/h2-console/**", "/error").permitAll()
        .requestMatchers("/api/**").permitAll()
        .anyRequest().authenticated()  // keeps future admin pages protected
      )
      .csrf(csrf -> csrf
        .ignoringRequestMatchers("/h2-console/**", "/api/**")  // ← your perfect fix
      )
      .headers(headers -> headers
        .frameOptions(frame -> frame.sameOrigin())
      );

    return http.build();
  }

  // Simple in-memory user for non-H2 paths (e.g., if you hit /api later)
  @Bean
  public UserDetailsService userDetailsService() {
    var user = User.withDefaultPasswordEncoder()
      .username("user")
      .password("{noop}password")  // {noop} for plain text (dev only!)
      .roles("USER")
      .build();
    return new InMemoryUserDetailsManager(user);
  }
}
