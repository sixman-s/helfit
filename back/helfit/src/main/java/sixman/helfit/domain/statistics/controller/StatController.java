package sixman.helfit.domain.statistics.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;
import sixman.helfit.domain.calendar.dto.CalendarDto;
import sixman.helfit.domain.calendar.entity.Calendar;
import sixman.helfit.domain.statistics.Dto.StatDto;
import sixman.helfit.domain.statistics.entity.Stat;
import sixman.helfit.domain.statistics.mapper.StatMapper;
import sixman.helfit.domain.statistics.service.StatService;
import sixman.helfit.response.ApiResponse;
import sixman.helfit.security.entity.UserPrincipal;

import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/stat")
public class StatController {
    private final String DEFAULT_URL = "/api/v1/stat";
    private final StatService statService;
    private final StatMapper statMapper;

    public StatController(StatService statService, StatMapper statMapper) {
        this.statService = statService;
        this.statMapper = statMapper;
    }
    @GetMapping("/calendar/{user-id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getKcalStat(@Positive @PathVariable("user-id") Long userId,
                                         @AuthenticationPrincipal UserPrincipal userPrincipal){
        List<Stat> allKcalByUserId = statService.getCalendarByUserId(userPrincipal.getUser().getUserId());

        List<StatDto.calendarResponse> responses = statMapper.statListToStatDtoResponseList(allKcalByUserId);

        return ResponseEntity.ok().body(ApiResponse.ok("data", responses));
    }
    @GetMapping("/board/{category-id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getBoardStat(@Positive @PathVariable ("category-id") Long categoryId) {
        List<Stat> boardList = statService.getBoardByRecent(categoryId);

        List<StatDto.boardResponse> responses = statMapper.statListToStatDtoBoardResponseList(boardList);

        return ResponseEntity.ok().body(ApiResponse.ok("data", responses));
    }
    @GetMapping("/physical")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getWeightStat(@AuthenticationPrincipal UserPrincipal userPrincipal){
        List<Stat> allWeightByUserId = statService.getWeightByUserId(userPrincipal.getUser().getUserId());

        List<StatDto.physicalResponse> responses = statMapper.statListToStatDtoPhysicalResponseList((allWeightByUserId));

        return ResponseEntity.ok().body(ApiResponse.ok("data", responses));
    }

}
