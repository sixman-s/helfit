package sixman.helfit.domain.statistics.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sixman.helfit.domain.calendar.dto.CalendarDto;
import sixman.helfit.domain.statistics.Dto.StatDto;
import sixman.helfit.domain.statistics.entity.Stat;

import java.util.Calendar;
import java.util.List;

@Mapper(componentModel = "spring")
public interface StatMapper {
    @Mapping(target = "kcal", source = "calendar.kcal")
    @Mapping(target = "recodedAt", source = "calendar.recodedAt")
    StatDto.calendarResponse statToStatDtoResponse(Stat stat);

    List<StatDto.calendarResponse> statListToStatDtoResponseList(List<Stat> stats);

    List<StatDto.boardResponse> statListToStatDtoBoardResponseList(List<Stat> stats);

    //Page<StatDto.calendarResponse> statPageToStatDtoResponsePage(Page<Stat> stats);
}
