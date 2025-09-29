package BetterPedia.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "article")
public class Article{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "longtext") private String body;
    private String author;
    private String tags;
    private LocalDate date;

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}
    public String getTitle(){return title;}
    public void setTitle(String title){this.title = title;}
    public String getBody(){return body;}
    public void setBody(String body){this.body = body;}
    public String getAuthor(){return author;}
    public void setAuthor(String author){this.author = author;}
    public String getTags(){return tags;}
    public void setTags(String tags){this.tags = tags;}
    public LocalDate getDate(){return date;}
    public void setDate(LocalDate date){this.date = date;}



}
