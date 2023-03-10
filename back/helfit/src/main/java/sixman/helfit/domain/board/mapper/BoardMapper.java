package sixman.helfit.domain.board.mapper;

import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import sixman.helfit.domain.board.dto.BoardDto;
import sixman.helfit.domain.board.entity.Board;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    Board boardPostToBoard(BoardDto.Post postDto);

}
