package de.muenchen.oss.praktikumsplaner.repositories;

import de.muenchen.oss.praktikumsplaner.domain.Meldezeitraum;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MeldezeitraumRepository extends CrudRepository<Meldezeitraum, UUID> {
}
