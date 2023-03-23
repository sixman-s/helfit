package sixman.helfit.domain.statistics.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(StatControllerTest.class)
@AutoConfigureMockMvc
public class StatControllerTest extends ControllerTest {
    @MockBean
    StatService statService;

    @MockBean
    StatMapper statMapper;

    @Autowired
    private MockMvc mockMvc;
//    @BeforeEach
//    void setup() {
//        getResource()
//    }

//@Test
//@DisplayName("[테스트] 칼로리, 생성일자 : LOCAL")
//@WithMockUserCustom
//void getKcalStatTest() throws Exception {
//    Stat stat1 = new Stat().setKcal();
//    given(statService.getCalendarByUserId(Mockito.anyLong()))
//            .willReturn((List<Stat>) calendarStatList.get)
//}
}