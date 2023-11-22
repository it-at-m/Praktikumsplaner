package de.muenchen.oss.praktikumsplaner.repository;

import de.muenchen.oss.praktikumsplaner.domain.Meldezeitraum;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MeldezeitraumRepository extends CrudRepository<Meldezeitraum, UUID> {
    @Query("SELECT e FROM Meldezeitraum e WHERE :date >= e.startZeitpunkt AND :date <= e.endZeitpunkt")
    Meldezeitraum findMeldezeitraumByDate(@Param("date") LocalDate date);
}
