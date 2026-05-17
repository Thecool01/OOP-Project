package oopproject.storage;

import java.io.Serializable;
import java.util.Date;

public class LogEntry implements Serializable {
    private Date timestamp;
    private String username;
    private String action;

    public LogEntry(String username, String action) {
        this(new Date(), username, action);
    }

    public LogEntry(Date timestamp, String username, String action) {
        this.timestamp = timestamp;
        this.username = username;
        this.action = action;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
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

    @Override
    public String toString() {
        return timestamp + " | " + username + " | " + action;
    }
}
