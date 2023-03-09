package sixman.helfit.domain.tag.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sixman.helfit.domain.tag.dto.TagDto;
import sixman.helfit.domain.tag.entity.Tag;
import sixman.helfit.domain.tag.mapper.TagMapper;
import sixman.helfit.domain.tag.service.TagService;
import sixman.helfit.response.ApiResponse;
import sixman.helfit.utils.UriUtil;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/tag")
public class TagController {
    private final static String TAG_DEFAULT_URL = "/api/v1/tag";
    private final TagService tagService;
    private final TagMapper mapper;

    public TagController(TagService tagService, TagMapper mapper) {
        this.tagService = tagService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postTag(@Valid @RequestBody TagDto.Post requestBody) {
        Tag tag = tagService.createTag(mapper.tagPostToTag(requestBody));
        URI location = UriUtil.createUri(TAG_DEFAULT_URL, tag.getTagId());
        return ResponseEntity.created(location).build();
    }
}
