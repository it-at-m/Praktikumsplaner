package de.muenchen.oss.praktikumsplaner.repository;

import de.muenchen.oss.praktikumsplaner.domain.StudiumsPraktikumsstelle;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudiumsPraktikumsstellenRepository extends CrudRepository<StudiumsPraktikumsstelle, UUID> {
    List<StudiumsPraktikumsstelle> findAllByMeldezeitraumID(final UUID id);

    List<StudiumsPraktikumsstelle> findAllByMeldezeitraumIDAndAssignedNwkIsNotNull(final UUID id);

}
