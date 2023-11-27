package de.muenchen.oss.praktikumsplaner.repository;

import de.muenchen.oss.praktikumsplaner.domain.NWK;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NWKRepository extends CrudRepository<NWK, UUID> {

    List<NWK> findNWKsByActiveIsTrueOrderByNachname();
}
