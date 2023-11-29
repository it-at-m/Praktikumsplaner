package de.muenchen.oss.praktikumsplaner.repository;

import de.muenchen.oss.praktikumsplaner.domain.StudiumsPraktikumsstelle;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface StudiumsPraktikumsstellenRepository extends PraktikumsstellenRepository<StudiumsPraktikumsstelle> {
    List<StudiumsPraktikumsstelle> findAllByMeldezeitraumID(final UUID id);
}
