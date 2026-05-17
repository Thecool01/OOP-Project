package oopproject.teaching;

import oopproject.users.Manager;

import java.io.Serializable;
import java.time.LocalDateTime;

public class News implements Serializable {
    private String newsId;
    private String title;
    private String text;
    private LocalDateTime createdAt = LocalDateTime.now();
    private Manager author;

    public News() {
    }

    public News(String newsId, String title, String text, Manager author) {
        this.newsId = newsId;
        this.title = title;
        this.text = text;
        this.author = author;
    }

    public String getNewsId() {
        return newsId;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Manager getAuthor() {
        return author;
    }
}
