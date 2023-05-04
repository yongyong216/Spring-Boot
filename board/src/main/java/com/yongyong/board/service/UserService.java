package com.yongyong.board.service;

import org.springframework.http.ResponseEntity;

import com.yongyong.board.dto.request.user.PostUserRequestDto;
import com.yongyong.board.dto.response.ResponseDto;

public interface UserService {

    public ResponseEntity<ResponseDto> postUser(PostUserRequestDto dto);

}
