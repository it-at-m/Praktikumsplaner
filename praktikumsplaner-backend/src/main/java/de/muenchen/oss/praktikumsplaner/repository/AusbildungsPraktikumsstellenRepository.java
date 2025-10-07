package de.muenchen.oss.praktikumsplaner.repository;

import de.muenchen.oss.praktikumsplaner.domain.AusbildungsPraktikumsstelle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AusbildungsPraktikumsstellenRepository extends CrudRepository<AusbildungsPraktikumsstelle, UUID> {
    List<AusbildungsPraktikumsstelle> findAllByMeldezeitraumID(final UUID id);

    List<AusbildungsPraktikumsstelle> findAllByMeldezeitraumIDAndAssignedNwkIsNotNull(final UUID id);
}
