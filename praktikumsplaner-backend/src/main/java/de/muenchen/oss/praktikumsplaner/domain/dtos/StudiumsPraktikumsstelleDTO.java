package de.muenchen.oss.praktikumsplaner.domain.dtos;

import de.muenchen.oss.praktikumsplaner.annotations.StudiumsAnnotation;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Referat;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

@Builder
@StudiumsAnnotation(studiengang = "studienart", message = "Keine korrekte Studienart")
public record StudiumsPraktikumsstelleDTO(@NotNull UUID id,
                                          @NotNull String dienststelle,

                                          @NotNull String oertlicheAusbilder,

                                          @Email @NotNull String email,

                                          @NotNull String taetigkeiten,

                                          @NotNull Dringlichkeit dringlichkeit,

                                          String namentlicheAnforderung,

                                          Referat referat,

                                          @NotNull boolean programmierkenntnisse,

                                          @NotNull Studiensemester studiensemester,

                                          @NotNull Studiengang studienart
                        ){
}
