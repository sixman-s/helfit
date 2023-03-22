package sixman.helfit.domain.physical.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sixman.helfit.domain.physical.entity.Physical;

import java.util.Optional;

public interface PhysicalRepositoryCustom {
    Optional<Physical> findPhysicalByUserIdWithinToday(Long userId);
    Optional<Physical> findPhysicalByUserId(Long userId);
    Page<Physical> findAllPhysicalByUserId(Long userId, Pageable pageable);
}
