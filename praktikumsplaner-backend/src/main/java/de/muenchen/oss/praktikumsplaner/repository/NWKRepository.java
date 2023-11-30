package de.muenchen.oss.praktikumsplaner.repository;

import de.muenchen.oss.praktikumsplaner.domain.NWK;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NWKRepository extends CrudRepository<NWK, UUID> {

    List<NWK> findNWKsByActiveIsTrueOrderByNachname();

    @Override
    List<NWK> findAll();

    @Query(
        "SELECT n FROM NWK n WHERE n.id NOT IN (SELECT ap.assignedNWK FROM AusbildungsPraktikumsstelle ap WHERE ap.assignedNWK IS NOT null) AND n.id NOT IN (SELECT sp.assignedNWK FROM StudiumsPraktikumsstelle sp WHERE sp.assignedNWK IS NOT null) AND n.active = true order by n.nachname"
    )
    List<NWK> findAllUnassigned();
}
