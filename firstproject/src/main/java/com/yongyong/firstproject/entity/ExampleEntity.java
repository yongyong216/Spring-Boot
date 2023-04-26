package com.yongyong.firstproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Example") // name을 설정하면 이런 이름으로 사용하겠다 라고 하는것임.
@Table(name = "Example")
public class ExampleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성과 관련 (전략)
    @Column(name = "example_column1", nullable = false, unique = true)
    private int pk;
    private String exampleColumn2;
    private boolean exampleColumn3;
}
