package org.example.asd.model;

import java.time.LocalDate;
import java.util.List;

public record Article(
    Long id,
    String title,
    String body,
    List<String> author,
    LocalDate publishDate,
    List<String> tags,
    Language language
)


{}
