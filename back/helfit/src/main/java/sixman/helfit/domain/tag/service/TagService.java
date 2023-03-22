package sixman.helfit.domain.tag.service;

import org.springframework.stereotype.Service;
import sixman.helfit.domain.tag.entity.Tag;
import sixman.helfit.domain.tag.repository.TagRepository;
import sixman.helfit.exception.BusinessLogicException;

import java.util.Optional;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }


    public Tag findTag(Tag tag) {
        Optional<Tag> optionalTag = tagRepository.findByTagName(tag.getTagName());
        if(optionalTag.isEmpty()){
            return tagRepository.save(tag);
        }
        else return optionalTag.get();
    }

    public void saveTag(Tag tag) {
        tagRepository.save(tag);
    }
}
