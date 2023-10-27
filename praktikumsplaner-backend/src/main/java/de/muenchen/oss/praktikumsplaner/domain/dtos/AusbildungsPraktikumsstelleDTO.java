package de.muenchen.oss.praktikumsplaner.domain.dtos;

import de.muenchen.oss.praktikumsplaner.annotations.AusbildungsAnnotation;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Referat;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import de.muenchen.oss.praktikumsplaner.domain.enums.YesNo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

@Builder
@AusbildungsAnnotation(studiengang = "ausbildungsrichtung", message = "Keine korrekte Ausbildungsrichtung")
public record AusbildungsPraktikumsstelleDTO(@NotNull UUID id,
                                             @NotNull String dienststelle,

                                             @NotNull String oertlicheAusbilder,

                                             @Email @NotNull String email,

                                             @NotNull String taetigkeiten,

                                             @NotNull Dringlichkeit dringlichkeit,

                                             String namentlicheAnforderung,

                                             Referat referat,

                                             @NotNull YesNo projektarbeit,

                                             @NotNull Ausbildungsjahr ausbildungsjahr,

                                             @NotNull Studiengang ausbildungsrichtung

                        ){
}
