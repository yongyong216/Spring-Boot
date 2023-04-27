package com.yongyong.board.entity.primaryKey;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.Data;

@Data
public class LikyPk implements Serializable { // 복합키를 쓸 클래스 생성

    @Column(name = "board_number")
    private int boardNumber;

    @Column(name = "user_email")
    private String userEmail;

}
