package sixman.helfit.domain.board.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sixman.helfit.domain.board.dto.BoardDto;
import sixman.helfit.domain.board.entity.Board;
import sixman.helfit.domain.board.mapper.BoardMapper;
import sixman.helfit.domain.board.service.BoardService;
import sixman.helfit.domain.file.service.FileService;
import sixman.helfit.domain.like.entity.Like;
import sixman.helfit.global.annotations.CurrentUser;
import sixman.helfit.response.ApiResponse;
import sixman.helfit.security.entity.UserPrincipal;
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
    private final FileService fileService;

    public BoardController(BoardMapper mapper, BoardService boardService, FileService fileService) {
        this.mapper = mapper;
        this.boardService = boardService;
        this.fileService = fileService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{category-id}/{user-id}")
    public ResponseEntity postBoards(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                     @Positive @PathVariable ("category-id") long categoryId,
                                     @Positive @PathVariable ("user-id") long userId,
                                     @Valid @RequestBody BoardDto.Post requestBody){
        Board board = boardService.createBoard(mapper.boardPostToBoard(requestBody,categoryId,userId),userPrincipal);
        URI location = UriUtil.createUri(BOARD_DEFAULT_URL,board.getBoardId());

        return ResponseEntity.created(location).body(ApiResponse.created());
    }
//    @PostMapping("/image")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<?> updateBoardImage(
//            @RequestParam MultipartFile[] multipartFiles
//    ) throws Exception {
//        List<String> imagePath = fileService.uploadFiles(multipartFiles);
//
//        return ResponseEntity.ok().body(ApiResponse.ok("resource", imagePath));
//    }
//
//    @DeleteMapping("/image/{board-id}")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<?> updateUserProfileImage( @Positive @PathVariable ("board-id") Long boardId) {
//        boardService.updateBoardProfileImage(boardId, null);
//
//        return ResponseEntity.ok().body(ApiResponse.noContent());
//    }

    @GetMapping("/{category-id}/{board-id}")
    public ResponseEntity getBoard(@Positive @PathVariable ("category-id") Long categoryId,
                                   @Positive @PathVariable ("board-id") Long boardId) {
        Board board = boardService.findBoardByAllId(categoryId, boardId);

        return new ResponseEntity(mapper.boardToResponse(board),HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity getBoards(@Positive @RequestParam int page) {
        Page<Board> pageBoards = boardService.findBoards(page-1);
        List<Board> listBoards = pageBoards.getContent();

        return new ResponseEntity(mapper.boardsToResponses(listBoards),HttpStatus.OK);
    }
    @GetMapping("{category-id}")
    public ResponseEntity getBoardByCategory(@Positive @PathVariable ("category-id") Long categoryId,
                                             @Positive @RequestParam int page) {
        Page<Board> pageBoards = boardService.findBoards(categoryId,page-1);
        List<Board> listBoards = pageBoards.getContent();
        Long boardsCount = boardService.findBoardsCount(categoryId);

        BoardDto.BoardListResponse response = new BoardDto.BoardListResponse(mapper.boardsToResponses(listBoards),boardsCount);
        return new ResponseEntity(response,HttpStatus.OK);
    }
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("{category-id}/{board-id}")
    public ResponseEntity patchBoard(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                     @Positive @PathVariable ("category-id") Long categoryId,
                                     @Positive @PathVariable ("board-id") Long boardId,
                                     @Valid @RequestBody BoardDto.Patch requestBody) {
        Board board = boardService.updateBoard(mapper.boardPatchToBoard(requestBody,categoryId)
                ,categoryId,boardId,userPrincipal);


        return new ResponseEntity(mapper.boardToResponse(board),HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("{category-id}/{board-id}")
    public ResponseEntity deleteBoard(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                      @Positive @PathVariable ("category-id") Long categoryId,
                                      @Positive @PathVariable ("board-id") Long boardId) {
        boardService.deleteBoard(categoryId,boardId,userPrincipal);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/view/{board-id}")
    public ResponseEntity addViewBoard(@Positive @PathVariable ("board-id") Long boardId){
        boardService.addView(boardId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/view/{board-id}")
    public ResponseEntity getViewBoard(@Positive @PathVariable ("board-id") Long boardId) {
        Board board = boardService.findBoardById(boardId);
        return new ResponseEntity(mapper.boardToBoardView(board),HttpStatus.OK);
    }

    @PostMapping("/likes/{board-id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity postLike(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                   @PathVariable ("board-id") @Positive Long boardId) {
        Like like = boardService.createLike(userPrincipal,boardId);
        URI location = UriUtil.createUri(BOARD_DEFAULT_URL +"/likes",like.getLikeId());

        return ResponseEntity.created(location).body(ApiResponse.created());
    }

    @GetMapping("/likes/{board-id}")
    public ResponseEntity getBoardLikes(@PathVariable ("board-id") @Positive Long boardId) {
        return new ResponseEntity(boardService.getBoardLikes(boardId),HttpStatus.OK);
    }

    @DeleteMapping("/likes/{board-id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity deleteLike(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                   @PathVariable ("board-id") @Positive Long boardId) {


        return new ResponseEntity(HttpStatus.OK);
    }
}
