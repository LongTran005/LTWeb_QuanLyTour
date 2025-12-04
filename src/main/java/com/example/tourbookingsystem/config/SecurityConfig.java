package com.example.tourbookingsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        // KHÁCH ĐƯỢC XEM TOUR VÀ ĐẶT TOUR
                        .requestMatchers("/", "/tour/**", "/booking/**", "/css/**").permitAll()

                        // ADMIN QUẢN LÝ
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // CÁC REQUEST KHÁC → yêu cầu login
                        .anyRequest().authenticated()
                )

                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/admin/tours", true)
                        .permitAll()
                )

                .logout(logout -> logout.permitAll());

        return http.build();
    }
}
