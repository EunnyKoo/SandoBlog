package com.example.sandoproject.dto;

import com.example.sandoproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ArticleForm {
    private String title;
    private String content;

    
    //폼 데이터를 담은 DTO 객체를 엔티티로 변환 
    public Article toEntity() {
        //entity-article의 생성자 입력 양식에 맞게 작성
        return new Article(null, title, content);
    }
}
