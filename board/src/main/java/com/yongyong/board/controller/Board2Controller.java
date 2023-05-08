package com.yongyong.board.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yongyong.board.dto.request.board.PatchBoardRequestDto;
import com.yongyong.board.dto.request.board2.PatchBoardRequestDto2;
import com.yongyong.board.dto.request.board2.PostBoardRequestDto2; // 2로 변경
import com.yongyong.board.dto.response.board.GetBoardResponseDto;
import com.yongyong.board.dto.response.board.GetBoardListResponseDto;
import com.yongyong.board.dto.response.ResponseDto;
import com.yongyong.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/board")
@RequiredArgsConstructor // 의존성 외부 주입 알아서 해줌
public class Board2Controller {

    private final BoardService boardService;

    // 1. 게시물 작성

    @PostMapping("") // 추가적인 path 없음
    public ResponseEntity<ResponseDto> postBoard(
            @AuthenticationPrincipal String userEamil, // 인증을 거쳐서 바로 가져오면 된다.
            @Valid @RequestBody PostBoardRequestDto2 requestBody) {
        ResponseEntity<ResponseDto> response = boardService.postBoard(userEamil, requestBody); // userEamil전달 해줘야함
        return response;
    }

    // 2. 특정게시물 조회
    @GetMapping("/{boardNumber}")
    public ResponseEntity<? super GetBoardResponseDto> getBoard(
            @PathVariable("boardNumber") Integer boardNumber) {
        ResponseEntity<? super GetBoardResponseDto> response = boardService.getBoard(boardNumber);
        return response;
    }

    // 3. 게시물 목록 조회
    @GetMapping("/list")
    public ResponseEntity<? super GetBoardListResponseDto> getBoar() {
        ResponseEntity<? super GetBoardListResponseDto> response = boardService.getBoardList();
        return response;
    }

    // 4. top3 게시물 목록 조회
    @GetMapping("/top3")
    public ResponseEntity<? super GetBoardListResponseDto> getBoardtop3() {
        ResponseEntity<? super GetBoardListResponseDto> response = boardService.getBoardTop3();
        return response;
    }

    // 5. 특정 게시물 수정
    @PatchMapping("")
    public ResponseEntity<ResponseDto> patchBoard(
            @AuthenticationPrincipal String userEmail,
            @Valid @RequestBody PatchBoardRequestDto2 requestBody) {
        ResponseEntity<ResponseDto> response = boardService.patchBoard(userEmail, requestBody);
        return response;
    }

    // 6. 특정 게시물 삭제
    @DeleteMapping("/{boardNumber}")
    public ResponseEntity<ResponseDto> deleteBoard(
            @PathVariable("userEmail") String userEmail,
            @PathVariable("boardNumber") Integer boardNumber) {
        ResponseEntity<ResponseDto> response = boardService.deleteBoard(userEmail, boardNumber);
        return response;

    }
}