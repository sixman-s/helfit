package sixman.helfit.domain.statistics.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sixman.helfit.domain.board.entity.Board;
import sixman.helfit.domain.board.repository.BoardRepository;
import sixman.helfit.domain.calendar.entity.Calendar;
import sixman.helfit.domain.calendar.repository.CalendarRepository;
import sixman.helfit.domain.statistics.entity.Stat;
import sixman.helfit.domain.statistics.repository.StatRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatService {
    private final StatRepository statRepository;
    private final CalendarRepository calendarRepository;
    private final BoardRepository boardRepository;

    public StatService(StatRepository statRepository, CalendarRepository calendarRepository, BoardRepository boardRepository) {
        this.statRepository = statRepository;
        this.calendarRepository = calendarRepository;
        this.boardRepository = boardRepository;
    }
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

}
