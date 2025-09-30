package BetterPedia.services;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import BetterPedia.model.Article;
import BetterPedia.repository.ArticleRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.springframework.stereotype.Service;


@Service
public class DownloadService {

    private final ArticleRepository articleRepository;
    public DownloadService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public byte[] make_txt_file(Long id){
        Article article = articleRepository.findById(id).orElse(null);
        String title = article.getTitle();
        String author = article.getAuthor();
        LocalDate date = article.getPublishdate();
        String body = article.getBody();

        String result =
                "Title: " + title + "\n" + "Author: " + author + "\n" + "Date: " + date + "\n" + "Body: " + body + "\n"+
                "Downloaded from BetterPedia. Do not forget to reference it. Cheers :)";
        return result.getBytes();
    }

    public byte[] make_pdf_file(Long id){
        Article article = articleRepository.findById(id).orElse(null);
        String title = article.getTitle();
        String author = article.getAuthor();
        LocalDate date = article.getPublishdate();
        String body = article.getBody();

        try(PDDocument document = new PDDocument(); ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()){
            PDPage page = new PDPage();
            document.addPage(page);

            try(PDPageContentStream pageContentStream = new PDPageContentStream(document, page)){
                pageContentStream.beginText();
                pageContentStream.showText(title);
                pageContentStream.showText(body);
                pageContentStream.endText();
            }
            document.save(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
