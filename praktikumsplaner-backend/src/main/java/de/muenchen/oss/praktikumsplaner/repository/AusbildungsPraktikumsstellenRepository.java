package de.muenchen.oss.praktikumsplaner.repository;

import de.muenchen.oss.praktikumsplaner.domain.AusbildungsPraktikumsstelle;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AusbildungsPraktikumsstellenRepository extends CrudRepository<AusbildungsPraktikumsstelle, UUID> {
    List<AusbildungsPraktikumsstelle> findAllByMeldezeitraumID(final UUID id);

    List<AusbildungsPraktikumsstelle> findAllByMeldezeitraumIDAndAssignedNwkIsNotNull(final UUID id);
}
