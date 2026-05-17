package oopproject.storage;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import oopproject.users.User;

public class LogEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String logId;
    private User user;
    private String username;
    private String action;
    private LocalDateTime timestamp;

    public LogEntry(User user, String action) {
        this(UUID.randomUUID().toString(), user, user == null ? null : user.getLogin(), action, LocalDateTime.now());
    }

    public LogEntry(String username, String action) {
        this(UUID.randomUUID().toString(), null, username, action, LocalDateTime.now());
    }

    public LogEntry(String logId, User user, String action, LocalDateTime timestamp) {
        this(logId, user, user == null ? null : user.getLogin(), action, timestamp);
    }

    public LogEntry(String logId, User user, String username, String action, LocalDateTime timestamp) {
        this.logId = logId;
        this.user = user;
        this.username = username;
        this.action = action;
        this.timestamp = timestamp;
    }


    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.username = user == null ? username : user.getLogin();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        String displayName = (user != null) ? user.getLogin() : username;
        if (displayName == null || displayName.isBlank()) {
            displayName = "Unknown User";
        }
        return String.format("[%s] ID: %s | User: %s | Action: %s", 
                timestamp.format(FORMATTER), logId, displayName, action);
    }
}
