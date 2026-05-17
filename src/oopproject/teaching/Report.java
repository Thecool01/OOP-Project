package oopproject.teaching;

import oopproject.enums.ReportType;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Report implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String reportId;
    private ReportType type;
    private final LocalDateTime createdAt = LocalDateTime.now();
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

    // prints report content to console
    public void print() {
        System.out.println(exportAsText());
    }

    // converts report data to readable text
    public String exportAsText() {
        return "[" + type + "] " + content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Report report)) return false;
        return Objects.equals(reportId, report.reportId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportId);
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportId='" + reportId + '\'' +
                ", type=" + type +
                ", createdAt=" + createdAt +
                ", content='" + content + '\'' +
                '}';
    }
}
