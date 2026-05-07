package de.muenchen.oss.praktikumsplaner.repository;

import de.muenchen.oss.praktikumsplaner.domain.Nwk;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NwkRepository extends CrudRepository<Nwk, UUID> {

    List<Nwk> findNwksByActiveIsTrueOrderByNachname();

    @Override
    List<Nwk> findAll();

    @Query(
        "SELECT n FROM Nwk n " +
                "WHERE n.id NOT IN " +
                "(SELECT p.assignedNwk.id FROM Praktikumsstelle p WHERE p.meldezeitraumID = :meldezeitraumId AND p.assignedNwk IS NOT NULL) " +
                "AND n.active = true ORDER BY n.nachname"
    )
    List<Nwk> findAllUnassignedInSpecificMeldzeitraum(@Param("meldezeitraumId") UUID meldezeitraumId);
}
