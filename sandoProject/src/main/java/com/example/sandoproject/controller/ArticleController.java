package com.example.sandoproject.controller;

import com.example.sandoproject.dto.ArticleForm;
import com.example.sandoproject.entity.Article;
import com.example.sandoproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j //로깅 기능을 위한 어노테이션 추가
@Controller
public class ArticleController {
    @Autowired //스프링 부트가 미리 생성해 놓은 repository 객체 주입(=DI: 의존성 주입)
    private ArticleRepository articleRepository;
    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
//        System.out.println(form.toString());
        log.info(form.toString());
        //1. DTO를 엔티티로 변환
        Article article = form.toEntity();
        log.info(article.toString());
//        System.out.println(article.toString());
        //2. 리파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
//        System.out.println(saved.toString());
        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) { //@PathVariable은 URL 요청으로 들어온 전달값을 컨트롤러의 매개변수로 가져옴
        //articleEntity에 담긴 데이터를 모델에 등록한다 -> MVC 패턴에 따라 조회한 데이터를 뷰 페이지에서 사용하기 위함
        log.info("id=" + id); //id를 잘 받았는지 확인하는 로그
        //1. id를 조회해 데이터 가져오기
            //Optional<Article> articleEntity = articleRepository.findById(id);
            //id 값으로 데이터를 찾을 떄 해당 id 값이 없으면 null을 반환한다.
        Article articleEntity = articleRepository.findById(id).orElse(null);
        //2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);
        //3. 뷰 페이지 반환하기/설정하기
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        //1. 모든 데이터 가져오기
        //캐스팅 or 형변환: 데이터 타입을 변환하는 것
        ArrayList<Article> articleEntityList = articleRepository.findAll();
        //2. 모델에 데이터 등록하기
        model.addAttribute("articleList", articleEntityList);
        //3. 뷰 페이지 설정하기
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        //수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        //모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);
        //뷰 페이지 설정하기
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) { //매개변수로 DTO 가져오기
        log.info(form.toString());
        //1. DTO를 entity로 변환하기
        Article articleEntity = form.toEntity();

        //2. 엔티티를 DB에 저장하기
        //2-1. DB에서 기존 데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        //2-2. 기존 데이터 값을 갱신하기
        if(target != null){
            articleRepository.save(articleEntity); //엔티티를 DB에 저장(갱신)
        }

        //3. 수정 결과 페이지로 리다이렉트 하기
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) { //id를 매개변수로 가져오기
        log.info("삭제 요청이 들어왔습니다");
        //1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        //2. 대상 엔티티 삭제하기
        if(target != null) { //삭제할 대상이 있는지 확인
            articleRepository.delete(target); //delete()메서드로 삭제
            rttr.addFlashAttribute("msg", "삭제됐습니다!");
        }
        //3. 결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }
}

