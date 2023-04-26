package com.yongyong.firstproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
public class ExampleResponseDto {
    private String data1;
    private String data2;
    private String data3;
}
