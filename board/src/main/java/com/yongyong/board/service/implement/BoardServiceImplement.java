package com.yongyong.board.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.stereotype.Service;

import com.yongyong.board.common.util.CustomResponse;
import com.yongyong.board.dto.request.board.PatchBoardRequestDto;
import com.yongyong.board.dto.request.board.PostBoardRequestDto;
import com.yongyong.board.dto.response.ResponseDto;
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

import net.bytebuddy.asm.Advice.Return;

import com.yongyong.board.dto.response.board.GetBoardListResponseDto;
import com.yongyong.board.dto.response.board.GetBoardResponseDto;

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
        ResponseDto body = null;

        String boardWriterEmail = dto.getBoardWriterEmail();

        try {
            // TODO 존재하지 않는 유저 오류 반환
            boolean existedUserEmail = userRepository.existsByEmail(boardWriterEmail);
            if (!existedUserEmail) {
                ResponseDto errorbody = new ResponseDto("NU", "Non-Existent User Email");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorbody);
            }
            BoardEntity boardEntity = new BoardEntity(dto);
            boardRepository.save(boardEntity);

            body = new ResponseDto("Su", "Success");

        } catch (Exception exception) {
            // TODO : 데이터 베이스 오류반환
            exception.printStackTrace();
            ResponseDto errorbody = new ResponseDto("DE", "Database Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorbody);
        }

        // TODO: 성공반환
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

        int boardNumber = dto.getBoardNumber();
        String userEmail = dto.getUserEmail();
        String boardTitle = dto.getBoardTitle();
        String boardContent = dto.getBoardContent();
        String boardImageUrl = dto.getBoardImageUrl();

        try {

            // TODO: 존재하지 않는 게시물 번호 반환
            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null)
                return CustomResponse.notExistBoardNumber();

            // TODO: 존재하지 않는 유저 이메일 반환
            boolean existedUserEmail = userRepository.existsByEmail(userEmail);
            if (!existedUserEmail)
                return CustomResponse.notExistUserEmail();

            // TODO: 권한 없음
            boolean equalsWriter = boardEntity.getWriterEmail().equals(userEmail);
            if (!equalsWriter)
                return CustomResponse.noPermissions();

            boardEntity.setTitle(boardTitle);
            boardEntity.setContent(boardContent);
            boardEntity.setBoardImageUrl(boardImageUrl);

            boardRepository.save(boardEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();

        }

        throw new UnsupportedOperationException("Unimplemented method 'patchBoard'");
    }

    @Override
    public ResponseEntity<ResponseDto> deleteBoard(String userEmail, Integer boardNumber) {

        ResponseDto body = null;

        try {
            if (boardNumber == null)
                return CustomResponse.vaildationFaild();

            // TODO: 존재하지 않는 게시물 번호 반환
            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null)
                return CustomResponse.notExistBoardNumber();

            // TODO: 존재하지 않는 유저 이메일 반환
            boolean existedUserEmail = userRepository.existsByEmail(userEmail);
            if (!existedUserEmail)
                return CustomResponse.notExistUserEmail();

            // TODO: 권한 없음 반환
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
