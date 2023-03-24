package sixman.helfit.domain.statistics.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sixman.helfit.domain.board.entity.Board;
import sixman.helfit.domain.board.entity.BoardTag;
import sixman.helfit.domain.calculator.entity.Calculator;
import sixman.helfit.domain.calculator.enums.ActivityLevel;
import sixman.helfit.domain.calculator.enums.Goal;
import sixman.helfit.domain.calendar.entity.Calendar;
import sixman.helfit.domain.category.entity.Category;
import sixman.helfit.domain.like.entity.Like;
import sixman.helfit.domain.physical.entity.Physical;
import sixman.helfit.domain.statistics.Dto.StatDto;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
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
    private Stat calendarStat1;
    private Stat calendarStat2;
    private Stat calendarStat3;
    private Stat boardStat1;
    private Stat boardStat2;
    private Stat boardStat3;
    private Stat physicalStat1;
    private Stat physicalStat2;
    private Stat physicalStat3;
    private Calendar calendar;
    private Calculator calculator;
    private Category category;
    private Physical physical;
    private Board board;
    private List<BoardTag> boardTags;
    private List<Like> likes;
    private StatDto.calendarResponse calendarResponse1;
    private StatDto.calendarResponse calendarResponse2;
    private StatDto.calendarResponse calendarResponse3;
    private StatDto.boardResponse boardResponse1;
    private StatDto.boardResponse boardResponse2;
    private StatDto.boardResponse boardResponse3;
    private StatDto.physicalResponse physicalResponse1;
    private StatDto.physicalResponse physicalResponse2;
    private StatDto.physicalResponse physicalResponse3;
    private List<Stat> calendarStatList;
    private List<Stat> boardStatList;
    private List<Stat> physicalStatList;
    private List<StatDto.calendarResponse> calendarResponseList;
    private List<StatDto.boardResponse> boardResponseList;
    private List<StatDto.physicalResponse> physicalResponseList;

    @BeforeEach
    void setup() throws Exception{
        Map<String, Object> userResource = userResource();
        user = (User) userResource.get("user");
        calendar = new Calendar(1L, "title", "content", 2500, "2023-03-24", user);
        physical = new Physical(1L, 19961213, 173, 78, Physical.Gender.MALE, user);
        calculator = new Calculator(1L, Goal.DIET, ActivityLevel.VERY_ACTIVE, 3011.0, user, physical);
        category = new Category(5L, "오운완");
        board = new Board(1L, "title", "text", "", category, user, boardTags, likes, 10);
        calendarStat1 = new Stat(user, calendar, 2500, "2023-03-22");
        calendarStat2 = new Stat(user, calendar, 2600, "2023-03-23");
        calendarStat3 = new Stat(user, calendar, 2700, "2023-03-24");
        calendarStatList = new ArrayList<>(Arrays.asList(calendarStat1, calendarStat2, calendarStat3));
        calendarResponseList = new ArrayList<>();
        calendarResponse1 = new StatDto.calendarResponse(2500, "2023-03-22");
        calendarResponse2 = new StatDto.calendarResponse(2600, "2023-03-23");
        calendarResponse3 = new StatDto.calendarResponse(2700, "2023-03-24");
        calendarResponseList.addAll(Arrays.asList(calendarResponse1, calendarResponse2, calendarResponse3));
        boardStat1 = new Stat(1L, "", "title1", "text1");
        boardStat2 = new Stat(2L, "", "title2", "text2");
        boardStat3 = new Stat(3L, "", "title2", "text3");
        boardStatList = new ArrayList<>(Arrays.asList(boardStat1, boardStat2, boardStat3));
        boardResponseList = new ArrayList<>();
        boardResponse1 = new StatDto.boardResponse(1L, "", "title1", "text1");
        boardResponse2 = new StatDto.boardResponse(2L, "", "title2", "text2");
        boardResponse3 = new StatDto.boardResponse(3L, "", "title3", "text3");
        boardResponseList = new ArrayList<>(Arrays.asList(boardResponse1, boardResponse2, boardResponse3));
        physicalStat1 = new Stat(78, "2023-03-22");
        physicalStat2 = new Stat(77, "2023-03-23");
        physicalStat3 = new Stat(76, "2023-03-24");
        physicalStatList = new ArrayList<>(Arrays.asList(physicalStat1, physicalStat2, physicalStat3));
        physicalResponse1 = new StatDto.physicalResponse(78, "2023-03-22");
        physicalResponse2 = new StatDto.physicalResponse(77, "2023-03-23");
        physicalResponse3 = new StatDto.physicalResponse(76, "2023-03-24");
        physicalResponseList = new ArrayList<>(Arrays.asList(physicalResponse1, physicalResponse2, physicalResponse3));
    }

    @Test
    @DisplayName("[테스트] 칼로리, 생성일자 조회: LOCAL")
    @WithMockUserCustom
    void getKcalStatTest() throws Exception {

        given(statService.getCalendarByUserId(Mockito.anyLong()))
                .willReturn(calendarStatList);
        given(statMapper.statListToStatDtoResponseList(Mockito.anyList()))
                .willReturn(calendarResponseList);
        getResource(DEFAULT_URL + "/calendar/{user-id}", 1L)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.data").isNotEmpty())
                .andDo(restDocs.document(
                        genRelaxedResponseHeaderFields("header"),
                        genRelaxedCalendarResponseBodyFields("body.data")
                ));
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
                .andExpect(jsonPath("$.body.data").isNotEmpty())
                .andDo(restDocs.document(
                        genRelaxedResponseHeaderFields("header"),
                        genRelaxedBoardResponseBodyFields("body.data")
                ));
    }

    @Test
    @DisplayName("[테스트] 사용자의 최근 몸무게 수정 조회 : LOCAL")
    @WithMockUserCustom
    void getWeightStatTest() throws Exception {
        given(statService.getWeightByUserId(Mockito.anyLong()))
                .willReturn(physicalStatList);
        given(statMapper.statListToStatDtoPhysicalResponseList(Mockito.anyList()))
                .willReturn(physicalResponseList);
        getResource(DEFAULT_URL + "/physical")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.data").isNotEmpty())
                .andDo(restDocs.document(
                        genRelaxedResponseHeaderFields("header"),
                        genRelaxedPhysicalResponseBodyFields("body.data")
                ));
    }

    private ResponseFieldsSnippet genRelaxedResponseHeaderFields(String beneath) {
        return relaxedResponseFields(
                beneathPath(beneath).withSubsectionId("header"),
                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지")
        );
    }

    private ResponseFieldsSnippet genRelaxedCalendarResponseBodyFields(String beneath) {
        return relaxedResponseFields(
                beneathPath(beneath).withSubsectionId("data"),
                fieldWithPath("kcal").type(JsonFieldType.NUMBER).description("칼로리"),
                fieldWithPath("recodedAt").type(JsonFieldType.STRING).description("생성 일자")
        );
    }

    private ResponseFieldsSnippet genRelaxedBoardResponseBodyFields(String beneath) {
        return relaxedResponseFields(
                beneathPath(beneath).withSubsectionId("data"),
                fieldWithPath("boardId").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                fieldWithPath("boardImageUrl").type(JsonFieldType.STRING).description("게시글 사진"),
                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                fieldWithPath("text").type(JsonFieldType.STRING).description("게시글 내용")
        );
    }

    private ResponseFieldsSnippet genRelaxedPhysicalResponseBodyFields(String beneath) {
        return relaxedResponseFields(
                beneathPath(beneath).withSubsectionId("data"),
                fieldWithPath("weight").type(JsonFieldType.NUMBER).description("계산 결과"),
                fieldWithPath("lastModifiedAt").type(JsonFieldType.STRING).description("수정 일자")
        );
    }
}