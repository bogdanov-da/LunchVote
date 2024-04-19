package org.bda.voteapp.config;

import org.bda.voteapp.controller.RestaurantController;
import org.bda.voteapp.controller.VoteController;
import org.bda.voteapp.controller.user.AdminUserController;
import org.bda.voteapp.controller.user.ProfileUserController;
import org.bda.voteapp.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.bda.voteapp.model.Role.ADMIN;
import static org.bda.voteapp.model.Role.USER;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class AppSecurityConfigurer {
    public static final org.bda.voteapp.model.User admin = new org.bda.voteapp.model.User(100001, "Admin",
            "admin@gmail.com", "admin_password",
            LocalDateTime.of(2020, 1, 1, 0, 0, 0), List.of(ADMIN, USER));
    public static final org.bda.voteapp.model.User user = new org.bda.voteapp.model.User(100000, "User",
            "user@gmail.com", "user_password",
            LocalDateTime.of(2022, 1, 1, 0, 0, 0), Collections.singleton(USER));

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
                .password(bCryptPasswordEncoder.encode("user"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("admin")
                .password(bCryptPasswordEncoder.encode("admin"))
                .roles("USER", "ADMIN")
                .build());
        return manager;
    }




    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers(HttpMethod.GET, AdminUserController.REST_URL).hasAnyRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.GET, RestaurantController.REST_URL, ProfileUserController.REST_URL).hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                                .requestMatchers(HttpMethod.POST, VoteController.REST_URL).hasAnyRole(USER.name())
                                .requestMatchers(HttpMethod.PUT, VoteController.REST_URL).hasAnyRole(USER.name())
                                .requestMatchers(HttpMethod.GET, VoteController.REST_URL + "/").hasAnyRole(USER.name())
                                .requestMatchers(HttpMethod.GET, VoteController.REST_URL + "/by-user").hasAnyRole(ADMIN.name())
                                .requestMatchers(HttpMethod.POST, RestaurantController.REST_URL).hasAnyRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.POST, AdminUserController.REST_URL).hasAnyRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.PUT, RestaurantController.REST_URL).hasAnyRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, RestaurantController.REST_URL, VoteController.REST_URL)
                                .hasAnyRole(Role.ADMIN.name())
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/swagger-resources/**").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/webjars/**").permitAll()
                                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

}
