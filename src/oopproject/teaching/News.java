package oopproject.teaching;

import oopproject.users.Manager;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class News implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String newsId;
    private String title;
    private String text;
    private final LocalDateTime createdAt = LocalDateTime.now();
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

    // publishes news to the system
    public void publish() {
        System.out.println("News published: " + title);
    }

    // allows manager to edit news text
    public void edit(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News news)) return false;

        if (newsId == null || news.newsId == null) {
            return false;
        }

        return Objects.equals(newsId, news.newsId);
    }

    @Override
    public int hashCode() {
        return newsId == null ? System.identityHashCode(this) : Objects.hash(newsId);
    }

    @Override
    public String toString() {
        return "News{" +
                "newsId='" + newsId + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", author=" + author +
                '}';
    }
}
