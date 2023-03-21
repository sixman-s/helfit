package sixman.helfit.domain.statistics.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sixman.helfit.domain.statistics.Dto.StatDto;
import sixman.helfit.domain.statistics.entity.Stat;
import sixman.helfit.domain.statistics.mapper.StatMapper;
import sixman.helfit.domain.statistics.service.StatService;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.restdocs.ControllerTest;
import sixman.helfit.restdocs.annotations.WithMockUserCustom;
import sixman.helfit.security.entity.ProviderType;
import sixman.helfit.security.entity.RoleType;
import sixman.helfit.security.entity.UserPrincipal;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(StatControllerTest.class)
public class StatControllerTest extends ControllerTest {
    @MockBean
    StatService statService;

    @MockBean
    StatMapper statMapper;

    @Autowired
    private MockMvc mockMvc;

@Test
@DisplayName("[테스트] 칼로리, 생성일자 : LOCAL")
@WithMockUserCustom
void getKcalStatTest() throws Exception {
// given
    List<StatDto.calendarResponse> responseList = new ArrayList<>();
    StatDto.calendarResponse GetKcal = new StatDto.calendarResponse(1000, "2023-03-21");
        responseList.add(GetKcal);

    given(statService.getCalendarByUserId(anyLong())).willReturn(new ArrayList<>());
    given(statMapper.statListToStatDtoResponseList(anyList())).willReturn(responseList);

    // when/then
        mockMvc.perform(
    get("/api/v1/stat/calendar/{user-id}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].kcal").value(GetKcal.getKcal()))
            .andExpect(jsonPath("$.data[0].recodedAt").value(GetKcal.getRecodedAt()));
}

}