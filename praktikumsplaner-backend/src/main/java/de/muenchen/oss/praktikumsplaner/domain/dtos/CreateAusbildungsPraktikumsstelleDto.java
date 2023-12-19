package de.muenchen.oss.praktikumsplaner.domain.dtos;

import de.muenchen.oss.praktikumsplaner.annotations.AusbildungsAnnotation;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Referat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@AusbildungsAnnotation(ausbildungsrichtung = "ausbildungsrichtung", message = "Keine korrekte Ausbildungsrichtung")
public record CreateAusbildungsPraktikumsstelleDto(@NotNull String dienststelle,

                                                   @NotNull String oertlicheAusbilder,

                                                   @Email @NotNull String email,

                                                   @NotNull String taetigkeiten,

                                                   @NotNull Dringlichkeit dringlichkeit,

                                                   String namentlicheAnforderung,

                                                   Referat referat,

                                                   boolean projektarbeit,

                                                   String programmierkenntnisse,

                                                   boolean planstelleVorhanden,

                                                   @NotNull Ausbildungsjahr ausbildungsjahr,

                                                   @NotNull Ausbildungsrichtung ausbildungsrichtung

                        ){
}
