package org.artdy.models;

public class Book {
    private String title;
    private String authorName;
    private int publication_date;
    private int person_id;

    public Book(String title, String authorName, int publicationDate) {
        this.title = title;
        this.authorName = authorName;
        this.publication_date = publicationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getPublication_date() {
        return publication_date;
    }

    public void setPublication_date(int publication_date) {
        this.publication_date = publication_date;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }
}
