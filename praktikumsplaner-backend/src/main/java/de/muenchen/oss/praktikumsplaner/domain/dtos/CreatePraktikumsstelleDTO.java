package de.muenchen.oss.praktikumsplaner.domain.dtos;

import de.muenchen.oss.praktikumsplaner.annotations.AusbildungOrStudium;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@AusbildungOrStudium(
        projektarbeit = "projektarbeit", ausbildungsjahr = "ausbildungsjahr", ausbildungsrichtung = "ausbildungsrichtung", studienart = "studienart", studiensemester = "studiensemester", programmierkenntnisse = "programmierkenntnisse", message = "Muss entweder Studiums- oder Ausbildungspraktikumsplatz sein"
)
@Builder
public record CreatePraktikumsstelleDTO(@NotNull String dienststelle,

                                        @NotNull String oertlicheAusbiler,

                                        @NotNull String email,

                                        @NotNull String taetigkeiten,

                                        @NotNull String dringlichkeit,
                                        String namentlicheAnforderung,

                                        String referat,

                                        String projektarbeit,

                                        String programmierkenntnisse,

                                        String ausbildungsjahr,

                                        String studiensemester,

                                        String ausbildungsrichtung,

                                        String studienart
                        ){
}
