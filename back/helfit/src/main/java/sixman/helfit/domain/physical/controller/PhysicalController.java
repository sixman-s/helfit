package sixman.helfit.domain.physical.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sixman.helfit.domain.physical.dto.PhysicalDto;
import sixman.helfit.domain.physical.entity.Physical;
import sixman.helfit.domain.physical.mapper.PhysicalMapper;
import sixman.helfit.domain.physical.service.PhysicalService;
import sixman.helfit.response.ApiResponse;
import sixman.helfit.security.entity.UserPrincipal;
import sixman.helfit.utils.PageUtil;
import sixman.helfit.utils.UriUtil;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/physical")
@RequiredArgsConstructor
@Validated
public class PhysicalController {
    private final String DEFAULT_URI = "/api/v1/physical";

    private final PhysicalService physicalService;
    private final PhysicalMapper physicalMapper;

    /*
     * # 회원 개인(신체정보) 생성
     *
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> postPhysical(
        @Valid @RequestBody PhysicalDto.Post requestBody,
        @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        Physical physical = physicalService.createPhysical(
            physicalMapper.physicalDtoPostToPhysical(requestBody),
            userPrincipal.getUser()
        );

        URI uri = UriUtil.createUri(DEFAULT_URI, physical.getPhysicalId());
        PhysicalDto.Response response = physicalMapper.physicalToPhysicalDtoResponse(physical);

        return ResponseEntity.created(uri).body(ApiResponse.created("data", response));
    }

    /*
     * # 회원 개인(신체)정보 조회 (당일 기준)
     *
     */
    @GetMapping("/today")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getPhysicalWithinToday(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Physical physical = physicalService.findPhysicalByUserIdWithinToday(userPrincipal.getUser().getUserId());

        PhysicalDto.Response response = physicalMapper.physicalToPhysicalDtoResponse(physical);

        return ResponseEntity.ok().body(ApiResponse.ok("data", response));
    }

    /*
     * # 회원 개인(신체)정보 조회 (최신 수정일 기준)
     *
     */
    @GetMapping("/recent")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getPhysical(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Physical physical = physicalService.findPhysicalByUserId(userPrincipal.getUser().getUserId());

        PhysicalDto.Response response = physicalMapper.physicalToPhysicalDtoResponse(physical);

        return ResponseEntity.ok().body(ApiResponse.ok("data", response));
    }


    /*
     * # 회원 개인(신체)정보 조회 (페이징)
     *
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getAllPhysical(
        @Positive @RequestParam Integer page,
        @Positive @RequestParam Integer size,
        @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        Page<Physical> physicalPage = physicalService.findAllPhysicalByUserId(userPrincipal.getUser().getUserId(), page, size);
        List<PhysicalDto.Response> physicalDtoReponseList = physicalMapper.physicalToPhysicalDtoResponseList(physicalPage.getContent());

        HashMap<String, Object> responses = new HashMap<>() {{
            put("physical", physicalDtoReponseList);
            put("pageInfo", PageUtil.getPageInfo(physicalPage));
        }};

        return ResponseEntity.ok().body(ApiResponse.ok("data", responses));
    }

    /*
     * # 회원 개인(신체)정보 수정
     *
     */
    @PatchMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> patchPhysical(
        @Valid @RequestBody PhysicalDto.Patch requestBody,
        @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        Physical physical = physicalService.updatePhysical(
            physicalMapper.physicalDtoPatchToPhysical(requestBody),
            userPrincipal.getUser().getUserId()
        );

        PhysicalDto.Response response = physicalMapper.physicalToPhysicalDtoResponse(physical);

        return ResponseEntity.ok().body(ApiResponse.ok("data", response));
    }
}
