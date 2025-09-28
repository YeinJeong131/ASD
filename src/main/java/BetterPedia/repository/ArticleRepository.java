package BetterPedia.repository;

import BetterPedia.model.Article;
import BetterPedia.model.Language;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ArticleRepository {
    private final List<Article> data = Arrays.asList(
            new Article(1L, "DummyTitle", "This is dummy text, lol", Arrays.asList("Davey", "Other group members: Group 10"), LocalDate.of(2025,9,3), List.of("Dummy"), Language.ENGLISH)
    );

    public Article findById(Long id) {
        return this.data.stream().filter(a -> a.id().equals(id)).findFirst().orElse(null);
    }
    public List<Article> getThemAll(){
        return this.data;
    }

}
