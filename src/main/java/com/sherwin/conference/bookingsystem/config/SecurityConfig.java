// src/main/java/com/sherwin/conference/bookingsystem/config/SecurityConfig.java
package com.sherwin.conference.bookingsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
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
        // ← CRITICAL: Permit error paths FIRST to break the loop
        .requestMatchers("/error").permitAll()
        .requestMatchers("/h2-console/**").permitAll()  // H2 full access
        // ← Add actuator if you add it later
        .requestMatchers("/actuator/**").permitAll()
        .anyRequest().authenticated()  // Everything else requires auth
      )
      // CSRF: Ignore for H2/error (safe for local dev)
      .csrf(csrf -> csrf
        .ignoringRequestMatchers("/h2-console/**", "/error")
      )
      // Frames: Allow for H2 console (iframe)
      .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.disable()
      )
      // Form login: Keep defaults, but we'll override creds below
      .formLogin(Customizer.withDefaults());

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
