package org.example.asd.repository;

import org.example.asd.model.Article;
import org.example.asd.model.Language;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ArticleRepository {
    private final Map<Long, Article> data;
    public ArticleRepository(){
        var author1 = new LinkedList<String>();
        author1.add("Davey");

        this.data = Map.of(
                1L, new Article(1L, "DummyTitle", "This is dummy text, lol",
                        author1, LocalDate.of(2025,9,3), List.of("Dummy"), Language.ENGLISH)


        );
    }
    public List<Article> findAll()    { return List.copyOf(this.data.values()); }

}
