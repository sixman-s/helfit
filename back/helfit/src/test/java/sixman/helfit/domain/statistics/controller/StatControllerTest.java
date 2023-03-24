package sixman.helfit.domain.statistics.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import sixman.helfit.domain.board.entity.Board;
import sixman.helfit.domain.board.entity.BoardTag;
import sixman.helfit.domain.calculator.entity.Calculator;
import sixman.helfit.domain.calculator.enums.ActivityLevel;
import sixman.helfit.domain.calculator.enums.Goal;
import sixman.helfit.domain.calendar.entity.Calendar;
import sixman.helfit.domain.category.entity.Category;
import sixman.helfit.domain.like.entity.Like;
import sixman.helfit.domain.physical.entity.Physical;
import sixman.helfit.domain.physical.repository.PhysicalRepositoryImpl;
import sixman.helfit.domain.statistics.dto.StatDto;
import sixman.helfit.domain.statistics.entity.Stat;
import sixman.helfit.domain.statistics.mapper.StatMapper;
import sixman.helfit.domain.statistics.service.StatService;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.restdocs.ControllerTest;
import sixman.helfit.restdocs.annotations.WithMockUserCustom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatController.class)
public class StatControllerTest extends ControllerTest {
    private String DEFAULT_URL = "/api/v1/stat";
    @MockBean
    StatService statService;

    @MockBean
    StatMapper statMapper;

    private User user;
    private Stat stat;
    private Calendar calendar;
    private Calculator calculator;
    private Category category;
    private Physical physical;
    private Board board;
    private List<BoardTag> boardTags;
    private List<Like> likes;
    private StatDto.calendarResponse calendarResponse;
    private StatDto.boardResponse boardResponse;
    private StatDto.physicalResponse physicalResponse;
    private List<Stat> calendarStatList;
    private List<StatDto.calendarResponse> calendarResponseList;
    private List<Stat> boardStatList;
    private List<StatDto.boardResponse> boardResponseList;
    private List<Stat> weightStatList;
    private List<StatDto.physicalResponse> weigthResponseList;

    @BeforeEach
    void setup() {
        Map<String, Object> userResource = userResource();
        user = (User) userResource.get("user");
        calendar = new Calendar(1L, "title", "content", 2500, "2023-03-24", user);
        physical = new Physical(1L, 19961213, 173, 78, Physical.Gender.MALE, user);
        calculator = new Calculator(1L, Goal.DIET, ActivityLevel.VERY_ACTIVE, 3011.0, user, physical);
        category = new Category(5L, "오운완");
        board = new Board(1L, "title", "text", "", category, user, boardTags, likes, 10);
        stat = new Stat(1L, user, calendar, calculator, category, calendar.getKcal(), calendar.getRecodedAt(),
                board.getBoardImageUrl(), "title", "text", 78, "2023-03-24", 1L);
        calendarStatList = new ArrayList<>(Arrays.asList(stat, stat, stat));
        calendarResponseList = new ArrayList<>(Arrays.asList(calendarResponse, calendarResponse));

        boardStatList = new ArrayList<>(Arrays.asList(stat, stat, stat));
        boardResponseList = new ArrayList<>(Arrays.asList(boardResponse, boardResponse));

        weightStatList = new ArrayList<>(Arrays.asList(stat, stat, stat));
        weigthResponseList = new ArrayList<>(Arrays.asList(physicalResponse, physicalResponse));

        calendarResponse = new StatDto.calendarResponse(2500, "2023-03-24");
        boardResponse = new StatDto.boardResponse(1L, "", "title", "text");
        physicalResponse = new StatDto.physicalResponse(78, "2023-03-24");
    }

    @Test
    @DisplayName("[테스트] 칼로리, 생성일자 : LOCAL")
    @WithMockUserCustom
    void getKcalStatTest() throws Exception {

        given(statService.getCalendarByUserId(Mockito.anyLong()))
                .willReturn(calendarStatList);
        given(statMapper.statListToStatDtoResponseList(Mockito.anyList()))
                .willReturn(calendarResponseList);
        getResource(DEFAULT_URL + "/calendar/{user-id}", 1L)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.data").isNotEmpty());
    }

    @Test
    @DisplayName("[테스트] 오운완 게시판 최근게시물 3개 조회 : LOCAL")
    @WithMockUserCustom
    void getBoardStatTest() throws Exception {
        given(statService.getBoardByRecent(Mockito.anyLong()))
                .willReturn(boardStatList);
        given(statMapper.statListToStatDtoBoardResponseList(Mockito.anyList()))
                .willReturn(boardResponseList);
        getResource(DEFAULT_URL + "/board/{category-id}", 5)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.data").isNotEmpty());
    }

    @Test
    @DisplayName("[테스트] 사용자의 최근 몸무게 수정 조회 : LOCAL")
    @WithMockUserCustom
    void getWeightStatTest() throws Exception {
        given(statService.getWeightByUserId(Mockito.anyLong()))
                .willReturn(weightStatList);
        given(statMapper.statListToStatDtoPhysicalResponseList(Mockito.anyList()))
                .willReturn(weigthResponseList);
        getResource(DEFAULT_URL + "/physical")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.data").isNotEmpty());
    }
}