package oopproject.teaching;

import oopproject.enums.ReportType;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Report implements Serializable {
    private String reportId;
    private ReportType type;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String content;

    public Report() {
    }

    public Report(String reportId, ReportType type, String content) {
        this.reportId = reportId;
        this.type = type;
        this.content = content;
    }

    public String getReportId() {
        return reportId;
    }

    public ReportType getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getContent() {
        return content;
    }

    public void print() {
        System.out.println(exportAsText());
    }

    public String exportAsText() {
        return "[" + type + "] " + content;
    }
}
