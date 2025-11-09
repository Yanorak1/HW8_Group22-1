package com.example.hw8_group22.model;

public class SearchItem {
    private String title;
    private String link;
    private String snippet;

    public SearchItem() {}

    public SearchItem(String title, String link, String snippet) {
        this.title = title;
        this.link = link;
        this.snippet = snippet;
    }

    public String getTitle() { return title; }
    public String getLink() { return link; }
    public String getSnippet() { return snippet; }

    public void setTitle(String title) { this.title = title; }
    public void setLink(String link) { this.link = link; }
    public void setSnippet(String snippet) { this.snippet = snippet; }
}