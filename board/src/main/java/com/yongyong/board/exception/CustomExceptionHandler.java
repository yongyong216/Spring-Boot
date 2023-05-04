package com.yongyong.board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.yongyong.board.common.util.CustomResponse;
import com.yongyong.board.dto.response.ResponseDto;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDto> handlerHttpMessageNotReadableException(
            HttpMessageNotReadableException exception) {
        // 결과가 ResponseEntity로 반환됨 컨트롤러이기 때문에 ... 그리고 처리하고자 하는 메세지를 매게변수로 받아온다.
        return CustomResponse.vaildationFaild();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> handlerMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {

        return CustomResponse.vaildationFaild();
    }
}
