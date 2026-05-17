package oopproject.services;

import java.util.ArrayList;
import java.util.List;
import oopproject.storage.LogEntry;
import oopproject.users.User;

public class LogService {
    private final List<LogEntry> logs;

    public LogService() {
        this.logs = new ArrayList<>();
    }

    public void addLog(User user, String action) {
        LogEntry newEntry = new LogEntry(user, action);
        this.logs.add(newEntry);
    }

    public List<LogEntry> getLogs() {
        return new ArrayList<>(this.logs);
    }
}