package de.muenchen.oss.praktikumsplaner.domain.dtos;

import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsjahr;
import de.muenchen.oss.praktikumsplaner.domain.enums.Ausbildungsrichtung;
import de.muenchen.oss.praktikumsplaner.domain.enums.Dringlichkeit;
import de.muenchen.oss.praktikumsplaner.domain.enums.Referat;
import de.muenchen.oss.praktikumsplaner.domain.enums.YesNo;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateAusbildungsPraktikumsstelleDTO(@NotNull String dienststelle,

                                                   @NotNull String oertlicheAusbiler,

                                                   @NotNull String email,

                                                   @NotNull String taetigkeiten,

                                                   @NotNull Dringlichkeit dringlichkeit,

                                                   String namentlicheAnforderung,

                                                   Referat referat,

                                                   @NotNull YesNo projektarbeit,

                                                   @NotNull Ausbildungsjahr ausbildungsjahr,

                                                   @NotNull Ausbildungsrichtung ausbildungsrichtung

                        ){
}
