package sixman.helfit.domain.map.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sixman.helfit.domain.map.entity.Map;
import sixman.helfit.domain.map.repository.MapRepository;
import sixman.helfit.domain.user.entity.User;
import sixman.helfit.domain.user.service.UserService;
import sixman.helfit.exception.BusinessLogicException;
import sixman.helfit.exception.ExceptionCode;
import sixman.helfit.security.entity.UserPrincipal;
import sixman.helfit.utils.CustomBeanUtil;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class MapService {
    private final UserService userService;
    private final MapRepository mapRepository;
    private final CustomBeanUtil<Map> customBeanUtil;



    public MapService(UserService userService, MapRepository mapRepository, CustomBeanUtil<Map> customBeanUtil) {
        this.userService = userService;
        this.mapRepository = mapRepository;
        this.customBeanUtil = customBeanUtil;
    }

    public Map createReview(Map map, User user) {
        map.setUser(user);
        map.setMapId(map.getMapId());
        map.setStar(map.getStar());
        map.setReview(map.getReview());
        return mapRepository.save(map);
    }

    @Transactional(readOnly = true)
    public void verifyReview(Map map, UserPrincipal userPrincipal) {
        User user = userService.findUserByUserId(map.getUser().getUserId());
        if (!Objects.equals(user.getUserId(), userPrincipal.getUser().getUserId())) {
            throw new BusinessLogicException(ExceptionCode.MISS_MATCH_USERID);
        }
    }

    public Map findUserReview(Long userId) {
        Optional<Map> optionalMap = mapRepository.findByUserId(userId);
        if (optionalMap.isPresent()) {
            Map map = optionalMap.get();
            return map;
        } else {
            throw new BusinessLogicException(ExceptionCode.MISS_MATCH_USERID);
        }
    }
    public Map updateMap(Map requestMap, Map verifiedMap){
        Map updatedMap = customBeanUtil.copyNonNullProperties(requestMap, verifiedMap);
        return mapRepository.save(updatedMap);
    }

    public Map updateReview(Map map, User user) {
        map.setUser(user);
        map.setStar(map.getStar());
        map.setReview(map.getReview());
        map.setModifiedAt(LocalDateTime.now());

        return mapRepository.save(map);
    }

    public void deleteReview(Long mapId){
        Map findReview = findVerifyReview(mapId);
        mapRepository.delete(findReview);
    }

    public Map findVerifyReview(Long mapId){
        Optional<Map> optionalMap =
                mapRepository.findByMapId(mapId);
        return optionalMap.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MISS_MATCH_MAPID));
    }

    public Map findVerifiedMapWithUserId(Long mapId, Long userId) {
        Optional<Map> byMapId = mapRepository.findByMapId(mapId);

        Map map = byMapId.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MISS_MATCH_MAPID));

        if (!map.getUser().getUserId().equals(userId))
            throw new BusinessLogicException(ExceptionCode.MISS_MATCH_USERID);

        return map;
    }


}
