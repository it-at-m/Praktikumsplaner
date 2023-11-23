package de.muenchen.oss.praktikumsplaner.repository;

import de.muenchen.oss.praktikumsplaner.domain.AusbildungsPraktikumsstelle;
import de.muenchen.oss.praktikumsplaner.domain.StudiumsPraktikumsstelle;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudiumsPraktikumsstellenRepository extends CrudRepository<StudiumsPraktikumsstelle, UUID> {
    Iterable<StudiumsPraktikumsstelle> findAllByMeldezeitraumID(UUID id);
}
