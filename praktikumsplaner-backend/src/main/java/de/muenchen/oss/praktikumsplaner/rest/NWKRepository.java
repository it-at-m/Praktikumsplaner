package de.muenchen.oss.praktikumsplaner.rest;

import de.muenchen.oss.praktikumsplaner.domain.NWK;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface NWKRepository extends CrudRepository<NWK, UUID> {
}
