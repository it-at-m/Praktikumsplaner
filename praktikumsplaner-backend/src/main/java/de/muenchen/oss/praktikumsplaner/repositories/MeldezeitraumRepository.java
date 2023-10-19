package de.muenchen.oss.praktikumsplaner.repositories;

import de.muenchen.oss.praktikumsplaner.domain.Meldezeitraum;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface MeldezeitraumRepository extends CrudRepository<Meldezeitraum, UUID> {
}