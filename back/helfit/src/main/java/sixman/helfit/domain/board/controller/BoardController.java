package sixman.helfit.domain.board.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sixman.helfit.domain.board.mapper.BoardMapper;
import sixman.helfit.domain.board.service.BoardService;
import sixman.helfit.response.ApiResponse;

@RestController
@RequestMapping("/api/v1/board")
public class BoardController {
    private final BoardMapper boardMapper;
    private final BoardService boardService;

    public BoardController(BoardMapper boardMapper, BoardService boardService) {
        this.boardMapper = boardMapper;
        this.boardService = boardService;
    }

//    @GetMapping
//    public ApiResponse getBoards(){
//
//    }
}
