package sixman.helfit.domain.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sixman.helfit.domain.calendar.entity.Calendar;

import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    Optional<Calendar> findByCalendarId(Long calendarId);

    @Query("select c from Calendar c where c.user.userId = :userId")
    List<Calendar> findAllByUserId(Long userId);

    @Query("select c from Calendar c where c.recodedAt = :recodedAt")
    Optional<Calendar> findByRecodedAt(String recodedAt);
}
