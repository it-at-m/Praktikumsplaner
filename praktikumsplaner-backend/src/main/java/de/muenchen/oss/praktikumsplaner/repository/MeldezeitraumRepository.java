package de.muenchen.oss.praktikumsplaner.repository;

import de.muenchen.oss.praktikumsplaner.domain.Meldezeitraum;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface MeldezeitraumRepository extends CrudRepository<Meldezeitraum, UUID> {
    @Query("SELECT e FROM Meldezeitraum e WHERE :date >= e.startZeitpunkt AND :date <= e.endZeitpunkt")
    Meldezeitraum findMeldezeitraumByDateInRange(@Param("date") LocalDate date);

    @Query("SELECT EXISTS(SELECT e FROM Meldezeitraum e WHERE :end >= e.startZeitpunkt AND :start <= e.endZeitpunkt)")
    Boolean isOverlappingMeldezeitraum(@Param("start") LocalDate start, @Param("end") LocalDate end);

    List<Meldezeitraum> findByStartZeitpunktAfterOrderByStartZeitpunktAsc(LocalDate date);

    /*
     * This method returns a list of all reporting periods that ended before the given date.
     * Sorted in descending order by end date.
     */
    List<Meldezeitraum> findByEndZeitpunktBeforeOrderByEndZeitpunktDesc(LocalDate date);

    @NonNull List<Meldezeitraum> findAll();
}
