package com.example.android.booklisting;

/**
 * Created by Jeffrey on 21-6-2017.
 */

public class Book {
    private String title;
    private String[] authors;
    private int publishedYear;
    private String description;
    private String url;

    public Book(String title, String[] authors, int publishedYear, String description, String url) {
        this.title = title;
        this.authors = authors;
        this.publishedYear = publishedYear;
        this.description = description;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String[] getAuthors() {
        return authors;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}
