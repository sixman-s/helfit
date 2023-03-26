package sixman.helfit.domain.physical.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import sixman.helfit.domain.physical.dto.PhysicalDto;
import sixman.helfit.domain.physical.entity.Physical;
import sixman.helfit.domain.physical.mapper.PhysicalMapper;
import sixman.helfit.domain.physical.service.PhysicalService;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.restdocs.ControllerTest;
import sixman.helfit.restdocs.annotations.WithMockUserCustom;
import sixman.helfit.restdocs.custom.CustomRequestFieldsSnippet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sixman.helfit.restdocs.custom.CustomRequestFieldsSnippet.customRequestFields;

@WebMvcTest(PhysicalController.class)
class PhysicalControllerTest extends ControllerTest {
    String DEFAULT_URI = "/api/v1/physical";

    @MockBean
    PhysicalService physicalService;

    @MockBean
    PhysicalMapper physicalMapper;

    private Physical physical;
    private PhysicalDto.Response physicalDtoResponse;
    private Page<Physical> physicalPage;
    private List<PhysicalDto.Response> physicalDtoReponseList;

    @BeforeEach
    void setup() {
        physical = new Physical(
            20020101,
            180,
            80,
            Physical.Gender.MALE
        );
        physical.setPhysicalId(1L);

        physicalDtoResponse = new PhysicalDto.Response(
            physical.getPhysicalId(),
            physical.getBirth(),
            physical.getHeight(),
            physical.getWeight(),
            physical.getGender()
        );

        physicalPage = new PageImpl<>(List.of(physical));
        physicalDtoReponseList = new ArrayList<>() {{
            add(physicalDtoResponse);
        }};
    }

    @Test
    @DisplayName("[테스트] 회원 개인(신체정보) 생성")
    @WithMockUserCustom
    void postPhysicalTest() throws Exception {
        given(physicalService.createPhysical(any(Physical.class), any(User.class)))
            .willReturn(physical);

        given(physicalMapper.physicalDtoPostToPhysical(any(PhysicalDto.Post.class)))
            .willReturn(physical);

        given(physicalMapper.physicalToPhysicalDtoResponse(any(Physical.class)))
            .willReturn(physicalDtoResponse);

        postResource(DEFAULT_URI, new PhysicalDto.Post(
            physical.getBirth(),
            physical.getHeight(),
            physical.getWeight(),
            physical.getGender().name())
        )
            .andExpect(status().isCreated())
            .andDo(restDocs.document(
                customRequestFields(PhysicalDto.Post.class, new HashMap<>() {{
                    put("birth", "회원 생년월일, Number");
                    put("height", "회원 키, Number");
                    put("weight", "회원 몸무게, Number");
                    put("gender", "회원 성별, String");
                }}),
                genRelaxedResponseHeaderFields(),
                genRelaxedResponseBodyFields("body.data")
            ));
    }

    @Test
    @DisplayName("[테스트] 회원 개인(신체)정보 조회 (당일 기준)")
    @WithMockUserCustom
    void getPhysicalWithinTodayTest() throws Exception {
        given(physicalService.findPhysicalByUserIdWithinToday(anyLong()))
            .willReturn(physical);

        given(physicalMapper.physicalToPhysicalDtoResponse(any(Physical.class)))
            .willReturn(physicalDtoResponse);

        getResource(DEFAULT_URI + "/today")
            .andExpect(status().isOk())
            .andDo(restDocs.document(
                genRelaxedResponseHeaderFields(),
                genRelaxedResponseBodyFields("body.data")
            ));
    }

    @Test
    @DisplayName("[테스트] 회원 개인(신체)정보 조회 (최신 수정일 기준)")
    @WithMockUserCustom
    void getPhysicalTest() throws Exception {
        given(physicalService.findPhysicalByUserId(anyLong()))
            .willReturn(physical);

        given(physicalMapper.physicalToPhysicalDtoResponse(any(Physical.class)))
            .willReturn(physicalDtoResponse);

        getResource(DEFAULT_URI + "/recent")
            .andExpect(status().isOk())
            .andDo(restDocs.document(
                genRelaxedResponseHeaderFields(),
                genRelaxedResponseBodyFields("body.data")
            ));
    }

    @Test
    @DisplayName("[테스트] 회원 개인(신체)정보 조회 (페이징)")
    @WithMockUserCustom
    void getAllPhysicalTest() throws Exception {
        given(physicalService.findAllPhysicalByUserId(anyLong(), anyInt(), anyInt()))
            .willReturn(physicalPage);

        given(physicalMapper.physicalToPhysicalDtoResponseList(anyList()))
            .willReturn(physicalDtoReponseList);

        getResource(DEFAULT_URI, new LinkedMultiValueMap<>() {{
            add("page", "1");
            add("size", "10");
        }})
            .andExpect(status().isOk())
            .andDo(restDocs.document(
                genRelaxedResponseHeaderFields(),
                relaxedResponseFields(
                    beneathPath("body.data").withSubsectionId("data"),
                    fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("회원 신체정보 조회 페이지"),
                    fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("회원 신체정보 조회 크기"),
                    fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("회원 신체정보 조회 가능 페이지 개수"),
                    fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("회원 신체정보 신체정보 개수"),
                    fieldWithPath("physical[].physicalId").type(JsonFieldType.NUMBER).description("회원 신체정보 식별자"),
                    fieldWithPath("physical[].birth").type(JsonFieldType.NUMBER).description("회원 생년월일"),
                    fieldWithPath("physical[].height").type(JsonFieldType.NUMBER).description("회원 키"),
                    fieldWithPath("physical[].weight").type(JsonFieldType.NUMBER).description("회원 몸무게"),
                    fieldWithPath("physical[].gender").type(JsonFieldType.STRING).description("회원 성별")
                )
            ));
    }

    @Test
    @DisplayName("[테스트] 회원 개인(신체)정보 수정")
    @WithMockUserCustom
    void patchPhysicalTest() throws Exception {
        given(physicalService.updatePhysical(any(Physical.class), anyLong()))
            .willReturn(physical);

        given(physicalMapper.physicalDtoPatchToPhysical(any(PhysicalDto.Patch.class)))
            .willReturn(physical);

        given(physicalMapper.physicalToPhysicalDtoResponse(any(Physical.class)))
            .willReturn(physicalDtoResponse);

        patchResource(DEFAULT_URI, new PhysicalDto.Patch(
            physical.getBirth(),
            physical.getHeight(),
            physical.getWeight(),
            physical.getGender().name()
        ))
            .andExpect(status().isOk())
            .andDo(restDocs.document(
                customRequestFields(PhysicalDto.Patch.class, new LinkedHashMap<>() {{
                    put("birth", "회원 생년월일, Number");
                    put("height", "회원 키, Number");
                    put("weight", "회원 몸무게, Number");
                    put("gender", "회원 성별, String");
                }}),
                genRelaxedResponseHeaderFields(),
                genRelaxedResponseBodyFields("body.data")
            ));
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
            fieldWithPath("physicalId").type(JsonFieldType.NUMBER).description("회원 신체정보 식별자"),
            fieldWithPath("birth").type(JsonFieldType.NUMBER).description("회원 생년월일"),
            fieldWithPath("height").type(JsonFieldType.NUMBER).description("회원 키"),
            fieldWithPath("weight").type(JsonFieldType.NUMBER).description("회원 몸무게"),
            fieldWithPath("gender").type(JsonFieldType.STRING).description("회원 성별")
        );
    }
}
