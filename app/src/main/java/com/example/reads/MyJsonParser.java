package com.example.reads;

public class MyJsonParser {

    public String title;
    public String subtitle;
    public String[] authors;
    public String publisher;
    public String publishedDate;
    public String id;

    public MyJsonParser(String id, String title, String subtitle, String[] authors, String publisher, String publishedDate) {
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.id = id;
    }
}
