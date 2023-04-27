package com.yongyong.board.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yongyong.board.dto.request.board.PatchBoardRequestDto;
import com.yongyong.board.dto.request.board.PostBoardRequestDto;
import com.yongyong.board.dto.response.ResponseDto;
import com.yongyong.board.entity.BoardEntity;
import com.yongyong.board.repository.BoardRepository;
import com.yongyong.board.repository.UserRepository;
import com.yongyong.board.service.BoardService;
import com.yongyong.board.dto.response.board.GetBoardListResponseDto;
import com.yongyong.board.dto.response.board.GetBoardResponseDto;

@Service
public class BoardServiceImplement implements BoardService {
    private UserRepository userRepository;
    private BoardRepository boardRepository;

    @Autowired
    public BoardServiceImplement(
            UserRepository userRepository,
            BoardRepository boardRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
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

        throw new UnsupportedOperationException("Unimplemented method 'getBoard'");
    }

    @Override
    public ResponseEntity<? super GetBoardListResponseDto> getBoardList() {

        throw new UnsupportedOperationException("Unimplemented method 'getBoardList'");
    }

    @Override
    public ResponseEntity<? super GetBoardListResponseDto> getBoardTop3() {

        throw new UnsupportedOperationException("Unimplemented method 'getBoardTop3'");
    }

    @Override
    public ResponseEntity<ResponseDto> patchBoard(PatchBoardRequestDto dto) {

        throw new UnsupportedOperationException("Unimplemented method 'patchBoard'");
    }

    @Override
    public ResponseEntity<ResponseDto> deleteBoard(String userEmail, Integer boardNumber) {

        throw new UnsupportedOperationException("Unimplemented method 'deleteBoard'");
    }

}