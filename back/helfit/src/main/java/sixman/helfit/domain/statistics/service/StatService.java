package sixman.helfit.domain.statistics.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sixman.helfit.domain.calendar.entity.Calendar;
import sixman.helfit.domain.calendar.repository.CalendarRepository;
import sixman.helfit.domain.statistics.entity.Stat;
import sixman.helfit.domain.statistics.repository.StatRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StatService {
    private final StatRepository statRepository;
    private final CalendarRepository calendarRepository;

    public StatService(StatRepository statRepository, CalendarRepository calendarRepository) {
        this.statRepository = statRepository;
        this.calendarRepository = calendarRepository;
    }
//    @Transactional(readOnly = true)
//    public List<Stat> findKcalByUserId(Long userId){
//        Optional<Stat> optionalStatCalendar = statRepository.findAllKcalByUserId(userId);
//
//        return statRepository.findAllByUserId(userId);
//    }
    @Transactional(readOnly = true)
    public List<Stat> getCalendarByUserId(Long userId) {
        List<Calendar> calendarList = calendarRepository.findAllByUserId(userId);
        List<Stat> calendarStatList = new ArrayList<>();
        for (Calendar calendar : calendarList) {
            Stat calendarStat = new Stat();
            calendarStat.setUser(calendar.getUser());
            calendarStat.setCalendar(calendar);
            calendarStat.setKcal(calendar.getKcal());
            calendarStat.setRecodedAt(calendar.getRecodedAt());
            calendarStatList.add(calendarStat);
        }
        return calendarStatList;
    }
    @Transactional(readOnly = true)
    public List<Stat> getBoardByLike(){
        return null;
    }



}
