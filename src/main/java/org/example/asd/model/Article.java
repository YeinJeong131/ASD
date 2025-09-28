package org.example.asd.model;

import jakarta.persistence.*;
import org.example.asd.model.Language;
import org.example.asd.model.StringListJsonConverter;

import java.time.LocalDate;
import java.util.*;

@Entity
public class Article {
    @Id
    @GeneratedValue
    Long id;
    String title;
    @Column(columnDefinition="TEXT") String body;
    LocalDate publishDate;
    @Enumerated(EnumType.STRING)
    Language lan;

    @Convert(converter = StringListJsonConverter.class)
    @Column(columnDefinition = "json", nullable = false)
    private List<String> authors = new ArrayList<>();

    @Convert(converter = StringListJsonConverter.class)
    @Column(columnDefinition = "json", nullable = false)
    private List<String> tags = new ArrayList<>();


    public Article() {

    }

    public Article(Long id, String title, LocalDate publishDate, Language lan, List<String> authors, List<String> tags) {
        this.id = id;
        this.title = title;
        this.publishDate = publishDate;
        this.lan = lan;
        this.authors = authors;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getBody(){
        return body;
    }
    public void setBody(String body){
        this.body = body;
    }
    public LocalDate getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }
    public Language getLang() {
        return lan;
    }
    public void setLang(Language lan) {
        this.lan = lan;
    }
    public List<String> getAuthors() {
        return authors;
    }
    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }
    public void setAuthors(String author) {
        this.authors.add(author);
    }
    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public void setTags(String tag) {
        this.tags.add(tag);
    }

}
