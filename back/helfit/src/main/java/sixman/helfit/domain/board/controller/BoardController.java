package sixman.helfit.domain.board.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sixman.helfit.domain.board.dto.BoardDto;
import sixman.helfit.domain.board.entity.Board;
import sixman.helfit.domain.board.mapper.BoardMapper;
import sixman.helfit.domain.board.service.BoardService;
import sixman.helfit.response.ApiResponse;
import sixman.helfit.utils.UriUtil;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/board")
public class BoardController {
    private final static String BOARD_DEFAULT_URL = "/api/v1/boards";
    private final BoardMapper mapper;
    private final BoardService boardService;

    public BoardController(BoardMapper mapper, BoardService boardService) {
        this.mapper = mapper;
        this.boardService = boardService;
    }

    @PostMapping("/{category-id}/{user-id}")
    public ResponseEntity postBoards(@Positive @PathVariable ("category-id") long categoryId,
                                     @Positive @PathVariable ("user-id") long userId,
                                     @Valid @RequestBody BoardDto.Post requestBody){
        Board board = boardService.createBoard(mapper.boardPostToBoard(requestBody,categoryId,userId));
        URI location = UriUtil.createUri(BOARD_DEFAULT_URL,board.getBoardId());

        return ResponseEntity.created(location).body(ApiResponse.created());
    }

    @GetMapping()
    public ResponseEntity getBoards() {
        Page<Board> pageBoards = boardService.findBoards();
        List<Board> listBoards = pageBoards.getContent();

        return new ResponseEntity(mapper.boardsToResponses(listBoards),HttpStatus.OK);
    }
}
