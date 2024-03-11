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

    @Modifying
    @Query("update AusbildungsPraktikumsstelle u set u.dienststelle = :dienststelle, u.email = :email, u.taetigkeiten = :taetigkeiten, u.oertlicheAusbilder = :ausbilder where u.id = :id")
    void updateAusbildungsPraktikumsstelleWithAssignedNwk(@Param(value = "id") UUID id, @Param(value = "dienststelle") String dienststelle, @Param(value = "ausbilder") String ausbilder, @Param(value = "taetigkeiten") String taetigkeit, @Param(value = "email") String email);
}
