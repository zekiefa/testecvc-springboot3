package br.com.cvc.evaluation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserDetailServiceConfig {
    @Value("${user.login}")
    private String user;
    @Value("${user.passwd}")
    private String userPass;
    @Value("${admin.login}")
    private String admin;
    @Value("${admin.passwd}")
    private String adminPass;

    @Bean
    public UserDetailsService userDetailsService(final BCryptPasswordEncoder bCryptPasswordEncoder) {
        final var manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername(this.user)
                        .password(bCryptPasswordEncoder.encode(this.userPass))
                        .roles("USER")
                        .build());
        manager.createUser(User.withUsername(this.admin)
                        .password(bCryptPasswordEncoder.encode(this.adminPass))
                        .roles("ADMIN", "USER")
                        .build());
        return manager;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
