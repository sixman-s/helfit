package sixman.helfit.domain.tag.service;

import org.springframework.stereotype.Service;
import sixman.helfit.domain.tag.entity.Tag;
import sixman.helfit.domain.tag.repository.TagRepository;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag createTag(Tag tag){
        return tagRepository.save(tag);
    }
}
