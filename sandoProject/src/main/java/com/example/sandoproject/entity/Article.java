package com.example.sandoproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor //생성자 대체
@ToString //ToString 대체
@Entity
public class Article {

    @Id //엔티티의 대표값 지정
    @GeneratedValue //자동 생성 기능 추가(숫자가 자동으로 매겨짐)
    private Long id;
    @Column //title 필드 선언, DB테이블의 title열과 연결됨
    private String title;
    @Column //content 필드 선언, DB테이블의 content열과 연결됨
    private String content;

}