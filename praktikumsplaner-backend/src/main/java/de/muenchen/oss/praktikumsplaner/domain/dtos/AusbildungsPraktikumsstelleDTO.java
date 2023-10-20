package de.muenchen.oss.praktikumsplaner.domain.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record AusbildungsPraktikumsstelleDTO(@NotNull UUID id,
                                             @NotNull String dienststelle,

                                             @NotNull String oertlicheAusbiler,

                                             @NotNull String email,

                                             @NotNull String taetigkeiten,

                                             @NotNull String dringlichkeit,

                                             String namentlicheAnforderung,

                                             String referat,

                                             @NotNull String projektarbeit,

                                             @NotNull String ausbildungsjahr,

                                             @NotNull String ausbildungsrichtung

                        ){
}
