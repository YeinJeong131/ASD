package betterpedia.controller;

import betterpedia.model.Article;
import betterpedia.repository.ArticleRepository;
import betterpedia.services.DownloadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DownloadControllerTest {
    @Autowired
    private DownloadService downloadService;
    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void isFileReturned(){
        Article article = new Article();
        article.setTitle("Title");
        article.setBody("Body");
        articleRepository.save(article);

        byte[] results = downloadService.make_txt_file(article.getId());
        assertTrue(results.length > 0, "Oops. Empty file");
    }
    @Test
    public void noArticle(){
        DownloadService downloadService2 = new DownloadService(articleRepository);
        byte[] bytes = downloadService2.make_txt_file(-1L);
        assertNull(bytes,"How did u return an article with id -1 :)");
    }
}
