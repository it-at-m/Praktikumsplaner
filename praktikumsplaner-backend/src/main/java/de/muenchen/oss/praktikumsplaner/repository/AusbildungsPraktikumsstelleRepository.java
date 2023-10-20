package de.muenchen.oss.praktikumsplaner.repository;

import de.muenchen.oss.praktikumsplaner.domain.AusbildungsPraktikumsstelle;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AusbildungsPraktikumsstelleRepository extends CrudRepository<AusbildungsPraktikumsstelle, UUID> {
}
