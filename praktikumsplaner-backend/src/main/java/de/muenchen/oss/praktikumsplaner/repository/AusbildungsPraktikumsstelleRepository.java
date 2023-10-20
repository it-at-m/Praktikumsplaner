package de.muenchen.oss.praktikumsplaner.repository;

import java.util.UUID;

import de.muenchen.oss.praktikumsplaner.domain.AusbildungsPraktikumsstelle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AusbildungsPraktikumsstelleRepository extends CrudRepository<AusbildungsPraktikumsstelle, UUID> {
}
