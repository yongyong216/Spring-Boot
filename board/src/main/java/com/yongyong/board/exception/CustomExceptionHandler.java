package com.yongyong.board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.yongyong.board.dto.response.ResponseDto;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDto> handlerHttpMessageNotReadableException(
            HttpMessageNotReadableException exception) {
        ResponseDto responseBody = new ResponseDto("VF", "Request Parameter Validation Failde");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

}
