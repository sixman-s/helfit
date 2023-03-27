package sixman.helfit.domain.calendar.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.util.LinkedMultiValueMap;
import sixman.helfit.domain.calendar.dto.CalendarDto;
import sixman.helfit.domain.calendar.entity.Calendar;
import sixman.helfit.domain.calendar.mapper.CalendarMapper;
import sixman.helfit.domain.calendar.service.CalendarService;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.restdocs.ControllerTest;
import sixman.helfit.restdocs.annotations.WithMockUserCustom;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sixman.helfit.restdocs.custom.CustomRequestFieldsSnippet.*;

@WebMvcTest(CalendarController.class)
class CalendarControllerTest extends ControllerTest {
    final String DEFAULT_URL = "/api/v1/calendar";

    @MockBean
    CalendarService calendarService;

    @MockBean
    CalendarMapper calendarMapper;

    private Calendar calendar;
    private List<Calendar> calendarList;
    private CalendarDto.Response calendarDtoResponse;
    private List<CalendarDto.Response> calendarDtoResponseList;

    @BeforeEach
    void setUp() {
        calendar = new Calendar(
            1L,
            "캘린더",
            "캘린더 내용",
            2000,
            "2023-01-01"
        );

        calendarDtoResponse = new CalendarDto.Response(
            calendar.getCalendarId(),
            calendar.getTitle(),
            calendar.getContent(),
            calendar.getKcal(),
            calendar.getRecodedAt()
        );

        calendarList = new ArrayList<>() {{
            add(calendar);
        }};

        calendarDtoResponseList = new ArrayList<>() {{
            add(calendarDtoResponse);
        }};
    }

    @Test
    @DisplayName("[테스트] 캘린더 등록")
    @WithMockUserCustom
    void postCalendarTest() throws Exception {
        given(calendarService.createCalendar(any(Calendar.class), any(User.class)))
            .willReturn(calendar);

        given(calendarMapper.calendarDtoPostToCalendar(any(CalendarDto.Post.class)))
            .willReturn(calendar);

        postResource(DEFAULT_URL, new CalendarDto.Post(
            calendar.getTitle(),
            calendar.getContent(),
            calendar.getKcal(),
            calendar.getRecodedAt()
        ))
            .apply(true)
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andDo(restDocs.document(
                customRequestFields(CalendarDto.Post.class, new LinkedHashMap<>() {{
                  put("title", "캘린더 제목, String");
                  put("content", "캘린더 내용, String");
                  put("kcal", "칼로리, Number");
                  put("recodedAt", "캘린더 등록일자, String");
                }})
            ));
    }

    @Test
    @DisplayName("[테스트] 캘린더 개별 조회")
    @WithMockUserCustom
    void getCalendarTest() throws Exception {
        given(calendarService.findCalendarByUserId(anyLong(), anyLong()))
            .willReturn(calendar);

        given(calendarMapper.calendarToCalendarDtoResponse(any(Calendar.class)))
            .willReturn(calendarDtoResponse);

        getResource(DEFAULT_URL + "/{calendar-id}", 1L)
            .apply(true)
            .andExpect(status().isOk())
            .andDo(restDocs.document(
                pathParameters(
                    parameterWithName("calendar-id").description("캘린더 식별자")
                ),
                genRelaxedResponseHeaderFields(),
                genRelaxedResponseBodyFields("body.data")
            ));

    }

    @Test
    @DisplayName("[테스트] 캘린더 등록일 기준 조회")
    @WithMockUserCustom
    void getCalendarWithQueryTest() throws Exception {

        given(calendarService.findCalendarByUserIdAndRecodedAt(anyLong(), anyString()))
            .willReturn(calendar);

        given(calendarMapper.calendarToCalendarDtoResponse(any(Calendar.class)))
            .willReturn(calendarDtoResponse);

        getResource(DEFAULT_URL, new LinkedMultiValueMap<>() {{
            add("recodedAt", "2023-01-01");
        }})
            .apply(true)
            .andExpect(status().isOk())
            .andDo(restDocs.document(
                requestParameters(
                    parameterWithName("recodedAt").description("질문 등록일자")
                ),
                genRelaxedResponseHeaderFields(),
                genRelaxedResponseBodyFields("body.data")
            ));
    }

    @Test
    @DisplayName("[테스트] 캘린더 전체 조회")
    @WithMockUserCustom
    void getAllCalendarTest() throws Exception {
        given(calendarService.findAllCalendarByUserId(anyLong()))
            .willReturn(calendarList);

        given(calendarMapper.calendarListToCalendarDtoResponseList(anyList()))
            .willReturn(calendarDtoResponseList);

        getResource(DEFAULT_URL)
            .apply(true)
            .andExpect(status().isOk())
            .andDo(restDocs.document(
                genRelaxedResponseHeaderFields(),
                genRelaxedResponseBodyFields("body.data[]")
            ));
    }

    @Test
    @DisplayName("[테스트] 캘린더 수정")
    @WithMockUserCustom
    void updateCalendarTest() throws Exception {
        given(calendarService.findCalendarByUserId(anyLong(), anyLong()))
            .willReturn(calendar);

        given(calendarService.updateCalendar(any(Calendar.class), any(Calendar.class)))
            .willReturn(calendar);

        given(calendarMapper.calendarDtoPatchToCalendar(any(CalendarDto.Patch.class)))
            .willReturn(calendar);

        given(calendarMapper.calendarToCalendarDtoResponse(any(Calendar.class)))
            .willReturn(calendarDtoResponse);

        patchResource(DEFAULT_URL + "/{calender-id}", new CalendarDto.Patch(
            "캘린더 수정 제목",
            "캘린더 수정 내용",
            3400
        ), calendar.getCalendarId())
            .apply(true)
            .andExpect(status().isOk())
            .andDo(restDocs.document(
                customRequestFields(CalendarDto.Patch.class, new LinkedHashMap<>() {{
                    put("title", "캘린더 제목, String");
                    put("content", "캘린더 내용, String");
                    put("kcal", "칼로리, Number");
                }}),
                genRelaxedResponseHeaderFields(),
                genRelaxedResponseBodyFields("body.data")
            ));
    }

    @Test
    @DisplayName("[테스트] 캘린더 삭제")
    @WithMockUserCustom
    void deleteCalendarTest() throws Exception {
        given(calendarService.findCalendarByUserId(anyLong(), anyLong()))
            .willReturn(calendar);

        doNothing().when(calendarService).deleteCalendar(any(Calendar.class));

        deleteResource(DEFAULT_URL + "/{calendar-id}", null, calendar.getCalendarId())
            .apply(true)
            .andExpect(status().isNoContent())
            .andDo(restDocs.document());
    }

    private ResponseFieldsSnippet genRelaxedResponseHeaderFields() {
        return relaxedResponseFields(
            beneathPath("header").withSubsectionId("header"),
            fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지")
        );
    }

    private ResponseFieldsSnippet genRelaxedResponseBodyFields(String beneath) {
        return relaxedResponseFields(
            beneathPath(beneath).withSubsectionId("data"),
            fieldWithPath("calendarId").type(JsonFieldType.NUMBER).description("캘린더 식별자"),
            fieldWithPath("title").type(JsonFieldType.STRING).description("캘린더 제목"),
            fieldWithPath("content").type(JsonFieldType.STRING).description("캘린더 내용"),
            fieldWithPath("recodedAt").type(JsonFieldType.STRING).description("캘린더 등록일자")
        );
    }
}
