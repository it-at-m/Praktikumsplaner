package de.muenchen.oss.praktikumsplaner.repository;

import de.muenchen.oss.praktikumsplaner.domain.BasePraktikumsstelle;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PraktikumsstellenRepository<S extends BasePraktikumsstelle> extends CrudRepository<BasePraktikumsstelle, UUID> {
}
