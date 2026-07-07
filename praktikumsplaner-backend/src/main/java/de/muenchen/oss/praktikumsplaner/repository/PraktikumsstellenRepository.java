package de.muenchen.oss.praktikumsplaner.repository;

import de.muenchen.oss.praktikumsplaner.domain.Praktikumsstelle;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PraktikumsstellenRepository extends CrudRepository<Praktikumsstelle, UUID> {
    List<Praktikumsstelle> findAllByMeldezeitraumID(UUID id);

    List<Praktikumsstelle> findAllByMeldezeitraumIDAndAssignedNwkIsNotNull(UUID id);
}
