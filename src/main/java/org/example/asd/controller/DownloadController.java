package org.example.asd.controller;
import org.example.asd.model.Language;
import org.example.asd.services.DownloadService;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/download")
public class DownloadController {
    private final DownloadService service;
    public DownloadController(DownloadService service) {
        this.service = service;
    }

//    @GetMapping("{id}/pdf")
//    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {
//        System.out.println("ID is: " + id);
//        byte[] pdf = service.make_pdf_file(id);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentLength(pdf.length);
//        headers.setContentDisposition(ContentDisposition.attachment().filename("Article_Betterpedia.pdf").build());
//        return ResponseEntity.ok().headers(headers).body(pdf);
//    }


    @GetMapping("{id}/txt")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id, @RequestParam(required=false) Language language) {
        System.out.println("ID is: " + id);
        byte[] b = service.make_txt_file(id,language);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentLength(b.length);
        headers.setContentDisposition(ContentDisposition.attachment().filename("Article_Betterpedia.txt").build());
        return ResponseEntity.ok().headers(headers).body(b);
    }



}
