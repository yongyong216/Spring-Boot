package com.yongyong.board.service;

import org.springframework.http.ResponseEntity;

import com.yongyong.board.dto.request.board.PatchBoardRequestDto;
import com.yongyong.board.dto.request.board.PostBoardRequestDto;
import com.yongyong.board.dto.response.ResponseDto;

public interface BoardService {
    public ResponseEntity<ResponseDto> postBoard(PostBoardRequestDto dto);

    public ResponseEntity<? super com.yongyong.board.dto.response.board.GetBoardResponseDto> getBoard(
            Integer boardNumber);

    public ResponseEntity<? super com.yongyong.board.dto.response.board.GetBoardListResponseDto> getBoardList();

    public ResponseEntity<? super com.yongyong.board.dto.response.board.GetBoardListResponseDto> getBoardTop3();

    public ResponseEntity<ResponseDto> patchBoard(PatchBoardRequestDto dto);

    public ResponseEntity<ResponseDto> deleteBoard(String userEmail, Integer boardNumber);
}
