package org.example.asd;

import org.example.asd.model.Article;
import org.example.asd.repository.ArticleRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DummyDataPreviewer {
    private final ArticleRepository repo = new ArticleRepository();

    @GetMapping("/")
    public List<Article> allArticles() {
        return repo.findAll();  //JSON view btw
    }

}
