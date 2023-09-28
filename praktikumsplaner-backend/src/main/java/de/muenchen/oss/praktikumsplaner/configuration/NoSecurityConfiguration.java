/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2023
 */
package de.muenchen.oss.praktikumsplaner.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("no-security")
@EnableWebSecurity
public class NoSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .headers()
                    .frameOptions()
                    .disable()
                .and().authorizeHttpRequests((requests) ->
                        requests.requestMatchers("/**")
                                .permitAll()
                                .anyRequest()
                                .permitAll()
                )
                .csrf()
                .disable();
        // @formatter:on
        return http.build();
    }

}
