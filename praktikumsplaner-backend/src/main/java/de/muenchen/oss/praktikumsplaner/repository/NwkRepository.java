package de.muenchen.oss.praktikumsplaner.repository;

import de.muenchen.oss.praktikumsplaner.domain.Nwk;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NwkRepository extends ListCrudRepository<Nwk, UUID> {

    List<Nwk> findAllByActiveIsTrueOrderByNachname();

    List<Nwk> findAllByActiveIsFalse();

    @Query(
        """
                    SELECT n FROM Nwk n
                    WHERE NOT EXISTS (SELECT 1 FROM Praktikumsstelle p WHERE p.meldezeitraumID = :meldezeitraumId AND p.assignedNwk = n)
                    ORDER BY n.nachname
                """
    )
    List<Nwk> findAllUnassignedInSpecificMeldezeitraum(@Param("meldezeitraumId") UUID meldezeitraumId);
}
