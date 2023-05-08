package com.yongyong.board.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yongyong.board.common.util.CustomResponse;
import com.yongyong.board.dto.request.board.PatchBoardRequestDto;
import com.yongyong.board.dto.request.board.PostBoardRequestDto;
import com.yongyong.board.dto.request.board2.PatchBoardRequestDto2;
import com.yongyong.board.dto.request.board2.PostBoardRequestDto2;
import com.yongyong.board.dto.response.ResponseDto;
import com.yongyong.board.dto.response.board.GetBoardListResponseDto;
import com.yongyong.board.dto.response.board.GetBoardResponseDto;
import com.yongyong.board.entity.BoardEntity;
import com.yongyong.board.entity.CommentEntity;
import com.yongyong.board.entity.LikyEntity;
import com.yongyong.board.entity.UserEntity;
import com.yongyong.board.entity.resultSet.BoardListResultSet;
import com.yongyong.board.repository.BoardRepository;
import com.yongyong.board.repository.CommentRepository;
import com.yongyong.board.repository.LikyRepository;
import com.yongyong.board.repository.UserRepository;
import com.yongyong.board.service.BoardService;

@Service
public class BoardServiceImplement implements BoardService {

    private UserRepository userRepository;
    private BoardRepository boardRepository;
    private CommentRepository commentRepository;
    private LikyRepository likyRepository;

    @Autowired
    public BoardServiceImplement(
            UserRepository userRepository,
            BoardRepository boardRepository,
            CommentRepository commentRepository,
            LikyRepository likyRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
        this.likyRepository = likyRepository;

    }

    @Override
    public ResponseEntity<ResponseDto> postBoard(PostBoardRequestDto dto) {

        String boardWriterEmail = dto.getBoardWriterEmail();
        PostBoardRequestDto2 dto2 = new PostBoardRequestDto2();

        ResponseEntity<ResponseDto> response = postBoard(boardWriterEmail, dto2);

        // TODO: 성공반환
        return response;

    }

    @Override
    public ResponseEntity<ResponseDto> postBoard(String userEmail, PostBoardRequestDto2 dto) {

        ResponseDto body = null;

        try {
            // * 존재하지 않는 유저 오류 반환 //
            boolean existedUserEmail = userRepository.existsByEmail(userEmail);
            if (!existedUserEmail) {
                ResponseDto errorBody = new ResponseDto("NU", "Non-Existent User Email");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorBody);
            }

            BoardEntity boardEntity = new BoardEntity(userEmail, dto);
            boardRepository.save(boardEntity);

            body = new ResponseDto("SU", "Success");

        } catch (Exception exception) {
            // * 데이터베이스 오류 반환 //
            exception.printStackTrace();
            ResponseDto errorBody = new ResponseDto("DE", "Database Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }

        // * 성공 반환 //
        return ResponseEntity.status(HttpStatus.OK).body(body);

    }

    @Override // <? super GetBoardResponseDto> 와일드 카드의 하한을 제한. GetBoardResponseDto와 그 조상들만가능
    // 반환하는 타입이 반환되는 타입이 다르다.
    // 성공되면 GetBoardResponseDto 클래스의 반환타입으로 반환될거고
    // 실패하면 code, message 형태인 조상클래스의 반환타입으로 반환될것이다.

    public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {

        GetBoardResponseDto body = null;

        try {
            if (boardNumber == null)
                return CustomResponse.vaildationFaild();

            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null)
                return CustomResponse.notExistBoardNumber();

            int viewCount = boardEntity.getViewCount();
            boardEntity.setViewCount(++viewCount);
            boardRepository.save(boardEntity);

            String boardWriterEmail = boardEntity.getWriterEmail();
            UserEntity userEntity = userRepository.findByEmail(boardWriterEmail);
            List<CommentEntity> commentEntities = commentRepository.findByBoardNumber(boardNumber);
            List<LikyEntity> likyEntities = likyRepository.findByBoardNumber(boardNumber);

            body = new GetBoardResponseDto(boardEntity, userEntity, commentEntities, likyEntities);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);

    }

    @Override
    public ResponseEntity<? super GetBoardListResponseDto> getBoardList() {
        GetBoardListResponseDto body = null;

        try {

            List<BoardListResultSet> resultSet = boardRepository.getList();
            System.out.println(resultSet.size());
            body = new GetBoardListResponseDto(resultSet);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @Override
    public ResponseEntity<? super GetBoardListResponseDto> getBoardTop3() {
        GetBoardListResponseDto body = null;

        try {

            List<BoardListResultSet> resultSet = boardRepository.getTop3List();
            body = new GetBoardListResponseDto(resultSet);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @Override
    public ResponseEntity<ResponseDto> patchBoard(PatchBoardRequestDto dto) {

        String userEmail = dto.getUserEmail();
        PatchBoardRequestDto2 dto2 = new PatchBoardRequestDto2(dto);

        ResponseEntity<ResponseDto> response = patchBoard(userEmail, dto2);

        return response;
    }

    @Override
    public ResponseEntity<ResponseDto> patchBoard(
            String userEmail,
            PatchBoardRequestDto2 dto) {

        int boardNumber = dto.getBoardNumber();
        String boardTitle = dto.getBoardTitle();
        String boardContent = dto.getBoardContent();
        String boardImageUrl = dto.getBoardImageUrl();

        try {
            // * 존재하지 않는 게시물 번호 반환
            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null)
                return CustomResponse.notExistBoardNumber();

            // * 존재하지 않는 유저 이메일 반환
            boolean existedUserEmail = userRepository.existsByEmail(userEmail);
            if (!existedUserEmail)
                return CustomResponse.notExistUserEmail();

            // * 권한 없음
            boolean equalWriter = boardEntity.getWriterEmail().equals(userEmail);
            if (!equalWriter)
                return CustomResponse.noPermissions();

            boardEntity.setTitle(boardTitle);
            boardEntity.setContent(boardContent);
            boardEntity.setBoardImageUrl(boardImageUrl);

            boardRepository.save(boardEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return CustomResponse.success();
    }

    @Override
    public ResponseEntity<ResponseDto> deleteBoard(String userEmail, Integer boardNumber) {

        try {
            if (boardNumber == null)
                return CustomResponse.vaildationFaild();

            // * 존재하지 않는 게시물 번호 반환
            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null)
                return CustomResponse.notExistBoardNumber();

            // * 존재하지 않는 유저 이메일 반환
            boolean existedUserEmail = userRepository.existsByEmail(userEmail);
            if (!existedUserEmail)
                return CustomResponse.notExistUserEmail();

            // * 권한 없음 반환
            boolean equalWriter = boardEntity.getWriterEmail().equals(userEmail);
            if (!equalWriter)
                return CustomResponse.noPermissions();

            commentRepository.deleteByBoardNumber(boardNumber);
            likyRepository.deleteByBoardNumber(boardNumber);
            boardRepository.delete(boardEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return CustomResponse.success();

    }

}
