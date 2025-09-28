package org.example.asd.services;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.example.asd.LanguageCodes.LanCodes;
import org.example.asd.model.Article;
import org.example.asd.model.Language;
import org.example.asd.repository.ArticleRepository;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;


@Service
public class DownloadService {

    private final ArticleRepository articleRepository;
    private final LanguageService languageService;
    private final StringHttpMessageConverter stringHttpMessageConverter;

    public DownloadService(ArticleRepository articleRepository, LanguageService languageService, StringHttpMessageConverter stringHttpMessageConverter) {
        this.articleRepository = articleRepository;
        this.languageService = languageService;
        this.stringHttpMessageConverter = stringHttpMessageConverter;
    }

    public byte[] make_txt_file(Long id) {
        Article a = articleRepository.findById(id).orElse(null);
        if (a == null) return null;

        String authors = (a.getAuthors() == null || a.getAuthors().isEmpty())
                ? ""
                : String.join(", ", a.getAuthors());

        String body = (a.getBody() == null) ? "" : a.getBody();

        String text =
                "Title: " + a.getTitle() + "\n" + "Author(s): " + authors + "\n" + "Date published: " + a.getPublishDate() + "\n" +
                        "Language: " + a.getLang() + "\n\n\n" + body + "\n\n" +
                        "This is downloaded directly from Betterpedia. Make sure to reference it if you use it. Cheers :)\n";

        return text.getBytes(StandardCharsets.UTF_8);
    }

    public byte[] make_txt_file(Long id, Language target) {
        Article a = articleRepository.findById(id).orElse(null);
        if (a == null) return null;

        // Target = request ?language=... OR saved article.lan
        String toIso  = LanCodes.changeToCode(target != null ? target : a.getLang());
        // Assume original text is English (change to detector if you want)
        String srcIso = "en";

        String title = a.getTitle() == null ? "" : a.getTitle();
        String body  = a.getBody()  == null ? "" : a.getBody();

        // DEBUG: confirm what's happening
        System.out.printf("Translating? src=%s to=%s -> %s%n",
                srcIso, toIso, !srcIso.equalsIgnoreCase(toIso));

        if (!srcIso.equalsIgnoreCase(toIso)) {
            // requires a real TranslatorService implementation
            title = languageService.translate(title, srcIso, toIso);
            body  = languageService.translate(body,  srcIso, toIso);
        }

        String authors = (a.getAuthors() == null || a.getAuthors().isEmpty())
                ? ""
                : String.join(", ", a.getAuthors());

        String text =
                "Title: " + title + "\n" +
                        "Author(s): " + authors + "\n" +
                        "Date published: " + (a.getPublishDate() == null ? "" : a.getPublishDate()) + "\n" +
                        "Language: " + (target != null ? target : a.getLang()) + "\n\n" +
                        body + "\n\n" +
                        "This is downloaded directly from Betterpedia. " +
                        "Make sure to reference it if you use it. Cheers :)\n";

        return text.getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }


    public byte[] make_txt_file2(Long id, Language language) {
        Article a = articleRepository.findById(id).orElse(null);
        if (a == null) return null;
        String from = LanCodes.changeToCode(a.getLang());
        String to = LanCodes.changeToCode(language);
        String title = a.getTitle();
        String body = a.getBody();
        languageService.translate(title, from, to);
        languageService.translate(body, from, to);

        List<String> authors = a.getAuthors();
        String txt =
                "Title: " + title + "\n" +
                "Author(s): " + authors + "\n" +
                "Date published: " + a.getPublishDate() + "\n" +
                "Language: " + language + "\n\n" +
                body + "\n\n" +
                "This is downloaded directly from Betterpedia. " +
                "Make sure to reference it if you use it. Cheers :)\n";
        return txt.getBytes(StandardCharsets.UTF_8);
    }


//    public byte[] make_pdf_file(Long id) {
//        System.out.println("This called");
//        Article a = articleRepository.findById(id);
//        if (a == null) return null;
//
//        try (PDDocument doc = new PDDocument(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
//            PDPage page = new PDPage(PDRectangle.A4);
//            doc.addPage(page);
//
//            float margin = 50f, x = margin, y = page.getMediaBox().getHeight() - margin;
//            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
//                // Title
//                cs.beginText();
//                cs.setFont(PDType1Font.HELVETICA_BOLD, 18);
//                cs.newLineAtOffset(x, y);
//                cs.showText(a.title() == null ? "" : a.title());
//                cs.endText();
//
//                // Body (split by \n only)
//                y -= 30f;
//                cs.beginText();
//                cs.setFont(PDType1Font.HELVETICA, 12);
//                cs.newLineAtOffset(x, y);
//                float leading = 16f;
//                String[] lines = (a.body() == null ? "" : a.body()).split("\n", -1);
//                for (String line : lines) {
//                    cs.showText(line);
//                    cs.newLineAtOffset(0, -leading);
//                }
//                cs.endText();
//            }
//
//            System.out.println("DONE");
//            doc.save(baos);
//            return baos.toByteArray();
//        } catch (Exception e) {
//            System.out.println("ERROR");
//            throw new RuntimeException("PDF gen failed", e);
//        }
//    }

    public byte[] make_doc_file(String title, String body) throws Exception{
        return null;
    }
}
