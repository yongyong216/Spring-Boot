package com.yongyong.board.service;

import org.springframework.http.ResponseEntity;

import com.yongyong.board.dto.request.auth.SignInRequestDto;
import com.yongyong.board.dto.request.auth.SignUpRequestDto;
import com.yongyong.board.dto.response.ResponseDto;
import com.yongyong.board.dto.response.auth.SignInResponseDto;

public interface Authservice {
    public ResponseEntity<ResponseDto> signUp(SignUpRequestDto dto);

    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
}
