package sixman.helfit.domain.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sixman.helfit.domain.calendar.entity.Calendar;
import sixman.helfit.domain.statistics.entity.Stat;

import java.util.List;
import java.util.Optional;

public interface StatRepository extends JpaRepository<Stat, Long> {
    @Query("select c from Calendar c where c.user.userId = :userId")
    List<Calendar> findAllByUserId(Long userId);

    Optional<Stat> findAllKcalByUserId(Long userId);
}
