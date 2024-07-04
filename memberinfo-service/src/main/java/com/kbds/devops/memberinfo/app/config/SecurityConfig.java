package com.kbds.devops.memberinfo.app.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeRequests(
                    authz -> authz.antMatchers("/resource/message").permitAll()
                            .anyRequest()
                            .authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());
        return httpSecurity.build();
    }
}
