package de.muenchen.oss.praktikumsplaner.configuration;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@ConfigurationProperties("app")
@Validated
public class PraktikumsplanerProperties {
    @NotBlank private String oertlAusbildungsleitungName;
    @NotBlank private String dienststelleAdresse;
}
