package de.muenchen.oss.praktikumsplaner.repository;

import de.muenchen.oss.praktikumsplaner.domain.Meldezeitraum;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MeldezeitraumRepository extends CrudRepository<Meldezeitraum, UUID> {
    @Query("SELECT e FROM Meldezeitraum e WHERE :date >= e.startZeitpunkt AND :date <= e.endZeitpunkt")
    Meldezeitraum findMeldezeitraumByDateInRange(@Param("date") final LocalDate date);

    @Query("SELECT EXISTS(SELECT e FROM Meldezeitraum e WHERE :end >= e.startZeitpunkt AND :start <= e.endZeitpunkt)")
    Boolean isOverlappingMeldezeitraum(@Param("start") final LocalDate start, @Param("end") final LocalDate end);

    /*
     * This method returns a list of all reporting periods that ended before the given date.
     * Sorted in descending order by end date.
     */
    List<Meldezeitraum> findByEndZeitpunktBeforeOrderByEndZeitpunktDesc(final LocalDate date);
}
