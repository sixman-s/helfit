package sixman.helfit.restdocs.enums;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sixman.helfit.global.enums.EnumType;
import sixman.helfit.response.ApiResponse;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static sixman.helfit.domain.user.entity.User.*;

@RestController
@RequestMapping("/api/v1/test")
public class EnumController {

    @GetMapping("/enums")
    public EnumDto<?> enums() {
        Map<String, String> userStatus = getDocs(UserStatus.values());

        return EnumDto.of(EnumDocs.builder()
                              .userStatus(userStatus)
                              .build());
    }

    private Map<String, String> getDocs(EnumType[] enumTypes) {
        return Arrays.stream(enumTypes)
                   .collect(Collectors.toMap(EnumType::getName, EnumType::getDescription));
    }
}
