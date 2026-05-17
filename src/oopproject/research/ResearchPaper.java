package oopproject.research;

import java.io.Serializable;
import java.util.Date;

public class ResearchPaper implements Serializable, Comparable<ResearchPaper> {
    private String paperId;
    private String title;
    private String authors;
    private String journal;
    private int pages;
    private Date publicationDate;
    private int citations;
    private String doi;
    private String publisher;

    public ResearchPaper() {
    }

    public ResearchPaper(String title, String authors, String journal, int pages,
                         Date datePublished, int citations, String doi) {
        this.title = title;
        this.authors = authors;
        this.journal = journal;
        this.pages = pages;
        this.publicationDate = datePublished;
        this.citations = citations;
        this.doi = doi;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Date getDatePublished() {
        return publicationDate;
    }

    public void setDatePublished(Date datePublished) {
        this.publicationDate = datePublished;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getCitations() {
        return citations;
    }

    public void setCitations(int citations) {
        this.citations = citations;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getLength() {
        return pages;
    }


    @Override
    public String toString() {
        return "ResearchPaper{" +
                "title='" + title + '\'' +
                ", authors='" + authors + '\'' +
                ", journal='" + journal + '\'' +
                ", citations=" + citations +
                '}';
    }
    @Override
    public int compareTo(ResearchPaper other) {
        if (other == null) {
            return 1;
        }
        return Integer.compare(other.getCitations(), this.citations);
    }
}
