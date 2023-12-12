package de.muenchen.oss.praktikumsplaner.domain.dtos;

import de.muenchen.oss.praktikumsplaner.annotations.AusbildungsAnnotation;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Referat;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

@Builder
@AusbildungsAnnotation(studiengang = "ausbildungsrichtung", message = "Keine korrekte Ausbildungsrichtung")
public record CreateAusbildungsPraktikumsstelleWithMeldezeitraumDto(@NotNull String dienststelle,

                                                                    @NotNull String oertlicheAusbilder,

                                                                    @Email @NotNull String email,

                                                                    @NotNull String taetigkeiten,

                                                                    @NotNull Dringlichkeit dringlichkeit,

                                                                    String namentlicheAnforderung,

                                                                    Referat referat,

                                                                    @NotNull boolean projektarbeit,

                                                                    String programmierkenntnisse,

                                                                    @NotNull boolean planstelleVorhanden,

                                                                    @NotNull Ausbildungsjahr ausbildungsjahr,

                                                                    @NotNull Studiengang ausbildungsrichtung,

                                                                    @NotNull UUID meldezeitraumID

                        ){
}
