package com.yongyong.board.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Board")
@Table(name = "Board")
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 값을 자동으로 생성
    private int boardNumber;
    private String weiterEmail;
    private String title;
    private String content;
    private String boardImageUrl;
    private String writeDatetime;
    private int viewCount;

}
