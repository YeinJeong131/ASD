package betterpedia.model;

import jakarta.persistence.*;

@Entity
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private int commentCount;
    private int editCount;
    private String badgeLevel;

    // Getters and Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public int getCommentCount() { return commentCount; }

    public void setCommentCount(int commentCount) { this.commentCount = commentCount; }

    public int getEditCount() { return editCount; }

    public void setEditCount(int editCount) { this.editCount = editCount; }

    public String getBadgeLevel() { return badgeLevel; }

    public void setBadgeLevel(String badgeLevel) { this.badgeLevel = badgeLevel; }
}