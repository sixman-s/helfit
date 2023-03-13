package sixman.helfit.domain.calendar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sixman.helfit.domain.calendar.dto.CalendarDto;
import sixman.helfit.domain.calendar.entity.Calendar;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CalendarMapper {
    CalendarMapper INSTANCE = Mappers.getMapper(CalendarMapper.class);

    Calendar calendarDtoPostToCalendar(CalendarDto.Post requestBody);
    Calendar calendarDtoPatchToCalendar(CalendarDto.Patch requestBody);

    CalendarDto.Response calendarToCalendarDtoResponse(Calendar calendar);
    List<CalendarDto.Response> calendarListToCalendarDtoResponseList(List<Calendar> calendarList);
}
