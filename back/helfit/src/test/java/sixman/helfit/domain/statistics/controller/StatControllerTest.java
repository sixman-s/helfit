package sixman.helfit.domain.statistics.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import sixman.helfit.domain.statistics.Dto.StatDto;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class StatControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Test
    void getKcalStatTest() throws Exception{
        //given
        StatDto.calendarResponse calendarResponse = new StatDto.calendarResponse(1000, "2023-03-21");
        String getContent = gson.toJson(calendarResponse);

        ResultActions getActions =
                mockMvc.perform(
                        get("api/v1/stat/calendar/{user-id}",2)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getContent)
                );
        String location = getActions.andReturn().getResponse().getHeader("Location");

        //when/then
        mockMvc.perform(
                get(location)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.kcal").value(calendarResponse.getKcal()))
                .andExpect(jsonPath("$.data.recodedAt").value(calendarResponse.getRecodedAt()));
    }

}