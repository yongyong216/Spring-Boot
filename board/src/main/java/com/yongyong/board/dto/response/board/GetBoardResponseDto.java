package com.yongyong.board.dto.response.board;

import java.util.List;

import com.yongyong.board.dto.response.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetBoardResponseDto extends ResponseDto {
    private int boardNumber;
    private String boardTitle;
    private String boardContent;
    private String boardImageUrl;
    private String boardWriteDatetime;
    private int viewCount;
    private String boardWriterEamil;
    private String boardWriterNickname;
    private String boardWriterProfileImageUrl;
    private List<Comment> commentList;
    private List<Liky> likeList;

}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Comment {
    private int commentNumber;
    private int boardNumber;
    private String commentWriterEmail;
    private String commentContent;
    private String commentWriteNickname;
    private String commentWriteProfileImageUrl;
    private String commentWriteDatetime;
}

@Getter
@Setter
class Liky {
    private int boardNumber;
    private String userEmail;
    private String userNickname;
    private String userProfileImeageUrl;
}
