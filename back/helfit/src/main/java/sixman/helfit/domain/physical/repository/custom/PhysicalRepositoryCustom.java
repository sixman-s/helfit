package sixman.helfit.domain.physical.repository.custom;

import sixman.helfit.domain.physical.entity.Physical;

import java.util.List;
import java.util.Optional;

public interface PhysicalRepositoryCustom {
    Optional<Physical> findPhysicalByUserIdWithinToday(Long userId);
    List<Physical> findAllPhysicalByUserId(Long userId);
}
