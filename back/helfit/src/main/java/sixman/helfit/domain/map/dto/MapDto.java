package sixman.helfit.domain.map.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sixman.helfit.domain.map.enums.Star;

import java.time.LocalDateTime;

public class MapDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post{
        private Star star;
        private String review;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch{
        private Star star;
        private String review;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class PostResponse{
        private Long mapId;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response{
        private Long mapId;
        private Star star;
        private String review;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
