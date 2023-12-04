package com.example.sandoproject.dto;

import com.example.sandoproject.entity.Article;

public class ArticleForm {
    private String title;
    private String content;

    //전송받은 제목과 내용을 필드에 저장하는 생성자 추가
    public ArticleForm(String title, String content) {
        this.title = title;
        this.content = content;
    }

    //데이터를 잘 받았는지 확인할 toString() 메서드 추가
    @Override
    public String toString() {
        return "ArticleForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
    
    //폼 데이터를 담은 DTO 객체를 엔티티로 변환 
    public Article toEntity() {
        //entity-article의 생성자 입력 양식에 맞게 작성
        return new Article(null, title, content);
    }
}
