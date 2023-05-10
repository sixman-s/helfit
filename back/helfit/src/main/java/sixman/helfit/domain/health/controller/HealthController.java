package sixman.helfit.domain.health.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/health")
@RequiredArgsConstructor
public class HealthController {

    private final Environment env;

    @GetMapping
    public String getActiveProfile() {
        List<String> profiles = Arrays.asList(env.getActiveProfiles());
        List<String> activeProfiles = Arrays.asList("blue", "green");
        String defaultProfile = profiles.isEmpty() ? "default" : profiles.get(profiles.size() - 1);

        return profiles.stream()
                   .filter(activeProfiles::contains)
                   .findAny()
                   .orElse(defaultProfile);
    }
}
