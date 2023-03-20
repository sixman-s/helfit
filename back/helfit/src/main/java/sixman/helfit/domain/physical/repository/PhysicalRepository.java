package sixman.helfit.domain.physical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sixman.helfit.domain.physical.entity.Physical;
import sixman.helfit.domain.physical.repository.custom.PhysicalRepositoryCustom;

public interface PhysicalRepository extends JpaRepository<Physical, Long>, PhysicalRepositoryCustom {
}
