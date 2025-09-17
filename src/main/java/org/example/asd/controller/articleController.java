package org.example.asd.controller;

import org.example.asd.model.Article;
import org.example.asd.repository.ArticleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class articleController {

    private final ArticleRepository articleRepository = new ArticleRepository();

    @GetMapping("/articles")
    public List<Article> allArticles() {
        return articleRepository.getThemAll();
    }
}
