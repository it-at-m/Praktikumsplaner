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
                "(SELECT ap.assignedNwk.id FROM AusbildungsPraktikumsstelle ap WHERE ap.meldezeitraumID=:meldezeitraumId AND " +
                "ap.assignedNwk IS NOT null) AND n.id NOT IN " +
                "(SELECT sp.assignedNwk.id FROM StudiumsPraktikumsstelle sp WHERE sp.meldezeitraumID=:meldezeitraumId AND  sp.assignedNwk IS NOT null) AND n.active = true order by n.nachname"
    )
    List<Nwk> findAllUnassignedInSpecificMeldzeitraum(@Param("meldezeitraumId") final UUID meldezeitraumId);
}
