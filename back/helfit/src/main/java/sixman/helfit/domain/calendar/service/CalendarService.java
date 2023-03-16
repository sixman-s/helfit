package sixman.helfit.domain.calendar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sixman.helfit.domain.calendar.entity.Calendar;
import sixman.helfit.domain.calendar.repository.CalendarRepository;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.exception.BusinessLogicException;
import sixman.helfit.exception.ExceptionCode;
import sixman.helfit.utils.CustomBeanUtil;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CalendarService {
    private final CalendarRepository calendarRepository;
    private final CustomBeanUtil<Calendar> customBeanUtil;

    public Calendar createCalendar(Calendar calendar, User user) {
        verifyExistsCalendarByRecodedAt(calendar.getRecodedAt());

        calendar.setUser(user);

        return calendarRepository.save(calendar);
    }

    @Transactional(readOnly = true)
    public List<Calendar> findAllCalendarByUserId(Long userId) {
        return calendarRepository.findAllByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Calendar findCalendarByUserIdAndRecodedAt(Long userId, String recodedAt) {
        Optional<Calendar> byUserIdAndRecodedAt = calendarRepository.findByUserIdAndRecodedAt(userId, recodedAt);

        return byUserIdAndRecodedAt.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND));
    }

    public Calendar updateCalendar(Calendar requestCalendar, Calendar verifiedCalendar) {
        Calendar updatedCalendar = customBeanUtil.copyNonNullProperties(requestCalendar, verifiedCalendar);

        return calendarRepository.save(updatedCalendar);
    }

    public void deleteCalendar(Calendar verifiedCalendar) {
        calendarRepository.delete(verifiedCalendar);
    }

    @Transactional(readOnly = true)
    public Calendar findVerifiedCalendar(Long calendarId) {
        Optional<Calendar> byCalendarId = calendarRepository.findByCalendarId(calendarId);

        return byCalendarId.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Calendar findVerifiedCalendarWithUserId(Long calendarId, Long userId) {
        Optional<Calendar> byCalendarId = calendarRepository.findByCalendarId(calendarId);

        Calendar calendar = byCalendarId.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND));

        if (!calendar.getUser().getUserId().equals(userId))
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);

        return calendar;
    }

    @Transactional(readOnly = true)
    public void verifyExistsCalendarByRecodedAt(String recodedAt) {
        Optional<Calendar> byCalendarWithUserId = calendarRepository.findByRecodedAt(recodedAt);

        byCalendarWithUserId.ifPresent((e) -> {
            throw new BusinessLogicException(ExceptionCode.ALREADY_EXISTS_INFORMATION);
        });
    }
}
