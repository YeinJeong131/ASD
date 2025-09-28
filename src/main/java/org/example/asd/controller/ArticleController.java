package org.example.asd.controller;

import org.example.asd.model.Article;
import org.example.asd.services.articleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    private final articleService service;
    public ArticleController(articleService service) {this.service = service;}

    @GetMapping
    public List<Article> getAllArticles() {return service.getAllArticles();}

    @GetMapping("{id}")
    public ResponseEntity<Article> method(@PathVariable Long id){
        Article article = service.getArticle(id);
        if(article == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(article);
    }

}
