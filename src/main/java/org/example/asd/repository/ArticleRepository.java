package org.example.asd.repository;

import org.example.asd.model.Article;
import org.example.asd.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findByTitle(String title);

    List<Article> findAllByTitle(String title);

    List<Article> findAllByTitleContainingIgnoreCase(String q);
}

