package org.example.asd.services;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
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

    public byte[] make_pdf_file(Long id) {
        System.out.println("This called");
        Article a = articleRepository.findById(id);
        if (a == null) return null;

        try (PDDocument doc = new PDDocument(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            float margin = 50f, x = margin, y = page.getMediaBox().getHeight() - margin;
            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                // Title
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 18);
                cs.newLineAtOffset(x, y);
                cs.showText(a.title() == null ? "" : a.title());
                cs.endText();

                // Body (split by \n only)
                y -= 30f;
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA, 12);
                cs.newLineAtOffset(x, y);
                float leading = 16f;
                String[] lines = (a.body() == null ? "" : a.body()).split("\n", -1);
                for (String line : lines) {
                    cs.showText(line);
                    cs.newLineAtOffset(0, -leading);
                }
                cs.endText();
            }

            System.out.println("DONE");
            doc.save(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            System.out.println("ERROR");
            throw new RuntimeException("PDF gen failed", e);
        }
    }

    public byte[] make_doc_file(String title, String body) throws Exception{
        return null;
    }
}
