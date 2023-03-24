package sixman.helfit.domain.tag.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sixman.helfit.domain.tag.entity.Tag;
import sixman.helfit.domain.tag.repository.TagRepository;

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
            return saveTag(tag);
        }
        else return optionalTag.get();
    }
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }


}
