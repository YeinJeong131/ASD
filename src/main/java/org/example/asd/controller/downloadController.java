package org.example.asd.controller;
import org.example.asd.services.downloadService;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/download")
public class downloadController {
    private final downloadService service;
    public downloadController(downloadService service) {
        this.service = service;
    }

    @GetMapping("/{id}/txt")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        byte[] b = service.make_txt_file(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentLength(b.length);
        headers.setContentDisposition(ContentDisposition.attachment().filename("Article_Betterpedia.txt").build());
        return ResponseEntity.ok().headers(headers).body(b);
    }
}
