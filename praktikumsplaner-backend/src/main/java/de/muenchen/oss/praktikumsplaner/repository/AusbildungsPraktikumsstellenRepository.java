package de.muenchen.oss.praktikumsplaner.repository;

import de.muenchen.oss.praktikumsplaner.domain.AusbildungsPraktikumsstelle;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface AusbildungsPraktikumsstellenRepository extends PraktikumsstellenRepository<AusbildungsPraktikumsstelle> {
    List<AusbildungsPraktikumsstelle> findAllByMeldezeitraumID(UUID id);
}
