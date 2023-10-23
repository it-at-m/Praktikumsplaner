package de.muenchen.oss.praktikumsplaner.domain.dtos;

import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Referat;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studienart;
import de.muenchen.oss.praktikumsplaner.domain.enums.Studiensemester;
import de.muenchen.oss.praktikumsplaner.domain.enums.YesNo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

@Builder
public record StudiumsPraktikumsstelleDTO(@NotNull UUID id,
                                          @NotNull String dienststelle,

                                          @NotNull String oertlicheAusbiler,

                                          @Email @NotNull String email,

                                          @NotNull String taetigkeiten,

                                          @NotNull Dringlichkeit dringlichkeit,

                                          String namentlicheAnforderung,

                                          Referat referat,

                                          @NotNull YesNo programmierkenntnisse,

                                          @NotNull Studiensemester studiensemester,

                                          @NotNull Studienart studienart
                        ){
}
