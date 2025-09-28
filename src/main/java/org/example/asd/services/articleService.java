package org.example.asd.services;

import org.example.asd.model.Article;
import org.example.asd.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class articleService {
    private final ArticleRepository articleRepository;
    public articleService(ArticleRepository articleRepository) {this.articleRepository = articleRepository;}

    public Article getArticle(Long id) {return articleRepository.findById(id).orElse(null);}
    public List<Article> getAllArticles() {return articleRepository.findAll();}
}
