package de.muenchen.oss.praktikumsplaner.repository;

import de.muenchen.oss.praktikumsplaner.domain.Nwk;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NwkRepository extends CrudRepository<Nwk, UUID> {

    List<Nwk> findNwksByActiveIsTrueOrderByNachname();

    @Override
    List<Nwk> findAll();

    @Query(
        "SELECT n FROM Nwk n WHERE n.id NOT IN (SELECT ap.assignedNwk FROM AusbildungsPraktikumsstelle ap WHERE ap.assignedNwk IS NOT null) AND n.id NOT IN (SELECT sp.assignedNwk FROM StudiumsPraktikumsstelle sp WHERE sp.assignedNwk IS NOT null) AND n.active = true order by n.nachname"
    )
    List<Nwk> findAllUnassigned();
}
