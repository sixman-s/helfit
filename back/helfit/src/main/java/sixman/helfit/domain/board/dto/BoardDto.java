package sixman.helfit.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import sixman.helfit.domain.comment.dto.CommentDto;

import sixman.helfit.domain.tag.dto.TagDto;
import sixman.helfit.global.annotations.NoKoreanCurseWords;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public class BoardDto {
    @Getter
    @AllArgsConstructor
    public static class Post {
        @NotNull
        @Size(min = 1, max = 2000)
        //@NoKoreanCurseWords
        private String title;

        @NotNull
        @Size(min = 1, max = 16383)
        //@NoKoreanCurseWords
        private String text;

        @Nullable
        private String boardImageUrl;

        @Nullable
       //@NoKoreanCurseWords
        @Size(max = 2000)
        private List<String> boardTags;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private long boardId;
        private long categoryId;
        private long userId;
        private long view;
        private int likesCount;
        private List<BoardDto.BoardLikeMember> likeUserInfo;
        private String  userProfileImage;
        private String userNickname;
        private String title;
        private String text;
        private String boardImageUrl;
        private List<TagDto.GetResponse> tags;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        @Size(min = 1, max = 2000)
        //@NoKoreanCurseWords
        private String title;
        @Size(min = 1, max = 20000)
        //@NoKoreanCurseWords
        private String text;
        private String boardImageUrl;
        private List<String> boardTags;
    }

    @Getter
    @AllArgsConstructor
    public static class View {
        private long view;
    }

    @AllArgsConstructor
    @Getter
    public static class BoardListResponse {
        private List<BoardDto.Response> boardResponses;
        private int count;
    }

    @AllArgsConstructor
    @Getter
    public static class BoardLikeMember {
        private Long userId;
        @Nullable
        private String userProfileImgUrl;
    }
}
