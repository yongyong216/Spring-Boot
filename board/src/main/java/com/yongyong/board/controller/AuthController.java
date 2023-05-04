package com.yongyong.board.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yongyong.board.dto.request.auth.SignInRequestDto;
import com.yongyong.board.dto.request.auth.SignUpRequestDto;
import com.yongyong.board.dto.response.ResponseDto;
import com.yongyong.board.dto.response.auth.SignInResponseDto;
import com.yongyong.board.service.Authservice;

@RestController
@RequestMapping("api/v2/auth")
public class AuthController {

    private Authservice authservice;

    @Autowired
    public AuthController(Authservice authservice) {
        this.authservice = authservice;
    }

    @PostMapping("sign-up")
    public ResponseEntity<ResponseDto> signUp(
            @Valid @RequestBody SignUpRequestDto requestBody) {
        ResponseEntity<ResponseDto> response = authservice.signUp(requestBody);

        return response;
    }

    @PostMapping("sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn(
            @Valid @RequestBody SignInRequestDto requestBody) {
        ResponseEntity<? super SignInResponseDto> response = authservice.signIn(requestBody);
        return response;
    }

}
