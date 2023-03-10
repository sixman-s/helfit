package sixman.helfit.domain.tag.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sixman.helfit.domain.tag.dto.TagDto;
import sixman.helfit.domain.tag.entity.Tag;
import sixman.helfit.domain.tag.mapper.TagMapper;
import sixman.helfit.domain.tag.service.TagService;
import sixman.helfit.response.ApiResponse;
import sixman.helfit.utils.UriUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

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

    @GetMapping
    public ResponseEntity getTag(@Valid @RequestBody TagDto.Get requestBody) {
        Tag tag = tagService.findTag(mapper.tagGetToTag(requestBody));

        return new ResponseEntity<>(mapper.tagToTagResponse(tag), HttpStatus.OK);
    }
}
