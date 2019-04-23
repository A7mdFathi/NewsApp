package com.example.android.newsapp;

/**
 * Created by ahmed on 6/21/18.
 */

public class News {
    private String nTitle;
    private String nAuthor;
    private String nCategory;
    private String nPublishDate;
    private String nUrl;

    public News(String nTitle, String nAuthor, String nCategory, String nPublishDate, String nUrl) {
        this.nTitle = nTitle;
        this.nAuthor = nAuthor;
        this.nCategory = nCategory;
        this.nPublishDate = nPublishDate;
        this.nUrl = nUrl;
    }

    public String getnCategory() { return nCategory; }

    public String getnTitle() {
        return nTitle;
    }

    public String getnAuthor() {
        return nAuthor;
    }

    public String getnPublishDate() {
        return nPublishDate;
    }

    public String getnUrl() {
        return nUrl;
    }
}