package sixman.helfit.domain.calendar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sixman.helfit.domain.calendar.dto.CalendarDto;
import sixman.helfit.domain.calendar.entity.Calendar;
import sixman.helfit.domain.calendar.mapper.CalendarMapper;
import sixman.helfit.domain.calendar.service.CalendarService;
import sixman.helfit.response.ApiResponse;
import sixman.helfit.security.entity.UserPrincipal;
import sixman.helfit.utils.UriUtil;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/calendar")
@Validated
public class CalendarController {
    private final String DEFAULT_URL = "/api/v1/calendar";

    private final CalendarService calendarService;
    private final CalendarMapper calendarMapper;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> postCalendar(
        @Valid @RequestBody CalendarDto.Post requestBody,
        @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        Calendar calendar = calendarService.createCalendar(
            calendarMapper.calendarDtoPostToCalendar(requestBody),
            userPrincipal.getUser()
        );

        URI uri = UriUtil.createUri(DEFAULT_URL, calendar.getCalendarId());

        return ResponseEntity.created(uri).body(ApiResponse.created());
    }

    @GetMapping("{calendar-id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getCalendar(@Positive @PathVariable("calendar-id") Long calendarId) {
        Calendar verifiedCalendar = calendarService.findVerifiedCalendar(calendarId);

        CalendarDto.Response response = calendarMapper.calendarToCalendarDtoResponse(verifiedCalendar);

        return ResponseEntity.ok().body(ApiResponse.ok("data", response));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getAllCalendar(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<Calendar> allCalendarByUserId = calendarService.findAllCalendarByUserId(userPrincipal.getUser().getUserId());

        List<CalendarDto.Response> responses = calendarMapper.calendarListToCalendarDtoResponseList(allCalendarByUserId);

        return ResponseEntity.ok().body(ApiResponse.ok("data", responses));
    }

    @PatchMapping("{calendar-id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateCalendar(
        @Positive @PathVariable("calendar-id") Long calendarId,
        @RequestBody CalendarDto.Patch requestBody
    ) {
        Calendar calendar = calendarService.updateCalendar(calendarId, calendarMapper.calendarDtoPatchToCalendar(requestBody));

        CalendarDto.Response response = calendarMapper.calendarToCalendarDtoResponse(calendar);

        return ResponseEntity.ok().body(ApiResponse.ok("data", response));
    }

    @DeleteMapping("{calendar-id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateCalendar(
        @Positive @PathVariable("calendar-id") Long calendarId
    ) {
        calendarService.deleteCalendar(calendarId);

        return ResponseEntity.noContent().build();
    }
}
