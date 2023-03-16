package sixman.helfit.domain.tag.mapper;

import org.mapstruct.Mapper;
import sixman.helfit.domain.tag.dto.TagDto;
import sixman.helfit.domain.tag.entity.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {

    Tag tagGetToTag(TagDto.Get getDto);
    TagDto.GetResponse tagToTagResponse(Tag tag);

}
