package BetterPedia.controller;



import BetterPedia.model.Article;
import BetterPedia.repository.ArticleRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
