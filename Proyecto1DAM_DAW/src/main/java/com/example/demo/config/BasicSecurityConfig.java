package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//@Configuration
public class BasicSecurityConfig {
    //@Bean
    public UserDetailsService userDetailsService() {
        var userDetailsSerive = new InMemoryUserDetailsManager();
        UserDetails user1 = User.builder()
                .username("postman")
                .password(this.passwordEncoder().encode("postman"))
                .authorities("read")
                .build();
        UserDetails user2 = User.builder()
                .username("browser")
                .password(this.passwordEncoder().encode("browser"))
                .authorities("read")
                .build();

        //Test
        userDetailsSerive.createUser(user1);
        userDetailsSerive.createUser(user2);
        return userDetailsSerive;
    }
    //@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
