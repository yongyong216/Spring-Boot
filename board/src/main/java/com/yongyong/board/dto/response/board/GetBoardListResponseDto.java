package com.yongyong.board.dto.response.board;

import java.util.List;

import com.yongyong.board.dto.response.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
public class GetBoardListResponseDto extends ResponseDto {
    private List<BoardSummary> boardList;

}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class BoardSummary {
    private int boardNumber;
    private String boardTitle;
    private String boardContent;
    private String boardImageUrl;
    private String boardWriteDatetime;
    private int viewCount;
    private String boardWriterEamil;
    private String boardWriterNickname;
    private String boardWriterProfileImageUrl;
    private int commentCount;
    private int likeCount;

}