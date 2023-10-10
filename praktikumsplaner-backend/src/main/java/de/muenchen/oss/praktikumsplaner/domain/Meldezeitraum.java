package de.muenchen.oss.praktikumsplaner.domain;

import de.muenchen.oss.praktikumsplaner.annotations.StartDateBeforeEndDate;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@StartDateBeforeEndDate(startDate = "getStartZeitpunkt", endDate = "getEndZeitpunkt", message = "Startdatum muss vor dem Enddatum liegen.")
public class Meldezeitraum extends BaseEntity {
    @NotNull
    @Size(max = 255, message = "Name für Zeitraum darf nicht länger als {max} Zeichen sein.")
    private String zeitraumName;

    @NotNull
    private LocalDate startZeitpunkt;

    @NotNull
    private LocalDate endZeitpunkt;
}
