package com.yongyong.firstproject.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Data
public class ExampleDto {
    @NotBlank // @NotNull을 했을 때 build.gradle 파일에서
              // implementation 'org.springframework.boot:spring-boot-starter-validation'추가해주기
              // 저장 누르면 아래에 뜨는거 yes누르면 됨!
    private String parameter1;
    @Length(min = 0, max = 20)
    private String parameter2;
    private String parameter3;
}
