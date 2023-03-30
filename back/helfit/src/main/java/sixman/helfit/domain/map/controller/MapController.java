package sixman.helfit.domain.map.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import sixman.helfit.domain.map.dto.MapDto;
import sixman.helfit.domain.map.entity.Map;
import sixman.helfit.domain.map.mapper.MapMapper;
import sixman.helfit.domain.map.service.MapService;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.response.ApiResponse;
import sixman.helfit.security.entity.UserPrincipal;
import sixman.helfit.utils.UriUtil;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/maps")
@RequiredArgsConstructor
public class MapController {
    private final static String MAP_DEFAULT_URL = "/api/v1/maps";
    private final MapMapper mapper;
    private final MapService mapService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<?> postReview(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                        @Valid @RequestBody MapDto.Post requestBody){
        Map map = mapService.createReview(mapper.mapPostToMap(requestBody),userPrincipal.getUser());
        URI location = UriUtil.createUri(MAP_DEFAULT_URL, map.getMapId());
        MapDto.PostResponse response = mapper.mapToPostResponse(map);
        return ResponseEntity.created(location).body(ApiResponse.created("data", response));
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{map-id}")
    public ResponseEntity<?> getReview(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                       @PathVariable("map-id") @Positive Long mapId){
        User user = userPrincipal.getUser();
        Map verifiedMapWithUserId = mapService.findVerifiedMapWithUserId(mapId,user.getUserId());
        MapDto.Response response = mapper.mapToResponse(verifiedMapWithUserId);
        return ResponseEntity.ok().body(ApiResponse.ok("data",response));
    }
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{map-id}")
    public ResponseEntity<?> patchReview(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                         @PathVariable("map-id")@Positive Long mapId,
                                         @Valid @RequestBody MapDto.Patch requestBody){
        User user = userPrincipal.getUser();

        Map verifiedReview = mapService.findVerifiedMapWithUserId(mapId,user.getUserId());

        Map map = mapService.updateMap(mapper.mapPatchToMap(requestBody),verifiedReview);

        Map afterReview = mapService.updateReview(map, user);

        MapDto.Response response = mapper.mapToResponse(afterReview);

        return ResponseEntity.ok().body(ApiResponse.ok("data", response));
    }
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{map-id}")
    public ResponseEntity<?> deleteReview(@PathVariable("map-id") @Positive Long mapId){
        mapService.deleteReview(mapId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
