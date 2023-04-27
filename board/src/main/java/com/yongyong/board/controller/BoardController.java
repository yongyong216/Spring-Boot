package com.yongyong.board.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yongyong.board.dto.request.board.PatchBoardRequestDto;
import com.yongyong.board.dto.request.board.PostBoardRequestDto;
import com.yongyong.board.dto.response.ResponseDto;
import com.yongyong.board.service.BoardService;

import com.yongyong.board.dto.response.board.GetBoardResponseDto;
import com.yongyong.board.dto.response.board.GetBoardListResponseDto;

@RestController
@RequestMapping("/api/v1/board")
public class BoardController {
    private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 1. 게시물 작성

    @PostMapping("") // 추가적인 path 없음
    public ResponseEntity<ResponseDto> postBoard(
            @Valid @RequestBody PostBoardRequestDto requestBody) {
        ResponseEntity<ResponseDto> response = boardService.postBoard(requestBody);
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
            @Valid @RequestBody PatchBoardRequestDto requestBody) {
        ResponseEntity<ResponseDto> response = boardService.patchBoard(requestBody);
        return response;
    }

    // 6. 특정 게시물 삭제
    @DeleteMapping("/{userEmail}/{boardNumber}")
    public ResponseEntity<ResponseDto> deleteBoard(
            @PathVariable("userEmail") String userEmail,
            @PathVariable("boardNumber") Integer boardNumber) {
        ResponseEntity<ResponseDto> response = boardService.deleteBoard(userEmail, boardNumber);
        return response;

    }
}