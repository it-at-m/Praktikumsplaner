package de.muenchen.oss.praktikumsplaner.repository;

import de.muenchen.oss.praktikumsplaner.domain.NWK;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NWKRepository extends CrudRepository<NWK, UUID> {
    @Query("select * from NWK where isActive")
    Iterable<NWK> findAllByActiveIsTrue();
}
