package sixman.helfit.domain.statistics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sixman.helfit.domain.board.entity.Board;
import sixman.helfit.domain.board.repository.BoardRepository;
import sixman.helfit.domain.calendar.entity.Calendar;
import sixman.helfit.domain.calendar.repository.CalendarRepository;
import sixman.helfit.domain.physical.entity.Physical;
import sixman.helfit.domain.physical.repository.PhysicalRepositoryImpl;
import sixman.helfit.domain.statistics.entity.Stat;
import sixman.helfit.domain.statistics.repository.StatRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatService {
    private final CalendarRepository calendarRepository;
    private final BoardRepository boardRepository;
    private final PhysicalRepositoryImpl physicalRepository;

    @Transactional(readOnly = true)
    public List<Stat> getCalendarByUserId(Long userId) {
        List<Calendar> calendarList = calendarRepository.findTop7ByUserIdOrderByRecodedAtDesc(userId, PageRequest.of(0, 7));
        List<Stat> calendarStatList = new ArrayList<>();

        List<LocalDate> sortedDates = calendarList.stream()
                .map(calendar -> LocalDate.parse(calendar.getRecodedAt()))
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        for (LocalDate date : sortedDates) {
            for (Calendar calendar : calendarList) {
                if (LocalDate.parse(calendar.getRecodedAt()).equals(date)) {
                    Stat calendarStat = new Stat();
                    calendarStat.setUser(calendar.getUser());
                    calendarStat.setCalendar(calendar);
                    calendarStat.setKcal(calendar.getKcal());
                    calendarStat.setRecodedAt(calendar.getRecodedAt());
                    calendarStatList.add(calendarStat);
                }
            }
        }
        Collections.reverse(calendarStatList);
        return calendarStatList;
    }
    @Transactional(readOnly = true)
    public List<Stat> getBoardByRecent(Long categoryId){
        Pageable pageable = PageRequest.of(0, 3, Sort.by("createdAt").descending());
        List<Board> boardList = boardRepository.findTop3ByCategoryIdOrderByCreatedAtDesc(categoryId, pageable);
        List<Stat> boardStatList = new ArrayList<>();
        for(Board board : boardList){
            Stat boardStat = new Stat();
            boardStat.setBoardImageUrl(board.getBoardImageUrl());
            boardStat.setTitle(board.getTitle());
            boardStat.setText(board.getText());
            boardStatList.add(boardStat);
        }
        return boardStatList;
    }
    @Transactional(readOnly = true)
    public List<Stat> getWeightByUserId(Long userId){
        Pageable pageable = PageRequest.of(1, 7, Sort.by("modifiedAt").descending());
        Page<Physical> weightList = physicalRepository.findAllPhysicalByUserId(userId,pageable);
        List<Stat> weightStatList = new ArrayList<>();
        for(Physical physical : weightList){
            Stat physicalStat = new Stat();
            physicalStat.setWeight(physical.getWeight());
            physicalStat.setLastModifiedAt(physical.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            weightStatList.add(physicalStat);
        }
        return weightStatList;
    }
}
