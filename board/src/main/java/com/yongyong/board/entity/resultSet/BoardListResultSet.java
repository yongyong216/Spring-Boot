package com.yongyong.board.entity.resultSet;

import java.security.PublicKey;

public interface BoardListResultSet {
    public int getBoardNumber();

    public String getBoardTitle();

    public String getBoardContent();

    public String getBoardImageUrl();

    public String getBoardWriterDatetime();

    public int getViewCount();

    public String getBoardWriterEamil();

    public String getBoardWriterNickname();

    public String getBoardWriterProfileImageUrl();

    public int getCommentCount();

    public int getLikeCount();
}
