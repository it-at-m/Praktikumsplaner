package de.muenchen.oss.praktikumsplaner.domain.dtos;

import de.muenchen.oss.praktikumsplaner.annotations.StudiumsAnnotation;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Referat;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiengang;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@StudiumsAnnotation(studiengangGetMethod = "studiengang", message = "Keine korrekte Studiengang")
public record CreateStudiumsPraktikumsstelleDto(@NotNull String dienststelle,

                                                @NotNull String oertlicheAusbilder,

                                                @Email @NotNull String email,

                                                @NotNull String taetigkeiten,

                                                @NotNull Dringlichkeit dringlichkeit,

                                                String namentlicheAnforderung,

                                                Referat referat,

                                                @NotNull String programmierkenntnisse,

                                                boolean planstelleVorhanden,

                                                @NotNull Studiensemester studiensemester,

                                                @NotNull Studiengang studiengang
                        ){
}
