package br.com.cvc.evaluation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
    @Value("${spring.security.debug:false}")
    boolean securityDebug;

    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
                        .csrf().disable()
                        .authorizeHttpRequests()
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
                        .and()
                            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        // filter configuration
                        .and()
                        .addFilterBefore(this.tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.debug(securityDebug)
                        .ignoring()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/css/**"),
                                        AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/js/**"),
                                        AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/img/**"),
                                        AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/lib/**"),
                                        AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/favicon.ico"));
    }
}
