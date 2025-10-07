package de.muenchen.oss.praktikumsplaner.repository;

import de.muenchen.oss.praktikumsplaner.domain.StudiumsPraktikumsstelle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudiumsPraktikumsstellenRepository extends CrudRepository<StudiumsPraktikumsstelle, UUID> {
    List<StudiumsPraktikumsstelle> findAllByMeldezeitraumID(UUID id);

    List<StudiumsPraktikumsstelle> findAllByMeldezeitraumIDAndAssignedNwkIsNotNull(UUID id);
}
