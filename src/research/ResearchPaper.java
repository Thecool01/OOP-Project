package research;

import java.util.Date;
import java.io.Serializable;

public class ResearchPaper implements Serializable {
    private String title;
    private String authors;
    private String journal;
    private int pages;
    private Date datePublished;
    private int citations;

    public ResearchPaper(String title, String authors, String journal, int pages, int citations) {
        this.title = title;
        this.authors = authors;
        this.journal = journal;
        this.pages = pages;
        this.citations = citations;
        this.datePublished = new Date();
    }

    public int getCitations() {
        return citations;
    }

    public int getPages() {
        return pages;
    }

    public String getTitle() {
        return title;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    @Override
    public String toString() {
        return String.format("Paper: %s | Citations: %d", title, citations);
    }
}
