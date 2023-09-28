/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2023
 */
package de.muenchen.oss.praktikumsplaner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Application class for starting the micro-service.
 */
@Configuration
@ComponentScan(
        basePackages = {
                "org.springframework.data.jpa.convert.threeten",
                "de.muenchen.oss.praktikumsplaner"
        }
)
@EntityScan(
        basePackages = {
                "org.springframework.data.jpa.convert.threeten",
                "de.muenchen.oss.praktikumsplaner"
        }
)
@EnableJpaRepositories(
        basePackages = {
                "de.muenchen.oss.praktikumsplaner"
        }
)
@EnableAutoConfiguration
public class MicroServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroServiceApplication.class, args);
    }

}
