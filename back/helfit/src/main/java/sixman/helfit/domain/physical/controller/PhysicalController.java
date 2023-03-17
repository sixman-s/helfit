package sixman.helfit.domain.physical.controller;

import lombok.RequiredArgsConstructor;
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
import sixman.helfit.utils.UriUtil;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/physical")
@RequiredArgsConstructor
@Validated
public class PhysicalController {
    private final String DEFAULT_URI = "/api/v1/physical";

    private final PhysicalService physicalService;
    private final PhysicalMapper physicalMapper;

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

        return ResponseEntity.created(uri).build();
    }

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

    @GetMapping("/today")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getPhysicalWithinToday(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Physical physical = physicalService.findPhysicalByUserIdWithinToday(userPrincipal.getUser().getUserId());

        PhysicalDto.Response response = physicalMapper.physicalToPhysicalDtoResponse(physical);

        return ResponseEntity.ok().body(ApiResponse.ok("data", response));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getAllPhysicalWithinToday(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Physical physical = physicalService.findAllPhysicalByUserId(userPrincipal.getUser().getUserId());

        PhysicalDto.Response response = physicalMapper.physicalToPhysicalDtoResponse(physical);

        return ResponseEntity.ok().body(ApiResponse.ok("data", response));
    }
}
