package org.example.asd.services;
import java.io.File;
import java.nio.charset.StandardCharsets;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.example.asd.model.Article;
import org.example.asd.repository.ArticleRepository;
import org.springframework.stereotype.Service;


@Service
public class downloadService {

    private final ArticleRepository articleRepository = new ArticleRepository();

    public byte[] make_txt_file(Long id){
        Article a = articleRepository.findById(id);
        if (a==null) return null;

        String returnThing = "Title: " + a.title() + "\n"+
                "Author(s): " + String.join(", ", a.author()) + "\n"+
                "Date published: " + a.publishDate() + "\n\n"+
                a.body() + "\n\n\n\n"+
                "This is downloaded directly from Betterpedia. make sure to refernfce if you wanna use it in anyhting. Cheers:)";

        return returnThing.getBytes(StandardCharsets.UTF_8);
    }

    public byte[] make_pdf_file(String title, String body) throws Exception{
        return null;
    }

    public byte[] make_doc_file(String title, String body) throws Exception{
        return null;
    }
}
