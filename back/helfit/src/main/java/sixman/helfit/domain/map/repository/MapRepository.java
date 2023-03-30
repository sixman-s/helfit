package sixman.helfit.domain.map.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sixman.helfit.domain.map.entity.Map;

import java.util.Optional;

public interface MapRepository extends JpaRepository<Map, Long> {
    Optional<Map> findByMapId(Long mapId);
    Optional<Map> findByUserId(Long userId);
}
