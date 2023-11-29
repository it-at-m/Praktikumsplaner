package de.muenchen.oss.praktikumsplaner.repository;

import de.muenchen.oss.praktikumsplaner.domain.Nwk;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NwkRepository extends CrudRepository<Nwk, UUID> {

    List<Nwk> findNWKsByActiveIsTrueOrderByNachname();
}
